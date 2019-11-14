package com.github.alperkurtul.firebaserealtimedatabase.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FirebaseDocumentPath {

    String value() default "";

}
