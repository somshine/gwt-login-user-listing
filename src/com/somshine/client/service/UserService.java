package com.somshine.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.somshine.shared.User;

@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {
	User doLogin(String username, String password);
	User getProfile(Long id);
}
