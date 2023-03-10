package edu.carroll.polyominoes.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val userDetailsService: UserDetailsService) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        return http.csrf().disable().authorizeHttpRequests().requestMatchers("/**").permitAll()
            .requestMatchers("/account").authenticated().anyRequest().authenticated().and().formLogin()
            .loginPage("/login").defaultSuccessUrl("/", false).permitAll().and().logout().logoutUrl("/logout")
            .logoutSuccessUrl("/login").and().userDetailsService(userDetailsService).build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


}