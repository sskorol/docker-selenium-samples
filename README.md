# Basic selenium test samples with video recording support
This project provides some common usage examples for [docker-selenium](https://github.com/sskorol/docker-selenium) and [docker-selenium-grid](https://github.com/sskorol/docker-selenium-grid). See technical details in related [article](http://qa-automation-notes.blogspot.com/2016/04/docker-selenium-and-bit-of-allure-how.html).

The main intention is to show how to use video recording feature within docker containers.

Custom capabilities with required video info are set in `WebDriverContainer`.

Video attachment process is triggered in `BaseTestListener`.

Use the following command to run tests against docker containers:
```
mvn clean test
```
Note that by default tests are configured to be executed against chrome and firefox in parallel. So you should either raise corresponding configuration first using `docker` or `docker-compose`, or modify `debug-suite.xml`, which controls `browser` and scaling options.

To generate test results report, you need to run:
```
mvn site
```
Please note that video recording feature is not officially released yet. So to use it, you'll need to build latest [Allure](https://github.com/allure-framework/allure-core) snapshot by your own.

In case if you use [allure-maven-plugin](https://github.com/allure-framework/allure-maven-plugin) for report generation, you may also need to rebuild it with reference to newly created `core` snapshot. Just change [allure.version](https://github.com/allure-framework/allure-maven-plugin/blob/master/src/main/java/ru/yandex/qatools/allure/report/AllureResolveMojo.java) property to corresponding snapshot and call `clean install` goal.

<a href="http://www.youtube.com/watch?feature=player_embedded&v=f73ea4-RVHo" target="_blank"><img src="http://img.youtube.com/vi/f73ea4-RVHo/0.jpg" alt="demo" width="800" height="600" border="10" /></a>
