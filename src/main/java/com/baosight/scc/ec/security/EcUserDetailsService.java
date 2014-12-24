package com.baosight.scc.ec.security;

import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.utils.UserAuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by zodiake on 2014/5/12.
 */
@Component
public class EcUserDetailsService implements UserDetailsService,Serializable{
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        EcUser u = new EcUser();
        if(s.equals("tom")) {
            u.setId("100047");
            u.setName("tom");
            u.setPassword("1");
            u.setUserType("O");
            Address address = new Address();
            address.setId("1");
            address.setCity("上海");
            address.setState("上海");
            address.setStreet("浦东新区浦电路370号");
            address.setZipCode("203323");
            address.setZipPhone("021");
            address.setPhone("23839434");
            address.setChildPhone("2343");
            address.setReceiverName("Charles");
            address.setMobile("1862229323");
            u.setDefaultAddress(address);
            u.setAuthorities(UserAuthorityUtils.USER_ROLES);
        }else if(s.equals("mary")){
            u.setId("100048");
            u.setName("mary");
            u.setPassword("1");
            u.setUserType("O");
            Address address = new Address();
            address.setId("1");
            address.setCity("上海");
            address.setState("上海");
            address.setStreet("浦东新区浦电路370号");
            address.setZipCode("203323");
            address.setZipPhone("021");
            address.setPhone("23839434");
            address.setChildPhone("2343");
            address.setReceiverName("Charles");
            address.setMobile("1862229323");
            u.setDefaultAddress(address);
            u.setAuthorities(UserAuthorityUtils.ADMIN_ROLES);
        }
        return new EcUserDetails(u);
    }

    private final class EcUserDetails extends EcUser implements UserDetails,Serializable{
        EcUserDetails(EcUser user){
            setId(user.getId());
            setName(user.getName());
            setPassword(user.getPassword());
            setDefaultAddress(user.getDefaultAddress());
            setAuthorities(user.getAuthorities());
        }

        public String getUsername() {
            return getName();
        }

        public boolean isAccountNonExpired() {
            return true;
        }

        public boolean isAccountNonLocked() {
            return true;
        }

        public boolean isCredentialsNonExpired() {
            return true;
        }

        public boolean isEnabled() {
            return true;
        }
    }
}
