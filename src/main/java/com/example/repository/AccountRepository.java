package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

    Account findAccountByAccountId(int accountId);

    Account findAccountByUsernameAndPassword(String username, String password);

    Account findAccountByUsername(String username);
}
