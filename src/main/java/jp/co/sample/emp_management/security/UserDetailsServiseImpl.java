package jp.co.sample.emp_management.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.domain.LoginAdministrator;
import jp.co.sample.emp_management.repository.AdministratorRepository;

@Service
public class UserDetailsServiseImpl implements UserDetailsService {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private HttpSession session;

	@Override
	public UserDetails loadUserByUsername(String mailAddress) throws UsernameNotFoundException {
		System.out.println("service");

		Administrator administrator = administratorRepository.findByMailAddress(mailAddress);
		if (Objects.isNull(administrator)) {
			throw new UsernameNotFoundException("そのEmailは登録されていません。");
		}
		session.setAttribute("administratorName", administrator.getName());
		
		// 権限付与
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
		
		return new LoginAdministrator(administrator,authorityList);

	}


}
