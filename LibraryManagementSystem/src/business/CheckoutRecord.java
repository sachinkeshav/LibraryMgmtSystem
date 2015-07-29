package business;

import java.io.Serializable;
import java.util.List;

public class CheckoutRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7086395344898382470L;
	private List<CheckoutRecordEntry> checkoutRecordEntries;

	public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
		return checkoutRecordEntries;
	}

	public void setCheckoutRecordEntries(List<CheckoutRecordEntry> checkoutRecordEntries) {
		this.checkoutRecordEntries = checkoutRecordEntries;
	}

}
