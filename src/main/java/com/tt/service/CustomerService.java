package com.tt.service;

import com.tt.entity.Customer;
import com.tt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    CustomerRepository repo;

    public CustomerService(@Autowired CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> getAll(){
        return repo.findAll();
    }

    public Customer add(Customer p){
        return repo.save(p);
    }

    public boolean deleteById(int id){
        repo.deleteById(id);
        return true;
    }

    public Customer findById(int id){
        return repo.findById(id).orElse(null);
    }

    public boolean isExists(int id){
        return repo.existsById(id);
    }
}
