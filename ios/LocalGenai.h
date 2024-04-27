
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNLocalGenaiSpec.h"

@interface LocalGenai : NSObject <NativeLocalGenaiSpec>
#else
#import <React/RCTBridgeModule.h>

@interface LocalGenai : NSObject <RCTBridgeModule>
#endif

@end
