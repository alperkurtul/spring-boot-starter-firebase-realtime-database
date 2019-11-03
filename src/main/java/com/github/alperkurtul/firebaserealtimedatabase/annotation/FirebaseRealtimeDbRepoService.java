package com.github.alperkurtul.firebaserealtimedatabase.annotation;

import com.github.alperkurtul.firebaserealtimedatabase.bean.FirebaseSaveResponse;

public interface FirebaseRealtimeDbRepoService<FC, FD, ID> {
    // Type <FC> : Class for Firebase Connection info and Document Id info
    // Type <FD> : Class for Firebase Document

    public FD read(FC fc);

    public FirebaseSaveResponse save(FC fc, FD fd);

    public void update(FC fc, FD fd);

    public void delete(FC fc);

}
