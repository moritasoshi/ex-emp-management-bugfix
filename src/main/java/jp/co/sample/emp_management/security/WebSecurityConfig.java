package jp.co.sample.emp_management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiseImpl userDetailsService;

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**", "/img/**", "/fonts/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.formLogin()
		.loginPage("/") // 自身で作ったログインの画面のURLを設定
		.loginProcessingUrl("/login")
		.failureUrl("/?error")
		.defaultSuccessUrl("/employee/showList", true)
		.usernameParameter("mailAddress") // formのname属性の値
		.passwordParameter("password") // formのname属性の値
		.and()
		.logout()
		.permitAll()
		.logoutSuccessUrl("/") // ログアウト時の遷移先URL
		.deleteCookies();// ログアウトするとCookieのJSESSIONIDを削除
		http
		.authorizeRequests()
		.antMatchers("/insert", "/", "/toInsert", "/login").permitAll()
		.anyRequest().authenticated();
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
}
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/img/**", "/css/**", "/javascript/**");
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http
//		.authorizeRequests()
//			.antMatchers("/insert", "/", "/toInsert").permitAll()
//			.antMatchers("/js/**", "/css/**", "/img/**").permitAll() // 制限なし
//			.anyRequest().authenticated()
//			.and()
//		.formLogin()
//			.loginPage("/") // 自身で作ったログインの画面のURLを設定
//			.loginProcessingUrl("/login")
//			.failureUrl("/login")
//			.usernameParameter("mailAddress") // formのname属性の値
//			.passwordParameter("password") // formのname属性の値
//			.defaultSuccessUrl("/employee/showList", true)
//			.and()
//		.logout()
//			.permitAll()
//			.logoutSuccessUrl("/") // ログアウト時の遷移先URL
//			.deleteCookies();// ログアウトするとCookieのJSESSIONIDを削除
//				
//	}
//
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Configuration
//	protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
//		@Autowired
//		UserDetailsService userDetailsService;
//
//		@Override
//		public void init(AuthenticationManagerBuilder auth) throws Exception {
//			auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//		}
//
//	}
//
//}
