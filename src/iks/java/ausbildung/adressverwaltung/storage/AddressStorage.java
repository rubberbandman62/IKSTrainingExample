package iks.java.ausbildung.adressverwaltung.storage;

import java.util.Iterator;

import iks.java.ausbildung.adressverwaltung.adresse.Address.Attribute;

public class AddressStorage implements Iterable<String[]> {

	private final int MAX_LENGTH = 100;
	public final int MAX_COLUMNS = Attribute.values().length;

	private String[][] addressArray = new String[MAX_LENGTH][MAX_COLUMNS];

	private static AddressStorage instance;

	public static AddressStorage getInstance() {
		if (instance == null) {
			instance = new AddressStorage();
		}
		return instance;
	}

	private AddressStorage() {
	}

	@Override
	public Iterator<String[]> iterator() {
		return new AddressStorageIterator();
	}

	private class AddressStorageIterator implements Iterator<String[]> {

		private int index = indexOfFirstAddress();

		@Override
		public boolean hasNext() {
			return index < MAX_LENGTH;
		}

		@Override
		public String[] next() {
			if (index >= MAX_LENGTH)
				return null;

			String[] address = addressArray[index];
			index = indexOfNextAddress(index + 1);

			return address;
		}

		private int indexOfNextAddress(int index) {
			while (index < MAX_LENGTH && addressArray[index][0] == null)
				index++;

			return index;
		}

		private int indexOfFirstAddress() {
			return indexOfNextAddress(0);
		}

	}

	public String[] readAddress(int id) {
		if (isValidID(id))
			return addressArray[id - 1];
		else
			return new String[MAX_COLUMNS];
	}

	public boolean isEmpty() {
		return !new AddressStorageIterator().hasNext();
	}

	public boolean isValidID(int id) {
		return id >= 1 && id <= MAX_LENGTH && addressArray[id - 1][0] != null;
	}

	public void updateAddress(String id, String[] newAddress) {
		int index = Integer.parseInt(id) - 1;
		newAddress[0] = id;
		addressArray[index] = newAddress;
	}

	public void deleteAddress(String id, String[] address) {
		int index = Integer.parseInt(id) - 1;
		addressArray[index] = new String[MAX_COLUMNS];
	}

	public boolean isFull() {
		return firstFreeIndex() == -1 ? true : false;
	}

	private int firstFreeIndex() {
		int index = 0;
		while (index < MAX_LENGTH) {
			if (addressArray[index][0] == null)
				return index;
			index++;
		}
		return -1;
	}

	public String[] insertAddress(String[] address) {
		int index = firstFreeIndex();
		if (index == -1) {
			return new String[MAX_COLUMNS];
		}
		address[0] = "" + (index + 1);
		addressArray[index] = address;
		return address;
	}

}
