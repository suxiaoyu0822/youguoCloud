package com.yogo.service;


import com.yogo.entity.Advertisement;
import com.yogo.entity.Client;

import java.util.List;

/**
 * Created by hp on 2017/7/15.
 */

public interface AdvertisementService {
    int getTotalAdv();
    List<Advertisement> getTotalAdvInfo();
    int insertAdv(String content, List<String> flagList);
    int selectByContent(String content);
    int selectId(String content);
    int delete(int id);
    int updateAdv(Advertisement advertisement);
    List<Client> selectAdvClient(int advId);
}
