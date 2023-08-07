package com.example.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class CustomerConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomerServiceConfig();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/cart/**").hasAnyAuthority("CUSTOMER","SHIPPER")
//                .antMatchers("/shop/find-product/**").hasAuthority("CUSTOMER")
//                .anyRequest().authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
//                .usernameParameter("username")
//                .loginProcessingUrl("/login") //
//                .defaultSuccessUrl("/") //có cũng được vì cái này là mặc định
//                .failureForwardUrl("/login?error")  // có cũng được vì cái này là mặc định
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
//                .and()
//                .rememberMe()
//                .key("AbcDefgHijKlmnOpqrs_1234567890").tokenValiditySeconds(3*24*60*60);
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/404");
    }
    //  <th:block sec:authorize="hasAuthority('CUSTOMER')"><block>
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/img/**", "/js/**","/css/**" ,"/webfonts/**","/addCss/**","/myJs/**", "/webjars/**");
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/admin/*").hasAnyRole("ADMIN", "SALE")
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/do-login")
//                        .defaultSuccessUrl("/admin/index")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests().requestMatchers("/* ").permitAll()
//                .requestMatchers("/admin/*").hasAnyAuthority("ADMIN", "SALE")
//                .requestMatchers("/admin/sale/*").hasAnyAuthority("SALE", "EDITOR")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/do-login")
//                .defaultSuccessUrl("/admin/index")
//                .permitAll()
//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout")
//                .permitAll();
//        return http.build();
//    }

}
