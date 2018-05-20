package iks.java.ausbildung.adressverwaltung.ui;

import java.util.Iterator;

import iks.java.ausbildung.adressverwaltung.AdressverwaltungMain;
import iks.java.ausbildung.adressverwaltung.adresse.Address.Attribute;
import iks.java.ausbildung.adressverwaltung.storage.AddressStorage;

public class AdressverwaltungUI {

	public static void printMainMenu() {
		System.out.println("\n\n\n");
		System.out.println("Hauptmenü");
		System.out.println();

		for (MenuOption option : MenuOption.values()) {
			if (option.no >= 0) {
				System.out.println("\t" + option.no + " - " + option.text);
			}
		}
	}

	public static MenuOption selectMainMenuOption() {
		System.out.println();
		System.out.print("Bitte wählen Sie ein Option: ");
		MenuOption option = AdressverwaltungUI.readMenuOption();
		while (option == MenuOption.NO_OPTION) {
			System.out.print("Bitte wählen Sie eine gültige Menüoption: ");
			option = AdressverwaltungUI.readMenuOption();
		}
		return option;
	}

	public static int readInteger() {
		int result;
		while (true) {
			try {
				result = AdressverwaltungMain.scanner.nextInt();
				break;
			} catch (Exception ex) {
				System.out.print("Bitte eine ganze Zahl eingeben: ");
			} finally {
				AdressverwaltungMain.scanner.nextLine();
			}
		}
		return result;
	}

	public static MenuOption readMenuOption() {
		int no = readInteger();
		for (MenuOption option : MenuOption.values()) {
			if (option.no == no) {
				return option;
			}
		}

		return MenuOption.NO_OPTION;
	}

	public static String[] enterNewAddress() {
		System.out.println("Geben sie die folgenden Attribute der neuen Adresse ein");
		String[] address = new String[Attribute.values().length];

		int index = 0;
		for (Attribute attribute : Attribute.values()) {
			if (attribute != Attribute.ID) {
				System.out.print("\t" + attribute.prompt + ": ");
				address[index] = AdressverwaltungMain.scanner.nextLine();
			}
			index++;
		}

		return address;
	}

	public static void addNewAddressToDatabase() {
		if (AdressverwaltungMain.storage.isFull()) {
			System.out.println("Die Datenbank ist voll. Es können keine weitere Adresse hinzugefügt werden");
			return;
		}
		String[] address = enterNewAddress();
		AdressverwaltungMain.storage.insertAddress(address);
	}

	public static void printAddress(String[] address) {
		int column = 0;
		for (Attribute attribute : Attribute.values()) {
			System.out.print(attribute.prompt + ": " + address[column]);
			if (column < Attribute.values().length - 1) {
				System.out.print(", ");
			} else {
				System.out.println();
			}
			column++;
		}

	}

	public static void changeAddress(String[] address) {
		System.out.println("Ändern Sie die Attribute der alten Adresse.");
		String[] newAddress = new String[Attribute.values().length];

		int index = 0;
		for (Attribute attribute : Attribute.values()) {
			String newValue = "";
			if (attribute != Attribute.ID) {
				System.out.print("\t" + attribute.prompt + "(" + address[index] + "): ");
				newValue = AdressverwaltungMain.scanner.nextLine();
			}
			if (newValue.length() > 0)
				newAddress[index] = newValue;
			else 
				newAddress[index] = address[index];
			index++;
		}
		AdressverwaltungMain.storage.updateAddress(address[0], newAddress);
	}

	public static void deleteAddress(String[] address) {
		printAddress(address);
		System.out.print("Soll die angezeigte Addresse wirklich gelöscht werden? (Y/N) ");
		String yn = AdressverwaltungMain.scanner.nextLine();
		if ("Y".equals(yn.toUpperCase())) {
			AdressverwaltungMain.storage.deleteAddress(address[0], address);
		}

	}

	public static void deleteAddress() {
		if (AdressverwaltungMain.storage.isEmpty()) {
			System.out.println("Es gibt noch keine Adressen die gelöscht werden könnten!");
			return;
		}
		System.out.print("Welche Adresse soll geändert werden? ");
		int id = readInteger();
		if (!AdressverwaltungMain.storage.isValidID(id)) {
			System.out.println("Die ID existiert nicht!");
			return;
		}
		String[] address = AdressverwaltungMain.storage.readAddress(id);
		deleteAddress(address);
	}

	public static void changeAddress() {
		if (AdressverwaltungMain.storage.isEmpty()) {
			System.out.println("Es gibt noch keine Adressen die geändert werden könnten!");
			return;
		}
		System.out.print("Welche Adresse soll geändert werden? ");
		int id = readInteger();
		if (!AdressverwaltungMain.storage.isValidID(id)) {
			System.out.println("Die ID existiert nicht!");
			return;
		}
		String[] address = AdressverwaltungMain.storage.readAddress(id);
		printAddress(address);
		changeAddress(address);
	}

	public static void printAddress() {
		if (AdressverwaltungMain.storage.isEmpty()) {
			System.out.println("Es gibt noch keine Adressen die ausgegeben werden könnten!");
			return;
		}
		System.out.print("Welche Adresse soll ausgegeben werden? ");
		int id = readInteger();
		if (!AdressverwaltungMain.storage.isValidID(id)) {
			System.out.println("Die ID existiert nicht!");
			return;
		}
		printAddress(AdressverwaltungMain.storage.readAddress(id));
	}

	public static void printAllAddresses() {
		for (Iterator<String[]> iterator = AddressStorage.getInstance().iterator(); iterator.hasNext();) {
			String[] address = (String[]) iterator.next();
			printAddress(address);
		}
	}

}
