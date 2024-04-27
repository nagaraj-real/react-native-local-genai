package com.localgenai

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class LocalGenaiModule internal constructor(context: ReactApplicationContext) :
  LocalGenaiSpec(context) {

  private val inferenceModel = InferenceModel.getInstance(context)
  @ReactMethod
  override fun chatWithLLM(prompt: String, promise: Promise) {
    Log.d("InferenceModule", "Prompt: $prompt")
    CoroutineScope(Dispatchers.Default).launch {
      generateResponse(prompt, promise)
    }
  }

  private suspend fun generateResponse(prompt:String, promise: Promise) {
    try {
      var output = StringBuilder("")
      val response = inferenceModel.generateResponseAsync(prompt)
      inferenceModel.partialResults
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
      promise.reject("Error",e)
    }
  }
  override fun getName(): String {
    return NAME
  }

  companion object {
    const val NAME = "LocalGenai"
  }
}
