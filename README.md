# PageVoice Android APK Builder

This project wraps the current PageVoice EPUB Studio website in a basic Android WebView app and builds a debug APK with GitHub Actions.

## Important limitation
This is a WebView wrapper around the hosted app, not a fully offline-native conversion engine. The first launch and website loading require internet. The APK does not make the web app's JavaScript voice engine or its dependencies fully offline by itself. Large EPUB/audio exports should be tested on the target phone.

## Build the APK (phone-friendly)
1. Create a GitHub account if needed.
2. Create a new repository named `pagevoice-android`.
3. Upload all files from this folder to that repository.
4. Open the repository's **Actions** tab and enable Actions if asked.
5. Run **Build PageVoice APK** using **Run workflow** (or push a commit to trigger it).
6. Open the completed workflow run, scroll to **Artifacts**, and download `PageVoice-debug-apk`.
7. Extract the ZIP and install `app-debug.apk` on your Android phone. Android may ask you to allow installation from that source.

## App URL
The app loads: https://pagevoice-epub-studio-e340on.v2.appdeploy.ai/

## Notes
- The generated APK is a debug build, suitable for personal testing, not Play Store publishing.
- This repository contains no signing key and does not create a Play Store release.
- DRM-protected EPUBs are not supported by the website.
