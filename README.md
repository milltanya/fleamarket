Запуск через консоль из директории с pom.xml:

```./mvnw spring-boot:run```

Сервер будет работать на http://localhost:8080/

- Получить список всех пользователей

  ```http://localhost:8080/api/users```

- Получить список всех товаров

  ```http://localhost:8080/api/products```
  
- Получить список категорий

  ```http://localhost:8080/api/categories```

- Получить самые новые товары

  ```http://localhost:8080/api/top_products?category=<CATEGORY>&numbe=r<NUMBER>```
  
  Возвращает json-список из <NUMBER> (по умолчанию 10) товаров из категории <CATEGORY> (если не указана, то просто список самых новых товаров)
  
- Получить информацию об определенном пользователе

  ```http://localhost:8080/api/user?login=<LOGIN>```
  
  Возвращает json-объект со всей информацией о пользователе, включая его товары
  
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
    "status":<0/1>,
    "message":<Wrong login/password> (если status=false)
  }
  ```
  
  Например
  ```$xslt
  curl -X POST -H "Content-Type: application/json" -d '{"login":"mylogin","password":"1111"}' http://localhost:8080/api/login
  {"status":0}
  ```
  
- Поменять пароль

  Отправить на ```http://localhost:8080/api/change_password``` json формата
  
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
    "status":<0/1>,
    "message":<Wrong login/password> (если status=false)
  }
  ```
  
  Например
  ```$xslt
  curl -X POST -H "Content-Type: application/json" -d '{"login":"milltanya","old_password":"1111","new_password":"1112"}' http://localhost:8080/api/changepassword
  {"status":0}
  ```
  
- Создать пользователя

  Отправить на ```http://localhost:8080/api/create_user``` json формата
  
  ```$xslt
  {
    *обязательные поля*
    "login":<login>,
    "email":<email>,
    "password":<password>,
    *необязательные поля*
    "name":<name>,
    "dorm":<dorm>,
    "phone":<phone>
  }
  ```  
  Ответ имеет вид 
  
  ```$xslt
  {
    "status":<0/1>,
  }
  ```
  
  Например
  ```$xslt
  curl -X POST -H "Content-Type: application/json" -d '{"login":"new_user","password":"1111","email":"kek@gmail.com","dorm":2}' http://localhost:8080/api/create_user
  {"status":0}
  ```
  
- Создать товар

  Отправить на ```http://localhost:8080/api/create_product``` json формата
  
  ```$xslt
  {
    *обязательные поля*
    "user":<login>,
    "name":<name>,
    "price":<price>,
    *необязательные поля*
    "category":<category>,
    "description":<description>,
    "dorm":<dorm>
  }
  ```  
  Ответ имеет вид 
  
  ```$xslt
  {
    "status":<0/1>,
  }
  ```
  
- Консоль базы данных. Там можно писать sql-запросы

  ```http://localhost:8080/h2-console```
  
  JDBC URL: jdbc:h2:mem:testdb
  
  Если не работает, ищем в логах такую строчку: ```H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:testdb'```

- Просто проверить, что сервер работает

  ```http://localhost:8080/test/```
  
- Добавить в базу пару тестовых записей

  ```http://localhost:8080/test/init```
