package pblweek2.megazine.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pblweek2.megazine.service.UserService;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // web security 를 활성화하고 spring mvc와 통합해주는 기능
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final AuthenticationFailureHandler MyLoginFailureHandler;
    private final AuthenticationSuccessHandler MyLoginSuccessHandler;


    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authorizeRequests()는 시큐리티 처리에 HttpServletRequest 를 이용한다는 것을 의미
        // logout은 WebSecurityConfigurerAdapter를 사용할 때 자동으로 적용됨 -> /logout에 접근하면 자동으로 http 세션 제거
        http
                .csrf().disable().exceptionHandling();


        http
                .authorizeRequests()
//                .antMatchers("/").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated() // 모든 요청에 대해 인증된 사용자만 접근 가능
                .and()
                .headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                    .formLogin()
                    // 로그인 페이지 url
    //                .loginPage("/user/login")
                    // 로그인 처리 (POST /user/login)
                    .loginProcessingUrl("/api/login")
                    .successHandler(MyLoginSuccessHandler)
    //                  .failureHandler(MyLoginFailureHandler)
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true); // 로그아웃 시 저장해둔 세션 날리기
    }

//    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    // Spring Security에서 모든 인증은 AuthenticationManager를 통해 이루어지며 AuthenticationManager를 생성하기 위해서는
    // AuthenticationManagerBuilder를 사용합니다.
    // 로그인 시 필요한 정보를 가져옵니다.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService) // user정보를 어느 service에서 가져올 지 결정
                .passwordEncoder(new BCryptPasswordEncoder());

    }

    @Bean
    // Spring IoC 컨테이너가 관리하는 자바 객체를 빈(Bean)이라고 부른다.
    // ApplicationContext 가 만들어서 그 안에 담고있는 객체
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Spring security에서 제공하는 비밀번호 암호화 객체
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3030")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
        }
    }

}
