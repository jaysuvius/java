package addressBook;
/**
 * @author Jeremy May
 * CS189 Data Structures Project
 * 9/12/2016
 * Bucket Hash Class
 */
public class ContactBucketHashTable {
	/** JM: 
	 * Array of contact objects
	 */
	private ContactList[] _contacts;
	
	/** JM:
	 * @return Array of contact objects
	 */
	public ContactList[] getContactBucketHashTable(){
		return _contacts;
	}
	
	/** JM:
	 * @param contacts
	 */
	public void setContactBucketHashTable(ContactList[] contacts){
		_contacts = contacts;
	}
	
	/** JM:
	 * @return Length of contact hash table
	 */
	public int getContactHashTableSize(){
		return _contacts.length;
	}
	
	/** JM:
	 * Default Constructor
	 */
	public ContactBucketHashTable(){}
	
	/** JM:
	 * Constructor
	 * @param tableSize
	 */
	public ContactBucketHashTable(int tableSize){
		_contacts = new ContactList[tableSize];
	}
	
	/** JM:
	 * Adds contact node to bucket hash table
	 * @param contact
	 */
	public void addContact(ContactNode contact){
		//JM: calculates hash value and inserts contact object into hash table at hash index
		int hashKey = Contact.getHash(getContactHashTableSize(), contact.getFirstName(), contact.getLastName());
		//JM: if hash location is empty, add a new contact linked list
		if (_contacts[hashKey] == null)
			_contacts[hashKey] = new ContactList();
		//JM: add contact object to linked list
		_contacts[hashKey].insert(contact, hashKey);
	}
	
	/** JM:
	 * Searches hash table for contact
	 * @param firstName
	 * @param lastName
	 * @return Contact details or Not Found
	 */
	public String findContact(String firstName, String lastName){
		//JM: calculate hash key and find contact node in associated linked list
		int hashKey = Contact.getHash(getContactHashTableSize(), firstName, lastName);
		ContactNode cn =_contacts[hashKey].find(hashKey, firstName, lastName); 
		if (cn != null)
			return "Found " + cn.toString();
		else
			return firstName + " "+ lastName + " Not Found";
	}
	
	/** JM:
	 * Removes contact from hash table
	 * @param firstName
	 * @param lastName
	 * @return Contact details or not found
	 */
	public String removeContact(String firstName, String lastName){
		//JM: Calculate hash key, Locate contact object in linked list and remove
		int hashKey = Contact.getHash(getContactHashTableSize(), firstName, lastName);
		ContactNode cn = _contacts[hashKey].remove(hashKey, firstName, lastName);
		if (cn != null)
			return "Removed: " + cn.toString();
		else
			return firstName + " "+ lastName + " Not Found";
	}
	
}

/** JM:
 * @author Jeremy May
 * CS189 Data Structures Project
 * 9/12/2016
 * Contact Node extending Contact Class
 */
class ContactNode extends Contact{
	
	/** JM:
	 * Node Key
	 */
	public int key;
	
	/** JM:
	 * Next contact node in linked list
	 */
	public ContactNode next;
	
	/** JM:
	 * Constructor for Contact Node initializing Contact superclass
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param phoneNumber
	 */
	public ContactNode(String firstName, String lastName, String emailAddress, String phoneNumber){
		super(firstName, lastName, emailAddress, phoneNumber);
	}
	
	/* (non-Javadoc)
	 * @see addressBook.Contact#toString()
	 */
	public String toString(){
		return super.toString();
	}

}
/**
 * @author Jeremy May
 * CS189 Data Structures Project
 * 9/12/2016
 * Linked List of Contact nodes
 */
class ContactList {
	
	/** JM:
	 * First node in Contact Linked List
	 */
	public ContactNode firstContact = null;
	/** JM:
	 * Count of nodes in linked list
	 */
	public int ContactCount = 0;
	
	/** JM:
	 * Default Constructor 
	 */
	public ContactList(){}
	
	/** JM:
	 * Inserts node into linked list
	 * @param newContact
	 * @param hashKey
	 */
	public void insert(ContactNode newContact, int hashKey){
		ContactNode previous = null;
		ContactNode current = firstContact;
		
		newContact.key = hashKey;
		
		//JM: Insert contact node into linked list
		while(current != null && newContact.key > current.key){
			
			previous = current;
			current = current.next;
		}
		
		if (previous == null)
			firstContact = newContact;
		else
			previous.next = newContact;
		
		newContact.next = current;
		ContactCount ++;
	}
	
	/** JM:
	 * Displays nodes in linked list
	 */
	public void showContactList(){
		ContactNode current = firstContact;
		
		while (current != null){
			System.out.println(current.toString());
			current = current.next;
		}
	}
	
	/** JM:
	 * Locates contact node by first and last name of contact
	 * @param hashKey
	 * @param firstName
	 * @param lastName
	 * @return Contact Node
	 */
	public ContactNode find(int hashKey, String firstName, String lastName){
		
		ContactNode current = firstContact;
		//JM: find contact node in linked list
		while (current != null && current.key <= hashKey){
			
			if (current.key == hashKey  && current.getFirstName() == firstName && current.getLastName() == lastName)
				return current;
			
			current = current.next;
		}
		return null;
	}
	
	/** JM:
	 * Removes contact node by first and last name
	 * @param hashKey
	 * @param firstName
	 * @param lastName
	 * @return Node that was removed
	 */
	public ContactNode remove(int hashKey, String firstName, String lastName){
		
		ContactNode current = firstContact;
		ContactNode previous = firstContact;

		//JM: remove contact node from linked list with matching first and last names and return contact object
		while (current.getFirstName() != firstName && current.getLastName() != lastName){
			
			if(current.next == null)
				return null;
			else {
				previous = current;
				current = current.next;
			}
			
		}
		if (current == firstContact){
			firstContact = firstContact.next;
		}
		else{
			previous.next = current.next;
		}
		return current;
	}
}
