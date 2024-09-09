
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNBatteryStatusSpec.h"

@interface BatteryStatus : NSObject <NativeBatteryStatusSpec>
#else
#import <React/RCTBridgeModule.h>

@interface BatteryStatus : NSObject <RCTBridgeModule>
#endif

@end
