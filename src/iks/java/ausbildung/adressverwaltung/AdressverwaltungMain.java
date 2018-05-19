package iks.java.ausbildung.adressverwaltung;

import java.util.Scanner;

import iks.java.ausbildung.adressverwaltung.storage.AddressStorage;
import iks.java.ausbildung.adressverwaltung.ui.AdressverwaltungUI;
import iks.java.ausbildung.adressverwaltung.ui.MenuOption;

public class AdressverwaltungMain {

	public static Scanner scanner = new Scanner(System.in);
	public static AddressStorage storage = AddressStorage.getInstance();

	public static void main(String[] args) {
		AdressverwaltungUI.printMainMenu();
		MenuOption option = AdressverwaltungUI.selectMainMenuOption();
		while (option != MenuOption.PROGRAM_END) {
			executeOption(option);
			AdressverwaltungUI.printMainMenu();
			option = AdressverwaltungUI.selectMainMenuOption();
		}

		scanner.close();
	}

	private static void executeOption(MenuOption option) {
		switch (option) {
		case ENTER_ADDRESS:
			AdressverwaltungUI.addNewAddressToDatabase();
			break;

		case DISPLAY_ALL_ADDRESSES:
			AdressverwaltungUI.printAllAddresses();
			break;

		case DISPLAY_ADDRESS:
			AdressverwaltungUI.printAddress();
			break;

		case CHANGE_ADDRESS:
			AdressverwaltungUI.changeAddress();
			break;

		case DELETE_ADDRESS:
			AdressverwaltungUI.deleteAddress();
			break;

		default:
			System.out.println("nothing to execute: " + option.text);
			break;
		}

	}
}
