package com.connexion.cps.service.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * i18n
 */
@Service
public class I18nService {

    private final Logger logger = LoggerFactory.getLogger(I18nService.class);

    private final MessageSource messageSource;

    public I18nService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Object...params) {

        try {
            String msg = messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
            if (StringUtils.isEmpty(msg)) {
                return StringUtils.EMPTY;
            }
            return String.format(msg, params);
        }catch (Exception e){
            logger.error("getMessage failed with exception:" + e.getMessage(), e);
        }
        return "";
    }
}
