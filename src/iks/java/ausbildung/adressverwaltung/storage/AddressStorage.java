package iks.java.ausbildung.adressverwaltung.storage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import iks.java.ausbildung.adressverwaltung.adresse.Address;

public class AddressStorage implements Iterable<Address>, StorageInterface {

	private HashMap<Integer, Address> addressMap = new HashMap<>();

	private static AddressStorage instance;

	public static AddressStorage getInstance() {
		if (instance == null) {
			instance = new AddressStorage();
		}
		return instance;
	}

	private AddressStorage() {}

	@Override
	public Iterator<Address> iterator() {
		return addressMap.values().stream().iterator();
	}

	@Override
	public Address readAddress(int id) {
		if (isValidID(id))
			return addressMap.get(id);
		else
			return null;
	}

	@Override
	public boolean isEmpty() {
		return addressMap.isEmpty();
	}

	@Override
	public boolean isValidID(int id) {
		return addressMap.get(id) != null;
	}

	@Override
	public Address updateAddress(Integer id, Address newAddress) {
		Address address = new Address(id, newAddress);
		addressMap.put(id, address);
		return address;
	}

	@Override
	public Address deleteAddress(Integer id) {
		return addressMap.remove(id);
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public Address insertAddress(Address address) {
		int nextIndex = addressMap.keySet().stream().max(Comparator.naturalOrder()).orElse(0) + 1;
		Address newAddress = new Address(nextIndex, address);
		addressMap.put(nextIndex, newAddress);

		return newAddress;
	}

}