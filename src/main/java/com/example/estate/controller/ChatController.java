package com.example.estate.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.estate.utils.ChatMessage;

@Controller
public class ChatController {
  private SimpMessagingTemplate simpMessagingTemplate;
  @MessageMapping("/chat.sendMessage")
  public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiver(), chatMessage.getSender(), chatMessage);
    return chatMessage;
  }
  
}