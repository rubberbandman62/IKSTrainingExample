package iks.java.ausbildung.adressverwaltung.storage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import iks.java.ausbildung.adressverwaltung.adresse.Address.Attribute;

public class AddressStorage implements Iterable<String[]> {

	public final int MAX_COLUMNS = Attribute.values().length;

	private HashMap<Integer, String[]> addressMap = new HashMap<>();

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
		return addressMap.values().iterator();
	}

	public String[] readAddress(int id) {
		if (isValidID(id))
			return addressMap.get(id);
		else
			return new String[MAX_COLUMNS];
	}

	public boolean isEmpty() {
		return addressMap.isEmpty();
	}

	public boolean isValidID(int id) {
		return addressMap.get(id) != null;
	}

	public void updateAddress(String id, String[] newAddress) {
		addressMap.put(Integer.parseInt(id), newAddress);
	}

	public String[] deleteAddress(String id, String[] address) {
		return addressMap.remove(Integer.parseInt(id));
	}

	public boolean isFull() {
		return false;
	}

	public String[] insertAddress(String[] address) {
		int nextIndex = addressMap.keySet().stream().max(Comparator.naturalOrder()).orElse(0) + 1;
		address[0] = "" + nextIndex;
		addressMap.put(nextIndex, address);
		return address;
		
	}

}
