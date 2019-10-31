package com.github.alperkurtul.firebaserealtimedatabase.annotation;

//public interface FirebaseRealtimeDbRepoService<AUTH, DOC, ID> {
//
//    DOC read(AUTH obj1, DOC obj2);
//
//}

public interface FirebaseRealtimeDbRepoService<T, ID> {

    T read(T obj1);

}
