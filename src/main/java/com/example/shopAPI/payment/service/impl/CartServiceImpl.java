package com.example.shopAPI.payment.service.impl;

import com.example.shopAPI.auth.model.AuthUser;
import com.example.shopAPI.auth.repository.UserRepository;
import com.example.shopAPI.mail.dto.EmailDetails;
import com.example.shopAPI.mail.service.EmailService;
import com.example.shopAPI.payment.service.ShoppingCartService;
import com.example.shopAPI.product.model.Product;
import com.example.shopAPI.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class CartServiceImpl implements ShoppingCartService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    EmailService emailService;

    @Override
    public String add(String email, String productId) {

        Product product = productRepository.findProductById(productId);

        mongoTemplate.update(AuthUser.class)
                .matching(where("email").is(email))
                .apply(new Update().push("Product", product))
                .first();

        return "Item added";
    }

    @Override
    public String remove(String email, String productId) {
        Product product = productRepository.findProductById(productId);

        mongoTemplate.update(AuthUser.class)
                .matching(where("email").is(email))
                .apply(new Update().pull("Product", product))
                .first();

        return "Item removed";
    }

    @Override
    public String pay(String email) {

        mongoTemplate.update(AuthUser.class)
                .matching(where("email").is(email))
                .apply(new Update().unset("Product"))
                .first();

        String msgBody = """
                Halo,
                Pesananmu selesai. Terima kasih sudah berbelanja dan mendukung para penjual di Tokopedia. 
                Bagaimana kondisi barangnya?
                """;
        String subject = "Pesanan Selesai";

        EmailDetails emailDetails = new EmailDetails(email, msgBody, subject);
        return emailService.sendSimpleMail(emailDetails);
    }
}
