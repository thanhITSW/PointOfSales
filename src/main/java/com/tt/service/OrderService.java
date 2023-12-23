package com.tt.service;

import com.tt.entity.Customer;
import com.tt.entity.Order;
import com.tt.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository repo;

    public OrderService(@Autowired OrderRepository repo) {
        this.repo = repo;
    }

    public List<Order> getAll(){
        return repo.findAll();
    }

    public Order add(Order p){
        return repo.save(p);
    }

    public boolean deleteById(int id){
        repo.deleteById(id);
        return true;
    }

    public Order findById(int id){
        return repo.findById(id).orElse(null);
    }

    public boolean isExists(int id){
        return repo.existsById(id);
    }

    public String getCurrentDay(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return currentDate.format(formatter);
    }
}
