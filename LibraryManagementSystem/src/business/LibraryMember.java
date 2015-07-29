package business;

import java.io.Serializable;

public class LibraryMember extends Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3438301790182522805L;
	private String memberId;
	private CheckoutRecord checkoutRecord;

	public LibraryMember(String f, String l, String t, Address a, String mId) {
		super(f, l, t, a);
		this.memberId = mId;
		checkoutRecord = new CheckoutRecord();
	}

	public String getMemberId() {
		return memberId;
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}

	public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}

}
