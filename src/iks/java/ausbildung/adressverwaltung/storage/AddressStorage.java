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

	private AddressStorage() {
	}

	/* (non-Javadoc)
	 * @see iks.java.ausbildung.adressverwaltung.storage.StorageInterface#iterator()
	 */
	@Override
	public Iterator<Address> iterator() {
		return addressMap.values().stream().iterator();
	}

	/* (non-Javadoc)
	 * @see iks.java.ausbildung.adressverwaltung.storage.StorageInterface#readAddress(int)
	 */
	@Override
	public Address readAddress(int id) {
		if (isValidID(id))
			return addressMap.get(id);
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see iks.java.ausbildung.adressverwaltung.storage.StorageInterface#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return addressMap.isEmpty();
	}

	/* (non-Javadoc)
	 * @see iks.java.ausbildung.adressverwaltung.storage.StorageInterface#isValidID(int)
	 */
	@Override
	public boolean isValidID(int id) {
		return addressMap.get(id) != null;
	}

	/* (non-Javadoc)
	 * @see iks.java.ausbildung.adressverwaltung.storage.StorageInterface#updateAddress(java.lang.Integer, iks.java.ausbildung.adressverwaltung.adresse.Address)
	 */
	@Override
	public void updateAddress(Integer id, Address newAddress) {
		addressMap.put(id, new Address(id, newAddress));
	}

	/* (non-Javadoc)
	 * @see iks.java.ausbildung.adressverwaltung.storage.StorageInterface#deleteAddress(java.lang.Integer)
	 */
	@Override
	public Address deleteAddress(Integer id) {
		return addressMap.remove(id);
	}

	public boolean isFull() {
		return false;
	}

	/* (non-Javadoc)
	 * @see iks.java.ausbildung.adressverwaltung.storage.StorageInterface#insertAddress(iks.java.ausbildung.adressverwaltung.adresse.Address)
	 */
	@Override
	public Address insertAddress(Address address) {
		int nextIndex = addressMap.keySet().stream().max(Comparator.naturalOrder()).orElse(0) + 1;
		addressMap.put(nextIndex, new Address(nextIndex, address));
		return address;
		
	}

}