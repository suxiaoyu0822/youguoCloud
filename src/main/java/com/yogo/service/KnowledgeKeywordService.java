package com.yogo.service;


import com.yogo.entity.KnowledgeKeyword;

import java.util.List;

/**
 * Created by hp on 2017/7/16.
 */
public interface KnowledgeKeywordService {
    List<KnowledgeKeyword> selectKeywordId(int knowledgeId);
    int selectKeywordIdNum(int keywordId);
    int delete(int keyword, int knowledgeId);
    int insert(KnowledgeKeyword knowledgeKeyword);
}
