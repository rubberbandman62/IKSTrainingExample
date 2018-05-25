package iks.java.ausbildung.adressverwaltung.storage;

import java.util.Iterator;

import iks.java.ausbildung.adressverwaltung.adresse.Address;

public interface StorageInterface {

	Iterator<Address> iterator();

	Address readAddress(int id);

	Address updateAddress(Integer id, Address newAddress);

	Address deleteAddress(Integer id);

	Address insertAddress(Address address);

	boolean isEmpty();

	boolean isFull();

	boolean isValidID(int id);

}