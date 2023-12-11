package com.tt.service;

import com.tt.entity.Account;
import com.tt.entity.Product;
import com.tt.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository repo;

    public AccountService(@Autowired AccountRepository repo) {
        this.repo = repo;
    }

    public List<Account> getAll(){
        return repo.findAll();
    }

    public Account add(Account p){
        return repo.save(p);
    }

    public boolean deleteById(int id){
        repo.deleteById(id);
        return true;
    }

    public Account findById(int id){
        return repo.findById(id).orElse(null);
    }

    public boolean isExists(int id){
        return repo.existsById(id);
    }

    public Account updateById(int id, Account p){
        Optional<Account> x = repo.findById(id);
        if(!x.isPresent()) return null;

        Account t = x.get();
        t.setFullName(p.getFullName());
        t.setEmail(p.getEmail());
        t.setPhoneNumber(p.getPhoneNumber());
        t.setRole(p.getRole());
        t.setActive(p.isActive());
        t.setStatus(p.getStatus());
        return repo.save(t);
    }
}
