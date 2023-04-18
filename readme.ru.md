# ShTP Mobile Client
[🇬🇧 English](/readme.md) [🇷🇺 Russian](/readme.ru.md)

## About
* Это Android клиент, разработанный как REST API клиент для [Back-End](https://github.com/ITClassDev/Backend) сервера.
* Подключение к серверу осуществляется посредством обенная сжатыми данными по протоколу Protocol Buffers, в отличии от FrondEnd сервера, что обусловлено необходимостью быть всегда в сети, даже при условии плохого интернета. (Временно используется JSON, при желании легко переключается и на ProtoBuf)
* Для обмена сообщений используется связка RxJava3 + Retrofit2, что позволяет асинхронно выполнять запросы.
* Пользовательский интерфейс основан на [макете Figma](https://www.figma.com/file/k45gvSrwIiGPbaLBunFHw9/Android?node-id=160%3A174&t=RIWBJ2Qv6cmYgMKH-1)

Пример альфа-версии приложения:
![](https://i.imgur.com/R5tsqiO.jpg)

## Сборка

По умолчанию сборка проекта проводится при каждом push запросе git и отправляется в [релизы](https://github.com/ITClassDev/Mobile/releases).
В качестве endpoint используется выделенный от школы - **shtp.1561.ru**

##### Ручная сборка приложения:
* Изменить адресс Backend-API по пути **app/src/main/kotlin/ru/slavapmk/shtp/Values.kt**:

  `const val ENDPOINT_URL = "YOUR_URL_HERE"`

* Установить Java 17, может подойти [Amazon Correto](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
* Прописать путь к папке bin в Java в переменную среды своей системы **JAVA_HOME**
* В папке с проектом прописать `./gradlew build --no-daemon`
* Собранный APK файл находится в папке **app/build/outputs/apk/debug**