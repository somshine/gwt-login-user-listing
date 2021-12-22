package com.somshine.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.somshine.client.ContactsServiceAsync;
import com.somshine.client.common.ColumnDefinition;
import com.somshine.client.common.SelectionModel;
import com.somshine.client.event.ContactUpdatedEvent;
import com.somshine.client.event.EditContactCancelledEvent;
import com.somshine.client.event.LoginEvent;
import com.somshine.client.service.UserServiceAsync;
import com.somshine.client.view.ContactsView;
import com.somshine.client.view.Login;
import com.somshine.shared.ContactDetails;
import com.somshine.shared.User;

public class LoginPresenter implements Presenter, Login.Presenter {

	private final UserServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Login view;

	public LoginPresenter(UserServiceAsync rpcService, HandlerManager eventBus, Login view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
		bind();
	}
	
	public void bind() {
		this.view.getSubmitButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doLogin();
			}
		});
	}

	@Override
	public void doLogin() {
		eventBus.fireEvent(new LoginEvent());
		Window.alert("Call the login service for the same.");
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

}
