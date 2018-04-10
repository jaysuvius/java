package addressBook;

/**
 * @author Jeremy May
 * CS189 Data Structures Project
 * 9/12/2016
 * Test Class
 */
public class ContactTest {

	public static void main(String[] args) {
		System.out.println("************************* Hash Table Output ***************************");
		testHashTable();
		System.out.println("************************* Bucket Hash Table Output ***************************");
		testBucketHashTable();
		System.out.println("************************* BST Output ***************************");
		testBst();
	}
	
	public static void testBst(){
		
		ContactBst bst = new ContactBst();
		
		bst.addNode(new Contact("Bob", "Smith", "bsmith@somewhere.com", "555-235-1111"));
		bst.addNode(new Contact("Jane", "Williams", "jw@something.com", "555-235-1112"));
		bst.addNode(new Contact("Mohammed", "al-Salam", "mas@someplace.com", "555-235-1113"));
		bst.addNode(new Contact("Pat", "Jones", "pjones@homesweethome.com", "555-235-1114"));
		bst.addNode(new Contact("Billy", "Kidd", "billy_the_kid@nowhere.com", "555-235-1115"));
		bst.addNode(new Contact("H.", "Houdini", "houdini@noplace.com", "555-235-1116"));
		bst.addNode(new Contact("Jack", "Jones", "jjones@hill.com", "555-235-1117"));
		bst.addNode(new Contact("Jill", "Jones", "jillj@hill.com", "555-235-1118"));
		bst.addNode(new Contact("John", "Doe", "jdoe@somedomain.com", "555-235-1119"));
		bst.addNode(new Contact("Jane", "Doe", "jdoe@somedomain.com", "555-235-1120"));
				
		System.out.println(bst.find("Pat", "Jones").toString());
		System.out.println(bst.find("Billy", "Kidd").toString());
		System.out.println(bst.remove("John", "Doe"));
		

		bst.addNode(new Contact("Test", "Case", "Test_Case@testcase.com", "555-235-1121"));
		bst.addNode(new Contact("Nadezhda", "Kanachekhovskaya", "dr.nadezhda.kanacheckovskaya@somehospital.moscow.ci.ru", "555-235-1122"));
		bst.addNode(new Contact("Jo", "Wu", "wu@h.com", "555-235-1123"));
		bst.addNode(new Contact("Millard", "Fillmore", "millard@theactualwhitehouse.us", "555-235-1124"));
		bst.addNode(new Contact("Bob", "vanDyke", "vandyke@nodomain.com", "555-235-1125"));
		bst.addNode(new Contact("Upside", "Down", "upsidedown@rightsideup.com", "555-235-1126"));

		System.out.println(bst.findNode("Jack", "Jones").toString());
		System.out.println(bst.findNode("Nadezhda", "Kanachekhovskaya").toString());
		
		System.out.println(bst.remove("Jill", "Jones"));
		System.out.println(bst.remove("John", "Doe"));
		
		System.out.println(bst.find("Jill", "Jones").toString());
		System.out.println(bst.find("John", "Doe").toString());

		
		
		
		System.out.println("--------------------In Order------------------------");
		bst.traverseInOrder(bst.getRoot());
		System.out.println("--------------------Pre Order------------------------");
		bst.traversePreOrder(bst.getRoot());
		System.out.println("--------------------Post Order------------------------");
		bst.traversePreOrder(bst.getRoot());

		
	}
		
