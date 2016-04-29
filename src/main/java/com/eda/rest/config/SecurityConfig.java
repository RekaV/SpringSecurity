package com.eda.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("agent").password("agent").roles("AGENT");
		auth.inMemoryAuthentication().withUser("client").password("client").roles("CLIENT");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	 /* http.authorizeRequests()
	  	//.antMatchers("/**").authenticated()
	  	
		.antMatchers("/get/").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/list/agent/**").permitAll()//access("hasRole('ROLE_USER')")
		.antMatchers("/cal/send/").permitAll();
 		//.and().formLogin();
*/		
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.httpBasic();
        http.authorizeRequests()
        		.antMatchers("/cal/send/").hasRole("CLIENT")
        		.antMatchers("/cal/create/").hasRole("AGENT")
        		.antMatchers("/cal/updatestatus/").hasRole("CLIENT")
        		.antMatchers("/event/create/").hasRole("AGENT")
        		.antMatchers("/event/list/questions/").hasRole("CLIENT")
        		.antMatchers("/gcmkey/create/").hasRole("CLIENT")
        		.antMatchers("/contact/update/").hasRole("CLIENT")
        		.antMatchers("/profile/create").hasRole("CLIENT")
        		.antMatchers("/profile/list").hasRole("CLIENT")
        		.antMatchers("/new/contact/send/").hasRole("CLIENT")
        		.antMatchers("/new/contact/create/").hasRole("AGENT")
        		.antMatchers("/new/contact/updatestatus/").hasRole("CLIENT")
        		.antMatchers("/email/send/").hasRole("CLIENT")
        		.antMatchers("/email/create/").hasRole("AGENT")
        		.antMatchers("/email/updatestatus/").hasRole("CLIENT")
        		.antMatchers("/send/direct/email").hasRole("AGENT")
        		.antMatchers("/autocomplete/phone/{thread_id}/").hasRole("AGENT")
        		.antMatchers("/autocomplete/email/{thread_id}/").hasRole("AGENT")
        		.antMatchers("/travel/insurance/create").hasRole("AGENT")
        		.antMatchers("/location/get/").hasRole("CLIENT")
        		.antMatchers("/location/create/").hasRole("AGENT")
        		.antMatchers("/errorlog/store/").hasRole("CLIENT")
        		.antMatchers("/agent/login/").hasRole("AGENT")
        		.antMatchers("/agent/logout/").hasRole("AGENT")
        		.antMatchers("/message/create/").hasRole("AGENT")//hasRole("AGENT")
        		.antMatchers("/servlet/audio/").hasRole("CLIENT")
        		.antMatchers("/message/listmessage/").hasRole("AGENT")
        		.antMatchers("/message/count/").hasRole("AGENT")
        		.antMatchers("/notes/send/").hasRole("CLIENT")
        		.antMatchers("/notes/create/").hasRole("AGENT")
        		.antMatchers("/notes/updatestatus/").hasRole("CLIENT")
        		.antMatchers("/sms/send/").hasRole("CLIENT")
        		.antMatchers("/sms/create/").hasRole("AGENT")
        		.antMatchers("/sms/updatestatus/").hasRole("CLIENT")
        		.antMatchers("/thread/updatestatus/{thread_id}").hasRole("AGENT")
        		.antMatchers("/thread/getstatus/").hasRole("CLIENT")
        		.antMatchers("/thread/listthread1/").hasRole("AGENT")
        		.antMatchers("/thread/listallthread/").hasRole("AGENT")
        		.antMatchers("/agent/updatestatus/").hasRole("AGENT")
        		.antMatchers("/agent/insert/").hasRole("AGENT")
        		.antMatchers("/agent/list/").hasRole("AGENT")
        		.antMatchers("/agent/update/").hasRole("AGENT")
        		.antMatchers("/thread/allthreadcount/").hasRole("AGENT")
        		.antMatchers("/thread/unpickedthreads/").hasRole("AGENT")
        		.antMatchers("/thread/pickedthreads/").hasRole("AGENT")
        		.antMatchers("/thread/closedthreads/").hasRole("AGENT")
        		.antMatchers("/watson/question/").hasRole("AGENT")
        		.antMatchers("/watson/answer/").hasRole("AGENT")
        		.antMatchers("/get/").hasRole("USER")
                .antMatchers("/list/agent/").hasRole("ADMIN");
                
	}
	
	
}
