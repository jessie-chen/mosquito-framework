package org.mosquito.framework.jdbc.autoconfigure;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mosquito.framework.jdbc.service.PrincipalProvider;
import org.mosquito.framework.jdbc.service.impl.PrincipalFallbackProvider;
import org.mosquito.framework.jdbc.service.impl.PrincipalShiroProvider;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.github.pagehelper.PageHelper;
import org.mosquito.framework.jdbc.mybatis.CommonXmlMapperBuilder;
import org.mosquito.framework.jdbc.mybatis.KeyGenMode;
import org.mosquito.framework.jdbc.mybatis.BigDecimalTypeHandler;

/** .
 * 数据库自动配置类
 * @author chenl .
 * 
 */
@Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DatabaseAutoConfiguration {

	@Autowired
	private MybatisProperties properties;

	@Autowired
	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	@Value("${mybatis.keyGenMode:IDENTITY}")
	private String keyGenMode;

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		CommonXmlMapperBuilder builder = new CommonXmlMapperBuilder();
		builder.setBaseResultMap("BaseResultMap");
		builder.setBaseTableName("BaseTable");
		builder.setGenerationType("GenerationType");
		builder.setBaseColumns("BaseColumns");
		builder.setKeyGenMode(KeyGenMode.parse(StringUtils.defaultString(keyGenMode, KeyGenMode.IDENTITY.getCode())));
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		if (this.properties.getConfigLocation() != null) {
			factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
		}
		factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
		factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
		Interceptor[] plugins = new Interceptor[] {pageHelper()};
		factory.setPlugins(plugins);
		factory.setMapperLocations(builder.builderCommonMapper(this.properties.resolveMapperLocations()));
		// factory.setMapperLocations(this.properties.resolveMapperLocations());
		TypeHandler<?>[] typeHandlers = new TypeHandler<?>[1];
		typeHandlers[0] = new BigDecimalTypeHandler();
		factory.setTypeHandlers(typeHandlers);
		return factory.getObject();
	}

	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties p = new Properties();
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		p.setProperty("dialect", "mysql");
		pageHelper.setProperties(p);
		return pageHelper;
	}


	@Bean
	@ConditionalOnMissingBean(PrincipalProvider.class)
	public PrincipalProvider fallbackPrincipalProvider() {
		try {
			Class.forName("org.apache.shiro.SecurityUtils");
			return new PrincipalShiroProvider();
		} catch (ClassNotFoundException e) {
			return new PrincipalFallbackProvider();
		}
	}

}
