package ui.controller;

import java.time.LocalDate;

public class OverdueData {
	private String isbn;
	private String title;
	private int copyNum;
	private LocalDate dueDate;
	private String checkedBy;

	public OverdueData(String isbn, String title, int copyNum, LocalDate dueDate, String checkBy) {
		this.isbn = isbn;
		this.title = title;
		this.copyNum = copyNum;
		this.dueDate = dueDate;
		this.checkedBy = checkBy;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public int getCopyNum() {
		return copyNum;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public String getCheckedBy() {
		return checkedBy;
	}

}
