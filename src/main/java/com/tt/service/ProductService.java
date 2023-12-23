package com.tt.service;

import com.tt.entity.Product;
import com.tt.repository.ProductRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.annotation.MultipartConfig;

import javax.swing.plaf.PanelUI;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@MultipartConfig()
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

    public String getCurrentDay(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return currentDate.format(formatter);
    }

    public List<Product> findByName(String name){
        List<Product> productList = new ArrayList<>();
        for(Product p : getAll()){
            if()
        }
    }

    public Product updateById(int id, Product p){
        Optional<Product> x = repo.findById(id);
        if(!x.isPresent()) return null;

        Product t = x.get();
        t.setName(p.getName());
        t.setBarCode(p.getBarCode());
        t.setCreateDate(p.getCreateDate());
        t.setCategory(p.getCategory());
        t.setUrlImage(p.getUrlImage());
        t.setImportPrice(p.getImportPrice());
        t.setRetailPrice(p.getRetailPrice());
        return repo.save(t);
    }


}
