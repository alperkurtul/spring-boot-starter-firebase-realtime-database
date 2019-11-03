package com.github.alperkurtul.useoffirebaserealtimedatabase.repository;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseRealtimeDbRepoServiceImpl;
import com.github.alperkurtul.useoffirebaserealtimedatabase.model.FirebaseAuthKeyAndDocumentId;
import com.github.alperkurtul.useoffirebaserealtimedatabase.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends FirebaseRealtimeDbRepoServiceImpl<FirebaseAuthKeyAndDocumentId, Product, String> {
}
