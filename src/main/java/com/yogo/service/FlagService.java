package com.yogo.service;


import com.yogo.entity.Flag;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public interface FlagService {
    List<String> recommendFlags(Integer clientId, String content);

    List<Flag> getFlagByContent(String content);

    List<Flag> selectFlags();

    int insertFlag(String name);

    int deleteFlag(int flagId);

    int updateFlag(int flagId, String name);

    String selectName(int clientId);

    int selectFlagId(String name);

    List<Flag> selectAllFlag(int clientId);

    List<Flag> selectAllUnusedFlag(int clientId);
    /**
     * Create By cjn
     */
    Flag selectAdv(int flagId);
}
