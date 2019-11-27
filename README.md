# An Easy Way to Access `Firebase Realtime Database` in Spring Boot
This project gives you the ability to access to `Firebase Realtime Database`. To achieve this, you just have to add some basic annotations and define a generic repository in your Spring Boot application. These are the list of methods you can use in this release(1.0.4):

- save (@Deprecated in 1.0.5.RELEASE)
- saveWithRandomId (added in 1.0.5.RELEASE)
- saveWithSpecificId (added in 1.0.5.RELEASE)
- read
- update
- delete

## How to Apply

### Configuration
Add this property in your `application.properties`.
```properties
firebase-realtime-database.database-url=[firebase realtime database url]
```

### Dependencies
Primarily, you have to add `spring-boot-starter-web` dependency in your Spring Boot application.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Then, you have to also add this dependency in your `pom.xml`.
```xml
<dependency>
    <groupId>com.github.alperkurtul</groupId>
    <artifactId>spring-boot-starter-firebase-realtime-database</artifactId>
    <version>1.0.4.RELEASE</version>
</dependency>
```

### and How to Use
1) create a class for your Firebase Realtime Database `Document`
2) annotate this class as `@FirebaseDocumentPath` and specify a path for your realtime database
3) create a `String` property for your `authentication idToken` and annotate it as `@FirebaseUserAuthKey`
   - for a valid `authentication idToken`, use <a href="https://github.com/alperkurtul/spring-boot-starter-firebase-user-authentication">`spring-boot-starter-firebase-user-authentication`</a>.
4) create a property for the ID and annotate it with `@FirebaseDocumentId`

```java
@FirebaseDocumentPath("/product")
public class Product {

    @FirebaseUserAuthKey
    private String authKey;
    
    @FirebaseDocumentId
    private String firebaseId;
    
    private String id;
    private String name;
    private BigDecimal price;

}
```

Then create a Repository class. This class must extend the `FirebaseRealtimeDbRepoServiceImpl` class.

```java
@Repository
public class ProductRepository extends FirebaseRealtimeDbRepoServiceImpl<Product, String> {
}
```

At last, put `@EnableFirebaseRealtimeDatabase` just next to `@SpringBootApplication` in your main class of Spring Boot application.

```java
@EnableFirebaseRealtimeDatabase
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

### Demo
Here is a demo that I made for you. <a href="https://github.com/alperkurtul/spring-boot-starter-firebase-realtime-database-demo">`Demo`</a>

### Releases
- 1.0.5.RELEASE
  - BugFix : Annotated fields (@FirebaseUserAuthKey and @FirebaseDocumentId) also were saved to Firebase Database. It is fixed.
  - `save` method `@Deprecated`
  - Instead of `save` method, `saveWithRandomId` method was added.
  - `saveWithSpecificId` method wad added as a new feature. By using this method, you can set specific FirebaseId of your record.
      

## Next
I hope, I will be able continue to add new features in the next. Don't be shy to send your advice to me.
Take care...
