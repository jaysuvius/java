
/**
 * Write a description of class Student here.
 * 
 * @author Jeremy May 1000413050 
 * @version 7/30/16
 */
public class Student
{
    // instance variables - replace the example below with your own
    private String studentId = "";
    private String firstName = "";
    private String lastName = "";
    private String emailAddress = "";
    private int age = 0;
    private int[] grades;

    /**
     * Constructor for objects of class Student
     */
    public Student(){
        
    }
    
    /**
     * Student Constructor will construct a student object from all the data points
     *
     * @param id A parameter
     * @param fn A parameter
     * @param ln A parameter
     * @param email A parameter
     * @param studentAge A parameter
     * @param studentGrades A parameter
     */
    public Student(String id, String fn, String ln, String email, int studentAge, int[] studentGrades){
        studentId = id;
        firstName = fn;
        lastName = ln;
        emailAddress = email;
        age = studentAge;
        grades = studentGrades;
    }
    
    
    //The following are that accessor and mutator methods used to control data in this object.
    public void setStudentId(String id){
        studentId = id;
    }
    
    public String getStudentId(){
        return studentId;
    }
    
    public void setFirstName(String fn){
        firstName = fn;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setLastName(String ln){
        lastName = ln;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public void setEmailAddress(String email){
        emailAddress = email;
    }
    
    public String getEmailAddress(){
        return emailAddress;
    }
    
    public void setAge(int a){
        age = a;
    }
    
    public int getAge(){
        return age;
    }
    
    public void setGrades(int[] gradesIn){
        grades = gradesIn;
    }
    
    public int[] getGrades(){
        return grades;
    }

}
