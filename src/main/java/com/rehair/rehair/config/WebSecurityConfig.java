package com.rehair.rehair.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				//누구나 접근
				.antMatchers("/","/join","/about","/notice","/membership","/eventDetail","/css/**","/js/**","/images/**").permitAll()
				//위 경로 제외 로그인해야 볼 수 있음.
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		UserDetails user =
//			 User.withDefaultPasswordEncoder()
//				.username("Test")
//				.password("1234")
//				.roles("USER") // 권한 3개 GUEST, USER, ADMIN
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		WebSecurityConfig config =  new WebSecurityConfig();
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(config.passwordEncoder())
				//인증처리
				.usersByUsernameQuery("select username,password,enabled "
						+ "from user "
						+ "where username = ?")
				//권한처리
				.authoritiesByUsernameQuery("select u.username,a.authname "
						+ "from user_auth ua inner join user u on ua.user_id=u.id "
						+ "inner join auth a on ua.auth_id=a.id "
						+ "where u.username = ?");
	}
	//패스워드 암호화
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}