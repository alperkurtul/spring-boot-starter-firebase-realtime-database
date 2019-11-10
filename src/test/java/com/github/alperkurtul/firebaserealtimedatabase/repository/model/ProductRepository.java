package com.github.alperkurtul.firebaserealtimedatabase.repository.model;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseRealtimeDbRepoServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends FirebaseRealtimeDbRepoServiceImpl<Product, String> {
}
