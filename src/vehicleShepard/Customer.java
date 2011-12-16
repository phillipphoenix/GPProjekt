package vehicleShepard;

public class Customer {
	private int userID, phone, phoneCode;
	private String address, country, firstName, lastName, licenseNumber;
	private java.sql.Date licenseExpDate;
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public int getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(int phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public java.sql.Date getLicenseExpDate() {
		return licenseExpDate;
	}

	public void setLicenseExpDate(java.sql.Date licenseExpDate) {
		this.licenseExpDate = licenseExpDate;
	}

	public Customer(int userID, int phone, int phoneCode, String address, String country, String firstName, String lastName, String licenseNumber, java.sql.Date licenseExpDate) {
		this.userID = userID;
		this.phone = phone;
		this.phoneCode = phoneCode;
		this.address = address;
		this.country = country;
		this.firstName = firstName;
		this.lastName = lastName;
		this.licenseNumber = licenseNumber;
		this.licenseExpDate = licenseExpDate;
	}
}
