package com.github.alperkurtul.useoffirebaserealtimedatabase.restcontroller;

import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseDbConfig;
import com.github.alperkurtul.useoffirebaserealtimedatabase.model.FirebaseAuthKeyAndDocumentId;
import com.github.alperkurtul.useoffirebaserealtimedatabase.model.Product;
import com.github.alperkurtul.useoffirebaserealtimedatabase.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductDbRestcontroller {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/productRead", method = RequestMethod.POST)
    public Product productRead(@RequestParam String authKey, @RequestParam String firebaseId) {

        Product resultProduct = productRepository.read(new FirebaseAuthKeyAndDocumentId(authKey, firebaseId));

        return resultProduct;

    }

}
