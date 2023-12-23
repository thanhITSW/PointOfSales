package com.tt.repository;

import com.tt.entity.Cart;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    @NonNull
    List<Cart> findAll();
}
