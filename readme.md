# ShTP Mobile Client
[🇬🇧 English](/readme.md) [🇷🇺 Russian](/readme.ru.md)

## About
* This is an Android client developed as a REST API client for the [Back-End](https://github.com/ITClassDev/Backend) server.
* Connection to the server is made using compressed data exchange via the Protocol Buffers protocol, unlike the FrondEnd server, which is necessary to be always online even with a bad internet connection. (JSON can be easily switched to ProtoBuf)
* RxJava3 + Retrofit2 are used to exchange messages, which allows executing requests asynchronously.
* The user interface is based on the [Figma design](https://www.figma.com/file/k45gvSrwIiGPbaLBunFHw9/Android?node-id=160%3A174&t=RIWBJ2Qv6cmYgMKH-1)

Example of the alpha version of the application:
![](https://i.imgur.com/ny1Pyvc.png)

## Build
By default, the project is built with every git push request and sent to [releases](https://github.com/ITClassDev/Mobile/releases).
The dedicated endpoint used is **shtp.1561.ru**

##### Manual application build:
* Change Backend-API address at **app/src/main/kotlin/ru/slavapmk/shtp/Values.kt**:

  `const val ENDPOINT_URL = "YOUR_URL_HERE"`
* Install Java 17, [Amazon Correto](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html) may be suitable
* Set the path to the Java bin folder in the **JAVA_HOME** environment variable
* Run `./gradlew build --no-daemon` in the project folder
* The compiled APK file is located in the **app/build/outputs/apk/debug** folder.