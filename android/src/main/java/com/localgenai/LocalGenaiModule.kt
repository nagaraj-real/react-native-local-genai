package com.localgenai

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class LocalGenaiModule internal constructor(private var context: ReactApplicationContext) :
    LocalGenaiSpec(context) {

    @ReactMethod
    override fun chatWithLLM(prompt: String, promise: Promise) {
        Log.d("InferenceModule", "Prompt: $prompt")
        CoroutineScope(Dispatchers.Default).launch {
            generateResponse(prompt, promise)
        }
    }

    @ReactMethod
    override fun setModelPath(path: String, promise: Promise) {
        Log.d("InferenceModule", "modelPath: $path")
        InferenceModel.updateModelPath(path)
    }

    @ReactMethod
    override fun setModelOptions(options: ReadableMap, promise: Promise) {
      InferenceModel.updateModelOptions(options)
    }

    private suspend fun generateResponse(prompt:String, promise: Promise) {
        try {
            var output = StringBuilder("")
            val response = InferenceModel.getInstance(context).generateResponseAsync(prompt)
            InferenceModel.getInstance(context).partialResults
                .collectIndexed { _, (partialResult, done) ->
                    output.append(partialResult)
                    if (done) {
                        promise.resolve(output.toString())
                    }
                }
            promise.resolve(response)

        } catch (e: Exception)
        {
            e.message?.let { Log.d("InferenceModule", it) }
            promise.reject("Error",e.message)
        }
    }
    override fun getName(): String {
        return NAME
    }

    companion object {
        const val NAME = "LocalGenai"
    }
}
