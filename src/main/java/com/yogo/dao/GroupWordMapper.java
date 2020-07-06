package com.yogo.dao;

import com.yogo.entity.GroupWord;
import com.yogo.entity.GroupWordExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupWordMapper {
    long countByExample(GroupWordExample example);

    int deleteByExample(GroupWordExample example);

    int insert(GroupWord record);

    int insertSelective(GroupWord record);

    List<GroupWord> selectByExample(GroupWordExample example);

    int updateByExampleSelective(@Param("record") GroupWord record, @Param("example") GroupWordExample example);

    int updateByExample(@Param("record") GroupWord record, @Param("example") GroupWordExample example);

    List<GroupWord> selectAll();
}