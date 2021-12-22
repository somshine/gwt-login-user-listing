package com.somshine.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.somshine.client.common.ColumnDefinition;
import com.somshine.client.view.ContactsView.Presenter;
import com.somshine.client.view.ContactsViewImpl.ContactsViewUiBinder;

public class Login extends Composite {
	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);
	private Presenter presenter;

	@UiTemplate("Login.ui.xml")
	interface LoginUiBinder extends UiBinder<Widget, Login> {
	}

	public interface Presenter {
		void doLogin();
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	public void setRowData(List rowData) {
	}

	public Login() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget() {
		return this;
	}

	@UiField
	Button buttonSubmit;

	@UiField
	TextBox loginBox;
	@UiField
	TextBox passwordBox;
	@UiField
	Label userNameError;
	@UiField
	Label passwordError;

	private Boolean tooShort = false;
	
	public Button getSubmitButton() {
		return this.buttonSubmit;
	}

	@UiHandler("loginBox")
	void handleLoginChange(ValueChangeEvent<String> event) {
		if (event.getValue().length() < 6) {
			userNameError.setText("Username to short (Its must be 6 character long)");
			tooShort = true;
			userNameError.setVisible(true);
		} else {
			tooShort = false;
			userNameError.setText("");
			userNameError.setVisible(false);
		}
	}

	@UiHandler("passwordBox")
	void handlePasswordChange(ValueChangeEvent<String> event) {
		if (event.getValue().length() < 6) {
			passwordError.setText("Password is to short (Its must be 6 character long)");
			tooShort = true;
			passwordError.setVisible(true);
		} else {
			tooShort = false;
			passwordError.setText("");
			passwordError.setVisible(false);
		}
	}
}
