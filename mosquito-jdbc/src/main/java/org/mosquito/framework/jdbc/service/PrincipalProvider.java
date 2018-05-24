package org.mosquito.framework.jdbc.service;


/**
 * 获取当前登陆用户
 */
public interface PrincipalProvider {

    String getPrincipal();
}
