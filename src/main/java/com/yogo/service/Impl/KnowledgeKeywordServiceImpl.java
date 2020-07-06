package com.yogo.service.Impl;

import com.yogo.dao.KnowledgeKeywordMapper;
import com.yogo.entity.KnowledgeKeyword;
import com.yogo.entity.KnowledgeKeywordExample;
import com.yogo.service.KnowledgeKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hp on 2017/7/16.
 */
@Service
public class KnowledgeKeywordServiceImpl implements KnowledgeKeywordService {
    @Autowired
    KnowledgeKeywordMapper knowledgeKeywordMapper;
    
    @Transactional
    public List<KnowledgeKeyword> selectKeywordId(int knowledgeId) {
        KnowledgeKeywordExample example = new KnowledgeKeywordExample();
        KnowledgeKeywordExample.Criteria criteria = example.createCriteria();
        criteria.andKnowledgeIdEqualTo(knowledgeId);
        return knowledgeKeywordMapper.selectByExample(example);
    }

    @Transactional
    public int selectKeywordIdNum(int keywordId) {
        System.out.println(keywordId);
        KnowledgeKeywordExample example = new KnowledgeKeywordExample();
        KnowledgeKeywordExample.Criteria criteria = example.createCriteria();
        criteria.andKeywordIdEqualTo(keywordId);
        return knowledgeKeywordMapper.selectByExample(example).size();
    }

    @Transactional
    public int delete(int keyword, int knowledgeId) {
        KnowledgeKeywordExample example = new KnowledgeKeywordExample();
        KnowledgeKeywordExample.Criteria criteria = example.createCriteria();
        criteria.andKeywordIdEqualTo(keyword);
        criteria.andKnowledgeIdEqualTo(knowledgeId);
        return knowledgeKeywordMapper.deleteByExample(example);
    }

    public int insert(KnowledgeKeyword knowledgeKeyword) {
        return knowledgeKeywordMapper.insert(knowledgeKeyword);
    }
}
