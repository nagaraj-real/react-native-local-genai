import React, { useEffect, useState } from 'react';
import {
  ActivityIndicator,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native';
import { chatWithLLM, setModelPath } from 'react-native-local-genai';

import Markdown from 'react-native-markdown-display';

function App(): React.JSX.Element {
  const [query, setQuery] = useState('');
  const [messages, setMessages] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setModelPath('/data/local/tmp/llm/gemma-2b-it-gpu-int4.bin');
  }, []);

  const sendMessage = async () => {
    console.log('Calling prompt!');
    setIsLoading(true);
    try {
      const response = await chatWithLLM(query);
      console.log(response);
      setIsLoading(false);
      setMessages((prevMessages) => [...prevMessages, response]);
    } catch (e) {
      console.error(e);
    }
  };

  const onChangeQuery = (text: string) => {
    setQuery(text);
  };

  return (
    <View style={styles.container}>
      <ScrollView style={styles.messagesContainer}>
        {messages.map((msg, index) => (
          <View key={index} style={styles.messageBubble}>
            <Markdown
              style={{
                heading1: { color: 'purple' },
                code_block: { color: '#111', fontSize: 14 },
                fence: { color: '#111', fontSize: 14 },
                code_inline: { color: '#111', fontSize: 14 },
              }}
            >
              {msg}
            </Markdown>
          </View>
        ))}
      </ScrollView>

      {isLoading && (
        <ActivityIndicator
          style={styles.loadingIndicator}
          size="large"
          color="#007bff"
        />
      )}
      <View style={styles.inputContainer}>
        <TextInput
          style={styles.input}
          value={query}
          onChangeText={onChangeQuery}
          placeholder="Type your message..."
          onSubmitEditing={sendMessage}
          returnKeyType="send"
        />
        <TouchableOpacity style={styles.sendButton} onPress={sendMessage}>
          <Text style={styles.sendButtonText}>Send</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
  messagesContainer: {
    flex: 1,
    paddingHorizontal: 10,
    paddingTop: 10,
  },
  messageBubble: {
    borderRadius: 10,
    paddingHorizontal: 15,
    paddingVertical: 10,
    marginBottom: 10,
    maxWidth: '70%',
    color: '#111',
  },
  messageText: {
    fontSize: 16,
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 10,
    paddingVertical: 10,
    borderTopWidth: 1,
    borderTopColor: '#ccc',
    marginTop: 10,
  },
  input: {
    flex: 1,
    height: 40,
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 20,
    paddingHorizontal: 15,
    marginRight: 10,
    color: '#fff',
    backgroundColor: '#383131',
  },
  sendButton: {
    backgroundColor: '#007bff',
    borderRadius: 20,
    paddingVertical: 10,
    paddingHorizontal: 20,
  },
  sendButtonText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 16,
  },
  loadingIndicator: {
    marginTop: 10,
    color: '#111',
  },
});

export default App;
