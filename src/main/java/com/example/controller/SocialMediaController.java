package com.example.controller;


import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

        private final AccountService accountService;

        public SocialMediaController(AccountService accountService) {
                this.accountService = accountService;
        }

        @PostMapping("/register")
        public ResponseEntity<Account> postAccount(@RequestBody Account account){
                try{
                        Account savedAcc = accountService.registerAccount(account);
                        return ResponseEntity.ok(savedAcc);
                } catch (IllegalArgumentException e){
                        return ResponseEntity.status(400).build();
                } catch (DuplicateUsernameException e) {
                        return ResponseEntity.status(409).build();
                }

        }

        @PostMapping("/login")
        public ResponseEntity<Account> postLogin(@RequestBody Account account){
                try{
                        Account authenticatedAccount = accountService.login(account);
                        return ResponseEntity.ok(authenticatedAccount);
                } catch (IllegalArgumentException e){
                        return ResponseEntity.status(401).build();
                }
        }
}
