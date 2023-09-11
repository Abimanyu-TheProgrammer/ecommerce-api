package com.example.shopAPI.payment.controller;


import com.example.shopAPI.auth.service.JwtService;
import com.example.shopAPI.payment.dto.CartDTO;
import com.example.shopAPI.payment.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService cartService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> addToCart(@RequestHeader (name="Authorization") String token,
                                            @RequestBody CartDTO CartDTO) {

        String email = jwtService.extractUserName(token.substring(7));
        String productId = CartDTO.getProductId();
        return new ResponseEntity<>(cartService.add(email, productId), HttpStatus.OK);
    }

    @DeleteMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeFromCart(@RequestHeader (name="Authorization") String token,
                                            @RequestBody CartDTO CartDTO) {

        String email = jwtService.extractUserName(token.substring(7));
        String productId = CartDTO.getProductId();
        return new ResponseEntity<>(cartService.remove(email, productId), HttpStatus.OK);
    }

    @PostMapping("/cart/pay")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeFromCart(@RequestHeader (name="Authorization") String token) {
        String email = jwtService.extractUserName(token.substring(7));
        return new ResponseEntity<>(cartService.pay(email), HttpStatus.OK);
    }

}
