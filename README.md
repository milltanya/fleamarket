Запуск через консоль из директории с pom.xml:

```./mvnw spring-boot:run```

Сервер будет работать на http://localhost:8080/

- Получить список всех пользователей

  ```http://localhost:8080/api/users```

- Получить список всех товаров

  ```http://localhost:8080/api/products```
  
- Залогиниться

  Отправить на ```http://localhost:8080/api/login``` json формата
  
  ```$xslt
  {
    "login":<login>,
    "password":<password>
  }
  ```  
  Ответ имеет вид 
  
  ```$xslt
  {
    "status":<true/false>,
    "message":<Wrong login/password> (если status=false)
  }
  ```
  
  Например
  ```$xslt
  curl -X POST -H "Content-Type: application/json" -d '{"login":"mylogin","password":"1111"}' http://localhost:8080/api/login
  {"status":true}
  ```
  
- Поменять пароль

  Отправить на ```http://localhost:8080/api/changepassword``` json формата
  
  ```$xslt
  {
    "login":<login>,
    "old_password":<old password>,
    "new_password":<new password>
  }
  ```  
  Ответ имеет вид 
  
  ```$xslt
  {
    "status":<true/false>,
    "message":<Wrong login/password> (если status=false)
  }
  ```
  
  Например
  ```$xslt
  curl -X POST -H "Content-Type: application/json" -d '{"login":"milltanya","old_password":"1111","new_password":"1112"}' http://localhost:8080/api/changepassword
  {"status":true}
  ```

- Консоль базы данных. Там можно писать sql-запросы

  ```http://localhost:8080/h2-console```
  
  JDBC URL: jdbc:h2:mem:testdb
  
  Если не работает, ищем в логах такую строчку: ```H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:testdb'```

- Просто проверить, что сервер работает

  ```http://localhost:8080/test/```
  
- Добавить в базу пару тестовых записей

  ```http://localhost:8080/test/init```
