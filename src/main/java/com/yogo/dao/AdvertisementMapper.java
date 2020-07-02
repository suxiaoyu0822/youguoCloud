package com.yogo.dao;

import com.yogo.entity.Advertisement;
import com.yogo.entity.AdvertisementExample;
import com.yogo.entity.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdvertisementMapper {
    int countByExample(AdvertisementExample example);

    int deleteByExample(AdvertisementExample example);

    int deleteByPrimaryKey(Integer advId);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    List<Advertisement> selectByExample(AdvertisementExample example);

    Advertisement selectByPrimaryKey(Integer advId);

    int updateByExampleSelective(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    int updateByExample(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);
    /**
     * Create By cjn
     */
    int countAdv();
    int insertAdv(@Param("content") String content);
    List<Advertisement> selectId(@Param("content") String content);
    List<Client> selectAdvClient(@Param("advId") int advId);
}