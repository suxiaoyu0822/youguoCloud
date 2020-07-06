package com.yogo.dao;

import com.yogo.entity.AccessAndNumDuring;
import com.yogo.entity.JoinUp;
import com.yogo.entity.JoinUpExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JoinUpMapper {
    Long getTodayClientCount(@Param("dayStartTime") Long dayStartTime, @Param("nowTime") Long nowTime);

//    int updateAllJoinUp(@Param("clientId")int clientId,@Param("qq") String wx,@Param("wx") String qq,@Param("weibo") String weibo);

    long countByExample(JoinUpExample example);

    int deleteByExample(JoinUpExample example);

    int insert(JoinUp record);

    int insertSelective(JoinUp record);

    List<JoinUp> selectByExample(JoinUpExample example);

    int updateByExampleSelective(@Param("record") JoinUp record, @Param("example") JoinUpExample example);

    int updateByExample(@Param("record") JoinUp record, @Param("example") JoinUpExample example);

    List<JoinUp> selectByClientId(int clientId);

    List<JoinUp> selectByRecord(@Param("accessId") int accessId, @Param("account") String account);

    JoinUp selectLastJoinByClientId(int clientId);

    List<JoinUp> selectDuringClientNum(@Param("startTime") long startTime, @Param("stopTime") long stopTime);

    List<AccessAndNumDuring> selectAccess(@Param("startTime") long startTime, @Param("stopTime") long stopTime);
}