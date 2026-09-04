package com.example.service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {


    private final AccountRepository accountRepository;


    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }



    public Account registerAccount(Account account){
        if (account.getUsername() == null ||
                account.getUsername().isBlank() ||
                account.getPassword() == null ||
                account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Input not valid.");
        }

        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new DuplicateUsernameException();
        }

        return accountRepository.save(account);


    }

    public Account login(Account account) {
        if (account == null ||
                account.getUsername() == null ||
                account.getPassword() == null) {
            throw new IllegalArgumentException("Invalid credentials.");
        }

        Account authenticatedAccount =
                accountRepository.findByUsernameAndPassword(
                        account.getUsername(),
                        account.getPassword()
                );

        if (authenticatedAccount == null) {
            throw new IllegalArgumentException("Invalid credentials.");
        }

        return authenticatedAccount;
    }
}
