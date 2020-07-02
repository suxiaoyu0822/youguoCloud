package com.yogo.service;


import com.yogo.entity.Keyword;

/**
 * Created by hp on 2017/7/16.
 */
public interface KeywordService {
    Keyword selectKeyword(int keywordId);
    Keyword selectByValue(String tagName);
    int deleteByKeywordId(int keywordId);
    int insertKeyword(String tagName);
}
