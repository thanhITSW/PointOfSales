package com.tt.repository;

import com.tt.entity.DetailsOrder;
import com.tt.entity.Order;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsOrderRepository extends CrudRepository<DetailsOrder, Integer> {
    @NonNull
    List<DetailsOrder> findAll();
}
