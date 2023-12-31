package com.tt.repository;

import com.tt.entity.Account;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @NonNull
    List<Account> findAll();
}
