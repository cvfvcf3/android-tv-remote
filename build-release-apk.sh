#!/bin/bash

# Generate Release Keystore
cd tv-remote-app/app

echo "🔐 Generating Release Keystore..."

keytool -genkey -v -keystore release.keystore \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -alias tvremote \
  -storepass android123 \
  -keypass android123 \
  -dname "CN=Android TV Remote, O=Personal, L=Karachi, ST=Sindh, C=PK"

echo "✅ Keystore generated: release.keystore"
echo ""
echo "🔨 Building Release APK..."

cd ../..
./gradlew clean
./tv-remote-app/gradlew -p tv-remote-app assembleRelease

echo ""
echo "✅ Release APK built successfully!"
echo ""
echo "📱 APK Location:"
echo "tv-remote-app/app/build/outputs/apk/release/app-release.apk"
echo ""
echo "📋 Installation Instructions:"
echo "1. Enable 'Unknown Sources' in Android TV settings"
echo "2. Transfer APK to TV device"
echo "3. Install using file manager"
echo "4. Open app and start receiving commands"
