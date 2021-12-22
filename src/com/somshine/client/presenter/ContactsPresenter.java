package com.somshine.client.presenter;

import com.somshine.client.ContactsServiceAsync;
import com.somshine.client.StockData;
import com.somshine.client.common.ColumnDefinition;
import com.somshine.client.common.SelectionModel;
import com.somshine.client.event.AddContactEvent;
import com.somshine.client.event.EditContactEvent;
import com.somshine.client.view.ContactsView;
import com.somshine.shared.ContactDetails;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;
import java.util.List;

public class ContactsPresenter implements Presenter, ContactsView.Presenter<ContactDetails> {
	private static final String JSON_URL = GWT.getModuleBaseURL() + "stockPrices?q=";

	private List<ContactDetails> contactDetails;
	private final ContactsServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final ContactsView<ContactDetails> view;
	private final SelectionModel<ContactDetails> selectionModel;

	public ContactsPresenter(ContactsServiceAsync rpcService, HandlerManager eventBus,
			ContactsView<ContactDetails> view, List<ColumnDefinition<ContactDetails>> columnDefinitions) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.view = view;
		this.selectionModel = new SelectionModel<ContactDetails>();
		this.view.setPresenter(this);
		this.view.setColumnDefinitions(columnDefinitions);
	}

	public void onAddButtonClicked() {
		eventBus.fireEvent(new AddContactEvent());
	}

	public void onDeleteButtonClicked() {
		deleteSelectedContacts();
	}

	public void onItemClicked(ContactDetails contactDetails) {
		eventBus.fireEvent(new EditContactEvent(contactDetails.getId()));
	}

	public void onItemSelected(ContactDetails contactDetails) {
		if (selectionModel.isSelected(contactDetails)) {
			selectionModel.removeSelection(contactDetails);
		} else {
			selectionModel.addSelection(contactDetails);
		}
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
		fetchContactDetails();
	}

	public void sortContactDetails() {

		// Yes, we could use a more optimized method of sorting, but the
		// point is to create a test case that helps illustrate the higher
		// level concepts used when creating MVP-based applications.
		//
		for (int i = 0; i < contactDetails.size(); ++i) {
			for (int j = 0; j < contactDetails.size() - 1; ++j) {
				if (contactDetails.get(j).getDisplayName()
						.compareToIgnoreCase(contactDetails.get(j + 1).getDisplayName()) >= 0) {
					ContactDetails tmp = contactDetails.get(j);
					contactDetails.set(j, contactDetails.get(j + 1));
					contactDetails.set(j + 1, tmp);
				}
			}
		}
	}

	public void setContactDetails(List<ContactDetails> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public List<ContactDetails> getContactDetails() {
		return contactDetails;
	}

	public ContactDetails getContactDetail(int index) {
		return contactDetails.get(index);
	}

	private void fetchContactDetails() {
		rpcService.getContactDetails(new AsyncCallback<ArrayList<ContactDetails>>() {
			public void onSuccess(ArrayList<ContactDetails> result) {
				contactDetails = result;
				sortContactDetails();
				view.setRowData(contactDetails);
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error fetching contact details");
			}
		});

		String url = JSON_URL + "SOM SFS KLA JAY VIR SUS";
		// Send request to server and catch any errors.
		// Send request to server and catch any errors.
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		try {
			Request request = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					displayError("Couldn’t retrieve JSON", false);
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						displayError("Data loaded successfully.", true);
						view.priceData(JsonUtils.<JsArray<StockData>>safeEval(response.getText()));
//						Window.alert(response.getText());
					} else {
						displayError("Couldn't retrieve JSON (" + response.getStatusText() + ")", false);
					}
				}
			});
		} catch (RequestException e) {
			displayError("Couldn’t retrieve JSON" + e.getMessage(), false);
		}
		
		getCrosSiteJsonData();
	}

	public void getCrosSiteJsonData() {
		try {
			String url = URL
					.encode("http://wireshoppy.lcl/stockprices.php?q=JAY VIRU ALA JAY VIR SUS&callback=callbacl1235");
	
			//To fetch the data form Cross site scripting.
			JsonpRequestBuilder builder = new JsonpRequestBuilder();
			builder.requestObject(url, new AsyncCallback<JsArray<StockData>>() {
				public void onFailure(Throwable caught) {
					displayError("Couldn't retrieve JSON", false);
				}
	
				public void onSuccess(JsArray<StockData> data) {
					if (data == null) {
						displayError("Couldn't retrieve JSON", false);
						return;
					}
					
					view.priceData(data);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Window.alert("Failed to load data :: " + e.getMessage());
		}
	}

	private void displayError(String error, Boolean isSuccess) {
		if (isSuccess) {
			this.view.getErrorMessage().setStyleName("alert alert-success");
			this.view.getErrorMessage().setText("Success: " + error);
		} else {
			this.view.getErrorMessage().setText("Error: " + error);
			this.view.getErrorMessage().setVisible(true);
		}
		this.view.getErrorMessage().setVisible(true);
	}

	private void deleteSelectedContacts() {
		List<ContactDetails> selectedContacts = selectionModel.getSelectedItems();
		ArrayList<String> ids = new ArrayList<String>();

		for (int i = 0; i < selectedContacts.size(); ++i) {
			ids.add(selectedContacts.get(i).getId());
		}

		rpcService.deleteContacts(ids, new AsyncCallback<ArrayList<ContactDetails>>() {
			public void onSuccess(ArrayList<ContactDetails> result) {
				contactDetails = result;
				sortContactDetails();
				view.setRowData(contactDetails);
			}

			public void onFailure(Throwable caught) {
				System.out.println("Error deleting selected contacts");
			}
		});
	}
}
