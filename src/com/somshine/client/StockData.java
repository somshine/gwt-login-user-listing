package com.somshine.client;

import com.google.gwt.core.client.JavaScriptObject;

public class StockData extends JavaScriptObject {
	protected StockData() {}                                              // (2)

	  public final native String getSymbol() /*-{ return this.symbol; }-*/; // (3)
	  public final native double getPrice() /*-{ return this.price; }-*/;
	  public final native double getChange() /*-{ return this.change; }-*/;

	  // Non-JSNI method to return change percentage.                       // (4)
	  public final double getChangePercent() {
	    return 100.0 * getChange() / getPrice();
	  }
}
