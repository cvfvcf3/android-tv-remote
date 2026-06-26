# Setup Guide - Android TV Remote App

## Prerequisites

- Android Studio Arctic Fox or newer
- Android SDK 21+
- Android device or emulator (Phone + TV)
- Same WiFi network

## Installation Steps

### 1. Clone Repository

```bash
git clone https://github.com/cvfvcf3/android-tv-remote.git
cd android-tv-remote
```

### 2. Open in Android Studio

#### Phone App
```bash
cd phone-remote-app
open -a "Android Studio" .
```

#### TV App
```bash
cd tv-remote-app
open -a "Android Studio" .
```

### 3. Build & Run

#### Phone Remote App
```bash
# Open phone-remote-app in Android Studio
# Select your phone device/emulator
# Click Run (Shift + F10)
```

#### TV Remote App
```bash
# Open tv-remote-app in Android Studio
# Select your TV device/emulator
# Click Run (Shift + F10)
```

## Configuration

### 1. Find TV IP Address

On Android TV:
1. Go to Settings
2. Network & Internet
3. WiFi
4. Connected network details
5. Note down the IP address (e.g., 192.168.1.100)

### 2. Connect Phone App

1. Open Phone Remote App
2. Enter TV IP address
3. Click Connect
4. Start controlling!

## Port Configuration

- **Default Port**: 9876
- To change port:
  - Phone App: Update `WiFiClient.kt` PORT constant
  - TV App: Update `RemoteService.kt` PORT constant

## Firewall Settings

Ensure port 9876 is open:

```bash
# On TV (if Linux-based)
sudo ufw allow 9876

# Or disable firewall temporarily
sudo ufw disable
```

## Troubleshooting

### Connection Fails
- Check both devices are on same WiFi
- Verify IP address is correct
- Ensure firewall allows port 9876
- Check device logs in Android Studio

### Commands Not Working
- Verify TV App is running
- Check system permissions
- Review Android logs
- Restart both apps

### Build Issues
- Clean project: `./gradlew clean`
- Rebuild: `./gradlew build`
- Sync Gradle: File > Sync Now

## Development Notes

### Code Structure

**Phone App:**
- `MainActivity.kt` - UI with Jetpack Compose
- `RemoteViewModel.kt` - Business logic
- `WiFiClient.kt` - Network communication

**TV App:**
- `MainActivity.kt` - Entry point
- `RemoteService.kt` - Background service
- `CommandHandler.kt` - Command processing

### Adding New Commands

1. Add command handler in `CommandHandler.kt`
2. Add ViewModel method in `RemoteViewModel.kt`
3. Add UI button in `MainActivity.kt` (Phone)
4. Test connection

## Performance Tips

- Use 2.4GHz WiFi for better range
- Keep TV near router
- Minimize network congestion
- Use local network (no internet required)

## Security Notes

⚠️ **Current Implementation**:
- No authentication
- No encryption
- Only works on local WiFi

**For Production**:
- Add SSL/TLS encryption
- Implement authentication
- Validate all inputs
- Rate limiting

## Future Enhancements

- [ ] UUID-based device identification
- [ ] Encrypted communication
- [ ] Device discovery
- [ ] Custom macros
- [ ] Voice control
- [ ] Gesture support
- [ ] Multiple TV support

## Support

For issues and questions:
1. Check GitHub Issues
2. Review logs in Android Studio
3. Test with different devices
4. Verify network configuration

---

**Happy controlling! 📺**
