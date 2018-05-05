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
		logger.info("��ʼ�� druid,�û���" + username);
		DruidDataSource bean = new DruidDataSource();
		bean.setDriverClassName(driverClassName);
		// �������� url��user��password
		bean.setUrl(url);
		bean.setUsername(username);
		bean.setPassword(password);
		// ���ó�ʼ����С����С�����
		bean.setInitialSize(1);
		bean.setMinIdle(1);
		bean.setMaxActive(10);
		// ���û�ȡ���ӵȴ���ʱ��ʱ��
		bean.setMaxWait(10000);
		// ���ü����òŽ���һ�μ�⣬�����Ҫ�رյĿ������ӣ���λ�Ǻ���
		bean.setTimeBetweenEvictionRunsMillis(60000);
		// ����һ�������ڳ�����С�����ʱ�䣬��λ�Ǻ���
		bean.setMinEvictableIdleTimeMillis(300000);
		bean.setTestWhileIdle(true);
		// ������ݿ�Ĳ�ѯ���
		bean.setValidationQuery("SELECT 1 from dual");
		bean.setTestOnBorrow(true);
		bean.setTestOnReturn(false);
		// ������������
		bean.setRemoveAbandoned(true);
		bean.setRemoveAbandonedTimeout(30);
		bean.setLogAbandoned(true);
		// ��PSCache������ָ��ÿ��������PSCache�Ĵ�С
		bean.setPoolPreparedStatements(true);
		bean.setMaxPoolPreparedStatementPerConnectionSize(20);
		// ���ü��ͳ�����ص�filters
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
		logger.info("��ʼ��C3P0,�û���" + username);
		ComboPooledDataSource bean = new ComboPooledDataSource();
		bean.setJdbcUrl(url);
		try {
			bean.setDriverClass(driverClassName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		bean.setUser(username);
		bean.setPassword(password);

		// ���ӳ��б�������С������ 
		bean.setMinPoolSize(1); 
		// ���ӳ��б��������������
		bean.setMaxPoolSize(10); 
		// ��ʼ��ʱ��ȡ��������
		bean.setInitialPoolSize(1); 
		// �����ӳ��е����Ӻľ���ʱ��c3p0һ��ͬʱ��ȡ��������
		bean.setAcquireIncrement(2);
		// ������ʱ��,60����δʹ�������ӱ����� 
		bean.setMaxIdleTime(60); 
		// ÿ60�����������ӳ��еĿ�������
		bean.setIdleConnectionTestPeriod(60); 
		// ���maxStatements��maxStatementsPerConnection��Ϊ0���򻺴汻�ر�
		bean.setMaxStatements(0); 
		// ���������ӳ��ڵ���������ӵ�е���󻺴�statements��
		bean.setMaxStatementsPerConnection(0); 
		// �����ڴ����ݿ��ȡ������ʧ�ܺ��ظ����ԵĴ���
		bean.setAcquireRetryAttempts(10); 
		// ÿ��connection�ύ��ʱ�򶼽�У������Ч��,���Ľϴ�,����ʹ��
		bean.setTestConnectionOnCheckout(false); 
		// ���������м��ʱ�䣬��λ����
		bean.setAcquireRetryDelay(1000); 
		// ���ӹر�ʱĬ�Ͻ�����δ�ύ�Ĳ����ع�
		bean.setAutoCommitOnClose(false); 
		// ��ȡ����ʧ�ܵ�������Դ����Ч����,�������Ϊtrue,��ô�ڳ��Ի�ȡ����ʧ�ܺ������Դ�������ѶϿ������ùر�
		bean.setBreakAfterAcquireFailure(false); 
		// �����ӳ�����ʱ���ͻ��˵���getConnection()��ȴ���ȡ�����ӵ�ʱ�䣬��ʱ���׳�SQLException,��λ����,Ĭ��Ϊ0,������ʱ
		bean.setCheckoutTimeout(3000); 
		// ÿ60�����������ӳ��еĿ�������
		bean.setIdleConnectionTestPeriod(600); 
		// �����Ϊtrue��ô��ȡ�����ӵ�ͬʱ��У�����ӵ���Ч��
		bean.setTestConnectionOnCheckin(true); 
		// ������ݿ�Ĳ�ѯ���
		bean.setPreferredTestQuery("select 1 from dual");
		return bean;
	}*/

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		logger.info("��ʼ��JDBC,�û���" + username);
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
	 * ����һ�����������
	 */
	@Bean(name = "transactionManager")
	public HibernateTransactionManager transactionManager() {
		logger.info("transactionManager �������������");
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
