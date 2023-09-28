package com.connexion.cps.controller.openapi;

import com.connexion.cps.common.domain.AjaxResult;
import com.connexion.cps.controller.base.BaseController;
import com.connexion.cps.controller.openapi.request.*;
import com.connexion.cps.service.common.I18nService;
import com.connexion.cps.service.game.IOpenApiService;
import com.connexion.cps.utils.IpUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//OpenAPI
@RestController
@RequestMapping("/admin/openapi/config")
@Slf4j
public class OpenApiConfigController extends BaseController {

    @Autowired
    private IOpenApiService openApiService;

    @Autowired
    private I18nService i18nService;

    @ApiOperation("get openapi configuration")
    @GetMapping("/get")
    public AjaxResult get(@Validated OpenApiConfigGetReq req) {
        return AjaxResult.success(openApiService.getConfig(req));
    }

    @ApiOperation(" create openapi configuration")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody @Validated OpenApiConfigSaveReq req) throws Exception {
        //whrite list ip
        String ips = req.getIpWhiteList();
        if (StringUtils.isNotBlank(ips)) {
            //validate ip
            String[] ip = ips.split(";");
            for (int i=0; i< ip.length; i++) {
                if (!IpUtils.validateIPFormat(ip[i])) {
                    throw new Exception(i18nService.getMessage("ip.not.valid"));
                }
            }
        }
        return AjaxResult.success(openApiService.saveConfig(req));
    }

    @ApiOperation("update openapi configuration")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody @Validated OpenApiConfigUpdateReq req) throws Exception {
        //whitelist ip
        String ips = req.getIpWhiteList();
        if (StringUtils.isNotBlank(ips)) {
            //validate ip
            String[] ip = ips.split(";");
            for (int i=0; i< ip.length; i++) {
                if (!IpUtils.validateIPFormat(ip[i])) {
                    throw new Exception(i18nService.getMessage("ip.not.valid"));
                }
            }
        }
        openApiService.updateConfig(req);
        return AjaxResult.success();
    }

    @ApiOperation("del openapi configuration")
    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody @Validated OpenApiConfigDeleteReq req) {
        openApiService.deleteConfig(req);
        return AjaxResult.success();
    }

    @ApiOperation("reset openapi configuration")
    @PostMapping("/reset")
    public AjaxResult reset(@RequestBody @Validated OpenApiConfigResetReq req) {
        openApiService.resetConfig(req);
        return AjaxResult.success();
    }
}
