package iks.java.ausbildung.adressverwaltung.ui;

public enum MenuOption {
	NO_OPTION(-1, "ungültige Option"), 
	ENTER_ADDRESS(1, "Adresse eingeben"), 
	DISPLAY_ALL_ADDRESSES(2, "alle Adressen anzeigen"), 
	DISPLAY_ADDRESS(3, "Adresse anzeigen"), 
	CHANGE_ADDRESS(4, "Adresse ändern"), 
	DELETE_ADDRESS(5, "Adresse löschen"), 
	PROGRAM_END(99, "Programm beenden");

	public final int no;
	public final String text;

	private MenuOption(int no, String text) {
		this.no = no;
		this.text = text;
	}
}
