### Bank Account Demo Application

### To build
```bash
mvn clean install
```

### To run
```bash
java -jar target/account-demo-0.0.1-SNAPSHOT.jar
```
#### Used Library
* h2 in memory database is used to store bankaccount and customer data
* when application running swagger UI can be accessed using http://localhost:8080/account-app-ws/swagger-ui.html#
* webclient is used to make http calls as RestTemplate will be deprecated soon
* I have used springfox-swagger2 and springfox-swagger-ui to generate api specification from code.

### Current Limitations because of time constraints
* Spring security not added
* password stored as text and no jwt authorization token to secure endpoints
* I have commented the transaction as flush changes result was not
  reflecting during active transaction and http calls need to make in same api as stated in requirement.
* I have added api specification file i have written in my swagger hub account to show my api specification skills. it's
  just an example of specification i have written earlier during open api course on Udemy.
  

  
