package com.server.boot.socket;

import com.server.boot.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    public static Map<String, UserDTO> socketSessionRepository = new HashMap<>();
    private static Set<WebSocketSession> sessions = new HashSet<>();
    private static List<String> userList = new ArrayList<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception { // 클라이언트에서 소켓 연결시 처음 호출
        System.out.println("Connected = " + session);
        HttpSession httpSession = (HttpSession)session.getAttributes().get("HTTP_SESSION");
        socketSessionRepository.put(session.getId(), (UserDTO)httpSession.getAttribute("user"));
        UserDTO user = socketSessionRepository.get(session.getId());

        userList.add(user.getName());
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage fullMessage) throws Exception {
        String stringMessage = fullMessage.getPayload();

        if(stringMessage.indexOf(" : ") == -1) {
            for (WebSocketSession s : sessions) {
                s.sendMessage(new TextMessage(userList.toString()));
            }
        }
        else {
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String currentTime = dateFormat.format(currentDate);
            String trim = String.format("%-10s", "");
            String payload = fullMessage.getPayload();
            String[] parts = payload.split(" : ");
            String sessionUserName = parts[0];
            String message = parts[1];
            String updatedPayload = message + trim + currentTime;
            TextMessage resultMessage = new TextMessage(sessionUserName + ": " + updatedPayload);

            for (WebSocketSession s : sessions) {
                s.sendMessage(resultMessage);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        UserDTO user = socketSessionRepository.get(session.getId());
        userList.remove(user.getName());

        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(userList.toString()));
            }
        }
        socketSessionRepository.remove(session.getId());
        sessions.remove(session);
    }
}
