package com.somshine.client.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SessionImpl extends RemoteServiceServlet {
	public void createSession(String Username) {
        getThreadLocalRequest().getSession().setAttribute("Username", Username);
    }

    public boolean validateSession(String Username) {
        if (getThreadLocalRequest().getSession().getAttribute("Username") != null) {
            return true;
        } else {
            return false;
        }
    }
}
