package iks.java.ausbildung.adressverwaltung;

import java.util.Scanner;

public class AdressverwaltungMain {

	private static Scanner scanner = new Scanner(System.in);

	private static final int MAX_LENGTH = 10;
	private static String[][] addressDatabase = new String[MAX_LENGTH][AddressAttribute.values().length];

	public static void main(String[] args) {
		printMainMenu();
		MenuOption option = selectMainMenuOption();
		while (option != MenuOption.PROGRAM_END) {
			executeOption(option);
			printMainMenu();
			option = selectMainMenuOption();
		}

		scanner.close();
	}

	private static void executeOption(MenuOption option) {
		switch (option) {
		case ENTER_ADDRESS:
			addNewAddressToDatabase();
			break;

		case DISPLAY_ALL_ADDRESSES:
			printAllAddresses();
			break;

		case DISPLAY_ADDRESS:
			printAddress();
			break;

		case CHANGE_ADDRESS:
			changeAddress();
			break;

		case DELETE_ADDRESS:
			deleteAddress();
			break;

		default:
			System.out.println("nothing to execute: " + option.text);
			break;
		}

	}

	private static void printAllAddresses() {
		int index = 1;
		for (String[] address : addressDatabase) {
			if (address[0] != null) {
				printAddress(index, address);
			}
			index++;
		}
	}
	
	private static void printAddress() {
		if (isAddressDatabaseEmpty()) {
			System.out.println("Es gibt noch keine Adressen die ausgegeben werden könnten!");
			return;
		}
		System.out.print("Welche Adresse soll ausgegeben werden? ");
		int id = readInteger();
		if (!isValidID(id)) {
			System.out.println("Die ID existiert nicht!");
			return;
		}
		printAddress(id, addressDatabase[id - 1]);
	}

	private static void changeAddress() {
		if (isAddressDatabaseEmpty()) {
			System.out.println("Es gibt noch keine Adressen die geändert werden könnten!");
			return;
		}
		System.out.print("Welche Adresse soll geändert werden? ");
		int id = readInteger();
		if (!isValidID(id)) {
			System.out.println("Die ID existiert nicht!");
			return;
		}
		printAddress(id, addressDatabase[id - 1]);
		changeAddress(id, addressDatabase[id - 1]);
	}

	private static void deleteAddress() {
		if (isAddressDatabaseEmpty()) {
			System.out.println("Es gibt noch keine Adressen die gelöscht werden könnten!");
			return;
		}
		System.out.print("Welche Adresse soll geändert werden? ");
		int id = readInteger();
		if (!isValidID(id)) {
			System.out.println("Die ID existiert nicht!");
			return;
		}
		deleteAddress(id, addressDatabase[id - 1]);
	}

	private static void deleteAddress(int id, String[] strings) {
		printAddress(id, addressDatabase[id - 1]);
		System.out.print("Soll die angezeigte Addresse wirklich gelöscht werden? (Y/N) ");
		String yn = scanner.nextLine();
		if ("Y".equals(yn.toUpperCase())) {
			addressDatabase[id - 1] = new String[AddressAttribute.values().length];
		}
		
	}

	private static void changeAddress(int id, String[] address) {
		System.out.println("Ändern Sie die Attribute der alten Adresse.");
		String[] newAddress = new String[AddressAttribute.values().length];

		int index = 0;
		for (AddressAttribute attribute : AddressAttribute.values()) {
			System.out.print("\t" + attribute.prompt + "(" + address[index] + "): ");
			String newValue = scanner.nextLine();
			if (newValue.length() > 0) {
				newAddress[index] = newValue;
			} else {
				newAddress[index] = address[index];
			}
			index++;
		}
		addressDatabase[id - 1] = newAddress;
	}

	private static boolean isValidID(int id) {
		return id >= 1 && id <= MAX_LENGTH && addressDatabase[id - 1][0] != null;
	}

	private static void printAddress(int index, String[] address) {
		System.out.print("" + index + " ");
		int column = 0;
		for (AddressAttribute attribute : AddressAttribute.values()) {
			System.out.print(attribute.prompt + ": " + address[column]);
			if (column < AddressAttribute.values().length - 1) {
				System.out.print(", ");
			} else {
				System.out.println();
			}
			column++;
		}

	}

	private static void addNewAddressToDatabase() {
		if (isAddressDatabaseFull()) {
			System.out.println("Die Datenbak ist voll. Es können keine weitere Adresse hinzugefügt werden");
			return;
		}
		String[] address = enterNewAdress();
		insertAddress(address);
	}

	private static int nextFreeIndex() {
		int index = 0;
		for (String[] address : addressDatabase) {
			if (address[0] == null) {
				break;
			}
			index++;
		}
		return index;
	}

	private static boolean isAddressDatabaseFull() {
		return nextFreeIndex() >= MAX_LENGTH;
	}

	private static boolean isAddressDatabaseEmpty() {
		for (String[] address : addressDatabase) {
			if (address[0] != null) {
				return false;
			}
		}
		return true;
	}

	private static void insertAddress(String[] address) {
		if (!isAddressDatabaseFull()) {
			addressDatabase[nextFreeIndex()] = address;
		}
	}

	private static String[] enterNewAdress() {
		System.out.println("Geben sie die folgenden Attribute der neuen Adresse ein");
		String[] address = new String[AddressAttribute.values().length];

		int index = 0;
		for (AddressAttribute attribute : AddressAttribute.values()) {
			System.out.print("\t" + attribute.prompt + ": ");
			address[index] = scanner.nextLine();
			index++;
		}

		return address;
	}

	private static void printMainMenu() {
		System.out.println("\n\n\n");
		System.out.println("Hauptmenü");
		System.out.println();

		for (MenuOption option : MenuOption.values()) {
			if (option.no >= 0) {
				System.out.println("\t" + option.no + " - " + option.text);
			}
		}
	}

	private static MenuOption selectMainMenuOption() {
		System.out.println();
		System.out.print("Bitte wählen Sie ein Option: ");
		MenuOption option = readMenuOption();
		while (option == MenuOption.NO_OPTION) {
			System.out.print("Bitte wählen Sie eine gültige Menüoption: ");
			option = readMenuOption();
		}
		return option;
	}

	private static int readInteger() {
		int result;
		while (true) {
			try {
				result = scanner.nextInt();
				break;
			} catch (Exception ex) {
				System.out.print("Bitte eine ganze Zahl eingeben: ");
			}
		}
		scanner.nextLine();
		return result;
	}

	private static MenuOption readMenuOption() {
		int no = readInteger();
		for (MenuOption option : MenuOption.values()) {
			if (option.no == no) {
				return option;
			}
		}

		return MenuOption.NO_OPTION;
	}
}
