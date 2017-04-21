package com.whenling.shop.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.whenling.castle.security.CustomUserDetails;
import com.whenling.shop.entity.Admin;
import com.whenling.shop.repo.AdminRepository;

public class AdminDetailsService implements UserDetailsService {

	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_SALESMAN = "salesman";
	public static final String ROLE_SHIPPER = "shipper";
	public static final String ROLE_UNDEFINE = "undefine";

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Admin admin = adminRepository.findByUsername(username);
		if (admin == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}

		String role = ROLE_UNDEFINE;
		if (admin.isAdmin()) {
			role = ROLE_ADMIN;
		} else if (admin.isSalesman()) {
			role = ROLE_SALESMAN;
		} else if (admin.isShipper()) {
			role = ROLE_SHIPPER;
		}
		return new CurrentUserDetails(admin.getId(), admin.getUsername(), admin.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_"+role));
	}

	public class CurrentUserDetails extends CustomUserDetails<Long, Admin> {

		public CurrentUserDetails(Long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities) {
			super(id, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		}

		private static final long serialVersionUID = 8220061317304759492L;

		@Override
		public Admin getCustomUser() {
			return adminRepository.getOne(getId());
		}
	}
}
