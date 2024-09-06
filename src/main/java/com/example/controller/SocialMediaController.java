package com.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;
import java.util.List;
import java.util.Map;
import com.example.entity.*;
import com.example.service.*;


@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService, ObjectMapper objectMapper){
        this.accountService = accountService;
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginHandler(@RequestBody Account account){
        try{
            Account temp = accountService.login(account);
            if(temp != null){
                String responseBody = objectMapper.writeValueAsString(temp);
                return ResponseEntity.ok(responseBody);
            }
            return ResponseEntity.status(401).build();
        }
        catch(JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerHandler(@RequestBody Account account){
        try{
            Account temp = accountService.addAccount(account);
            if(temp != null){
                String responseBody = objectMapper.writeValueAsString(temp);
                return ResponseEntity.ok(responseBody);
            }
            return ResponseEntity.status(409).build();
        }
        catch(JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<String> createMessageHandler(@RequestBody Message message){
        try{
            Message m = messageService.addMessage(message);
            if(m != null){
                String responseBody = objectMapper.writeValueAsString(m);
                return ResponseEntity.ok(responseBody);
            }
            return ResponseEntity.status(400).build();
        }
        catch(JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<String> deleteMessageHandler(@PathVariable("id") int messageId){
        try{
            if(messageService.getMessage(messageId) != null){
                messageService.removeMessage(messageId);
                return ResponseEntity.ok("1");
            }
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().build();
        }
        
    }

    @GetMapping("/accounts/{id}/messages")
    public ResponseEntity<String> getUserMessagesHandler(@PathVariable("id") int accountId){
        try{
            if(accountService.findAccountByAccountId(accountId) != null){
                List<Message> messages = messageService.getUserMessages(accountId);
                String responseBody = objectMapper.writeValueAsString(messages);
                return ResponseEntity.ok(responseBody);
            }
            return ResponseEntity.ok().build();
        }
        catch(JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<String> getAllMessages(){
        try{
            List<Message> messages = messageService.getAllMessages();
            String responseBody = objectMapper.writeValueAsString(messages);
            return ResponseEntity.ok(responseBody);
        }
        catch(JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<String> getMessageHandler(@PathVariable("id") int messageid){
        try{
            Message m = messageService.getMessage(messageid);
            if(m != null){
                String responseBody = objectMapper.writeValueAsString(m);
                return ResponseEntity.ok(responseBody);
            }
            return ResponseEntity.ok().build();
        }
        catch(JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }

    @PatchMapping("/messages/{id}")
    public ResponseEntity<String> updateMessageHandler(@PathVariable("id") int messageId, @RequestBody Map<String, String> updateFields){
        String newMessage = updateFields.get("messageText");
        if(newMessage.length() > 255 || newMessage == "" || messageService.getMessage(messageId) == null){
            return ResponseEntity.status(400).build();
        }
        messageService.updateMessage(messageId, newMessage);
        return ResponseEntity.ok("1");
    }
}
