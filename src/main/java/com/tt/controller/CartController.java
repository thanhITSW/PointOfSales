package com.tt.controller;

import com.tt.dto.JsonMessage;
import com.tt.entity.Cart;
import com.tt.entity.Product;
import com.tt.service.CartService;
import com.tt.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carts")
public class CartController {

    CartService service;
    ProductService productService;

    public CartController(@Autowired CartService service,@Autowired ProductService productService) {
        this.service = service;
        this.productService = productService;
    }

    @GetMapping("")
    public String getCarts(HttpSession session, Model model){
        model.addAttribute("listCarts", service.getAll(session));
        model.addAttribute("employeeName", "Nguyễn Mai Tấn Thành");
        return "Cart";
    }

    @PostMapping("/add")
    @ResponseBody
    public JsonMessage addCart(@RequestParam String value, @RequestParam String type, HttpSession session){
        JsonMessage jsonMessage = new JsonMessage();
        Cart cart = new Cart();
        cart.setEmployeeName("Nguyễn Mai Tấn Thành");

//        List<Cart> cartList = service.getAll(session);
//        int quantity = 0;
//        for(Cart c : cartList){
//            if(c.getEmployeeName().equals(cart.getEmployeeName()) && )
//        }
        if("id".equals(type)){
            Product p = productService.findById(Integer.parseInt(value));
            cart.setProductName(p.getName());
            cart.setProductBarcode(p.getBarCode());
            cart.setPrice(p.getRetailPrice());
            cart.setQuantity(1);
            cart.setUrl_image(p.getUrlImage());
            cart.setTotalPrice(p.getRetailPrice());
        }

        service.add(session, cart);

        jsonMessage.setCode(0);
        jsonMessage.setMessage("Add product successfully");
        return jsonMessage;
    }
}
