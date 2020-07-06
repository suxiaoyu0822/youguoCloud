package com.yogo.dao;

import com.yogo.entity.CommonLanguage;
import com.yogo.entity.CommonLanguageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommonLanguageMapper {
    List<CommonLanguage> selectAllServiceAndCommonLanguage(int serviceId);

    long countByExample(CommonLanguageExample example);

    int deleteByExample(CommonLanguageExample example);

    int insert(CommonLanguage record);

    int insertSelective(CommonLanguage record);

    List<CommonLanguage> selectByExample(CommonLanguageExample example);

    int updateByExampleSelective(@Param("record") CommonLanguage record, @Param("example") CommonLanguageExample example);

    int updateByExample(@Param("record") CommonLanguage record, @Param("example") CommonLanguageExample example);

    int deleteCommonLanguage(int commonLanguageId);
    
    int addCommonLanguageFrequency(int commonLanguageId);

    int updateCommonLanguage(CommonLanguage commonLanguage);
}