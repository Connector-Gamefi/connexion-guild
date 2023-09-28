package com.connexion.cps.service.game;

import com.connexion.cps.controller.openapi.request.*;
import com.connexion.cps.domain.game.OpenApiConfigEntity;

public interface IOpenApiService {

    OpenApiConfigEntity getConfig(OpenApiConfigGetReq req);

    OpenApiConfigEntity saveConfig(OpenApiConfigSaveReq req) throws Exception;

    void updateConfig(OpenApiConfigUpdateReq req);

    void deleteConfig(OpenApiConfigDeleteReq req);

    void resetConfig(OpenApiConfigResetReq req);

}
