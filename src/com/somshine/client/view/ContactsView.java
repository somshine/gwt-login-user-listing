package com.somshine.client.view;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.somshine.client.StockData;
import com.somshine.client.common.ColumnDefinition;

import java.util.List;

public interface ContactsView<T> {

  public interface Presenter<T> {
    void onAddButtonClicked();
    void onDeleteButtonClicked();
    void onItemClicked(T clickedItem);
    void onItemSelected(T selectedItem);
  }
  
  void setPresenter(Presenter<T> presenter);
  Label getErrorMessage();
  void setColumnDefinitions(List<ColumnDefinition<T>> columnDefinitions);
  void setRowData(List<T> rowData);
  void priceData(JsArray<StockData> prices);
  Widget asWidget();
}
