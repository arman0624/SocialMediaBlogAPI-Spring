package com.example.service;

import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account a){
        if(accountRepository.findAccountByUsername(a.getUsername()) != null || a.getPassword().length() < 4 || a.getUsername() == ""){
            return null;
        }
        return accountRepository.save(a);
    }

    public Account login(Account a){
        return accountRepository.findAccountByUsernameAndPassword(a.getUsername(), a.getPassword());
    }

    public Account findAccountByAccountId(int id){
        return accountRepository.findAccountByAccountId(id);
    }
}
