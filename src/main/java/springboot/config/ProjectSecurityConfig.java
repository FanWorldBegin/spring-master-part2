package springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    /**
     * From Spring Security 5.7, the WebSecurityConfigurerAdapter is deprecated to encourage users
     * to move towards a component-based security configuration. It is recommended to create a bean
     * of type SecurityFilterChain for security related configurations.
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().ignoringAntMatchers("/saveMsg").ignoringAntMatchers("/h2-console/**")
                //添加跨域忽略
                .ignoringAntMatchers("/public/**").ignoringAntMatchers("/api/**")
                //禁止跨域限制
                .ignoringAntMatchers("/data-api/**").and()
                .authorizeRequests()
                .mvcMatchers("/dashboard").authenticated()
                .mvcMatchers("/updateProfile").authenticated()
                .mvcMatchers("/displayProfile").authenticated()
                // 保护自动生成的api
                .mvcMatchers("/data-api/**").authenticated()
                .mvcMatchers("/student/**").hasRole("STUDENT")
                .mvcMatchers("/displayMessages").hasRole("ADMIN")
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/api/**").authenticated()
                .mvcMatchers("/home").permitAll()
                .mvcMatchers("/holidays/**").permitAll()
                .mvcMatchers("/contact").permitAll()
                .mvcMatchers("/saveMsg").permitAll()
                .mvcMatchers("/courses").permitAll()
                .mvcMatchers("/about").permitAll()
                .mvcMatchers("/login").permitAll()
                // 匹配以public开头的路径
                .mvcMatchers("/public/**").permitAll()
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                .and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().httpBasic();

        http.headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("12345")
//                .roles("USER")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("54321")
//                .roles("USER","ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

}