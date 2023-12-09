package com.tt.service;

import com.tt.entity.Product;
import com.tt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository repo;

    public ProductService(@Autowired ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll(){
        return repo.findAll();
    }

    public Product add(Product p){
        return repo.save(p);
    }

    public boolean deleteById(int id){
        repo.deleteById(id);
        return true;
    }

    public Product findById(int id){
        return repo.findById(id).orElse(null);
    }

    public boolean isExists(int id){
        return repo.existsById(id);
    }

    public String getNameById(int id){
        Optional<Product> p = repo.findById(id);
        return p.map(Product::getName).orElse(null);
    }
}
