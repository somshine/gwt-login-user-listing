package com.somshine.client;

import com.somshine.client.common.ContactsColumnDefinitionsFactory;
import com.somshine.client.event.AddContactEvent;
import com.somshine.client.event.AddContactEventHandler;
import com.somshine.client.event.ContactUpdatedEvent;
import com.somshine.client.event.ContactUpdatedEventHandler;
import com.somshine.client.event.EditContactEvent;
import com.somshine.client.event.EditContactEventHandler;
import com.somshine.client.event.LoginEvent;
import com.somshine.client.event.LoginEventHandler;
import com.somshine.client.event.EditContactCancelledEvent;
import com.somshine.client.event.EditContactCancelledEventHandler;
import com.somshine.client.presenter.ContactsPresenter;
import com.somshine.client.presenter.EditContactPresenter;
import com.somshine.client.presenter.LoginPresenter;
import com.somshine.client.presenter.Presenter;
import com.somshine.client.service.SessionImpl;
import com.somshine.client.service.UserService;
import com.somshine.client.service.UserServiceAsync;
import com.somshine.client.view.ContactsViewImpl;
import com.somshine.client.view.EditContactView;
import com.somshine.client.view.Login;
import com.somshine.shared.ContactDetails;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private final ContactsServiceAsync rpcService;
	private UserServiceAsync rpcUserService;
//	private SessionImpl sessionService;
	private HasWidgets container;
	private ContactsViewImpl<ContactDetails> contactsView = null;
	private EditContactView editContactView = null;
	
	public AppController(ContactsServiceAsync rpcService, HandlerManager eventBus) {
		this.eventBus = eventBus;
//		this.sessionService = new SessionImpl();
		this.rpcService = rpcService;
		UserServiceAsync rpcUserService = GWT.create(UserService.class);
		this.rpcUserService = rpcUserService;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(AddContactEvent.TYPE, new AddContactEventHandler() {
			public void onAddContact(AddContactEvent event) {
				doAddNewContact();
			}
		});

		eventBus.addHandler(EditContactEvent.TYPE, new EditContactEventHandler() {
			public void onEditContact(EditContactEvent event) {
				doEditContact(event.getId());
			}
		});

		eventBus.addHandler(EditContactCancelledEvent.TYPE, new EditContactCancelledEventHandler() {
			public void onEditContactCancelled(EditContactCancelledEvent event) {
				doEditContactCancelled();
			}
		});

		eventBus.addHandler(ContactUpdatedEvent.TYPE, new ContactUpdatedEventHandler() {
			public void onContactUpdated(ContactUpdatedEvent event) {
				doContactUpdated();
			}
		});
		
		eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
			@Override
			public void doLogin(LoginEvent event) {
				History.newItem("list");
			}
		});
	}

	private void doAddNewContact() {
		History.newItem("add");
	}

	private void doEditContact(String id) {
		History.newItem("edit", false);
		Presenter presenter = new EditContactPresenter(rpcService, eventBus, new EditContactView(), id);
		presenter.go(container);
	}

	private void doEditContactCancelled() {
		History.newItem("list");
	}

	private void doContactUpdated() {
		History.newItem("list");
	}

	private void showLogin() {
		History.newItem("login");
	}

	public void go(final HasWidgets container) {
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("login");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		
		//Check user login or not
//		if (!this.sessionService.validateSession("somshine")) {
//			new LoginPresenter(rpcUserService, eventBus, new Login()).go(container);
//		} else {
			if (token != null) {
				switch (token) {
				case "login":
					new LoginPresenter(rpcUserService, eventBus, new Login()).go(container);
					break;
	
				case "add":
				case "edit":
					GWT.runAsync(new RunAsyncCallback() {
						public void onFailure(Throwable caught) {
						}
	
						public void onSuccess() {
							// lazily initialize our views, and keep them around to be reused
							//
							if (editContactView == null) {
								editContactView = new EditContactView();
	
							}
							new EditContactPresenter(rpcService, eventBus, editContactView).go(container);
						}
					});
					break;
	
				default:
					this.loadDashboar();
					break;
				}
			}
//		}
	}
	
	private void loadDashboar() {
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess() {
				// lazily initialize our views, and keep them around to be reused
				//
				if (contactsView == null) {
					contactsView = new ContactsViewImpl<ContactDetails>();

				}
				new ContactsPresenter(rpcService, eventBus, contactsView,
						ContactsColumnDefinitionsFactory.getContactsColumnDefinitions()).go(container);
			}
		});
	}
}
