package com.tt.service;

import com.tt.entity.Account;
import com.tt.entity.DetailsOrder;
import com.tt.repository.DetailsOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailOrderService {
    private DetailsOrderRepository repo;

    public DetailOrderService(@Autowired DetailsOrderRepository repo) {
        this.repo = repo;
    }

    public List<DetailsOrder> getAll(){
        return repo.findAll();
    }

    public DetailsOrder add(DetailsOrder p){
        return repo.save(p);
    }

    public boolean deleteById(int id){
        repo.deleteById(id);
        return true;
    }

    public DetailsOrder findById(int id){
        return repo.findById(id).orElse(null);
    }

    public boolean isExists(int id){
        return repo.existsById(id);
    }
}
