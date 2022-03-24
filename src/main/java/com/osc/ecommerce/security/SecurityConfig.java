package com.osc.ecommerce.security;

import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.security.filter.CustomAuthenticationFilter;
import com.osc.ecommerce.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .httpBasic()
                .disable();

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/auth/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tokens/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/tokens/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/admins/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/admins/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/categories/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/categories/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/customers/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/customer/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/products/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/products/**").hasAnyAuthority("ROLE_SUPPLIER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/products/**").hasAnyAuthority("ROLE_SUPPLIER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/roles/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/roles/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/suppliers/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/suppliers/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService((UserDetailsService) userService);
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
