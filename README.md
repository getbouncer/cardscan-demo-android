# Overview

This repository serves as a demonstration for the CardScan library. [CardScan](https://cardscan.io/) is a relatively small library (1.9 MB) that provides fast and accurate payment card scanning.

CardScan is the foundation for CardVerify enterprise libraries, which validate the authenticity of payment cards as they are scanned.

![demo](docs/images/demo.gif)

## Contents

* [Requirements](#requirements)
* [Demo](#demo)
* [Installation](#installation)
* [Using](#using)
* [Customizing](#customizing)
* [Developing](#developing)
* [Authors](#authors)
* [License](#license)

## Requirements

* Android API level 21 or higher
* AndroidX compatibility
* Kotlin coroutine compatibility

Note: Your app does not have to be written in kotlin to integrate this library, but must be able to depend on kotlin functionality.

## Demo

This repository contains a demonstration app for the CardScan product. To build and install this library follow the following steps:

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
    implementation 'com.getbouncer:cardscan-ui:2.0.0012'
}
```

## Using

Most apps that integrate CardScan should do so with the [CardScan UI library](https://github.com/getbouncer/cardscan-ui-android). See the README file in that repository for details on integration.

To build a custom user interface on top of the scan framework, use the [scan-ui-card library](https://github.com/getbouncer/scan-ui-card-android). See the README file in that repository for details on integration.

## Provisioning an API key

Make sure that you get an [API key](https://api.getbouncer.com/console) and configure the library when your application launches. 

## Name and expiration extraction support (BETA)

To test name / expiration extraction, please first provision an API key, then reach out to support@bouncer.com with details about your use case and estimated volumes.

Before launching the CardScan flow, make sure to call the ```CardScanActivity.initializeNameExtraction()``` function with your API key:

```kotlin
CardScanActivity.initializeNameExtraction(this, API_KEY)
```

## Customizing

CardScan is built to be customized to fit your UI.

### Basic modifications

To modify text, colors, or padding of the default UI, see the [customization](https://github.com/getbouncer/cardscan-ui-android/blob/master/docs/customize.md) documentation.

### Extensive modifications

To modify arrangement or UI functionality, CardScan can be used as a library for your custom implementation. See examples in the [scan-ui-card-android](https://github.com/getbouncer/scan-ui-card-android) repository.

## Developing

See the [development docs](docs/develop.md) for details on developing for CardScan.

## Authors

Adam Wushensky, Sam King, and Zain ul Abi Din

## License

This library is available under paid and free licenses. See the [LICENSE](LICENSE) file for the full license text.

### Quick summary
In short, this library will remain free forever for non-commercial applications, but use by commercial applications is limited to 90 days, after which time a licensing agreement is required. We're also adding some legal liability protections.

After this period commercial applications need to convert to a licensing agreement to continue to use this library.
* Details of licensing (pricing, etc) are available at [https://cardscan.io/pricing](https://cardscan.io/pricing), or you can contact us at [license@getbouncer.com](mailto:license@getbouncer.com).

### More detailed summary
What's allowed under the license:
* Free use for any app for 90 days (for demos, evaluations, hackathons, etc).
* Contributions (contributors must agree to the [Contributor License Agreement](Contributor%20License%20Agreement))
* Any modifications as needed to work in your app

What's not allowed under the license:
* Commercial applications using the license for longer than 90 days without a license agreement. 
* Using us now in a commercial app today? No worries! Just email [license@getbouncer.com](mailto:license@getbouncer.com) and we’ll get you set up.
* Redistribution under a different license
* Removing attribution
* Modifying logos
* Indemnification: using this free software is ‘at your own risk’, so you can’t sue Bouncer Technologies, Inc. for problems caused by this library

Questions? Concerns? Please email us at [license@getbouncer.com](mailto:license@getbouncer.com) or ask us on [slack](https://getbouncer.slack.com).
