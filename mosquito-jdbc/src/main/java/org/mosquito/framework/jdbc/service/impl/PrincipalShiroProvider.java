package org.mosquito.framework.jdbc.service.impl;

import org.apache.shiro.SecurityUtils;
import org.mosquito.framework.jdbc.service.PrincipalProvider;

public class PrincipalShiroProvider implements PrincipalProvider {

    @Override
    public String getPrincipal() {
//        return (String) SecurityUtils.getSubject().getPrincipal();
        return "shiro";
    }
}
