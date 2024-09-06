package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.Message;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message addMessage(Message m){
        if(m.getMessageText()=="" || m.getMessageText().length() > 255 || accountRepository.findAccountByAccountId(m.getPostedBy()) == null){
            return null;
        }
        return messageRepository.save(m);
    }

    public void removeMessage(int messageId) {
        messageRepository.deleteById(messageId);
    }

    public List<Message> getUserMessages(int accountId){
        return messageRepository.getMessagesByAccountId(accountId);
    }

    public List<Message> getAllMessages(){
        return messageRepository.getMessages();
    }

    public Message getMessage(int messageId){
        return messageRepository.findMessageByMessageId(messageId);
    }

    public void updateMessage(int messageId, String m){
        Message um = messageRepository.findMessageByMessageId(messageId);
        um.setMessageText(m);
        messageRepository.save(um);
    }
}
