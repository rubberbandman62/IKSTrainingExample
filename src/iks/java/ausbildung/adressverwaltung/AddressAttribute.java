package iks.java.ausbildung.adressverwaltung;

public enum AddressAttribute {
	NAME("Name"),
	STREET("Stra�e"),
	STREET_NUMBER("Hausnummer"),
	ZIP_CODE("Postleitzahl"),
	CITY("Ort");
	
	public final String prompt;
	
	private AddressAttribute(String prompt) {
		this.prompt = prompt;
	}

}
