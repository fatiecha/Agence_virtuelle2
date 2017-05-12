package org.gestion.av.serviceImpl;

import java.util.ArrayList;

import org.gestion.av.dao.IAgenceDao;
import org.gestion.av.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IAgenceDao dao;

	public IAgenceDao getDao() {
		return dao;
	}

	public void setDao(IAgenceDao dao) {
		this.dao = dao;
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

		Client client = dao.getClientByEmail(mail);

		if (client != null) {

			String password = client.getMDP();

			// Now llet's create Spring Security user Object
			org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(
					mail, password, true, true, true, true, new ArrayList<>());
			return securityUser;
		} else {
			throw new UsernameNotFoundException("Username not found");
		}
	}
}
