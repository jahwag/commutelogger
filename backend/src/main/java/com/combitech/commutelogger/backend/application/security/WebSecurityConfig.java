package com.combitech.commutelogger.backend.application.security;

import com.microsoft.azure.spring.autoconfigure.aad.AADAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AADAuthenticationFilter aadAuthFilter;

    public WebSecurityConfig(AADAuthenticationFilter aadAuthFilter) {
        this.aadAuthFilter = aadAuthFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/*")
            .authenticated()
            .and()
            .addFilterBefore(aadAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/")
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true);

        http.csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

}