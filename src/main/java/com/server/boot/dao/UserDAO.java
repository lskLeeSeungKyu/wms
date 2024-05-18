package com.server.boot.dao;

import com.server.boot.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDAO {

    UserDTO findByUser(String ID);
    List<UserDTO> selectUser(Map<String, String> map);
    void userModify(UserDTO userDTO);
    void userGenerate(UserDTO userDTO);
    void userDelete(UserDTO userDTO);
    List<Map<String, Object>> mainPageQuery(String query);

    void messageLog(String message);

    void requestLog(Map<String, String> map);
}
