package com.github.alperkurtul.firebaserealtimedatabase.annotation;

import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseRealtimeDatabaseConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Documented
@Import(FirebaseRealtimeDatabaseConfiguration.class)
public @interface EnableFirebaseRealtimeDatabase {
}
