# Crash Reporter

![](https://img.shields.io/github/languages/top/sunilpaulmathew/CrashReporter)
![](https://img.shields.io/github/contributors/sunilpaulmathew/CrashReporter)
![](https://img.shields.io/github/license/sunilpaulmathew/CrashReporter)

A real-time crash monitor & reporting library for Android

![](demo/animation.gif)

## Download

Step 1: Add it in your root-level build.gradle at the end of repositories:
```
allprojects {
        repositories {
                ...
                maven { url 'https://jitpack.io' }
        }
}
```

Step 2: Add dependency to the app-level build.gradle:
```
dependencies {
        implementation 'com.github.sunilpaulmathew:CrashReporter:Tag'
}
```
*Please Note: **Tag** should be replaced with the latest **[commit id](https://github.com/sunilpaulmathew/CrashReporter/commits/master)**.*

Step 2: Initialize library after the setContentView of main activity;
```
new CrashReporter(contactDetails, this).initialize();
```

*Please Note: **contactDetails** should be a **String** containing the details to reach the developer (such as E-Mail, Telegram id, etc.) of app.*

The library will record and prompt to share the log after the occurrence of any crash, as long as the activity in which the library initialized is live.