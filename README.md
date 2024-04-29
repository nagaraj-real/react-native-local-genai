# React Native Local Gen AI

Local Generative AI capabilities using google mediapipe.

Non-blocking local LLM inference using quantized models.

Supports only Android now. iOS support coming soon !

## Pre-requisites

Generative AI models are large in size and should not be bundled in apk. 
Ideally in production, the model must be downloaded from server upon user request.
For development, we manually download the preferred model to PC and push to an android debugging device (adb).

Gemma models compatible with mediapipe format can be downloaded directly from [Kaggle](https://www.kaggle.com/) (no conversion needed)

```bash
  # Export your Kaggle username and API key
  # export KAGGLE_USERNAME=
  # export KAGGLE_KEY=

  curl -L -u $KAGGLE_USERNAME:$KAGGLE_KEY\
  -o ~/Downloads/model.tar.gz\
  https://www.kaggle.com/api/v1/models/google/gemma/tfLite/gemma-2b-it-cpu-int4/1/download

  # Extract model
  tar xf ~/Downloads/model.tar.gz
```

For other models, they need to be converted/quantized.
Checkout the below links on how to download and convert models to media pipe compatible format.
 - https://developers.google.com/mediapipe/solutions/genai/llm_inference#models
 - https://developers.google.com/mediapipe/solutions/genai/llm_inference/android#model

#### Android Inference

For testing in Android, push the downloaded model to a physical device in developer mode using the below commands.

```sh
# Remove any previously loaded models
adb shell rm -r /data/local/tmp/llm/

adb shell mkdir -p /data/local/tmp/llm/

adb push ~/Downloads/gemma-2b-it-cpu-int4.bin /data/local/tmp/llm/gemma-2b-it-cpu-int4.bin
```

## Installation

```sh
yarn add react-native-local-gen-ai
#or
npm i react-native-local-gen-ai
```
## Usage

Update **minSdkVersion** to **24** in android/build.gradle file.

Invoke chatWithLLM async method with your prompt.

```ts
import { chatWithLLM } from 'react-native-local-gen-ai';

// non-blocking prompting !!
const response = await chatWithLLM("hello !");
console.log(response)

// Response

! 👋

I am a large language model, trained by Google.
I can talk and answer your questions to the best of my knowledge.

What would you like to talk about today? 😊
```

[Optional] Override model options

```ts
import { setModelOptions } from 'react-native-local-gen-ai'

/* Default model path is set to 
   /data/local/tmp/llm/gemma-2b-it-cpu-int4.bin

   For other model variants, modelPath needs to be 
   updated before invoking chatWithLLM
*/
useEffect(()=>{
    setModelOptions({
        modelPath: '/data/local/tmp/llm/gemma-2b-it-gpu-int4.bin',
        randomSeed: 0, // Default 0
        topK: 30, // Default 40
        temperature: 0.7, // Default 0.8
        maxTokens: 1000 // Default 512
    });
},[])
```

## GPU inference in Android

For GPU models, add an entry in Application Manifest file (android/app/src/main/AndroidManifest.xml) to use openCL. 

```xml
<application>
    <!-- Add this for gpu inference -->
    <uses-library android:name="libOpenCL.so"
        android:required="false"/>
</application>
```

## Expo 

Use local app development instead of Expo GO. Rest of the steps remain the same.

More info on Expo local app development can be found here: 

https://docs.expo.dev/guides/local-app-development/

```sh 
npx expo run:android
```


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)

Uses [Google Mediapipe](https://github.com/google/mediapipe) under the hood.
