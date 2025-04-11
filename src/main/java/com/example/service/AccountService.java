package com.example.service;

import java.sql.SQLDataException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account login(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

    public Account register(String username, String password) throws SQLDataException {
        if (accountRepository.findByUsername(username) != null) {
            throw new SQLDataException();
        }
        if (password.length() >= 4 && !username.isEmpty()) {
            Account account = new Account(username, password);
            return accountRepository.save(account);
        }
        return null;
    }
}
