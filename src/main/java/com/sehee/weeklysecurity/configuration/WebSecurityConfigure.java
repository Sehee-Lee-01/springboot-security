package com.sehee.weeklysecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.authorization.AuthenticatedAuthorizationManager.fullyAuthenticated;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;
import static org.springframework.security.authorization.AuthorizationManagers.allOf;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/assets/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/me")
                                .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin")
                                .access(allOf(hasRole("ADMIN"), fullyAuthenticated()))
                        .anyRequest()
                                .permitAll())
                .formLogin((formLogin) ->
                        formLogin.defaultSuccessUrl("/")
                                .permitAll())
                .logout((logout) ->
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/"))
                .rememberMe((rememberMe) ->
                        rememberMe.rememberMeCookieName("remember-me")
                                .tokenValiditySeconds(300)
                                .key("my-remember-me"))
                .requiresChannel((secure) ->
                        secure.anyRequest().requiresSecure());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}user123")
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}
