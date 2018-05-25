package iks.java.ausbildung.adressverwaltung.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import iks.java.ausbildung.adressverwaltung.adresse.Address;
import iks.java.ausbildung.adressverwaltung.adresse.Address.Attribute;

public class AddressViewDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 8974567197158749051L;

	public enum Status {
		CHANGED, COPIED, DELETED, UNCHANGED;
	}

	private Address oldAddress;
	private Address newAddress;

	private static final String CHANGE_CMD = "Ändern";
	private static final String COPY_CMD = "Kopieren";
	private static final String DELETE_CMD = "Löschen";
	private static final String CANCEL_CMD = "Abbrechen";
	private static final String EXECUTE_CMD = "Ausführen";

	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel streetLabel;
	private JLabel houseNumberLabel;
	private JLabel zipCodeLabel;
	private JLabel cityTextField;

	private JButton changeButton = new JButton(CHANGE_CMD);
	private JButton copyButton = new JButton(COPY_CMD);
	private JButton deleteButton = new JButton(DELETE_CMD);
	private JButton cancelButton = new JButton(CANCEL_CMD);
	private JButton executeButton = new JButton(EXECUTE_CMD);

	private Status status;

	public AddressViewDialog(Address address) {
		setTitle("Adresse anzeigen");
		oldAddress = address;
		this.status = Status.UNCHANGED;

		this.firstNameLabel = new JLabel(address.firstName);
		this.lastNameLabel = new JLabel(address.lastName);
		this.streetLabel = new JLabel(address.street);
		this.houseNumberLabel = new JLabel(address.streetNumber);
		this.zipCodeLabel = new JLabel(address.zipCode);
		this.cityTextField = new JLabel(address.city);

		setupDialog();

		pack();
		setVisible(true);
	}

	private void setupDialog() {
		setModal(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(200, 200);
		getContentPane().setLayout(new BorderLayout());

		add(createContent(), BorderLayout.NORTH);
		add(createButtons(), BorderLayout.SOUTH);

		getRootPane().setDefaultButton(cancelButton);
	}

	private Component createButtons() {
		JPanel buttonPanel = new JPanel();
		changeButton.addActionListener(this);
		copyButton.addActionListener(this);
		deleteButton.addActionListener(this);
		cancelButton.addActionListener(this);
		executeButton.addActionListener(this);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(changeButton);
		buttonPanel.add(copyButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(executeButton);
		buttonPanel.add(cancelButton);
		
		executeButton.setEnabled(false);

		return buttonPanel;
	}

	private Component createContent() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(6, 1));

		contentPanel.add(createStringInputElement("Vorname:", this.firstNameLabel));
		contentPanel.add(createStringInputElement("Nachname:", this.lastNameLabel));
		contentPanel.add(createStringInputElement("Straße:", this.streetLabel));
		contentPanel.add(createStringInputElement("Hausnummer:", this.houseNumberLabel));
		contentPanel.add(createStringInputElement("PLZ:", this.zipCodeLabel));
		contentPanel.add(createStringInputElement("Ort:", this.cityTextField));

		return contentPanel;
	}

	private void updateContent(Address address) {
		this.firstNameLabel.setText(address.firstName);
		this.lastNameLabel.setText(address.lastName);
		this.streetLabel.setText(address.street);
		this.houseNumberLabel.setText(address.streetNumber);
		this.zipCodeLabel.setText(address.zipCode);
		this.cityTextField.setText(address.city);
	}

	private Component createStringInputElement(String label, JLabel textContent) {
		JPanel stringInput = new JPanel();
		stringInput.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lbl = new JLabel(label);
		lbl.setPreferredSize(new Dimension(80, 10));
		stringInput.add(lbl);
		stringInput.add(textContent);

		return stringInput;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == changeButton) {
			setTitle("Adresse anzeigen");
			AddressInputDialog inputDialog = new AddressInputDialog(oldAddress);
			if (inputDialog.isSaved()) {
				this.newAddress = inputDialog.getAddress();
				this.oldAddress = newAddress;
				this.updateContent(newAddress);
				this.status = Status.CHANGED;
				copyButton.setEnabled(false);
				deleteButton.setEnabled(false);
				
				setTitle("Soll diese Änderung wirklich durchgeführt werden?");
				executeButton.setEnabled(true);
			}
		}
		if (event.getSource() == copyButton) {
			this.newAddress = new Address(oldAddress);
			this.status = Status.COPIED;
			changeButton.setEnabled(false);
			copyButton.setEnabled(false);
			deleteButton.setEnabled(false);
			
			setTitle("Soll diese Adresse wirklich kopiert werden?");
			executeButton.setEnabled(true);
		}
		if (event.getSource() == deleteButton) {
			this.status = Status.DELETED;
			changeButton.setEnabled(false);
			copyButton.setEnabled(false);
			deleteButton.setEnabled(false);

			setTitle("Soll diese Adresse wirklich gelöscht werden?");
			executeButton.setEnabled(true);
		}
		if (event.getSource() == cancelButton) {
			this.dispose();
		}
		if (event.getSource() == executeButton) {
			this.dispose();
		}
	}

	public Status getStatus() {
		return status;
	}

	public Address getAddress() {
		switch (this.status) {
		case CHANGED:
			return newAddress;
		case COPIED:
			return newAddress;
		default:
			return oldAddress;
		}
	}

	public static void main(String[] args) {
		AddressViewDialog view = new AddressViewDialog(
				new Address("Name", "Nachname", "Straße", "Hausnummer", "PLZ", "Ort"));
		switch (view.status) {
		case CHANGED:
			System.out.println("changed:");
			System.out.println(view.getAddress());
			break;
		case COPIED:
			System.out.println("copied:");
			System.out.println(view.getAddress());
			break;
		case DELETED:
			System.out.println("deleted:");
			System.out.println(view.getAddress());
			break;
		case UNCHANGED:
			System.out.println("unchanged");
			System.out.println(view.getAddress());
		}
	}

	class AddressUpdater extends FocusAdapter {

		Attribute attribute;

		public AddressUpdater(Attribute attribute) {
			this.attribute = attribute;
		}

		@Override
		public void focusGained(FocusEvent event) {
			((JTextField) event.getSource()).selectAll();
		}

	}

}
