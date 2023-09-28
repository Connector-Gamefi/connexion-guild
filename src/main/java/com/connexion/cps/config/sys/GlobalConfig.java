package com.connexion.cps.config.sys;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class GlobalConfig {

    @Value("${gosdk.hostname}")
    private String goPath;

    @Value("${user.expire.time}")
    private Long expireTime;

    @Value("${googleAuth.issuer}")
    private String googleAuthIssuer;

    @Value("${googleAuth.url}")
    private String googleAuthUrl;

    @Value("${mailgun.api.key}")
    private String mailgunKey;

    @Value("${mailgun.api.us.url}")
    private String mailgunUsUrl;

    @Value("${mailgun.batch.size}")
    private String mailgunBatchSize;

    @Value("${mailgun.api.market.domain}")
    private String marketDomain;

    @Value("${mailgun.api.market.from}")
    private String marketFrom;

    @Value("${mailgun.api.verify.domain}")
    private String verifyDomain;

    @Value("${mailgun.api.verify.from}")
    private String verifyFrom;

    @Value("${airdrop.title}")
    private String airdropTitle;

    @Value("${airdrop.content}")
    private String airdropContent;
}
