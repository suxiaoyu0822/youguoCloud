package com.yogo.bean;


import com.yogo.entity.Keyword;
import com.yogo.entity.Knowledge;

import java.util.List;

/**
 * Created by hp on 2017/7/16.
 */
public class KnowledgeAndKeyword {
    Knowledge knowledge;
    List<Keyword> keywords;

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }
}
    