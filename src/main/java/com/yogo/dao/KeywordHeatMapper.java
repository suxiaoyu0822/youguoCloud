package com.yogo.dao;

import com.yogo.entity.KeywordAndHeat;
import com.yogo.entity.KeywordHeat;
import com.yogo.entity.KeywordHeatExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface KeywordHeatMapper {
    long countByExample(KeywordHeatExample example);

    int deleteByExample(KeywordHeatExample example);

    int insert(KeywordHeat record);

    int insertSelective(KeywordHeat record);

    List<KeywordHeat> selectByExample(KeywordHeatExample example);

    int updateByExampleSelective(@Param("record") KeywordHeat record, @Param("example") KeywordHeatExample example);

    int updateByExample(@Param("record") KeywordHeat record, @Param("example") KeywordHeatExample example);
    /**
     * CJN
     */
    List<KeywordAndHeat> getDayHeat(@Param("timestamp") long timestamp);
}