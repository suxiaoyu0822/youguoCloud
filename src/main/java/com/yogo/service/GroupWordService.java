package com.yogo.service;


import com.yogo.entity.GroupWord;
import com.yogo.util.Trie;

import java.util.List;

/**
 * Created by Lee on 2017/7/15.
 */
public interface GroupWordService {

    Trie getTrie();

    int getServiceGroupIdByContent(String content);
    /**
     * Create By Cjn
     */
    int insertGroupWord(GroupWord groupWord);
    int deleteGroupWord(GroupWord groupWord);
    List<GroupWord> selectGroupTag(int id);
    int selectTagIsExit(int groupId, String content);
}
