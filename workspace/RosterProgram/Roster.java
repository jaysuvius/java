import java.util.ArrayList;

/**
 * This is the main static class for the roster application.
 * 
 * @author Jeremy May 
 * @version 7/31/16
 */
public class Roster
{
    
    private static ArrayList<Student> studentsList = new ArrayList<Student>();
    
    /**
     * Method main
     *
     * @param args A parameter
     */
    public static void main(String[] args)
    {
        String [] students = {"1,John,Smith,John1989@gmail.com,20,88,79,59", 
                     "2,Suzan,Erickson,Erickson_1990@gmailcom,19,91,72,85",
                     "3,Jack,Napoli,The_lawyer99yahoo.com,19,85,84,87",
                     "4,Erin,Black,Erin.black@comcast.net,22,91,98,82",
                     "5,Jeremy,May,jmay22@wgu.edu,44,100,90,78"};
        for (String s : students){
            String[] studentArray = s.split(",");
            add(studentArray[0], studentArray[1], studentArray[2], studentArray[3], Integer.parseInt(studentArray[4]), Integer.parseInt(studentArray[5]),
            Integer.parseInt(studentArray[6]), Integer.parseInt(studentArray[7]));
        }
        
        print_all();
        print_invalid_emails();
        System.out.print("\n");
        for (Student s : studentsList){
            print_average_grade(s.getStudentId());
        }
        System.out.print("\n");
        remove("3");
        System.out.print("\n");
        remove("3");

    }
    
    public static void print(String studentId){
        Student studentToPrint = null;
        for (Student s : studentsList){
            if (Integer.parseInt(s.getStudentId()) == Integer.parseInt(studentId)){
                studentToPrint = s;
            }
        }
        if (studentToPrint == null){
            System.out.print("Student Id: " + studentId + " not found");
        }
        else{
           String gradesOut = studentToPrint.getGrades()[0] + ", " + studentToPrint.getGrades()[1] + ", " + studentToPrint.getGrades()[2];
           System.out.printf("%-10s\t%-10s\t%-10s\t%-30s\t%3s\t%-12s\n", studentToPrint.getStudentId(),studentToPrint.getFirstName(), studentToPrint.getLastName()
           , studentToPrint.getEmailAddress(), studentToPrint.getAge(), gradesOut);
        }
    }
    
    
    
    /**
     * Method add will add a student to the student list object
     *
     * @param studentId Student Id parameter
     * @param firstname First Name parameter
     * @param lastname Last Name parameter
     * @param emailaddress Email Address parameter
     * @param age Age parameter
     * @param grade1 Grade 1 parameter
     * @param grade2 Grade 2 parameter
     * @param grade3 Grade 3 parameter
     */
    public static void add(String studentId, String firstname, String lastname, String emailaddress, int age, int grade1, int grade2, int grade3){
        int[] grades = new int[3];
        grades[0] = grade1;
        grades[1] = grade2;
        grades[2] = grade3;
        
        Student studentData = new Student(studentId, firstname, lastname, emailaddress, age, grades);
        studentsList.add(studentData);
    }
    
    /**
     * Method remove will remove a student from the roster list by their Id
     *
     * @param studentId Student Id for the student to be remove
     */
    public static void remove(String studentId){
        Student studentToRemove = null;
        for (Student s : studentsList){
            if (Integer.parseInt(s.getStudentId()) == Integer.parseInt(studentId)){
                studentToRemove = s;
            }
        }
        if (studentToRemove == null){
            System.out.print("Student Id: " + studentId + " not found");
        }
        else{
            studentsList.remove(studentToRemove);
            System.out.print("Successfully removed student " + studentId);
        }
    }
    
    /**
     * Method print_all
     * This method will print all the students and their grades in tab delimited format
     */
    public static void print_all(){
        System.out.printf("%-10s\t%-10s\t%-10s\t%-30s\t%3s\t%-12s\n", "StudentId","First Name", "Last Name", "Email", "Age", "Grades"); 
        
        for (Student s : studentsList){
          print(s.getStudentId());
        }
        System.out.print("\n");
    }   
    
    /**
     * Method print_average_grade will print the average grade for each student in the roster
     *
     * @param studentId Id of student whos grades to average
     */
    public static void print_average_grade(String studentId){
        double gradeSum = 0;
        double average = 1;
        String studentName = "";
        for (Student s : studentsList){
            if (s.getStudentId() == studentId){
                for (int i : s.getGrades()){
                    gradeSum = gradeSum + i;
                }
                average = gradeSum/s.getGrades().length;
                studentName = s.getFirstName() + " " + s.getLastName();
            }
        }
        
        System.out.printf("Grade Point Average for %-20s: %2.2f\n", studentName, average);
        
    }
    
    /**
     * Method print_invalid_emails will print all of the invalid email addresses in the roster list
     *
     */
    public static void print_invalid_emails(){
        for (Student s : studentsList){
            if (!s.getEmailAddress().contains("@") || !s.getEmailAddress().contains(".") || s.getEmailAddress().contains(" ")){
                System.out.printf("Email address for student %-5s: %-10s %-10s %-20s is invalid\n", s.getStudentId(), s.getFirstName(), s.getLastName(), s.getEmailAddress());
            }
        }
    }

}
