package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Message postMessage(Message message) {
        if ((!message.getMessageText().isEmpty()) 
        && message.getMessageText().length() <= 255
        && accountRepository.existsById(message.getPostedBy())) {
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessage(int id) {
        return messageRepository.findById(id);
    }

    public Message deleteMessage(int id) {
        return messageRepository.deleteById(id);
    }

    public Message updateMessage(int id, String messageText) {
        if ((!messageText.isEmpty()) 
        && messageText.length() <= 255
        && accountRepository.existsById(id)) {
            Message message = messageRepository.findById(id);
            message.setMessageText(messageText);
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getMessagesByAccount(int account_id) {
        return messageRepository.findByPostedBy(account_id);
    }
}
