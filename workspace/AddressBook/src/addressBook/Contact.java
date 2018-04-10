package addressBook;

/**
 * @author Jeremy May
 * CS189 Data Structures Project
 * 9/12/2016
 *
 */
public class Contact {
	
	/** JM:
	 * Private instance vars
	 */
	private String _firstName = "";
	private String _lastName = "";
	private String _emailAddress = "";
	private String _phoneNumber = "";
	
	/** JM:
	 * @return firstName
	 */
	public String getFirstName(){
		return _firstName;
	}
	
	/** JM:
	 * @param firstName
	 */
	public void setFirstName(String firstName){
		_firstName = firstName;
	}
	
	/** JM:
	 * @return lastName
	 */
	public String getLastName(){
		return _lastName;
	}
	
	/** JM:
	 * @param lastName
	 */
	public void setLastName(String lastName){
		_lastName = lastName;
	}
	
	/** JM:
	 * @return fullName
	 */
	public String getFullName(){
		return _firstName + " " + _lastName;
	}
	
	/** JM:
	 * @return emailAddress
	 */
	public String getEmailAddress(){
		return _emailAddress;
	}
	
	/** JM:
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress){
		_emailAddress = emailAddress;
	}
	
	/** JM:
	 * @return phoneNumber
	 */
	public String getPhoneNumber(){
		return _phoneNumber;
	}
	
	/** JM:
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber){
		_phoneNumber = phoneNumber;
	}
	
	/** JM:
	 * Default Constructor
	 */
	public Contact(){}
	
	/** JM:
	 * Constructor
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param phoneNumber
	 */
	public Contact(String firstName, String lastName, String emailAddress, String phoneNumber){
		_firstName = firstName;
		_lastName = lastName;
		_emailAddress = emailAddress;
		_phoneNumber = phoneNumber;
	}
	
	/** JM:
	 * @param tableSize
	 * @param firstName
	 * @param lastName
	 * @return Double hashed code based on full name string
	 */
	public static int getHash(int tableSize, String firstName, String lastName){
		int hashKey = 0;
		String fullName = firstName.toLowerCase() + " " + lastName.toLowerCase();
		for (int i = 0; i < fullName.length(); i++){
			int charCode = fullName.charAt(i) - 96;
			hashKey = hashKey + (hashKey * 27 + charCode) % tableSize;
		}
		return hashKey % tableSize;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "firstName = " + getFirstName() + " : lastName= " + getLastName() + " : emailAddress= "
				+ getEmailAddress() + " : phoneNumber= " + getPhoneNumber();
	}
}
