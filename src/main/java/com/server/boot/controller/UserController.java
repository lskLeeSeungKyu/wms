package com.server.boot.controller;

import com.server.boot.dto.UserDTO;
import com.server.boot.service.UserService;
import com.server.boot.socket.MyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://www.lsg-wms.site")
public class UserController {

    private final UserService userService;
    private final MyWebSocketHandler myWebSocketHandler;

    public static List<String> loginUsers = new ArrayList<>();

    public static boolean multiLoginFlag = false;

    @PostMapping("/login.wms")
    public UserDTO login(@RequestBody Map<String, String> loginForm, HttpServletRequest request) {
        UserDTO userDTO = userService.login(loginForm);
        if(userDTO != null) {

            //중복 로그인 체크
           for(String userId : loginUsers) {
               if(Objects.equals(userId, userDTO.getId())) {
                   userDTO.setName("중복");
                   return userDTO;
               }
           }

            // 로그인 완료 - 서버에 세션을 저장하고 있음.
            HttpSession session = request.getSession();
            session.setAttribute("user", userDTO);
            loginUsers.add(userDTO.getId());

            return userDTO;
        }
        return null;
    }

    @PostMapping("/multiLogin.wms")
    public UserDTO multiLogin(@RequestBody Map<String, String> loginForm, HttpServletRequest request) {
        UserDTO userDTO = userService.login(loginForm);
        
        HttpSession session = request.getSession();

        session.setAttribute("user", userDTO);
        multiLoginFlag = true;

        return userDTO;
    }

    @PostMapping("/logout.wms")
    public void logout(HttpSession session) {
        UserDTO logoutUser = (UserDTO)session.getAttribute("user");
        System.out.println("loginUsers = " + loginUsers);
        loginUsers.remove(logoutUser.getId());
        session.invalidate();
    }

    @PostMapping("/browserClose.wms") //현재 안씀
    public void browserClose() {
        System.out.println("close");
    }

    @PostMapping("/userModify")
    public Map<String, Object> userModify(@RequestBody UserDTO userDTO) {
        return userService.userModify(userDTO);
    }

    @PostMapping("/userGenerate")
    public Map<String, Object> userGenerate(@RequestBody UserDTO userDTO) {
        return userService.userGenerate(userDTO);
    }

    @PostMapping("/userDelete")
    public Map<String, Object> userDelete(@RequestBody List<UserDTO> userDTO) {
        return userService.userDelete(userDTO);
    }

    @PostMapping("/sessionInfo")
    public UserDTO sessionInfo(HttpSession session) {
        return (UserDTO)session.getAttribute("user");
    }

/*    @PostMapping("/mainPageQuery")
    public List<Map<String, Object>> mainPageQuery(@RequestBody String query) throws UnsupportedEncodingException {
        return userService.mainPageQuery(URLDecoder.decode(query, "UTF-8"));
    }*/

    @PostMapping("/selectUser")
    public List<UserDTO> selectUser(@RequestBody Map<String, String> map) {
        return userService.selectUser(map);
    }

    @PostMapping("/socketResetRequest")
    public List<UserDTO> userProfiles() {
        Map<String, UserDTO> socketSessionRepository = myWebSocketHandler.socketSessionRepository;
        List<UserDTO> userList = new ArrayList<>(socketSessionRepository.values());
        return userList;
    }

}
