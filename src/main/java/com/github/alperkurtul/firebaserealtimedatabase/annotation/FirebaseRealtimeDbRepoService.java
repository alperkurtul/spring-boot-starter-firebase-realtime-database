package com.github.alperkurtul.firebaserealtimedatabase.annotation;

import com.github.alperkurtul.firebaserealtimedatabase.bean.FirebaseSaveResponse;

public interface FirebaseRealtimeDbRepoService<T, ID> {

    public T read(T obj);

    public FirebaseSaveResponse save(T obj);
    public FirebaseSaveResponse saveWithRandomId(T obj);

    public FirebaseSaveResponse saveWithSpecificId(T obj);

    public void update(T obj);

    public void delete(T obj);

}
