package com.yogo.bean;


import com.yogo.entity.Client;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public class SearchClientByNameResp {
    private List<Client> clientList;

    public SearchClientByNameResp(List<Client> clientList) {
        this.clientList = clientList;
    }

    public SearchClientByNameResp() {
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    @Override
    public String toString() {
        return "SearchClientByNameResp{" +
                "clientList=" + clientList +
                '}';
    }
}
