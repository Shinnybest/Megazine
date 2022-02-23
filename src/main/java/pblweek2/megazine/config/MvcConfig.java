package pblweek2.megazine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        // "/" url로 오면 setViewName -> 페이지 html 로 이동
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/user/signup").setViewName("signup");
        registry.addViewController("/user/login").setViewName("login");
    }
}
