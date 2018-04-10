package addressBook;

/**
 * @author Jeremy May
 * CS189 Data Structures Project
 * 9/12/2016
 * Non bucketed hash table implementation
 */
public class ContactHashTable {
	
	/** JM:
	 * Array of Contact Objects
	 */
	private Contact[] _contacts;
	
	/** JM:
	 * @return	Array of contacts
	 */
	public Contact[] getContactHashTable(){
		return _contacts;
	}
	
	/** JM:
	 * @param contacts
	 */
	public void setContactHashTable(Contact[] contacts){
		_contacts = contacts;
	}
	
	/** JM:
	 * Default Constructor
	 */
	public ContactHashTable(){}
	
	/** JM:
	 * @param contactCount	Number of Contacts to Insert
	 */
	public ContactHashTable(int contactCount){
		_contacts = new Contact[getNextPrimeNumber(contactCount)];
	}
	
	/** JM:
	 * Creates Contact Object to pass add to hash table
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param phoneNumber
	 */
	public void addContact(String firstName, String lastName, String emailAddress, String phoneNumber){
		Contact contact = new Contact(firstName, lastName, emailAddress, phoneNumber);
		addContact(contact);
	}
		
	/** JM:
	 * Adds Contact Object to Hash Table
	 * @param contact	Contact Object
	 */
	public void addContact(Contact contact){
		//JM: Gets hash code for first name and last name key and inserts into the next available hash table location
		int hashCode = Contact.getHash(_contacts.length, contact.getFirstName(), contact.getLastName());
		while(_contacts[hashCode] != null)
			hashCode ++;
		
		_contacts[hashCode] = contact;
	}
	
	/** JM:
	 * Searches for contact matching First and Last Name
	 * @param firstName	Contact First Name
	 * @param lastName	Contact last Name
	 * @return Contact Details or Not Found if not found in hash table
	 */
	public String find(String firstName, String lastName){
		//JM: Locate index of key in hash table and return contact object details or not found if appropriate
		if (indexOf(firstName, lastName) > -1)
			return _contacts[indexOf(firstName, lastName)].toString();
		else
			return firstName + " "+ lastName + " Not Found";
	}
	
	
	/** JM:
	 * @param firstName Contact first name
	 * @param lastName	Contact last name
	 * @return Array index of object matching first and last name
	 */
	public int indexOf(String firstName, String lastName){
		//JM: calculates hash code and locates the contact object in the hash table
		int hashCode = Contact.getHash(_contacts.length, firstName, lastName);
		while(_contacts[hashCode] != null && hashCode < _contacts.length){
			if (_contacts[hashCode].getFirstName() == firstName && _contacts[hashCode].getLastName() == lastName)
				return hashCode;
			else			
				hashCode ++;
		}

		return -1;
		
	}
	
	/** JM:
	 * @param contact
	 * @return Array index of matching contact object
	 */
	public int indexOf(Contact contact){
		//JM: rerturns hash table index of contact object
		return indexOf(contact.getFirstName(), contact.getLastName());
	}
	
	public String remove(String firstName, String lastName){
		//JM: removes contact from hash table and returns details, or not found as appropriate
		if(removeContact(firstName, lastName))
			return "Removed " + firstName + " " + lastName;
		else
			return firstName + " " + lastName + " Not Found";
	}
	
	/** JM:
	 * Removes contact matching first and last name
	 * @param firstName Contact first name
	 * @param lastName	Contact last name
	 * @return Boolean representing whether or not contact was removed
	 */
	public boolean removeContact(String firstName, String lastName){
		//JM: gets index of matching first and last names, removes contact, and returns contact details
		int contactIndex = indexOf(firstName, lastName);
		if (contactIndex != -1){
			_contacts[contactIndex] = null;
			return true;
		}
		
		return false;
	}
	
	
	/** JM:
	 * Removes matching contact object
	 * @param contact
	 * @return Boolean whether or not contact was removed
	 */
	public boolean removeContact(Contact contact){
		//JM: removes and returns boolean value indicating success
		if (indexOf(contact) != -1){
			_contacts[indexOf(contact)] = null;
			return true;
		}
		
		return false;
	}
	
	/** JM:
	 * @param number minimum number to start prime search
	 * @return boolean representing whether or not a number is prime
	 */
	private boolean isPrime(int number){
		//JM: tests parameter to find out if it is prime
		if(number%2 ==0)
			return false;
		
		for (int i = 3; i * i <= number; i +=2){
			
			if (number % i == 0)
				return false;
		}
		
		return true;
	}
	
	/** JM:
	 * Gets next prime number greater than minimum value
	 * @param min
	 * @return Next available prime number for hash table sizing
	 */
	private int getNextPrimeNumber(int min){
		//JM: iterates over all numbers until a prime number is located
		for (int i = min; true; i++){
			if (isPrime(i))
				return i;
		}
		
	}
	

}
