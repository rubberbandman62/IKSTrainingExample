package iks.java.ausbildung.adressverwaltung.adresse;


/**
 * Address
 * 
 * Immutable class with an identifier ID. Usually the ID will be calculated by a storage system. New addresses
 * usually don't have an id (null). The id can only be set by a special constructor, which takes the new id and
 * the original address.
 * 
 * @author hto
 *
 */
public class Address {

	public enum Attribute {
		ID("Id"), FIRST_NAME("Vorname"), LAST_NAME("Nachname"), STREET("Straﬂe"), STREET_NUMBER("Hausnummer"), ZIP_CODE(
				"Postleitzahl"), CITY("Ort");

		public final String prompt;

		private Attribute(String prompt) {
			this.prompt = prompt;
		}
	}

	public final Integer id;
	public final String firstName;
	public final String lastName;
	public final String name;
	public final String street;
	public final String streetNumber;
	public final String zipCode;
	public final String city;

	private Address(Integer id, String firstName, String lastName, String street, String streetNumber, String zipCode, String city) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = lastName + ", " + firstName;
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
	}

	public Address(String firstName, String lastName, String street, String streetNumber, String zipCode, String city) {
		this(null, firstName, lastName, street, streetNumber, zipCode, city);
	}

	/**
	 * Convenience Constructor to create an address from an array of strings. The array must contain six attributes in
	 * the order: firstName, lastName, street, streetNumber, zipCode, city
	 * The resulting address has no id (null)
	 * 
	 * @param attributeArray
	 */
	public Address(String[] attributeArray) {
		this(attributeArray[1], attributeArray[2], attributeArray[3], attributeArray[4], attributeArray[5], attributeArray[6]);
	}

	
	/**
	 * Constructor to create a copy of other with an id. Usually called by a storage system. 
	 * 
	 * @param id
	 * @param other
	 */
	public Address(Integer id, Address other) {
		this(id, other.firstName, other.lastName, other.street, other.streetNumber, other.zipCode, other.city);
	}

	/**
	 * Constructor to create a copy of other with no id (null). The reason is to keep the id unique. 
	 * 
	 * @param id
	 * @param other
	 */
	public Address(Address other) {
		this(null, other);
	}

	public String[] toAttributeArray() {
		return new String[] { "" + this.id, this.firstName, this.lastName, this.street, this.streetNumber, this.zipCode,
				this.city };
	}

	@Override
	public String toString() {
		return "" + this.id + ". " + this.name + ", " + this.street + ", " + this.streetNumber + ", " + this.zipCode
				+ ", " + this.city;
	}
}
