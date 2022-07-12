package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/about/aboutUs").setViewName("about/aboutUs");
		registry.addViewController("/login/login").setViewName("login/login");
		registry.addViewController("/login/sel_login").setViewName("login/sel_login");
		registry.addViewController("/recipe/addrecipe").setViewName("recipe/addrecipe");
		registry.addViewController("/cart/cart").setViewName("cart/cart");
		registry.addViewController("/login/modimypagechk").setViewName("login/modimypagechk");
		registry.addViewController("/community/board_write").setViewName("community/board_write");
		registry.addViewController("/login/find_id").setViewName("login/find_id");
		registry.addViewController("/login/find_pw").setViewName("login/find_pw");
		registry.addViewController("/login/signUp").setViewName("login/signUp");
		registry.addViewController("/login/seller_signUp").setViewName("login/seller_signUp");
		registry.addViewController("/login/idmailchk").setViewName("login/idmailchk");
		registry.addViewController("/login/modisellerchk").setViewName("login/modisellerchk");
		
		
	}
	
	
}
