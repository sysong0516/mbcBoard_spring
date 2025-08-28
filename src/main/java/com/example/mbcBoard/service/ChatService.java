package com.example.mbcBoard.service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatService {
    public String processMessage(String inputMessage) {
        log.info("ChatService에서 메시지 처리: {}", inputMessage);
        // 메시지 가공 로직 추가 가능
        return inputMessage;
    }
}
