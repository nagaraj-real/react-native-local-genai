# React Native Local Gen AI

Local Generative AI capabilities using google mediapipe.

Non-blocking local LLM inference using quantized models.

Supports only android now. iOS support coming soon !

## Pre-requisites

These models are large in size and should not be bundled in apk. 
Ideally in production, the model must be downloaded from server upon user request.

For development, we manually download the preferred model to PC, quantize(if needed) and push to an android debugging device (adb).

Checkout the below links on how to download and quantize models
 - https://developers.google.com/mediapipe/solutions/genai/llm_inference#models

 - https://developers.google.com/mediapipe/solutions/genai/llm_inference/android#model


#### Android Inference

For testing in Android, push the downloaded model to a physical device in developer mode using the below commands.

```sh
# Remove any previously loaded models
adb shell rm -r /data/local/tmp/llm/

adb shell mkdir -p /data/local/tmp/llm/

adb push ./gemma-2b-it-cpu-int4.bin /data/local/tmp/llm/gemma-2b-it-cpu-int4.bin
```

## Installation

```sh
yarn add react-native-local-gen-ai
```
## Usage

Update **minSdkVersion** to **24** in android/build.gradle file.

Update the model path using  setModelPath.

Invoke chatWithLLM async method to pass prompts

```js
import { chatWithLLM, setModelPath } from 'react-native-local-gen-ai';

// Set model path
useEffect(()=>{
    setModelPath("/data/local/tmp/llm/gemma-2b-it-cpu-int4.bin")
},[])

// non-blocking prompting !!
const response = await chatWithLLM("hello !");
console.log(response)

// Response

! 👋

I am a large language model, trained by Google. I can talk and answer your questions to the best of my knowledge.

What would you like to talk about today? 😊
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


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)

Made using mediapipe under the hood (https://github.com/google/mediapipe)