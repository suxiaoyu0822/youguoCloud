package com.yogo.dao;

import com.yogo.entity.Flag;
import com.yogo.entity.FlagExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FlagMapper {
    List<Flag> selectAllUnusedFlag(int clientId);

    List<Flag> selectAllFlag(int clientId);

    int deleteFlag(int flagId);

    int updateFlag(Flag flag);

    Object selectFlagId(String name);

    String selectName(int flagId);

    long countByExample(FlagExample example);

    int deleteByExample(FlagExample example);

    int insert(Flag record);

    int insertSelective(Flag record);

    List<Flag> selectByExample(FlagExample example);

    int updateByExampleSelective(@Param("record") Flag record, @Param("example") FlagExample example);

    int updateByExample(@Param("record") Flag record, @Param("example") FlagExample example);
}