package com.tt.controller;

import com.tt.entity.Product;
import com.tt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    ProductService service;

    @GetMapping("")
    public String index(Model model){
        List<Product> productList = service.getAll();
        model.addAttribute("products", productList);
        return "Home";
    }

    @PostMapping("/search-products")
    public String searchProduct(Model model, @RequestBody String keyword){
        List<Product> productList = new ArrayList<>();
        return null;
    }
}
