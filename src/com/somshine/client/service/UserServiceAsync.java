package com.somshine.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.somshine.shared.Contact;
import com.somshine.shared.User;

public interface UserServiceAsync {
	public void doLogin(String username, String password, AsyncCallback<User> callback);
	public void getProfile(Long id, AsyncCallback<User> callback);
}
