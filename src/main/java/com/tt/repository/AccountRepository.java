package com.tt.repository;

import com.tt.entity.Account;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    @NonNull
    List<Account> findAll();
}
