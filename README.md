# Android demo of Optical Character Recognition (OCR) using ML Kit

In this simple demo, a screenshot of the first few paragraphs of "Moby Dick" is embedded in the app. This image is scanned using Google's [ML Kit](https://developers.google.com/ml-kit) to extract the text content.

You will need to have [Firebase](https://firebase.google.com/docs/ml-kit/android/recognize-text) associated with your project in order to do OCR using Google's APIs.

This is not done using the cloud version of ML Kit. The project uses Firebase's ML Kit "on-device", so it may take a short while to download the tools that are used for text extraction.

The screenshot shows the app after text has been extracted.

![Text extracted](https://github.com/fullStackOasis/android-ml-kit-on-device-ocr-demo/raw/master/android_ml_kit_ocr_demo.png)
