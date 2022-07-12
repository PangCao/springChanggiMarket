package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import control.SpringBoardController;
import control.SpringCartController;
import control.SpringLoginController;
import control.SpringRecipeController;

@Configuration
public class ControllerConfig {
	
	@Bean
	public SpringBoardController springBoradController() {
		return new SpringBoardController();
	}

	
	@Bean
	public SpringLoginController springLoginController() {
		return new SpringLoginController();
	}
	
	@Bean
	public SpringCartController springCartController() {
		return new SpringCartController();
	}
	
	@Bean
	public SpringRecipeController springRecipeController() {
		return new SpringRecipeController();
	}
	
}