	public static void testBucketHashTable(){
		
		ContactBucketHashTable cth = new ContactBucketHashTable(13);
		cth.addContact(new ContactNode("Bob", "Smith", "bsmith@somewhere.com", "555-235-1111"));
		cth.addContact(new ContactNode("Jane", "Williams", "jw@something.com", "555-235-1112"));
		cth.addContact(new ContactNode("Mohammed", "al-Salam", "mas@someplace.com", "555-235-1113"));
		cth.addContact(new ContactNode("Pat", "Jones", "pjones@homesweethome.com", "555-235-1114"));
		cth.addContact(new ContactNode("Billy", "Kidd", "billy_the_kid@nowhere.com", "555-235-1115"));
		cth.addContact(new ContactNode("H.", "Houdini", "houdini@noplace.com", "555-235-1116"));
		cth.addContact(new ContactNode("Jack", "Jones", "jjones@hill.com", "555-235-1117"));
		cth.addContact(new ContactNode("Jill", "Jones", "jillj@hill.com", "555-235-1118"));
		cth.addContact(new ContactNode("John", "Doe", "jdoe@somedomain.com", "555-235-1119"));
		cth.addContact(new ContactNode("Jane", "Doe", "jdoe@somedomain.com", "555-235-1120"));

		System.out.println(cth.findContact("Pat", "Jones").toString());
		System.out.println(cth.findContact("Billy", "Kidd").toString());
		System.out.println(cth.removeContact("John", "Doe"));		
		
		cth.addContact(new ContactNode("Test", "Case", "Test_Case@testcase.com", "555-235-1121"));
		cth.addContact(new ContactNode("Nadezhda", "Kanachekhovskaya", "dr.nadezhda.kanacheckovskaya@somehospital.moscow.ci.ru", "555-235-1122"));
		cth.addContact(new ContactNode("Jo", "Wu", "wu@h.com", "555-235-1123"));
		cth.addContact(new ContactNode("Millard", "Fillmore", "millard@theactualwhitehouse.us", "555-235-1124"));
		cth.addContact(new ContactNode("Bob", "vanDyke", "vandyke@nodomain.com", "555-235-1125"));
		cth.addContact(new ContactNode("Upside", "Down", "upsidedown@rightsideup.com", "555-235-1126"));
			
		System.out.println(cth.findContact("Jack", "Jones").toString());
		System.out.println(cth.findContact("Nadezhda", "Kanachekhovskaya").toString());
		System.out.println(cth.removeContact("Jill", "Jones"));
		System.out.println(cth.removeContact("John", "Doe"));
		System.out.println(cth.findContact("Jill", "Jones").toString());
		System.out.println(cth.findContact("John", "Doe").toString());
	}
	
	public static void testHashTable(){
		
		ContactHashTable cth = new ContactHashTable(32);
		cth.addContact(new Contact("Bob", "Smith", "bsmith@somewhere.com", "555-235-1111"));
		cth.addContact(new Contact("Jane", "Williams", "jw@something.com", "555-235-1112"));
		cth.addContact(new Contact("Mohammed", "al-Salam", "mas@someplace.com", "555-235-1113"));
		cth.addContact(new Contact("Pat", "Jones", "pjones@homesweethome.com", "555-235-1114"));
		cth.addContact(new Contact("Billy", "Kidd", "billy_the_kid@nowhere.com", "555-235-1115"));
		cth.addContact(new Contact("H.", "Houdini", "houdini@noplace.com", "555-235-1116"));
		cth.addContact(new Contact("Jack", "Jones", "jjones@hill.com", "555-235-1117"));
		cth.addContact(new Contact("Jill", "Jones", "jillj@hill.com", "555-235-1118"));
		cth.addContact(new Contact("John", "Doe", "jdoe@somedomain.com", "555-235-1119"));
		cth.addContact(new Contact("Jane", "Doe", "jdoe@somedomain.com", "555-235-1120"));

		System.out.println(cth.find("Pat", "Jones").toString());
		System.out.println(cth.find("Billy", "Kidd").toString());
		System.out.println(cth.remove("John", "Doe"));
		
		cth.addContact(new Contact("Test", "Case", "Test_Case@testcase.com", "555-235-1121"));
		cth.addContact(new Contact("Nadezhda", "Kanachekhovskaya", "dr.nadezhda.kanacheckovskaya@somehospital.moscow.ci.ru", "555-235-1122"));
		cth.addContact(new Contact("Jo", "Wu", "wu@h.com", "555-235-1123"));
		cth.addContact(new Contact("Millard", "Fillmore", "millard@theactualwhitehouse.us", "555-235-1124"));
		cth.addContact(new Contact("Bob", "vanDyke", "vandyke@nodomain.com", "555-235-1125"));
		cth.addContact(new Contact("Upside", "Down", "upsidedown@rightsideup.com", "555-235-1126"));
			
		System.out.println(cth.find("Jack", "Jones").toString());
		System.out.println(cth.find("Nadezhda", "Kanachekhovskaya").toString());
		System.out.println(cth.remove("Jill", "Jones"));
		System.out.println(cth.remove("John", "Doe"));
		System.out.println(cth.find("Jill", "Jones").toString());
		System.out.println(cth.find("John", "Doe").toString());
	}
}
