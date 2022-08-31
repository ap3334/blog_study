package jung.study.blog_study.config.auth;

import jung.study.blog_study.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class PrincipalDetail implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {return "ROLE_" + user.getRole();});

        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠기지 않았는지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화 되어 있는지
    @Override
    public boolean isEnabled() {
        return true;
    }
}
