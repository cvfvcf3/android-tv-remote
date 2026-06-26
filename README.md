# Android TV Remote Control App

ایک complete Android TV remote control app جو WiFi کے ذریعے TV کو control کرتا ہے۔

## Features

- 📱 **Phone App**: Remote controller interface
- 📺 **TV App**: Command receiver service
- 🌐 **WiFi Communication**: Socket-based communication
- 🎮 **Complete Controls**:
  - Volume (up/down/mute)
  - Power (on/off/toggle)
  - Channel (up/down/set)
  - Media controls (play/pause/stop/next/previous)
  - Input selection
  - Custom buttons

## Architecture

```
Android Phone App (Kotlin)
         ↓ WiFi Socket
    Android TV App (Kotlin)
```

## Project Structure

```
android-tv-remote/
├── phone-remote-app/          # Phone controller app
│   ├── app/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── kotlin/com/example/tvremote/
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── viewmodel/RemoteViewModel.kt
│   │   │   │   │   └── network/WiFiClient.kt
│   │   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── settings.gradle.kts
│
├── tv-remote-app/             # TV receiver app
│   ├── app/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── kotlin/com/example/tvremote/
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── service/RemoteService.kt
│   │   │   │   │   └── handler/CommandHandler.kt
│   │   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── settings.gradle.kts
│
└── docs/
    └── SETUP.md
```

## Getting Started

### Requirements
- Android Studio (latest version)
- Android SDK 21+
- Two Android devices (Phone + TV)
- Same WiFi network
- Port 9876 (open on TV)

### Installation

#### Phone Remote App
```bash
cd phone-remote-app
./gradlew build
./gradlew installDebug
```

#### TV Remote App
```bash
cd tv-remote-app
./gradlew build
./gradlew installDebug
```

## Usage

1. **Start TV App** on Android TV device
2. **Open Phone App** on Android phone
3. **Enter TV IP Address** - Find TV's IP from network settings
4. **Connect** and start controlling!

## Communication Protocol

### Commands Format
```json
{
  "command": "volume",
  "action": "up",
  "timestamp": 1234567890
}
```

### Supported Commands
- `volume`: up, down, mute, set_level
- `power`: on, off, toggle
- `channel`: up, down, set_number
- `media`: play, pause, stop, next, previous
- `input`: select_input, list_inputs

## Features Breakdown

### Phone App (Remote Controller)
- **Beautiful UI** with dark theme
- **Power Button** - Red circular button
- **Volume Controls** - Up/Down buttons
- **Channel Controls** - Channel up/down
- **Media Controls** - Play, Pause, Next, Previous
- **Connection Status** - Shows connected TV IP
- **Auto-Discovery** - Find TV on same WiFi

### TV App (Command Receiver)
- **Background Service** - Runs without UI
- **WiFi Server** - Listens on port 9876
- **Command Handler** - Processes all commands
- **System Integration** - Uses Android APIs for control
- **Logging** - Debug information

## Network Details

- **Protocol**: TCP Socket
- **Port**: 9876
- **Format**: JSON
- **Encoding**: UTF-8

## Development Notes

### Technologies Used
- **Kotlin** - Modern Android development
- **Jetpack Compose** - UI for Phone app
- **Coroutines** - Async operations
- **Material Design 3** - Modern UI components
- **Android TV APIs** - TV app optimization

### Testing
1. Use Android Studio Emulator or physical device
2. Ensure both devices are on same WiFi
3. Check firewall settings allow port 9876
4. Enable USB Debugging for testing

## Troubleshooting

### Connection Issues
- Verify both devices are on same WiFi network
- Check TV IP address is correct
- Ensure port 9876 is not blocked
- Restart both apps

### Command Not Working
- Check TV app is running in background
- Verify connection is still active
- Check logs in Android Studio
- Restart the TV app service

## Future Enhancements

- [ ] Auto-discovery of TV devices
- [ ] Multiple TV support
- [ ] Custom button mapping
- [ ] Voice control integration
- [ ] Gesture controls
- [ ] Bluetooth support
- [ ] Screen mirroring
- [ ] App recommendations

## License

MIT License

## Author

Created for Android TV remote control via WiFi

---

**Developed with ❤️ for Android TV**
