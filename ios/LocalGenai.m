#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(LocalGenai, NSObject)

RCT_EXTERN_METHOD(chatWithLLM:(NSString *)prompt
                  resolve: (RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setModelPath:(NSString *)path
                  resolve:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)

@end
