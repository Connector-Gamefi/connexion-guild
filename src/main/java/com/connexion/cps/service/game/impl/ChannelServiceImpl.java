package com.connexion.cps.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.connexion.cps.config.redis.RedisService;
import com.connexion.cps.domain.game.ChannelEntity;
import com.connexion.cps.mapper.game.ChannelMapper;
import com.connexion.cps.service.game.IChannelService;
import com.connexion.cps.utils.jwt.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.connexion.cps.common.constans.UserConstants.CPS_TOKEN_KEY_PREFIX;

/**
 * Channel Service
 */
@Service
public class ChannelServiceImpl implements IChannelService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public ChannelEntity getChannelByLoginName(String loginName) {
        LambdaQueryWrapper<ChannelEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ChannelEntity::getLoginName, loginName);
        return this.channelMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public String genToken(ChannelEntity u) {
        String key = StringUtils.join(CPS_TOKEN_KEY_PREFIX, u.getLoginName() + "");
        String token = JWTUtil.createToken(u.getLoginName());

        redisService.set(key, token);

        return token;
    }
}
