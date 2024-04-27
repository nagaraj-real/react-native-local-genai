# react-native-local-genai

Local generative ai capabilities using google mediapipe.
Non-blocking local LLM inference using quantized models.

Supports only android now. IOS support coming soon !

## Prerequisite

Download the preferred model in android.
Checkout out models supported here - https://developers.google.com/mediapipe/solutions/genai/llm_inference#models

Use the same path as given below.

```sh
adb shell rm -r /data/local/tmp/llm/
adb shell mkdir -p /data/local/tmp/llm/
adb push output_path /data/local/tmp/llm/model_version.bin
```

## Installation

```sh
npm install react-native-local-genai
```

## Usage

```js
import { chatWithLLM } from 'react-native-local-genai';

// ...

const response = await chatWithLLM("hello !");
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)

Made using mediapipe under the hood (https://github.com/google/mediapipe)