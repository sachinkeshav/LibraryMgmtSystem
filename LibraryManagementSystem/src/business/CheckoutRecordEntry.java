package business;

import java.io.Serializable;
import java.time.LocalDate;

public final class CheckoutRecordEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1175045592830492196L;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private BookCopy copyNum;

	public CheckoutRecordEntry(LocalDate cdate, LocalDate dDate, BookCopy cn) {
		this.checkoutDate = cdate;
		this.dueDate = dDate;
		this.copyNum = cn;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public BookCopy getCopyNum() {
		return copyNum;
	}

}
