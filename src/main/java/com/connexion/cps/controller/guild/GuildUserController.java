package com.connexion.cps.controller.guild;

import com.connexion.cps.common.domain.AjaxResult;
import com.connexion.cps.controller.base.BaseController;
import com.connexion.cps.controller.guild.request.LoginReq;
import com.connexion.cps.controller.guild.response.LoginResultVO;
import com.connexion.cps.domain.game.ChannelEntity;
import com.connexion.cps.service.common.I18nService;
import com.connexion.cps.service.game.IChannelService;
import com.connexion.cps.utils.EncryptUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * guild login controller
 */
@RestController
@RequestMapping("/guild/user")
@Slf4j
public class GuildUserController extends BaseController {

    @Autowired
    private IChannelService channelService;

    @Autowired
    private I18nService i18nService;

    /**
     * CPS login
     *
     * @param loginReq
     * @return
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody @Validated LoginReq loginReq) {
        ChannelEntity admin = channelService.getChannelByLoginName(loginReq.getUsername());

        if (admin == null) {
            return AjaxResult.error(i18nService.getMessage("err.login.failed"));
        }

        if (admin.getBanState() != null && admin.getBanState() == 1) {
            return AjaxResult.error(i18nService.getMessage("err.login.banned"));
        }

        String pwdMD5 = EncryptUtils.md5(loginReq.getPassword());
        if (!pwdMD5.equals(admin.getLoginPwd())) {
            return AjaxResult.error(i18nService.getMessage("err.login.failed"));
        }

        return AjaxResult.success(new LoginResultVO(admin, channelService.genToken(admin)));
    }
}
