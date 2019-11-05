package com.github.alperkurtul.firebaserealtimedatabase.repository;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseRealtimeDbRepoServiceImpl;
import com.github.alperkurtul.firebaserealtimedatabase.model.FirebaseAuthKeyAndDocumentId;
import com.github.alperkurtul.firebaserealtimedatabase.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends FirebaseRealtimeDbRepoServiceImpl<FirebaseAuthKeyAndDocumentId, Product, String> {
}
