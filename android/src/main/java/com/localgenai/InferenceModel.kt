import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import java.io.File
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import com.facebook.react.bridge.ReadableMap
import android.util.Log

class InferenceModel private constructor(context: Context) {
    private var llmInference: LlmInference
    private val modelExists: Boolean
        get() = File(modelPath).exists()

    private val _partialResults = MutableSharedFlow<Pair<String, Boolean>>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val partialResults: SharedFlow<Pair<String, Boolean>> = _partialResults.asSharedFlow()

    init {
        if (!modelExists) {
           throw IllegalArgumentException("Model not found at path: $modelPath")
        }

        val options = LlmInference.LlmInferenceOptions.builder()
            .setModelPath(modelPath)
            .setMaxTokens(maxTokens)
            .setTopK(topK)
            .setTemperature(temperature)
            .setRandomSeed(randomSeed)
            .setResultListener { partialResult, done ->
                _partialResults.tryEmit(partialResult to done)
            }
            .build()
        Log.d("InferenceModel", "Options: $options")
        llmInference = LlmInference.createFromOptions(context, options)
    }

    fun generateResponseAsync(prompt: String) {
        llmInference.generateResponseAsync(prompt)
    }

    fun generateResponse(prompt:String): String {
        return llmInference.generateResponse(prompt);
    }



    companion object {
        private var instance: InferenceModel? = null
        private var modelPath: String = "/data/local/tmp/llm/gemma-2b-it-cpu-int4.bin"
        private var maxTokens: Int = 512
        private var topK: Int = 40
        private var temperature: Float = 0.8f
        private var randomSeed: Int =0
        fun getInstance(context: Context): InferenceModel {
            return if (instance != null) {
                instance!!
            } else {
                InferenceModel(context).also { instance = it }
            }
        }
      fun updateModelPath(path:String){
        if(!path.equals(modelPath)){
          modelPath = path
          resetInstance()
        }
      }

      fun updateModelOptions(options: ReadableMap){
        modelPath = if (options.hasKey("modelPath")) options.getString("modelPath")?:modelPath else modelPath
        maxTokens = if (options.hasKey("maxTokens")) options.getInt("maxTokens")?:maxTokens else maxTokens
        topK = if (options.hasKey("topK")) options.getInt("topK")?:topK else topK
        temperature = if (options.hasKey("temperature")) options.getDouble("temperature").toFloat()?:temperature else temperature
        randomSeed = if (options.hasKey("randomSeed")) options.getInt("randomSeed")?:randomSeed else randomSeed
        resetInstance()
      }

        private fun resetInstance(){
          instance=null
        }


    }
}
