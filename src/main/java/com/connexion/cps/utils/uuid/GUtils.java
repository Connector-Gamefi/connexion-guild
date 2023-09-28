package com.connexion.cps.utils.uuid;

import com.connexion.cps.utils.security.EncryptUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class GUtils {

    /**
     * salt password
     * @param uid
     * @param password
     * @return
     */
    public static String makeupPassword(String uid, String password) {

        return EncryptUtils.md5(StringUtils.join(uid, password)).toUpperCase();

    }

    /***
     * gen uuid
     */
    public static String generateUUID(){

        UUID uuid = UUID.randomUUID();
        String txt = uuid.toString().replace("-","");

        return txt;
    }

    /**
     * format url
     * @param url
     * @return
     */
    public static String formatUrl(String url) {

        if (StringUtils.isEmpty(url)) {
            return StringUtils.EMPTY;
        }

        url = url.trim();

        while (url.endsWith("/")) {
            url = url.substring(0, url.length()-1);
        }

        return url;

    }
}
