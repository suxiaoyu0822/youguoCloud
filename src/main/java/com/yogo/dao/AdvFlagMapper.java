package com.yogo.dao;

import com.yogo.entity.AdvFlag;
import com.yogo.entity.AdvFlagExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdvFlagMapper {
    int countByExample(AdvFlagExample example);

    int deleteByExample(AdvFlagExample example);

    int insert(AdvFlag record);

    int insertSelective(AdvFlag record);

    List<AdvFlag> selectByExample(AdvFlagExample example);

    int updateByExampleSelective(@Param("record") AdvFlag record, @Param("example") AdvFlagExample example);

    int updateByExample(@Param("record") AdvFlag record, @Param("example") AdvFlagExample example);
}