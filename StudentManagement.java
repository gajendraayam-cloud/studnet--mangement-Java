import java.io.*;
import java.util.*;

class Student implements Serializable {
    int id;
    String name;
    String department;
    double cgpa;

    public Student(int id, String name, String department, double cgpa) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.cgpa = cgpa;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Dept: " + department + ", CGPA: " + cgpa;
    }
}

public class StudentManagement {
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        loadData();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Delete Student");
            System.out.println("5. Save & Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> viewStudents();
                case 3 -> searchStudent(sc);
                case 4 -> deleteStudent(sc);
                case 5 -> {
                    saveData();
                    System.out.println("Data saved. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter CGPA: ");
        double cgpa = sc.nextDouble();

        students.add(new Student(id, name, dept, cgpa));
        System.out.println("Student added!");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    static void searchStudent(Scanner sc) {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();
        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Found: " + s);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void deleteStudent(Scanner sc) {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                System.out.println("Student deleted!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    @SuppressWarnings("unchecked")
    static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (ArrayList<Student>) ois.readObject();
            System.out.println("Data loaded from file.");
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }

    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
