package com.connexion.cps.config.gosdk;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GosdkConfig {
    @Value("${gosdk.hostname}")
    private String hostname;

    @Value("${gosdk.blindBoxHostname}")
    private String blindBoxHostname;

    @Value("${gosdk.cache.expire.time}")
    private Long cacheExpireTime;
}
