package com.connexion.cps.domain.vo;


import com.connexion.cps.domain.sys.SysUser;

import java.io.Serializable;
import java.util.Set;

/**
 * login user
 *
 * 
 */
public class LoginUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    private String token;

    /**
     * userid
     */
    private Long userid;

    /**
     * username
     */
    private String username;

    /**
     * login time
     */
    private Long loginTime;

    /**
     * expire time
     */
    private Long expireTime;

    /**
     * ip
     */
    private String ipaddr;

    /**
     * permissions
     */
    private Set<String> permissions;

    /**
     * roles
     */
    private Set<String> roles;

    /**
     * user info
     */
    private SysUser sysUser;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Long getUserid()
    {
        return userid;
    }

    public void setUserid(Long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Long getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Long loginTime)
    {
        this.loginTime = loginTime;
    }

    public Long getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Long expireTime)
    {
        this.expireTime = expireTime;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    public Set<String> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<String> roles)
    {
        this.roles = roles;
    }

    public SysUser getSysUser()
    {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser)
    {
        this.sysUser = sysUser;
    }
}
