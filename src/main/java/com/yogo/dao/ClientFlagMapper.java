package com.yogo.dao;

import com.yogo.entity.ClientFlag;
import com.yogo.entity.ClientFlagExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientFlagMapper {
    int deleteClientFlag(@Param("clientId") int clientId, @Param("flagId") int flagId);

    long countByExample(ClientFlagExample example);

    int deleteByExample(ClientFlagExample example);

    int insert(ClientFlag record);

    int insertSelective(ClientFlag record);

    List<ClientFlag> selectByExample(ClientFlagExample example);

    int updateByExampleSelective(@Param("record") ClientFlag record, @Param("example") ClientFlagExample example);

    int updateByExample(@Param("record") ClientFlag record, @Param("example") ClientFlagExample example);
}