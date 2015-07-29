package business;

import java.io.Serializable;

public class LibraryMember extends Person implements Serializable {

	private String memberId;

	public LibraryMember(String f, String l, String t, Address a, String mId) {
		super(f, l, t, a);
		this.memberId = mId;
	}

	public String getMemberId() {
		return memberId;
	}

	@Override
	public String toString() {
		return "[" + memberId + ":" + super.getFirstName() + ", " + super.getLastName() + "]";
	}

}
