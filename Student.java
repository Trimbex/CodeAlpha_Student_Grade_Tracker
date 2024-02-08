import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Student {
    private String Name;

    public static ArrayList<Student> students = new ArrayList<>();

    private ArrayList<String> Courses;
    private ArrayList<Double> Percentages;

    private ArrayList<String> Grades;
    public int Average;

    enum State
    {
      HOME,ADDINGSTUDENT, REMOVINGSTUDENT, ADDINGCOURSE, REMOVINGCOURSE,EXIT, VIEWDATA
    }

   public Student()
   {
     Name = null;
       Courses = null;
       Percentages = null;
       Grades = null;
     Average = 0;
   }
   public Student(String Name)
   {
       this.Name = Name;
       Courses = new ArrayList<>();
       Percentages = new ArrayList<>();
       Grades = new ArrayList<>();
       Average = 0;
   }
   public
String ToGrade(double percentage)
{
    if (percentage > 97.0) {
        return "A+";
    } else if (percentage >= 93.0) {
        return "A";
    } else if (percentage >= 89.0) {
        return "A-";
    } else if (percentage >= 84.0) {
        return "B+";
    } else if (percentage >= 80.0) {
        return "B";
    } else if (percentage >= 76.0) {
        return "B-";
    } else if (percentage >= 73.0) {
        return "C+";
    } else if (percentage >= 70.0) {
        return "C";
    } else if (percentage >= 67.0) {
        return "C-";
    } else if (percentage >= 64.0) {
        return "D+";
    } else if (percentage >= 60.0) {
        return "D";
    } else {
        return "F";
    }
}


   public void addCourse(Student student,String CourseName,double Percentage)
   {
       if(student != null) {
           student.Courses.add(CourseName);
           student.Percentages.add(Percentage);
           student.Grades.add(ToGrade(Percentage));
           student.DisplayStudentData();

       }
       else {
           System.out.println("Student Not found");
       }
   }

   public void removeCourse(Student student,String CourseName)
   {
       if(Courses.contains(CourseName))
       {
           int index = student.Courses.indexOf(CourseName);
           student.Courses.remove(CourseName);
           student.Percentages.remove(index);
           student.Grades.remove(index);
           student.DisplayStudentData();
           System.out.println("Success");
       }
       else
       {
           System.out.println("Course Note Found");
       }
   }

   double AveragePercentage()
   {
       double average = 0;
       for (Double percentage : Percentages) {
           average += percentage;
       }
       average = average/Percentages.size();
       return average;
   }

   void ScoreRange() {

       double max, min;
       int maxIndex = 0, minIndex = 0;

       if (Percentages.size() == 0) {
           return;
       }

       max = min = Percentages.get(0);


       for (int i = 1; i < Percentages.size(); i++) {
           double percentage = Percentages.get(i);
           if (percentage > max) {
               max = percentage;
               maxIndex = i;
           }
           if (percentage < min) {
               min = percentage;
               minIndex = i;
           }
       }


       System.out.println(Name + "'s highest score is " + max + "% in " + Courses.get(maxIndex) + " with a grade " + ToGrade(max) + ".");
       System.out.println(Name + "'s lowest score is " + min + "% in " + Courses.get(minIndex) + " with a grade " + ToGrade(min) + ".");
   }





   public void DisplayStudentData()
   {
       System.out.println("Student's Name: " + Name);
       System.out.println("Courses Enrolled:");
       for (int i = 0; i < Courses.size(); i++) {
           System.out.println("   Course: " + Courses.get(i));
           System.out.println("   Percentage: " + Percentages.get(i));
           System.out.println("   Grade: " + Grades.get(i));
       }
       System.out.println("   Average Percentage: " + AveragePercentage() + "%");
       System.out.println("   Average Grade: " + ToGrade(AveragePercentage()));
       ScoreRange();



   }

   public void DisplayAllStudentsData()
    {
        for (Student student : students)
        {
        student.DisplayStudentData();
        }
        }


    public void AddStudent(String StudentName)
    {

        Student student = new Student(StudentName);
        students.add(student);
        System.out.println("Success");
        student.DisplayStudentData();
    }

    public void RemoveStudent(Student Student)
    {
       if(Student != null) {
           students.remove(Student);
           System.out.println("Success");
       }
       else
       {
           System.out.println("Student Not Found");
       }

    }

    public Student getStudent(String name)
    {
        for (Student student : students) {
            if (student.Name.equals(name)) {
                return student;
            }
        }
        return null;
    }

    public String instructions()
    {
        return """
                Welcome to Student Tracker!
                To perform actions, please enter the corresponding number:
                1. Add a new student
                2. Remove a student
                3. Add a course for a student
                4. Remove a course for a student
                5. View all students' data
                6. Toggle recurring instructions message.
                7. Exit the program
                Enter your choice:\s""";
    }

    public void Program() {
        boolean instructions = true;
        State state = State.HOME;
        while (state != State.EXIT) {
             state = State.HOME;
            Scanner stateentry = new Scanner(System.in);
            if(instructions) System.out.println(instructions());
            switch (stateentry.nextInt()) {
                case 1 -> state = State.ADDINGSTUDENT;
                case 2 -> state = State.REMOVINGSTUDENT;
                case 3 -> state = State.ADDINGCOURSE;
                case 4 -> state = State.REMOVINGCOURSE;
                case 5 -> state = State.VIEWDATA;
                case 6 -> instructions = !instructions;
                case 7 -> state = State.EXIT;
                default -> System.out.println("Error, wrong number.");
            }
            switch (state) {
                case ADDINGSTUDENT -> {
                    System.out.println("Enter Student's Name");
                    Scanner studentScanner = new Scanner(System.in);
                    AddStudent(studentScanner.nextLine());
                }
                case REMOVINGSTUDENT -> {
                    System.out.println("Enter Student's Name");
                    Scanner removeStudentScanner = new Scanner(System.in);
                    String removeStudentName = removeStudentScanner.nextLine();
                    Student studentToRemove = getStudent(removeStudentName);
                    if (studentToRemove != null) {
                        System.out.println("Removed Student " + removeStudentName + " from the student list");
                        students.remove(studentToRemove);
                    } else {
                        System.out.println("Student not found");
                    }
                }
                case ADDINGCOURSE -> {
                    Scanner details = new Scanner(System.in);
                    System.out.println("Enter Student's Name");
                    String name = details.nextLine();
                    System.out.println("Enter Course's Name");
                    String coursename = details.nextLine();
                    System.out.println("Enter Course's percentage");
                    double coursepercentage = details.nextDouble();
                    addCourse(getStudent(name),coursename,coursepercentage);
                }
                case REMOVINGCOURSE -> {
                    Scanner student = new Scanner(System.in);
                    System.out.println("Enter Student's Name");
                    String name = student.nextLine();
                    System.out.println("Enter Course's Name");
                    String coursename = student.nextLine();
                    removeCourse(getStudent(name),coursename);

                }
                case VIEWDATA -> DisplayAllStudentsData();
                default -> {
                }
               
            }





            }
        }

    }



