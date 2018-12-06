package com.example.demo.auth;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalAuthentication
public class SecurityConfig{
//public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//                .antMatchers("/", "/register").permitAll()
//            	.antMatchers("/", "/register").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//            .logout()
//                .permitAll();
//    }

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER");
//		auth.inMemoryAuthentication().withUser("kai").password("{noop}kai").roles("USER");
//	}

}
