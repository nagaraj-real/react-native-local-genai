#import "LocalGenai.h"

@implementation LocalGenai
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(chatWithLLM:(NSString *)prompt
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
    resolve(@"Not implemented for ios yet !!");
}

RCT_EXPORT_METHOD(setModelPath:(NSString *)path
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
    resolve(@"Not implemented for ios yet !!");
}

// Don't compile this code when we build for the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeLocalGenaiSpecJSI>(params);
}
#endif

@end
