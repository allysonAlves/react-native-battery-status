import { useState, useEffect } from 'react';
import { StyleSheet, View, Text, Button } from 'react-native';
import {
  getBatteryStatusAsync,
  useBatteryStatus,
} from 'react-native-battery-status';

export default function App() {
  const [result, setResult] = useState<number | undefined>();
  const batteryPercentage = useBatteryStatus();

  const handleGetBatteryStatus = () => {
    getBatteryStatusAsync().then(setResult);
  };

  useEffect(() => {
    getBatteryStatusAsync().then(setResult);
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      <Text>batteryPercentage: {batteryPercentage}</Text>
      <Button title="get Battery Status" onPress={handleGetBatteryStatus} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
