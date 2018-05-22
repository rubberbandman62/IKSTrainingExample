package iks.java.ausbildung.adressverwaltung;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import iks.java.ausbildung.adressverwaltung.storage.AddressStorage;
import iks.java.ausbildung.adressverwaltung.ui.AdressverwaltungUI;
import iks.java.ausbildung.adressverwaltung.ui.MenuOption;

public class AdressverwaltungApp {

	private Scanner scanner;
	private PrintStream out;
	private AdressverwaltungUI ui;
	private AddressStorage storage;

	public AdressverwaltungApp(InputStream input, PrintStream output) {
		scanner = new Scanner(input);
		out = output;
		storage = AddressStorage.getInstance();
		ui = new AdressverwaltungUI(scanner, out, storage);
	}
	
	public void run() {
		MenuOption option = ui.selectExecutionOption(MenuOption.values());
		while (option != MenuOption.PROGRAM_END) {
			executeOption(option);
			option = ui.selectExecutionOption(MenuOption.values());
		}

		scanner.close();
	}

	private void executeOption(MenuOption option) {
		switch (option) {
		case ENTER_ADDRESS:
			ui.addNewAddressToDatabase();
			break;

		case DISPLAY_ALL_ADDRESSES:
			ui.printAllAddresses();
			break;

		case DISPLAY_ADDRESS:
			ui.printAddress();
			break;

		case CHANGE_ADDRESS:
			ui.changeAddress();
			break;

		case DELETE_ADDRESS:
			ui.deleteAddress();
			break;

		default:
			System.out.println("nothing to execute: " + option.text);
			break;
		}

	}

	public static void main(String[] args) {
		AdressverwaltungApp app = new AdressverwaltungApp(System.in, System.out);
		app.run();
	}

}
