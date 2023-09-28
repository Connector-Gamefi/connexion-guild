package com.connexion.cps.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.connexion.cps.common.enums.DeletedEnum;
import com.connexion.cps.config.redis.RedisService;
import com.connexion.cps.controller.openapi.request.*;
import com.connexion.cps.domain.game.*;
import com.connexion.cps.mapper.game.OpenApiConfigMapper;
import com.connexion.cps.service.common.I18nService;
import com.connexion.cps.service.game.*;
import com.connexion.cps.utils.uuid.GUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenApiServiceImpl implements IOpenApiService {

    private static final Logger logger = LoggerFactory.getLogger(OpenApiServiceImpl.class);

    @Autowired
    private OpenApiConfigMapper openApiConfigMapper;

    @Autowired
    private I18nService i18nService;
    @Autowired
    private RedisService redisService;

    private static final String OpenApiConfig_RedisKey = "OpenApiConfig_RedisKey_%s";

    private String getOpenApiConfigRedisKey(String appKey) {
        return String.format(OpenApiConfig_RedisKey, appKey);
    }
    @Override
    public OpenApiConfigEntity getConfig(OpenApiConfigGetReq req) {
        LambdaQueryWrapper<OpenApiConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpenApiConfigEntity::getAppID, req.getAppID());
        wrapper.eq(OpenApiConfigEntity::getChannelID, req.getChannelID());
        wrapper.eq(OpenApiConfigEntity::getDeleted, DeletedEnum.UNDELETED.getState());
        return openApiConfigMapper.selectOne(wrapper);
    }

    @Override
    public OpenApiConfigEntity saveConfig(OpenApiConfigSaveReq req) throws Exception {
        LambdaQueryWrapper<OpenApiConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpenApiConfigEntity::getAppID, req.getAppID());
        wrapper.eq(OpenApiConfigEntity::getChannelID, req.getChannelID());
        wrapper.eq(OpenApiConfigEntity::getDeleted, DeletedEnum.UNDELETED.getState());
        OpenApiConfigEntity entity = openApiConfigMapper.selectOne(wrapper);
        if (null != entity) {
            logger.error("openapi saveConfig failed. appID:{}-channelID:{}", req.getAppID(), req.getChannelID());
            throw new Exception(i18nService.getMessage("err.openapi.config.exists"));
        }
        OpenApiConfigEntity configEntity = new OpenApiConfigEntity();
        configEntity.setAppID(req.getAppID());
        configEntity.setChannelID(req.getChannelID());
        configEntity.setLabel(req.getLabel());
        configEntity.setIpWhiteList(req.getIpWhiteList());
        String appKey = GUtils.generateUUID();
        configEntity.setAppKey(appKey);
        configEntity.setSecretKey(GUtils.generateUUID());
        openApiConfigMapper.insert(configEntity);

        String key = getOpenApiConfigRedisKey(appKey);
        redisService.delete(key);

        return configEntity;
    }

    @Override
    public void updateConfig(OpenApiConfigUpdateReq req) {
        LambdaUpdateWrapper<OpenApiConfigEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OpenApiConfigEntity::getId, req.getId());
        wrapper.eq(OpenApiConfigEntity::getDeleted, DeletedEnum.UNDELETED.getState());
        wrapper.set(OpenApiConfigEntity::getIpWhiteList, req.getIpWhiteList().trim());
        openApiConfigMapper.update(null, wrapper);

        OpenApiConfigEntity entity = openApiConfigMapper.selectById(req.getId());
        String key = getOpenApiConfigRedisKey(entity.getAppKey());
        redisService.delete(key);
    }

    @Override
    public void deleteConfig(OpenApiConfigDeleteReq req) {
        OpenApiConfigEntity entity = new OpenApiConfigEntity();
        entity.setId(req.getId());
        entity.setDeleted(DeletedEnum.DELETED.getState());
        openApiConfigMapper.updateById(entity);

        OpenApiConfigEntity queryEntity = openApiConfigMapper.selectById(req.getId());
        String key = getOpenApiConfigRedisKey(queryEntity.getAppKey());
        redisService.delete(key);
    }

    @Override
    public void resetConfig(OpenApiConfigResetReq req) {
        LambdaUpdateWrapper<OpenApiConfigEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OpenApiConfigEntity::getId, req.getId());
        wrapper.eq(OpenApiConfigEntity::getDeleted, DeletedEnum.UNDELETED.getState());
        String appKey = GUtils.generateUUID();
        wrapper.set(OpenApiConfigEntity::getAppKey, appKey);
        wrapper.set(OpenApiConfigEntity::getSecretKey, GUtils.generateUUID());
        openApiConfigMapper.update(null, wrapper);

        String key = getOpenApiConfigRedisKey(appKey);
        redisService.delete(key);
    }

}
