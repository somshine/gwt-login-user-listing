package com.somshine.client;

import com.google.gwt.user.client.rpc.RemoteService; 
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;

import com.somshine.shared.Contact;
import com.somshine.shared.ContactDetails;

@RemoteServiceRelativePath("contactsService")
public interface ContactsService extends RemoteService {
	
  Contact addContact(Contact contact);
  Boolean deleteContact(String id); 
  ArrayList<ContactDetails> deleteContacts(ArrayList<String> ids);
  ArrayList<ContactDetails> getContactDetails();
  Contact getContact(String id);
  Contact updateContact(Contact contact);
}
