package com.rehair.rehair.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/files/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/","/account/login","/account/join","/account/duplicateUsername","/client/about","/client/notice","/client/notice_detail","/client/event","/client/event_detail")
				.permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/account/login")
				.loginProcessingUrl("/account/login")
				.defaultSuccessUrl("/")
				.and()
			.logout()
				.invalidateHttpSession(true);
	}

	// Authentication 로그인(인증)에 관한 설정
	// Authroization 권한에 관한 설정

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { // spring 내부에서 인증처리를 해주는 소스
		auth.jdbcAuthentication()
				.dataSource(dataSource)

				.usersByUsernameQuery("select username,password,is_enabled "
						+ "from user "
						+ "where username = ?")
				.authoritiesByUsernameQuery("select u.username, a.name " // ManyToMany 매칭이므로 join을 이용하였다.
						+ "from user_auth ua inner join user u on ua.user_id = u.user_id "
						+ "inner join auth a on ua.auth_id = a.id "
						+ "where u.username = ?")
				.passwordEncoder(passwordEncoder()); // 비밀번호 암호화 추가
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}