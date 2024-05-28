package com.server.boot.service;

import com.server.boot.dao.UserDAO;
import com.server.boot.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;

    @Transactional(readOnly = true)
    public UserDTO login(Map<String, String> loginForm) {
        UserDTO user = userDAO.findByUser(loginForm.get("ID"));
        if(user == null || !Objects.equals(loginForm.get("PW"), user.getPw())) return null;

        return user;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "user")
    public List<UserDTO> selectUser(Map<String, String> map) {
        return userDAO.selectUser(map);
    }

    @Transactional
    @CacheEvict(cacheNames = "user", allEntries = true)
    public Map<String, Object> userModify(UserDTO userDTO) {
        userDAO.userModify(userDTO);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        resultMap.put("user", userDTO);
        return resultMap;
    }

    @Transactional
    @CacheEvict(cacheNames = "user", allEntries = true)
    public Map<String, Object> userGenerate(UserDTO userDTO) {
        userDAO.userGenerate(userDTO);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        resultMap.put("user", userDTO);
        return resultMap;
    }

    @Transactional
    @CacheEvict(cacheNames = "user", allEntries = true)
    public Map<String, Object> userDelete(List<UserDTO> userDTO) {
        for(UserDTO user : userDTO) {
            userDAO.userDelete(user);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        resultMap.put("user", userDTO);
        return resultMap;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> mainPageQuery(String query) {
        query = query.substring(0, query.length() - 1);
        return userDAO.mainPageQuery(query);
    }

    @Transactional
    public void messageLog(String message) {
        userDAO.messageLog(message);
    }

    @Transactional
    public void requestLog(Map<String, String> map) { userDAO.requestLog(map); }

    @Transactional
    public void filterLog(Map<String, String> map) { userDAO.filterLog(map); }

}
