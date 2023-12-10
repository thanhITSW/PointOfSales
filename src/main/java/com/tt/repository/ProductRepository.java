package com.tt.repository;

import com.tt.entity.Product;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @NonNull
    List<Product> findAll();

}
