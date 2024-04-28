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
npm install react-native-local-genai
```

## Usage

```js
import { chatWithLLM, setModelPath } from 'react-native-local-genai';

// Set model path
useEffect(()=>{
    setModelPath("/data/local/tmp/llm/gemma-2b-it-cpu-int4.bin")
},[])

// non-blocking prompting !!
const response = await chatWithLLM("hello !");
```

## GPU inference in Android

For GPU models, add an entry in Application Manifest file to use openCL. 

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