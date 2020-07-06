package com.yogo.service;


import com.yogo.bean.Message;
import com.yogo.bean.RobotChat;
import com.yogo.entity.Knowledge;
import com.yogo.util.Trie;

import java.util.List;

/**
 * Created by Lee on 2017/7/9.
 */
public interface KnowledgeService {

    int addKnowledgeAndKey(String question, String answer, int level, int author, long time, List<String> keyList);

    int removeKnowledge(int knowledgeId);

    Trie readTrieFromDB();

    Trie readTrieFromFile();

    List<Knowledge> getKnowledgeByContent(String content);

    Message<RobotChat> getRobotChat(String content);

    /***
     * Create By Cjn
     */
    List<Knowledge> selectKnowledgeBySearchName(String keyword);
    List<Knowledge> selectKnowledge();
    int updateKnowledge(Knowledge knowledge);
    Knowledge selectById(int id);
    Knowledge selectByContent(String content);
}
