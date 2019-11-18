# An Easy Way to Access `Firebase Realtime Database` in Spring Boot
This project gives you the ability to access to `Firebase Realtime Database`. To achieve this, you just have to add some basic annotations and define a generic repository in your Spring Boot application. These are the list of methods you can use in this release:

- save
- read
- update
- delete

## How to Apply

### Configurations

Add this property in your `application.properties`.
```properties
firebase-realtime-database.database-url=[firebase realtime ratabase url]
```

### Dependencies

Add this dependency in your `pom.xml`.
```xml
<dependency>
    <groupId>com.github.alperkurtul</groupId>
    <artifactId>spring-boot-starter-firebase-realtime-database</artifactId>
    <version>1.0.4.RELEASE</version>
</dependency>
```

### and How to Use

1) create a class for your Firebase Realtime Database `Document`
2) annotate your class as `@FirebaseDocumentPath` and specify a path for your realtime database path.
3) create a `String` property for your authentication idToken and annotate it as `@FirebaseUserAuthKey`.
3) create an ID property and annotate it with `@FirebaseDocumentId`
4) 

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

Then create a class that extends the `DefaultFirebaseRealtimeDatabaseRepository` class, and annotate it with `@Repository`
or mark it as a Spring `@Bean` in your application configuration.

```java
@Repository
public class AlbumRepository extends DefaultFirebaseRealtimeDatabaseRepository<Album, String> {
}
```

Finally, put `@EnableFirebaseRepositories` just next to `@SpringBootApplication` or in any `@Configuration` class in your
application.






<h4>aaaaaaaaa</h4>

<a target="_blank" href="https://oss.sonatype.org/content/repositories/snapshots/com/github/alperkurtul/spring-boot-starter-firebase-realtime-database/">`oss_sonatype_org`</a> 

<a target="_blank" href="https://repo1.maven.org/maven2/com/github/alperkurtul/spring-boot-starter-firebase-realtime-database/">repo1_maven_org</a>

<a target="_blank" href="https://search.maven.org/search?q=g:com.github.alperkurtul%20AND%20a:spring-boot-starter-firebase-realtime-database&core=gav">search_maven_org</a>

<a target="_blank" href="https://mvnrepository.com/artifact/com.github.alperkurtul/spring-boot-starter-firebase-realtime-database">mvnrepository_com</a>




<di>aaaaaa</di>
