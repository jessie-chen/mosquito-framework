package org.mosquito.framework.jdbc.service.impl;

import org.mosquito.framework.jdbc.service.PrincipalProvider;

public class PrincipalFallbackProvider implements PrincipalProvider {

    @Override
    public String getPrincipal() {
        return "";
    }
}
