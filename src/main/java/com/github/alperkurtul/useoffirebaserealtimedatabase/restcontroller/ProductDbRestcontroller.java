package com.github.alperkurtul.useoffirebaserealtimedatabase.restcontroller;

import com.github.alperkurtul.useoffirebaserealtimedatabase.model.FirebaseAuthKeyAndId;
import com.github.alperkurtul.useoffirebaserealtimedatabase.model.Product;
import com.github.alperkurtul.useoffirebaserealtimedatabase.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ProductDbRestcontroller {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/productRead", method = RequestMethod.POST)
    public Product productRead(@RequestParam String authKey, @RequestParam String firebaseId) {

        FirebaseAuthKeyAndId firebaseAuthKeyAndId = new FirebaseAuthKeyAndId(authKey, firebaseId);

        Product product = new Product();

        Product resultProduct = productRepository.read(firebaseAuthKeyAndId, product);

        return resultProduct;

    }

}
