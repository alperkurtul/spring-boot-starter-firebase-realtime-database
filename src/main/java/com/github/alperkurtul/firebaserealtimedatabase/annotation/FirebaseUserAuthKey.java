package com.github.alperkurtul.firebaserealtimedatabase.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FirebaseUserAuthKey {
}
