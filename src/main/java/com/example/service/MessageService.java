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

    public int deleteMessage(int id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    public int updateMessage(int id, String messageText) {
        if ((!messageText.isEmpty()) 
        && messageText.length() <= 255
        && messageRepository.existsById(id)) {
            Message message = messageRepository.findById(id);
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getMessagesByAccount(int account_id) {
        return messageRepository.findByPostedBy(account_id);
    }
}
