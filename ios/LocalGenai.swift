
import Foundation
import MediaPipeTasksGenAI


@objc(LocalGenai)
class LocalGenai: NSObject {

  init() {
    model = OnDeviceModel()
    chat = model.startChat()
  }

 @objc(chatWithLLM:resolve:reject:)
 func chatWithLLM(_ prompt: String,resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: @escaping RCTPromiseRejectBlock) throws -> String {
     do {
      try chat.sendMessage(prompt) { message in 
          resolve(message)
        }
     }catch {
        reject("Error while sending message")
    }
 }

 @objc(setModelPath:resolve:reject:)
 func setModelPath(_ path: String,resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: @escaping RCTPromiseRejectBlock) throws -> String {
     do {
       resolve("done")
     }catch {
        reject("Error while sending message")
    }
 }


}

final class OnDeviceModel {

  private var modelPath:String="gemma-2b-it-cpu-int4"

  private var inference: LlmInference! = {
    let path = Bundle.main.path(forResource: modelPath, ofType: "bin")!
    let llmOptions = LlmInferenceOptions()
    options.baseOptions.modelPath = modelPath
    options.maxTokens = 1000
    options.topk = 40
    options.temperature = 0.8
    options.randomSeed = 101
    return LlmInference(options: llmOptions)
  }()

  func generateResponse(prompt: String) async throws -> String {
    var partialResult = ""
    return try await withCheckedThrowingContinuation { continuation in
      do {
        try inference.generateResponseAsync(inputText: prompt) { partialResponse, error in
          if let error = error {
            continuation.resume(throwing: error)
            return
          }
          if let partial = partialResponse {
            partialResult += partial
          }
        } completion: {
          let aggregate = partialResult.trimmingCharacters(in: .whitespacesAndNewlines)
          continuation.resume(returning: aggregate)
          partialResult = ""
        }
      } catch let error {
        continuation.resume(throwing: error)
      }
    }
  }

  func startChat() -> Chat {
    return Chat(model: self)
  }

  }

  final class Chat {

    private let model: OnDeviceModel

    private var history = [String]()

    init(model: OnDeviceModel) {
      self.model = model
    }

    private func compositePrompt(newMessage: String) -> String {
      return history.joined(separator: "\n") + "\n" + newMessage
    }

    func sendMessage(_ text: String) async throws -> String {
      let prompt = compositePrompt(newMessage: text)
      let reply = try await model.generateResponse(prompt: prompt)

      history.append(text)
      history.append(reply)

      print("Prompt: \(prompt)")
      print("Reply: \(reply)")
      return reply
    }

  }
