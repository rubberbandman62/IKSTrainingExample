package iks.java.ausbildung.adressverwaltung.ui;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

import iks.java.ausbildung.adressverwaltung.adresse.Address;
import iks.java.ausbildung.adressverwaltung.adresse.Address.Attribute;
import iks.java.ausbildung.adressverwaltung.storage.AddressStorage;
import iks.java.ausbildung.adressverwaltung.storage.StorageInterface;

public class AdressverwaltungUI {
	
	private Scanner scanner;
	private StorageInterface storage;
	private PrintStream out;
	
	public AdressverwaltungUI(Scanner scanner, PrintStream out, StorageInterface storage) {
		this.scanner = scanner;
		this.out = out;
		this.storage = storage;
	}

	public MenuOption selectExecutionOption(MenuOption[] options) {
		printMainMenu(options);
		return selectMainMenuOption(options);
	}
	
	private void printMainMenu(MenuOption[] options) {
		out.println("\n\n\n");
		out.println("Hauptmenü");
		out.println();

		for (MenuOption option : options) {
			if (option.no >= 0) {
				out.println("\t" + option.no + " - " + option.text);
			}
		}
	}

	private MenuOption selectMainMenuOption(MenuOption[] options) {
		out.println();
		out.print("Bitte wählen Sie ein Option: ");
		MenuOption option = readMenuOption(options);
		while (option == MenuOption.NO_OPTION) {
			out.print("Bitte wählen Sie eine gültige Menüoption: ");
			option = readMenuOption(options);
		}
		return option;
	}

	public int readInteger() {
		int result;
		while (true) {
			try {
				result = scanner.nextInt();
				break;
			} catch (Exception ex) {
				out.print("Bitte eine ganze Zahl eingeben: ");
			} finally {
				scanner.nextLine();
			}
		}
		return result;
	}

	private MenuOption readMenuOption(MenuOption[] options) {
		int no = readInteger();
		for (MenuOption option : options) {
			if (option.no == no) {
				return option;
			}
		}

		return MenuOption.NO_OPTION;
	}

	public String[] enterNewAddress() {
		out.println("Geben Sie die folgenden Attribute der neuen Adresse ein");
		String[] address = new String[Attribute.values().length];

		int index = 0;
		for (Attribute attribute : Attribute.values()) {
			if (attribute != Attribute.ID) {
				out.print("\t" + attribute.prompt + ": ");
				address[index] = scanner.nextLine();
			}
			index++;
		}

		return address;
	}

	public void addNewAddressToDatabase() {
		if (storage.isFull()) {
			out.println("Die Datenbank ist voll. Es können keine weitere Adresse hinzugefügt werden");
			return;
		}
		String[] address = enterNewAddress();
		storage.insertAddress(new Address(address));
	}

	public void printAddress(Address address) {
		int column = 0;
		for (Attribute attribute : Attribute.values()) {
			out.print(attribute.prompt + ": " + address.toStringArray()[column]);
			if (column < Attribute.values().length - 1) {
				out.print(", ");
			} else {
				out.println();
			}
			column++;
		}
	}

	public void changeAddress(Address address) {
		out.println("Ändern Sie die Attribute der alten Adresse.");
		String[] newAddress = new String[Attribute.values().length];
		String[] oldAddress = address.toStringArray();

		int index = 0;
		for (Attribute attribute : Attribute.values()) {
			String newValue = "";
			if (attribute != Attribute.ID) {
				out.print("\t" + attribute.prompt + "(" + oldAddress[index] + "): ");
				newValue = scanner.nextLine();
			}
			if (newValue.length() > 0)
				newAddress[index] = newValue;
			else 
				newAddress[index] = oldAddress[index];
			index++;
		}
		storage.updateAddress(address.id, new Address(newAddress));
	}

	public void deleteAddress(Address address) {
		printAddress(address);
		out.print("Soll die angezeigte Addresse wirklich gelöscht werden? (Y/N) ");
		String yn = scanner.nextLine();
		if ("Y".equals(yn.toUpperCase())) {
			storage.deleteAddress(address.id);
		}

	}

	public void deleteAddress() {
		if (storage.isEmpty()) {
			out.println("Es gibt noch keine Adressen die gelöscht werden könnten!");
			return;
		}
		out.print("Welche Adresse soll gelöscht werden? ");
		int id = readInteger();
		if (!storage.isValidID(id)) {
			out.println("Die ID existiert nicht!");
			return;
		}
		Address address = storage.readAddress(id);
		deleteAddress(address);
	}

	public void changeAddress() {
		if (storage.isEmpty()) {
			out.println("Es gibt noch keine Adressen die geändert werden könnten!");
			return;
		}
		out.print("Welche Adresse soll geändert werden? ");
		int id = readInteger();
		if (!storage.isValidID(id)) {
			out.println("Die ID existiert nicht!");
			return;
		}
		Address address = storage.readAddress(id);
		printAddress(address);
		changeAddress(address);
	}

	public void printAddress() {
		if (storage.isEmpty()) {
			out.println("Es gibt noch keine Adressen die ausgegeben werden könnten!");
			return;
		}
		out.print("Welche Adresse soll ausgegeben werden? ");
		int id = readInteger();
		if (!storage.isValidID(id)) {
			out.println("Die ID existiert nicht!");
			return;
		}
		printAddress(storage.readAddress(id));
	}

	public void printAllAddresses() {
		for (Iterator<Address> iterator = AddressStorage.getInstance().iterator(); iterator.hasNext();) {
			Address address = iterator.next();
			printAddress(address);
		}
	}

}