***Технологии:***

 *Java 11*   
 *Spring Boot*  
 *Spring Web*   
 *Spring Security*   
 *Spring Data*  
 *PostgresSQL*  
 *jaxb*  
 *jackson*  
 *junit*  
 *Swagger*  
 *Lombok*
  ---------------
**Запуск:**

   *PostgresSQL* 
   * Подключиться к работающему Postgres. Настройки - application.properties
    * Запустить миграцию DB UserMigration.sql
 
*Spring*
  * Запустить Spring Boot application. Main class - CurrencyConverterApplication 

*Взаимодействие*
  * Swagger api 
    * Перейти по ссылке localhost:8080/swagger-ui.html#/
    * Вы будите Not Authenticated.
    * Миграция создает несколько пользователей:
        * Login: Alice Pass: 1q2w3e4r  Authority: USER
        * Login: Bob   Pass: 123456    Authority: ADMIN
        
    * Базовый функционал /info или (localhost:8080/info) доступен всем.
    * Остальной функционал базируется на Granted Authority пользователя
    * Что бы залогиниться перейдите localhost:8080/login
    * Так же есть localhost:8080/logout
    * Вернитесь localhost:8080/swagger-ui.html#/
    * В вкладке "REAL END POINTS" взаимодействие через Json, настроены сразу работающие примеры,
    можно удобно редактировать. Все достаточно подробно описано аннотациями и в закладке 
Model. 
    * Вкладка Fake это фэйковые End Point'ы для получения реверансов Postman'ом 
  * Postman
    * адреса реквестов можно посмотреть в контролере CurrencyController 
  * Тесты
    * Так же есть тесты где можно удобно более гибко проверить работу сервиса. Основной - MainConversionServiceTest
