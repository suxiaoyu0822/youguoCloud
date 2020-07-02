package com.yogo.dao;

import com.yogo.entity.OperationUser;
import com.yogo.entity.OperationUserExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OperationUserMapper {
    int deleteOperationUser(int userId);

    int updateOperationUser(OperationUser operationUser);

    List<OperationUser> selectUserIdByUsername(String username);

    String selectUsernameByUserId(int userId);

    long countByExample(OperationUserExample example);

    int deleteByExample(OperationUserExample example);

    int insert(OperationUser record);

    int insertSelective(OperationUser record);

    List<OperationUser> selectByExample(OperationUserExample example);

    int updateByExampleSelective(@Param("record") OperationUser record, @Param("example") OperationUserExample example);

    int updateByExample(@Param("record") OperationUser record, @Param("example") OperationUserExample example);
}