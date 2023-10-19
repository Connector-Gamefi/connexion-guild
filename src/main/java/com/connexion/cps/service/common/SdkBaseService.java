package com.connexion.cps.service.common;

import com.connexion.cps.config.gosdk.GosdkConfig;
import com.connexion.cps.domain.vo.BaseResp;
import com.connexion.cps.exception.GosdkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SdkBaseService {

    @Autowired
    private GosdkConfig config;

    /**
     * search admin info
     */
    public String getUrl(String path) {
        return config.getHostname() + path;
    }

    public String getBlindBoxUrl(String path) {
        return config.getBlindBoxHostname() + path;
    }

    public void handleRes(BaseResp resp) {
        if (resp.code != 0) {
            throw new GosdkException(resp.getMsg());
        }
    }

    public HashMap<String, Object> formatListRes(BaseResp resp) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("records", resp.data);
        data.put("total", resp.total);
        return data;
    }
}
