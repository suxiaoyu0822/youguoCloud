package com.yogo.dao;

import com.yogo.entity.KnowledgeKeyword;
import com.yogo.entity.KnowledgeKeywordExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface KnowledgeKeywordMapper {
    long countByExample(KnowledgeKeywordExample example);

    int deleteByExample(KnowledgeKeywordExample example);

    int insert(KnowledgeKeyword record);

    int insertSelective(KnowledgeKeyword record);

    List<KnowledgeKeyword> selectByExample(KnowledgeKeywordExample example);

    int updateByExampleSelective(@Param("record") KnowledgeKeyword record, @Param("example") KnowledgeKeywordExample example);

    int updateByExample(@Param("record") KnowledgeKeyword record, @Param("example") KnowledgeKeywordExample example);

    int deleteByKnowledgeId(int knowledgeId);

    int selectByKeywordId(int keywordId);
}