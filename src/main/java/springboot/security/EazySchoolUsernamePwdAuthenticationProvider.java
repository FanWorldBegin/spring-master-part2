package springboot.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import springboot.model.Person;
import springboot.model.Roles;
import springboot.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class EazySchoolUsernamePwdAuthenticationProvider
        implements AuthenticationProvider
{
    @Autowired
    private PersonRepository personRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        // 获取密码, 邮箱
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        // 使用email 去查找当前的用户
        Person person = personRepository.readByEmail(email);
        // 密码是否和存储的数据相等
        if(null != person && person.getPersonId()>0 &&
                pwd.equals(person.getPwd())){
            // 清除密码相关token
            return new UsernamePasswordAuthenticationToken(
                    // 这里传入的是name 不是email
                    person.getName(), pwd, getGrantedAuthorities(person.getRoles()));
        }else{
            throw new BadCredentialsException("Invalid credentials!");
        }
    }

    // Security will always maintain all your rules with the prefix role and the score.
    private List<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roles.getRoleName()));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}