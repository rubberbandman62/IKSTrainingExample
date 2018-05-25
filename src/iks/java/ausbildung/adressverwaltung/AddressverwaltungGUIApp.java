package iks.java.ausbildung.adressverwaltung;

import javax.swing.SwingUtilities;

import iks.java.ausbildung.adressverwaltung.gui.AddressverwaltungGUI;

public class AddressverwaltungGUIApp {

	public static void main(String[] args) {
		AddressverwaltungGUI gui = new AddressverwaltungGUI();
		System.out.println("Adressverwaltung started in thread " + Thread.currentThread().getName());

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gui.setupGUI();
			}
		});

		System.out.println("Waiting for all child threads to terminate: " + Thread.currentThread().getName());
	}

}
