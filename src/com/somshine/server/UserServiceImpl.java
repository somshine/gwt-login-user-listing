package com.somshine.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.somshine.client.service.UserService;
import com.somshine.shared.User;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	@Override
	public User doLogin(String username, String password) {
		User user = new User(1L, 1, "Somnath", "Shinde", "Ranzani");
		return user;
	}

	@Override
	public User getProfile(Long id) {
		User user = new User(1L, 1, "Somnath", "Shinde", "Ranzani");
		return user;
	}

}
