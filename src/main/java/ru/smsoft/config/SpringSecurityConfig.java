package ru.smsoft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.smsoft.handler.AppAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${user.login}")
    private String user;
    @Value("${user.password}")
    private String password;
    @Value("${user.role}")
    private String userRole;
    @Autowired
    private AppAccessDeniedHandler accessDeniedHandler;

    /**
     * Создание юзера
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(user).password("{noop}" + password).roles(userRole);
    }

    /**
     * роль USER будет иметь доступ к /convert и /history
     * кастомный "403 access denied" обработчик
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/convert", "/history", "/history/**").hasAnyRole(userRole)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login_check")
                .loginPage("/")
                .defaultSuccessUrl("/convert")
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
}
