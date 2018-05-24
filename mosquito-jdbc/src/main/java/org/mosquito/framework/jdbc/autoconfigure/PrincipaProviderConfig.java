package org.mosquito.framework.jdbc.autoconfigure;

import org.mosquito.framework.jdbc.service.PrincipalProvider;
import org.mosquito.framework.jdbc.service.impl.PrincipalFallbackProvider;
import org.mosquito.framework.jdbc.service.impl.PrincipalShiroProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(PrincipalProvider.class)
public class PrincipaProviderConfig {

    @Bean
    public PrincipalProvider fallbackPrincipalProvider() {
        try {
            Class.forName("org.apache.shiro.SecurityUtils");
            return new PrincipalShiroProvider();
        } catch (ClassNotFoundException e) {
            return new PrincipalFallbackProvider();
        }
    }

}
