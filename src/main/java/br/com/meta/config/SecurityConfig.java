package br.com.meta.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author samuel-cruz
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests() //
				.antMatchers("/v2/api-docs", //
						"/swagger-resources/**", //
						"/swagger-ui.html", //
						"/webjars/**", //
						"/source") //
				.permitAll() //
				.and() //
				.authorizeRequests() //
				.anyRequest().authenticated() //
				.and() //
				.httpBasic() //
				.and() //
				.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()) //
				.and().csrf().disable();
	}

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		auth.inMemoryAuthentication() //
				.withUser("meta") //
				.password(encoder.encode("meta@123")) //
				.roles("USER");
	}
}
