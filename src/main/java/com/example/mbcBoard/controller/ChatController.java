package com.example.mbcBoard.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbcBoard.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
	
    private final ChatService chatService;

    @MessageMapping("/send")
    @SendTo("/sub/messages")
    public String sendMessage(String inputMessage) {
        log.info("메시지 들어옴 : {}", inputMessage);
        String processedMessage = chatService.processMessage(inputMessage);
        
        return processedMessage;
    }
}