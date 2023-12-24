package com.tt.service;

import com.tt.entity.Cart;
import com.tt.repository.CartRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private CartRepository repo;
    private static final String CART_SESSION_KEY = "cart";

    public CartService(@Autowired CartRepository repo) {
        this.repo = repo;
    }

    public List<Cart> getAll(HttpSession session){
        List<Cart> cart = (List<Cart>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void add(HttpSession session, Cart cartItem){
        List<Cart> cart = getAll(session);
        cart.add(cartItem);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public boolean deleteById(int id){
        repo.deleteById(id);
        return true;
    }

    public Cart findById(int id){
        return repo.findById(id).orElse(null);
    }

    public boolean isExists(int id){
        return repo.existsById(id);
    }
}
