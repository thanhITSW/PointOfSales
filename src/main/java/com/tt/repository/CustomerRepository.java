package com.tt.repository;

import com.tt.entity.Cart;
import com.tt.entity.Customer;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    @NonNull
    List<Customer> findAll();
}
