package iks.java.ausbildung.adressverwaltung.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import iks.java.ausbildung.adressverwaltung.adresse.Address;

public class AddressInputDialog extends JDialog implements ActionListener, KeyListener, FocusListener {

	private static final long serialVersionUID = 8974567197158749051L;

	private Address oldAddress;
	private Address newAddress;

	private static final String SAVE_CMD = "Speichern";
	private static final String CANCEL_CMD = "Abbrechen";

	private JTextField firstNameTextField = new JTextField(20);
	private JTextField lastNameTextField = new JTextField(20);
	private JTextField streetTextField = new JTextField(20);
	private JTextField houseNumberTextField = new JTextField(5);
	private JTextField zipCodeTextField = new JTextField(5);
	private JTextField cityTextField = new JTextField(20);

	private JButton saveButton = new JButton(SAVE_CMD);
	private JButton cancelButton = new JButton(CANCEL_CMD);

	private boolean saved;

	// Dialog to create a new address
	public AddressInputDialog() {
		this("Neue Adresse eingeben", null);
	}

	// Dialog to change an existing address. Addresses are immutable. So actually 
	// a copy with the same ID will be created.
	public AddressInputDialog(Address address) {
		this("Adresse bearbeiten", address);
	}

	private AddressInputDialog(String title, Address address) {
		setTitle(title);
		oldAddress = address;

		setupDialog();

		pack();
		setVisible(true);
	}

	private void setupDialog() {
		setModal(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(200, 200);
		setLayout(new BorderLayout());

		add(createContentPanel(), BorderLayout.NORTH);
		add(createButtonPanel(), BorderLayout.SOUTH);
		
		getRootPane().setDefaultButton(saveButton);
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		
		cancelButton.addActionListener(this);
		cancelButton.addKeyListener(this);
		
		saveButton.addActionListener(this);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);

		return buttonPanel;
	}

	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(6, 1));
		
		contentPanel.add(createStringInputPanel("Vorname:", this.firstNameTextField));
		contentPanel.add(createStringInputPanel("Nachname:", this.lastNameTextField));
		contentPanel.add(createStringInputPanel("Straﬂe:", this.streetTextField));
		contentPanel.add(createStringInputPanel("Hausnummer:", this.houseNumberTextField));
		contentPanel.add(createStringInputPanel("PLZ:", this.zipCodeTextField));
		contentPanel.add(createStringInputPanel("Ort:", this.cityTextField));

		return contentPanel;
	}

	private JPanel createStringInputPanel(String label, JTextField textField) {
		JPanel stringInputPanel = new JPanel();
		stringInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lbl = new JLabel(label);
		lbl.setPreferredSize(new Dimension(80, 10));
		stringInputPanel.add(lbl);
		stringInputPanel.add(textField);
		textField.addFocusListener(this);

		return stringInputPanel;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		newAddress = new Address(this.firstNameTextField.getText(), 
				this.lastNameTextField.getText(), 
				this.streetTextField.getText(), 
				this.houseNumberTextField.getText(), 
				this.zipCodeTextField.getText(), 
				this.cityTextField.getText());
		saved = event.getSource() == saveButton ? true : false;
		this.dispose();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// the default button is the save button. In case the cancel button has the focus and
		// ENTER is pressed, we don't want the default behavior. 
		if (event.getSource() == cancelButton && event.getKeyCode() == KeyEvent.VK_ENTER) {
			saved = false;
			event.consume();
			this.dispose();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event) {}
	
	@Override
	public void keyTyped(KeyEvent event) {}
	
	public boolean isSaved() {
		return saved;
	}

	@Override
	public void focusGained(FocusEvent event) {
		((JTextField) event.getSource()).selectAll();
	}
	
	@Override
	public void focusLost(FocusEvent arg0) {}
	
	public Address getAddress() {
		if (isSaved()) {
			// if there is an old address, this is a change dialog and the new address receives
			// the id of the old address. Addresses are immutable and the ID cann only be set by a special 
			// constructor. Usually the unique ID is calculated by the storage.
			if (oldAddress != null)
				return new Address(oldAddress.id, newAddress);
			else
				return newAddress;
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		AddressInputDialog dialog = new AddressInputDialog();
		System.out.println("status: " + (dialog.isSaved() ? "saved" : "not saved"));		
		System.out.println("address: " + dialog.getAddress());		
	}

}
