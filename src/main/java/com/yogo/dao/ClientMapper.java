package com.yogo.dao;

import com.yogo.entity.Client;
import com.yogo.entity.ClientAndImlInfo;
import com.yogo.entity.ClientExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientMapper {
    int updateClient(Client client);

    int deleteClient(int clientId);

    long countByExample(ClientExample example);

    int deleteByExample(ClientExample example);

    int insert(Client record);

    int insertSelective(Client record);

    List<Client> selectByExample(ClientExample example);

    int updateByExampleSelective(@Param("record") Client record, @Param("example") ClientExample example);

    int updateByExample(@Param("record") Client record, @Param("example") ClientExample example);

    int selectLastId();
    
    List<ClientAndImlInfo> selectClientDetail(@Param("clientId") int clientId);
}