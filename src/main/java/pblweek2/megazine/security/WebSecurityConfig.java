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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pblweek2.megazine.exceptionhandler.AccessDeniedHandler;
import pblweek2.megazine.exceptionhandler.CustomAuthenticationEntryPoint;
import pblweek2.megazine.jwt.JwtAuthenticationFilter;
import pblweek2.megazine.jwt.JwtTokenProvider;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // web security 를 활성화하고 spring mvc와 통합해주는 기능
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    // Spring IoC 컨테이너가 관리하는 자바 객체를 빈(Bean)이라고 부른다.
    // ApplicationContext 가 만들어서 그 안에 담고있는 객체
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return new BCryptPasswordEncoder(); // Spring security에서 제공하는 비밀번호 암호화 객체
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


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
//                .httpBasic().disable()
                .csrf().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(new AccessDeniedHandler())
                .and()
                    .cors()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/board").permitAll()
                .antMatchers("/api/board/{boardId}").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/register").permitAll()
                .anyRequest().authenticated() // 모든 요청에 대해 인증된 사용자만 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
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





}
