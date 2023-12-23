package com.tt.service;

import com.tt.entity.Cart;
import com.tt.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private CartRepository repo;

    public CartService(@Autowired CartRepository repo) {
        this.repo = repo;
    }

    public List<Cart> getAll(){
        return repo.findAll();
    }

    public Cart add(Cart p){
        return repo.save(p);
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
