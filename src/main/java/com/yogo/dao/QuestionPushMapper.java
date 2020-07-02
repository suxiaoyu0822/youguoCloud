package com.yogo.dao;

import com.yogo.entity.QuestionPush;
import com.yogo.entity.QuestionPushExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionPushMapper {
    long countByExample(QuestionPushExample example);

    int deleteByExample(QuestionPushExample example);

    int insert(QuestionPush record);

    int insertSelective(QuestionPush record);

    List<QuestionPush> selectByExample(QuestionPushExample example);

    int updateByExampleSelective(@Param("record") QuestionPush record, @Param("example") QuestionPushExample example);

    int updateByExample(@Param("record") QuestionPush record, @Param("example") QuestionPushExample example);
}