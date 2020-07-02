package com.yogo.service;


import com.yogo.entity.OperationUser;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public interface OperationUserService {

    int insertUser(String username, String password);

    int deleteUser(int userId);

    int updateUser(int userId, String password);

    List<OperationUser> selectUserIdByUsername(String username);

    String selectUsernameByUserId(int userId);

    List<OperationUser> selectAllUser();

    OperationUser selectUser(int userId, String password);
}
