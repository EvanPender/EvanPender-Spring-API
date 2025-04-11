package com.example.controller;

import java.sql.SQLDataException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public ResponseEntity getMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMessage(@PathVariable int messageId) {
        Message message = messageService.getMessage(messageId);
        if (message != null) {
            return ResponseEntity.status(200).body(message);
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getMessagesByAccount(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByAccount(accountId);
        if (messages != null) {
            return ResponseEntity.status(200).body(messages);
        }
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.register(account.getUsername(), account.getPassword());
            if (registeredAccount != null) {
                return ResponseEntity.status(200).body(registeredAccount);
            }
            return ResponseEntity.status(400).build();
        }
        catch(SQLDataException e){
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
        if (loggedInAccount != null) {
            return ResponseEntity.status(200).body(loggedInAccount);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity postMessage(@RequestBody Message message) {
        Message postedMessage = messageService.postMessage(message);
        if (postedMessage != null) {
            return ResponseEntity.status(200).body(postedMessage);
        }
        return ResponseEntity.status(400).build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessage(@PathVariable int messageId, @RequestBody String text) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.readTree(text).path("messageText").asText();
        if (messageService.updateMessage(messageId, message) == 1) {
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).build();
    }
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessage(@PathVariable int messageId) {
        System.out.println("testing");
        if (messageService.deleteMessage(messageId) == 1) {
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).build();
    }
}
