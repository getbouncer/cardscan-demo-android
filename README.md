# CardScan

This repository serves as a demonstration for the CardScan library. [CardScan](https://cardscan.io/) is a relatively small library (1.9 MB) that provides fast and accurate payment card scanning.

CardScan serves as the foundation for CardVerify enterprise libraries, which validate the authenticity of payment cards as they are scanned.

![CardScan](docs/images/cardscan_demo.gif)

## Contents

* [Requirements](#requirements)
* [Demo](#demo)
* [Installation](#installation)
* [Using CardScan](#using-cardscan)
* [Customizing](#customizing-cardscan)
* [Developing](#developing-cardscan)
* [Authors](#authors)
* [License](#license)

## Requirements

* Android API level 21 or higher
* AndroidX compatibility
* Kotlin coroutine compatibility

## Demo

This repository contains a demonstration app for the CardScan libraries. To build and install this library follow the following steps:

1. Clone the repository from github
    ```bash
    git clone --recursive https://github.com/getbouncer/cardscan-demo-android
    ```
    
2. Build the library using gradle or [android studio](https://developer.android.com/studio).
    a. Using android studio, open the directory `cardscan-demo-android`. Install the app on your device or an emulator by clicking the play button in the top right of android studio.
    
    ![build_android_studio](docs/images/build_android_studio.png)
    
  
    
    b. Using gradle, build the demo app by executing the following command:
    
    ```bash
    ./gradlew demo:assembleRelease
    ```
    This will create a release APK in the `cardscan-demo/build/outputs/apk` directory. Copy this file to your device and install it.

## Installation

The CardScan libraries are published in the [jcenter](https://jcenter.bintray.com/com/getbouncer/) repository, so for most gradle configurations you only need to add the dependencies to your app's `build.gradle` file:

```gradle
dependencies {
    implementation 'com.getbouncer:cardscan-ui:2.0.0001'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3'
}
```

## Using CardScan

CardScan UI provides a user interface through which payment cards can be scanned.

```kotlin
class MyActivity : Activity {

    /**
     * This method should be called as soon in the application as possible to give time for
     * the SDK to warm up ML model processing.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        CardScanActivity.warmUp(this)
    }

    private fun onScanCardClicked() {
        CardScanActivity.start(this, enableEnterCardManually = true)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (CardScanActivity.isScanResult(requestCode)) {
            if (resultCode == Activity.RESULT_OK) {
                handleCardScanSuccess(CardScanActivity.getScannedCard(data))
            } else if (resultCode == Activity.RESULT_CANCELED) {
                handleCardScanCanceled(data.getIntExtra(RESULT_CANCELED_REASON, -1))
            }
        }
    }
    
    private fun handleCardScanSuccess(result: ScanResult) {
        // do something with the scanned credit card
    }
    
    private fun handleCardScanCanceled(reason: Int) = when (reason) {
        CANCELED_REASON_USER -> handleUserCanceled()
        CANCELED_REASON_ENTER_MANUALLY -> handleEnterCardManually()
        CANCELED_REASON_CAMERA_ERROR -> handleCameraError()
        else -> handleCardScanFailed()
    }
    
    private fun handleUserCanceled() {
        // do something when the user cancels the card scan
    }
    
    private fun handleEnterCardManually() {
        // do something when the user wants to enter a card manually
    }
    
    private fun handleCameraError() {
        // do something when camera had an error
    }
    
    private fun handleCardScanFailed() {
        // do something when scanning a card failed
    }
}
```

## Customizing CardScan

CardScan is built to be customized to fit your UI.

### Basic modifications

To modify text, colors, or padding of the default UI, see the [customization](docs/customize.md) documentation.

### Extensive modifications

To modify arrangement or UI functionality, CardScan can be used as a library for your custom implementation. See examples in the [cardscan-base-android](https://github.com/getbouncer/cardscan-base-android) repository.

## Developing CardScan

See the [development docs](docs/develop.md) for details on developing for CardScan.

## Authors

Adam Wushensky, Sam King, and Zain ul Abi Din

## License

CardScan is available under non-commercial and commercial licenses. See the LICENSE file for more info.
