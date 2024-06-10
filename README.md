# Buy Ticket service
## Рогатнев Дмитрий Александрович, БПИ 229

### Запуск

Для запуска проекта потребуется установить (если не имеется) [docker](https://www.docker.com/products/docker-desktop/).

Чтобы запустить проект понадобится перейти в корневую директорию проекта и исполнить команду:

```bash
docker-compose up --build
```

По умолчанию для запуска базы данных авторизации используется порт `6432`, а для базы данных хранения заказов `5555`.
Если они заняты, то можно изменить эти порты в [docker-compose](docker-compose.yaml) и application файлах  файле (желательно, чтобы
они не были заняты). Порты `8080` и `8000` должны
быть свободны для запуска сервисов.

### Описание проекта

Данный проект состоит из двух сервисов: один отвечает за регистрацию и авторизацию пользователей, а второй - за 
непосредственную покупку билетов. Также в проекте присутствует две базы данных: для сервиса авторизации и покупки билетов
соответственно.

### Архитектура проекта

Проект состоит из двух сервисов:

* [auth](auth) - этот сервис отвечает за авторизацию и проверку токенов на валидность, с к нему подключена база данных auth postgresql.
* [tickets](tickets) - этот сервис отвечает за заказ билетов для авторизованных пользователей, он имеет свою отдельную postgresql базу данных tickets. Также он отправляет запросы на сервис auth для валидации JWT токенов.

### Документация

Документация по пользованию API находится в следующих файлах:

* [Авторизация](auth/Swagger.json)
* [Заказ билетов](tickets/Swagger.json)

### Список используемых технологий

Все используемые технологии описаны в файлах pom.xml для [auth](auth/pom.xml) и [tickets](tickets/pom.xml).

* Java 17
* Kotlin 1.9.24
* Spring boot 3.3.0
* spring-security-crypto
* spring-boot-starter-web
* jackson-module-kotlin
* kotlin-reflect
* kotlin-stdlib
* postgresql
* spring-boot-starter-test
* spring-security-test
* spring-boot-starter-data-jpa
* spring-boot-starter-data-rest
* modelmapper
* lombok
* testng
* spring-context
* jjwt
* java-jwt
* spring-boot-test-autoconfigure
* springdoc-openapi-starter-webmvc-ui
* spring-boot-starter-tomcat
* khttp
* spring-boot-configuration-processor
* kotlin-test-junit5
* mysql-connector-j

### Важные замечания

* После регистрации обязательно надо залогиниться для получения токена
* При тестировании API можно взаимодействовать только с теми ручками, которые прописаны в документации
* Для того, чтобы работал Authorization через HEADER лучше отправлять запрос через Postman в Authorization, в приоритете будет Authorisation head токен.