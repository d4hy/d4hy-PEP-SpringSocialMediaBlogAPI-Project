package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SocialMediaController {

        private final AccountService accountService;
        private final MessageService messageService;

        public SocialMediaController(AccountService accountService, MessageService messageService) {
                this.accountService = accountService;
                this.messageService = messageService;
        }

        @PostMapping("/register")
        public ResponseEntity<Account> register(@RequestBody Account account){
                try{
                        Account savedAccount = accountService.registerAccount(account);
                        return ResponseEntity.ok(savedAccount);
                } catch (IllegalArgumentException e){
                        return ResponseEntity.status(400).build();
                } catch (DuplicateUsernameException e) {
                        return ResponseEntity.status(409).build();
                }

        }

        @PostMapping("/login")
        public ResponseEntity<Account> login(@RequestBody Account account){
                try{
                        Account authenticatedAccount = accountService.login(account);
                        return ResponseEntity.ok(authenticatedAccount);
                } catch (IllegalArgumentException e){
                        return ResponseEntity.status(401).build();
                }
        }

        @PostMapping("/messages")
        public ResponseEntity<Message> createMessage(@RequestBody Message message) {
                try {
                        return ResponseEntity.ok(messageService.createMessage(message));
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(400).build();
                }
        }

        @GetMapping("/messages")
        public ResponseEntity<List<Message>> getAllMessages() {
                return ResponseEntity.ok(messageService.getAllMessages());
        }

        @GetMapping("/messages/{messageId}")
        public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
                Message message = messageService.getMessageById(messageId);
                if (message == null) {
                        return ResponseEntity.ok().build();
                }
                return ResponseEntity.ok(message);
        }

        @DeleteMapping("/messages/{messageId}")
        public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
                if (!messageService.deleteMessageById(messageId)) {
                        return ResponseEntity.ok().build();
                }
                return ResponseEntity.ok(1);
        }

        @PatchMapping("/messages/{messageId}")
        public ResponseEntity<Integer> updateMessageText(
                @PathVariable Integer messageId,
                @RequestBody Message message) {
                try {
                        return ResponseEntity.ok(messageService.updateMessageText(messageId, message));
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(400).build();
                }
        }

        @GetMapping("/accounts/{accountId}/messages")
        public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId) {
                return ResponseEntity.ok(messageService.getMessagesByAccountId(accountId));
        }
}
