package iks.java.ausbildung.adressverwaltung.adresse;

public class Address {
	
	public enum Attribute {
		ID("Id"),
		NAME("Name"),
		STREET("Stra�e"),
		STREET_NUMBER("Hausnummer"),
		ZIP_CODE("Postleitzahl"),
		CITY("Ort");
		
		public final String prompt;
		
		private Attribute(String prompt) {
			this.prompt = prompt;
		}

	}
	
	public final Integer id;
	public final String name;
	public final String street;
	public final String streetNumber;
	public final String zipCode;
	public final String city;

	public Address(String name, String street, String streetNumber, String zipCode, String city) {
		this.id = null;
		this.name = name;
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
	}

	public Address(String[] address) {
		this.id = null;
		this.name = address[1];
		this.street = address[2];
		this.streetNumber = address[3];
		this.zipCode = address[4];
		this.city = address[5];
	}
	
	public Address(Integer id, Address other) {
		this.id = id;
		this.name = other.name;
		this.street = other.street;
		this.streetNumber = other.streetNumber;
		this.zipCode = other.zipCode;
		this.city = other.city;
	}

	public Address(Integer id , String[] address) {
		this.id = id;
		this.name = address[1];
		this.street = address[2];
		this.streetNumber = address[3];
		this.zipCode = address[4];
		this.city = address[5];
	}
	
	public String[] toStringArray() {
		return new String[] {"" + this.id, this.name, this.street, this.streetNumber, this.zipCode, this.city};
	}

}
