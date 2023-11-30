package com.bookworm.conf.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.bookworm.constant.Role;
import com.bookworm.error.BookWormAccessDeniedHandler;
import com.bookworm.error.BookWormAuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	private final BookWormAuthenticationEntryPoint bookWormAuthenticationEntryPoint;
	
	private final String RESOURCE_SERVER_RESOURCE_ID = "bookwormApi";

	public ResourceServerConfiguration(BookWormAuthenticationEntryPoint bookWormAuthenticationEntryPoint) {
		this.bookWormAuthenticationEntryPoint = bookWormAuthenticationEntryPoint;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_SERVER_RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.antMatcher("/bookworm/**").authorizeRequests()
				.antMatchers("/bookworm/registry/sign-up/**").permitAll()
				.antMatchers("/bookworm/customer/record/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
				.antMatchers("/bookworm/customer/orders/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
				.antMatchers("/bookworm/book/record/**").hasAuthority(Role.ADMIN.name())
				.antMatchers("/bookworm/book/details/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
				.antMatchers("/bookworm/book/stock/**").hasAuthority(Role.ADMIN.name())
				.antMatchers("/bookworm/order/record/**").hasAuthority(Role.USER.name())
				.antMatchers("/bookworm/order/details/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
				.antMatchers("/bookworm/order/all/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
				.antMatchers("/bookworm/statistics/monthly/**").hasAuthority(Role.ADMIN.name())
				.antMatchers("/bookworm/**").authenticated().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(bookWormAuthenticationEntryPoint)
				.accessDeniedHandler(new BookWormAccessDeniedHandler());

	}
}
