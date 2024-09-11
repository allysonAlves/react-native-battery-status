import { useEffect, useState } from 'react';
import { NativeModules, NativeEventEmitter, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-battery-status' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const BatteryStatusModule = NativeModules.BatteryStatus
  ? NativeModules.BatteryStatus
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function getBatteryStatusAsync(): Promise<number> {
  return BatteryStatusModule.getBatteryStatusAsync();
}

const BatteryEventEmitter = new NativeEventEmitter(BatteryStatusModule);

export function useBatteryStatus() {
  const [batteryPercentage, setBatteryPercentage] = useState<number | null>(
    null
  );

  useEffect(() => {
    BatteryStatusModule.addListener('onBatteryStatusChange');

    const subscription = BatteryEventEmitter.addListener(
      'onBatteryStatusChange',
      (newBatteryPercentage) => {
        setBatteryPercentage(newBatteryPercentage.toFixed(2));
      }
    );

    return () => {
      BatteryStatusModule.removeListeners(1);
      subscription.remove();
    };
  }, []);

  return batteryPercentage;
}
