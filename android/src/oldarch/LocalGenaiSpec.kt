package com.localgenai

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.Promise

abstract class LocalGenaiSpec internal constructor(context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {

  abstract fun chatWithLLM(prompt: String, promise: Promise)

  abstract fun setModelPath(path: String, promise: Promise)
}
