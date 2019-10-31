package com.github.alperkurtul.useoffirebaserealtimedatabase.repository;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseRealtimeDbRepoServiceImpl;
import com.github.alperkurtul.useoffirebaserealtimedatabase.model.FirebaseAuthKeyAndId;
import com.github.alperkurtul.useoffirebaserealtimedatabase.model.Product;
import org.springframework.stereotype.Repository;

//@Repository
//public class ProductRepository extends FirebaseRealtimeDbRepoServiceImpl<FirebaseAuthKeyAndId, Product, String> {
//}

@Repository
public class ProductRepository extends FirebaseRealtimeDbRepoServiceImpl<Product, String> {
}
