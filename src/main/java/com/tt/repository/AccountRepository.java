package com.tt.repository;

import com.tt.entity.Account;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    List<Account> findAll();
}
