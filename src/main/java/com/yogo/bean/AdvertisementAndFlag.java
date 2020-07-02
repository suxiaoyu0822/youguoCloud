package com.yogo.bean;


import com.yogo.entity.Advertisement;
import com.yogo.entity.Flag;

import java.util.List;

/**
 * Created by hp on 2017/7/18.
 */
public class AdvertisementAndFlag {
    public Advertisement advertisement;
    public List<Flag> flagList;

    public List<Flag> getFlagList() {
        return flagList;
    }

    public void setFlagList(List<Flag> flagList) {
        this.flagList = flagList;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
