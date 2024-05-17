package com.server.boot.socket;

import com.server.boot.controller.UserController;
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

        HttpSession httpSession = (HttpSession)session.getAttributes().get("HTTP_SESSION");

        // 중복 로그인으로 로그인했다면 웹소켓에서 기존 세션을 만료시킨다.
        if(UserController.multiLoginFlag == true) {
            UserDTO loginUser = (UserDTO)httpSession.getAttribute("user");
            String logindUserId = "";

            for(Map.Entry<String, UserDTO> elem : socketSessionRepository.entrySet()) {
                if(Objects.equals(elem.getValue().getId(), loginUser.getId())) {
                    logindUserId = elem.getKey(); //기존 로그인 유저의 sessionid
                }
            }

            for (WebSocketSession s : sessions) {
                if(Objects.equals(s.getId(), logindUserId)) {
                    s.sendMessage(new TextMessage("multiLogin"));
                    sessions.remove(s);
                }
            }
            UserController.loginUsers.add(loginUser.getId());
        }

        System.out.println("Connected = " + session);


        socketSessionRepository.put(session.getId(), (UserDTO)httpSession.getAttribute("user"));
        UserDTO user = socketSessionRepository.get(session.getId());

        userList.add(user.getName());
        sessions.add(session);

        UserController.multiLoginFlag = false;
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

        System.out.println("disConnected = " + session);

        UserDTO user = socketSessionRepository.get(session.getId());
        userList.remove(user.getName());

        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(userList.toString()));
            }
        }

        socketSessionRepository.remove(session.getId());
        sessions.remove(session);

        /*
         * 사용자가 브라우저를 그냥 종료시켰을때, 중복로그인 체크를 위해 서버에서도 로그인한 유저 리스트에서 아이디를 삭제한다.
         * 브라우저를 종료해도 웹소켓은 연결 해제된다.
         **/
        HttpSession httpSession = (HttpSession)session.getAttributes().get("HTTP_SESSION");

        try {
            UserDTO logoutUser = (UserDTO)httpSession.getAttribute("user");

            if(logoutUser != null) {
                UserController.loginUsers.remove(logoutUser.getId());
            }

        } catch (IllegalStateException e) {
            System.out.println("예외 @@@@@@@" + e);
        }

    }
}
