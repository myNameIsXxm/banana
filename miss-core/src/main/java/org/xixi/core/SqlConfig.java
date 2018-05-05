package org.xixi.core;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:app.properties")
@EnableTransactionManagement
public class SqlConfig {

	private static final Logger logger = LoggerFactory.getLogger(SqlConfig.class);

	@Value("${sql.conn.driver}")
	private String driverClassName;

	//////////////////////////////////////////////////////////

	@Value("${sql.conn.url}")
	private String url;

	@Value("${sql.conn.username}")
	private String username;

	@Value("${sql.conn.password}")
	private String password;

	////////////////////////////////////////////////////////

	@Value("${hibernate.dialect}")
	private String dialect;

	@Value("${hibernate.format_sql}")
	private String formatSql;

	@Value("${hibernate.show_sql}")
	private String showSql;

	/*@Bean(name = "dataSource")
	public DataSource getDataSource() {
		logger.info("初始化 druid,用户：" + username);
		DruidDataSource bean = new DruidDataSource();
		bean.setDriverClassName(driverClassName);
		// 基本属性 url、user、password
		bean.setUrl(url);
		bean.setUsername(username);
		bean.setPassword(password);
		// 配置初始化大小、最小、最大
		bean.setInitialSize(1);
		bean.setMinIdle(1);
		bean.setMaxActive(10);
		// 配置获取连接等待超时的时间
		bean.setMaxWait(10000);
		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		bean.setTimeBetweenEvictionRunsMillis(60000);
		// 配置一个连接在池中最小生存的时间，单位是毫秒
		bean.setMinEvictableIdleTimeMillis(300000);
		bean.setTestWhileIdle(true);
		// 检测数据库的查询语句
		bean.setValidationQuery("SELECT 1 from dual");
		bean.setTestOnBorrow(true);
		bean.setTestOnReturn(false);
		// 断线重连设置
		bean.setRemoveAbandoned(true);
		bean.setRemoveAbandonedTimeout(30);
		bean.setLogAbandoned(true);
		// 打开PSCache，并且指定每个连接上PSCache的大小
		bean.setPoolPreparedStatements(true);
		bean.setMaxPoolPreparedStatementPerConnectionSize(20);
		// 配置监控统计拦截的filters
		try {
			bean.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Filter> filters = new ArrayList<Filter>();
		Slf4jLogFilter logFilter = new Slf4jLogFilter();
		logFilter.setStatementExecutableSqlLogEnable(false);
		filters.add(logFilter);
		bean.setProxyFilters(filters);
		return bean;
	}*/

	
	/*@Bean(name = "dataSource")
	public DataSource getDataSource() {
		logger.info("初始化C3P0,用户：" + username);
		ComboPooledDataSource bean = new ComboPooledDataSource();
		bean.setJdbcUrl(url);
		try {
			bean.setDriverClass(driverClassName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		bean.setUser(username);
		bean.setPassword(password);

		// 连接池中保留的最小连接数 
		bean.setMinPoolSize(1); 
		// 连接池中保留的最大连接数
		bean.setMaxPoolSize(10); 
		// 初始化时获取的连接数
		bean.setInitialPoolSize(1); 
		// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
		bean.setAcquireIncrement(2);
		// 最大空闲时间,60秒内未使用则连接被丢弃 
		bean.setMaxIdleTime(60); 
		// 每60秒检查所有连接池中的空闲连接
		bean.setIdleConnectionTestPeriod(60); 
		// 如果maxStatements与maxStatementsPerConnection均为0，则缓存被关闭
		bean.setMaxStatements(0); 
		// 定义了连接池内单个连接所拥有的最大缓存statements数
		bean.setMaxStatementsPerConnection(0); 
		// 定义在从数据库获取新连接失败后重复尝试的次数
		bean.setAcquireRetryAttempts(10); 
		// 每个connection提交的时候都将校验其有效性,消耗较大,谨慎使用
		bean.setTestConnectionOnCheckout(false); 
		// 两次连接中间隔时间，单位毫秒
		bean.setAcquireRetryDelay(1000); 
		// 连接关闭时默认将所有未提交的操作回滚
		bean.setAutoCommitOnClose(false); 
		// 获取连接失败但是数据源仍有效保留,如果设置为true,那么在尝试获取连接失败后该数据源将申明已断开并永久关闭
		bean.setBreakAfterAcquireFailure(false); 
		// 当连接池用完时，客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException,单位毫秒,默认为0,永不超时
		bean.setCheckoutTimeout(3000); 
		// 每60秒检查所有连接池中的空闲连接
		bean.setIdleConnectionTestPeriod(600); 
		// 如果设为true那么在取得连接的同时将校验连接的有效性
		bean.setTestConnectionOnCheckin(true); 
		// 检测数据库的查询语句
		bean.setPreferredTestQuery("select 1 from dual");
		return bean;
	}*/

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		logger.info("初始化JDBC,用户：" + username);
		DriverManagerDataSource bean = new DriverManagerDataSource();
		bean.setDriverClassName(driverClassName);
		bean.setUrl(url);
		bean.setUsername(username);
		bean.setPassword(password);
		return bean;
	}

	@Bean(name = "sessionFactory")
	public AnnotationSessionFactoryBean getSessionFactory() {
		logger.info("sessionFactory");
		AnnotationSessionFactoryBean bean = new AnnotationSessionFactoryBean();
		bean.setDataSource(getDataSource());
		bean.setPackagesToScan("com.es.entity");
		Properties hiberProper = new Properties();
		hiberProper.setProperty("hibernate.dialect", dialect);
		hiberProper.setProperty("connection.autoReconnect", "true");
		hiberProper.setProperty("connection.autoReconnectForPools", "true");
		hiberProper.setProperty("connection.is-connection-validation-required", "true");
		hiberProper.setProperty("hibernate.show_sql", showSql);
		hiberProper.setProperty("hibernate.format_sql", formatSql);
		hiberProper.setProperty("hibernate.hbm2ddl.auto", "none");
		bean.setHibernateProperties(hiberProper);
		return bean;
	}

	@Bean(name = "hibernateTemplate")
	public HibernateTemplate getHibernateTemplate() {
		logger.info("hibernateTemplate");
		HibernateTemplate bean = new HibernateTemplate();
		bean.setSessionFactory(getSessionFactory().getObject());
		return bean;
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate getJdbcTemplate() {
		logger.info("jdbcTemplate");
		JdbcTemplate bean = new JdbcTemplate();
		bean.setDataSource(getDataSource());
		bean.setQueryTimeout(30);
		return bean;
	}

	/**
	 * 声明一个事务管理器
	 */
	@Bean(name = "transactionManager")
	public HibernateTransactionManager transactionManager() {
		logger.info("transactionManager 金三事务管理器");
		HibernateTransactionManager bean = new HibernateTransactionManager();
		bean.setSessionFactory(getSessionFactory().getObject());
		bean.setDataSource(getDataSource());
		return bean;
	}


	@Bean
	public static PropertySourcesPlaceholderConfigurer loadProperties() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		return configurer;
	}

}
