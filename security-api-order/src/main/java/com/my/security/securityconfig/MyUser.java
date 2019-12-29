/**
 * 
 */
package com.my.security.securityconfig;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liuwei
 *
 */
public class MyUser implements UserDetails {

    @Setter
    @Getter
    private String username;//这两个属性 直接提出来
    @Setter
    @Getter
    private String password;//这两个属性 直接提出来
	@Setter
	@Getter
	private Long id;// 自定属性

	/**
	 * 
	 */
	private static final long serialVersionUID = -9171631069036764965L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
