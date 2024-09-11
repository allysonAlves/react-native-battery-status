# React Native Battery Status

**React Native Battery Status** is a library that allows you to access and monitor the battery status of mobile devices using React Native.

## Installation

To install the library, run the following command:

```bash
npm install react-native-battery-status
```

If you are using iOS, remember to run the `pod install` command inside the `ios` directory after installation.

## Usage

Import the methods provided by the library and use them in your application:

```js
import { getBatteryStatusAsync, useBatteryStatus } from 'react-native-battery-status';

// Example using getBatteryStatusAsync to get the current battery status.
const batteryPercentage = await getBatteryStatusAsync();

// Example using useBatteryStatus as a hook to monitor battery status in real-time.
const batteryPercentage = useBatteryStatus();
```

### Available Methods

- **getBatteryStatusAsync()**: Returns the current battery percentage of the device.
- **useBatteryStatus()**: A React hook that provides the battery percentage in real-time.

## Contributing

If you would like to contribute to the development of this package, please check out our [Contributing Guide](CONTRIBUTING.md) for more information on the development workflow.

## License

This project is licensed under the [MIT License](LICENSE).

---

Created with [create-react-native-library](https://github.com/callstack/react-native-builder-bob).

---
