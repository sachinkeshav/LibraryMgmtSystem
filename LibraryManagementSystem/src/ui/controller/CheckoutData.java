package ui.controller;

import java.time.LocalDate;

public class CheckoutData {
	private String memberId;
	private String title;
	private LocalDate dueDate;
	private LocalDate checkoutDate;

	public CheckoutData(String memberId, String title, LocalDate dueDate, LocalDate checkoutDate) {
		this.memberId = memberId;
		this.title = title;
		this.dueDate = dueDate;
		this.checkoutDate = checkoutDate;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

}
