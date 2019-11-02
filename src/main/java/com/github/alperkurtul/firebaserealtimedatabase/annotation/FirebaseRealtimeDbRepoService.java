package com.github.alperkurtul.firebaserealtimedatabase.annotation;

public interface FirebaseRealtimeDbRepoService<FC, FD, ID> {

    FD read(FC fc);

}
