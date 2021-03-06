package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import dao.BoardDao;
import dao.CartDao;
import dao.RecipeDao;
import dao.LoginDao;

@Configuration
@EnableTransactionManagement
public class AppConfig {
	
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/changgimarket?ServerTimezone=Asia/Seoul&useSSL=false");
		ds.setUsername("root");
		ds.setPassword("1234");
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	
	@Bean
	public BoardDao boardDao() {
		return new BoardDao(dataSource());
	}
	
	@Bean
	public LoginDao loginDao() {
		return new LoginDao(dataSource());
	}
	
	@Bean
	public RecipeDao recipeDao() {
		return new RecipeDao(dataSource());
	}
	
	@Bean
	public CartDao cartDao() {
		return new CartDao(dataSource());
	}
}
