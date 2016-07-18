# Basic selenium test samples with video recording / proxy support
This project provides some common usage examples for [docker-selenium](https://github.com/sskorol/docker-selenium) and [docker-selenium-grid](https://github.com/sskorol/docker-selenium-grid). See technical details in related [article](http://qa-automation-notes.blogspot.com/2016/04/docker-selenium-and-bit-of-allure-how.html).

The main intention is to show how to use the following features in docker containers:

 - video recording
 - network traffic recording

Custom capabilities with required video info / proxy are set in `WebDriverContainer`.

Video / HAR attachment process is triggered in `BaseTestListener`.

Note that this project depends on [browsermob-proxy-rest](https://github.com/sskorol/browsermob-proxy-rest). So first you'll need to deploy it into your local maven repository. You may also want to build BMP image from [docker-browsermob-proxy](https://github.com/sskorol/docker-browsermob-proxy).

HAR attachment is implemented as an embedded version of [HAR Viewer](http://www.softwareishard.com/har/viewer). 

Use the following command to run tests against docker containers:
```
mvn clean test
```

To generate test results report, you need to run:
```
mvn site
```

[![demo](http://img.youtube.com/vi/oxanT-d48N0/0.jpg)](https://youtu.be/oxanT-d48N0)
