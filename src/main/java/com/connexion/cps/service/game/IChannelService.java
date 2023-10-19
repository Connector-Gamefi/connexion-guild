package com.connexion.cps.service.game;

import com.connexion.cps.domain.game.ChannelEntity;

public interface IChannelService {

    ChannelEntity getChannelByLoginName(String loginName);

    String genToken(ChannelEntity u);
}
