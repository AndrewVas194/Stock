package com.example.StockDiplom.config;

import com.example.StockDiplom.models.User;
import com.example.StockDiplom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.StockDiplom.models.Enums.roles.Role.ADMIN;
import static com.example.StockDiplom.models.Enums.roles.Role.USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserService userService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().disable() .csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/","/groups/list","/registration").permitAll()
                .antMatchers("/user","/user/account","/groups/add","/groups/delete/{id}","/groups/{groupId}/members",
                        "confirm/stuff-confirm-delete","/stuff/delete/{id}","/groups/{groupId}/addMember","/stuff/add",
                        "/trade/groups/{groupId}/{user2Id}","/trade/groups/{groupId}/{user2Id}/{stuffId}")
                .hasAuthority(USER.getAuthority())

                .antMatchers("/**").hasAuthority(ADMIN.getAuthority())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
