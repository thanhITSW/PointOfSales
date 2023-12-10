package com.tt.controller;

import com.tt.entity.Product;
import com.tt.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/products")
public class ProductController {
    ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(Model model){
        List<Product> productList = service.getAll();
        model.addAttribute("products", productList);
        return "ManageProduct";
    }

    @PostMapping("/add")
    public String add(Product p ,Model model, HttpServletRequest req) throws IOException, ServletException {
//        if(p.getBarCode().equals("") || p.getBarCode() == null){
//            model.addAttribute("errorMessage", "BarCode can not be empty");
//            return "ManageProduct";
//        }
//        else if(p.getName().equals("") || p.getName() == null){
//            model.addAttribute("errorMessage", "Name can not be empty");
//            return "ManageProduct";
//        }
//        else if(p.getCategory().equals("") || p.getCategory() == null){
//            model.addAttribute("errorMessage", "Category can not be empty");
//            return "ManageProduct";
//        }
//        else if(p.getImportPrice() <= 0){
//            model.addAttribute("errorMessage", "Import Price is invalid");
//            return "ManageProduct";
//        }
//        else if(p.getRetailPrice() <= 0 ) {
//            model.addAttribute("errorMessage", "Retail Price is invalid");
//            return "ManageProduct";
//        }
//        else if (image.isEmpty()) {
//            model.addAttribute("errorMessage", "Please provide image");
//            return "ManageProduct";
//        }
//        else{

        Part part = req.getPart("image");
        File file = new File("");
        String currentDirectory = file.getAbsolutePath();
        String uploadPath = currentDirectory + "\\src\\main\\resources\\static\\Image\\products";
        String destination = uploadPath + File.separator + part.getSubmittedFileName();
        String url = "\\Image\\products" + File.separator + part.getSubmittedFileName();
        part.write(destination);

        p.setUrlImage(url);
        p.setCreateDate(service.getCurrentDay());
        service.add(p);
        model.addAttribute("successMessage", "Add product sucessfully!");
        return "redirect:/products";
    }




}
