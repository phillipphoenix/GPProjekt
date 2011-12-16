package vehicleShepard;

public class Customer {
	private int userID, phone, phoneCode;
	private String address, country, firstName, lastName, licenseNumber, licenseExpDate;
	
	public Customer(int userID, int phone, int phoneCode, String address, String country, String firstName, String lastName, String licenseNumber, String licenseExpDate) {
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

	public int getUserID() {
		return userID;
	}

	public int getPhone() {
		return phone;
	}

	public int getPhoneCode() {
		return phoneCode;
	}

	public String getAddress() {
		return address;
	}

	public String getCountry() {
		return country;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public String getLicenseExpDate() {
		return licenseExpDate;
	}

}
