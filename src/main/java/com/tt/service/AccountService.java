package com.tt.service;

import com.tt.entity.Account;
import com.tt.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
