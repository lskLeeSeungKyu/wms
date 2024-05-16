package com.server.boot.controller;

import com.server.boot.dto.UserDTO;
import com.server.boot.service.UserService;
import lombok.RequiredArgsConstructor;
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
@CrossOrigin(origins = "http://10.101.52.96:8081")
public class UserController {

    private final UserService userService;

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

        System.out.println("userDTO = " + userDTO.getName());
        HttpSession session = request.getSession();

        session.setAttribute("user", userDTO);
        multiLoginFlag = true;

        return userDTO;
    }

    @PostMapping("/logout.wms")
    public void logout(HttpSession session) {
        UserDTO logoutUser = (UserDTO)session.getAttribute("user");
        loginUsers.remove(logoutUser.getId());
        session.invalidate();
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

    @PostMapping("/mainPageQuery")
    public List<Map<String, Object>> mainPageQuery(@RequestBody String query) throws UnsupportedEncodingException {
        return userService.mainPageQuery(URLDecoder.decode(query, "UTF-8"));
    }
}
