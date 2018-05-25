package iks.java.ausbildung.adressverwaltung.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import iks.java.ausbildung.adressverwaltung.adresse.Address;
import iks.java.ausbildung.adressverwaltung.storage.AddressStorage;

public class AddressverwaltungGUI extends JFrame implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = -9212728436039725127L;

	private JButton create = new JButton("Adresse anlegen");

	private Vector<Address> addresses = new Vector<>();
	private JList<Address> addressList = new JList<>();

	public void setupGUI() {
		this.setSize(400, 400);
		setLocation(400, 400);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setTitle("Adressverwaltung");
		this.setJMenuBar(createMenuBar());

		addressList.addListSelectionListener(this);

		Container window = this.getContentPane();
		window.setLayout(new GridLayout(1, 1));
		ScrollPane scp = new ScrollPane();
		scp.add(addressList);
		window.add(scp);

		getRootPane().setDefaultButton(create);

		this.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(create);
		create.addActionListener(this);

		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		AddressInputDialog inputDialog = new AddressInputDialog();
		if (inputDialog.isSaved()) {
			Address address = inputDialog.getAddress();
			this.addresses.add(AddressStorage.getInstance().insertAddress(address));
			this.addressList.setListData(this.addresses);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getValueIsAdjusting())
			return;

		int indexToWorkOn = this.addressList.getSelectedIndex();
		if (indexToWorkOn < 0)
			return;

		Address oldAddress = this.addresses.get(indexToWorkOn);
		AddressViewDialog viewDialog = new AddressViewDialog(oldAddress);
		Address newAddress = viewDialog.getAddress();

		AddressStorage storage = AddressStorage.getInstance();

		switch (viewDialog.getStatus()) {
		case CHANGED:
			newAddress = storage.updateAddress(oldAddress.id, newAddress);
			this.addresses.set(indexToWorkOn, newAddress);
			break;
		case COPIED:
			newAddress = storage.insertAddress(newAddress);
			this.addresses.add(storage.insertAddress(newAddress));
			break;
		case DELETED:
			storage.deleteAddress(newAddress.id);
			this.addresses.remove(indexToWorkOn);
			break;
		default:
			return;
		}

		this.addressList.setListData(this.addresses);
	}
}
