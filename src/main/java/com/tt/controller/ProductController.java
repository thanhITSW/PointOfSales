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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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

    @GetMapping("/add")
    public String getAdd(){
        return "redirect:/products";
    }

    @PostMapping("/add")
    public String add(Product p ,Model model, HttpServletRequest req) throws IOException, ServletException{
        if(p.getBarCode().equals("") || p.getBarCode() == null){
            model.addAttribute("errorMessage", "BarCode can not be empty");
            return "ManageProduct";
        }
        else if(p.getName().equals("") || p.getName() == null){
            model.addAttribute("errorMessage", "Name can not be empty");
            return "ManageProduct";
        }
        else if(p.getCategory().equals("") || p.getCategory() == null){
            model.addAttribute("errorMessage", "Category can not be empty");
            return "ManageProduct";
        }
        else if(p.getImportPrice() <= 0){
            model.addAttribute("errorMessage", "Import Price is invalid");
            return "ManageProduct";
        }
        else if(p.getRetailPrice() <= 0 ) {
            model.addAttribute("errorMessage", "Retail Price is invalid");
            return "ManageProduct";
        }
        else {

            Part part = req.getPart("image");
            if (part.getSize() > 0){
                File file = new File("");
                String currentDirectory = file.getAbsolutePath();
                String uploadPath = currentDirectory + "\\src\\main\\resources\\static\\Image\\products";
                String destination = uploadPath + File.separator + part.getSubmittedFileName();
                String url = part.getSubmittedFileName();
                part.write(destination);

                p.setUrlImage(url);
            }
            else {
                p.setUrlImage("default.png");
            }

            p.setCreateDate(service.getCurrentDay());
            model.addAttribute("successMessage", "Add product sucessfully!");
            service.add(p);
            return "ManageProduct";
        }
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("products") Product product, Model model, HttpServletRequest req) throws IOException, ServletException {

        try {
            int productId = Integer.parseInt(req.getParameter("id"));

            Product existingProduct = service.findById(productId);
            if (existingProduct == null) {
                model.addAttribute("errorMessage", "Product not found.");
                return "ManageProduct";
            }

            existingProduct.setBarCode(product.getBarCode());
            existingProduct.setName(product.getName());
            existingProduct.setImportPrice(product.getImportPrice());
            existingProduct.setRetailPrice(product.getRetailPrice());
            existingProduct.setCategory(product.getCategory());

            Part part = req.getPart("image");
            if (part.getSize() > 0) {
                File file = new File("");
                String currentDirectory = file.getAbsolutePath();
                String uploadPath = currentDirectory + "\\src\\main\\resources\\static\\Image\\products";
                String destination = uploadPath + File.separator + part.getSubmittedFileName();
                String url = part.getSubmittedFileName();
                part.write(destination);

                //xoa image truoc do
                String imagePath = uploadPath + File.separator + existingProduct.getUrlImage();
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    imageFile.delete();
                }

                existingProduct.setUrlImage(url);
            }

            service.updateById(productId,existingProduct);

            model.addAttribute("successMessage", "Product updated successfully!");
            return "ManageProduct";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating product: " + e.getMessage());
            return "ManageProduct";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int productId, Model model, HttpServletRequest req){
        try {
            Product existingProduct = service.findById(productId);
            if (existingProduct == null) {
                model.addAttribute("errorMessage", "Product not found.");
                return "ManageProduct";
            }

            File file = new File("");
            String currentDirectory = file.getAbsolutePath();
            String uploadPath = currentDirectory + "\\src\\main\\resources\\static\\Image\\products";
            String imagePath = uploadPath + File.separator + existingProduct.getUrlImage();

            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            }

            service.deleteById(productId);

            model.addAttribute("successMessage", "Product deleted successfully!");
            return "ManageProduct";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error deleting product: " + e.getMessage());
            return "ManageProduct";
        }
    }
}
