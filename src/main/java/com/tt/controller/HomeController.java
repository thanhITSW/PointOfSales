package com.tt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tt.entity.JsonResponse;
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
        model.addAttribute("errorMessage","");
        return "Home";
    }

    @PostMapping("/search-products")
    @ResponseBody
    public JsonResponse<List<Product>> searchProduct(Model model, @RequestParam String keyword) throws JsonProcessingException {
        List<Product> productList = service.findByName(keyword);
        JsonResponse<List<Product>> jsonResponse = new JsonResponse<>();
        jsonResponse.setCode(0);
        jsonResponse.setMessage("Success");
        jsonResponse.setData(productList);

        return jsonResponse;
    }
}
