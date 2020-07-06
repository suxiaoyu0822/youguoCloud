package com.yogo.dao;

import com.yogo.entity.Knowledge;
import com.yogo.entity.KnowledgeExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface KnowledgeMapper {
    long countByExample(KnowledgeExample example);

    int deleteByExample(KnowledgeExample example);

    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    List<Knowledge> selectByExample(KnowledgeExample example);

    int updateByExampleSelective(@Param("record") Knowledge record, @Param("example") KnowledgeExample example);

    int updateByExample(@Param("record") Knowledge record, @Param("example") KnowledgeExample example);

    int selectLastId();

    //查询到的是所有知识库，最高权限
    List<Knowledge> selectByKeywordId(int keywordId);

    //level小于等于1，即机器人能看到的知识库
    List<Knowledge> selectByKeywordId1(int keywordId);

    int deleteById(int knowledgeId);

    Knowledge selectByKnowledgeId(int knowledgeId);
    
    List<Knowledge> selectKnowledgeBySearchName(@Param("keyword") String keyword);
}