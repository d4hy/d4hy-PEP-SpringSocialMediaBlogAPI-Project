package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message == null ||
                message.getMessageText() == null ||
                message.getMessageText().isBlank() ||
                message.getMessageText().length() > 255 ||
                message.getPostedBy() == null ||
                !accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("Message is not valid.");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public boolean deleteMessageById(Integer messageId) {
        if (!messageRepository.existsById(messageId)) {
            return false;
        }

        messageRepository.deleteById(messageId);
        return true;
    }

    public int updateMessageText(Integer messageId, Message message) {
        if (message == null ||
                message.getMessageText() == null ||
                message.getMessageText().isBlank() ||
                message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text is not valid.");
        }

        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isEmpty()) {
            throw new IllegalArgumentException("Message does not exist.");
        }

        existingMessage.get().setMessageText(message.getMessageText());
        messageRepository.save(existingMessage.get());
        return 1;
    }

    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
