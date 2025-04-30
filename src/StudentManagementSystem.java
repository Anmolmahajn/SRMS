import java.util.*;

public class StudentManagementSystem {
    private static HashMap<Integer, Student> studentMap = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            String role = LoginSystem.loginMenu();
            if (role == null) break; // user chose exit

            boolean loggedIn = true;

            while (loggedIn) {
                System.out.println("\n--- Student Record Management ---");
                System.out.println("1. View All Students");
                if (role.equals("admin")) {
                    System.out.println("2. Add Student");
                    System.out.println("3. Search Student by ID");
                    System.out.println("4. Update Student");
                    System.out.println("5. Delete Student");
                    System.out.println("6. Sort Students by Marks");
                    System.out.println("7. Sort Students by Name");
                    System.out.println("8. View All Users");
                    System.out.println("9. View Total Student Count");
                    System.out.println("10. View Top Scorer");
                    System.out.println("11. View Average Marks");
                } else {
                    System.out.println("2. Search Student by ID");
                }
                System.out.println("0. Logout");

                System.out.print("Enter your choice: ");
                String input = sc.nextLine();
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (Exception e) {
                    choice = -1;
                }

                switch (choice) {
                    case 1: viewStudents(); break;
                    case 2:
                        if (role.equals("admin")) addStudent();
                        else searchStudent();
                        break;
                    case 3: if (role.equals("admin")) searchStudent(); break;
                    case 4: if (role.equals("admin")) updateStudent(); break;
                    case 5: if (role.equals("admin")) deleteStudent(); break;
                    case 6: if (role.equals("admin")) sortStudentsByMarks(); break;
                    case 7: if (role.equals("admin")) sortStudentsByName(); break;
                    case 8: if (role.equals("admin")) LoginSystem.viewAllUsers(); break;
                    case 9: if (role.equals("admin")) showStudentCount(); break;
                    case 10: if (role.equals("admin")) showTopScorer(); break;
                    case 11: if (role.equals("admin")) showAverageMarks(); break;
                    case 0:
                        System.out.println("🔒 Logged out.\n");
                        loggedIn = false;
                        break;
                    default:
                        System.out.println("❌ Invalid choice!");
                }
            }
        }

        System.out.println("👋 Exiting program.");
    }


    private static void addStudent() {
        try {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(sc.nextLine());

            if (studentMap.containsKey(id)) {
                System.out.println("⚠️ Student ID already exists.");
                return;
            }

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Marks: ");
            double marks = Double.parseDouble(sc.nextLine());

            studentMap.put(id, new Student(id, name, age, marks));
            System.out.println("✅ Student Added!");
        } catch (Exception e) {
            System.out.println("❌ Invalid input. Please try again.");
        }
    }

    private static void viewStudents() {
        if (studentMap.isEmpty()) {
            System.out.println("⚠️ No student records found.");
            return;
        }
        for (Student s : studentMap.values()) {
            System.out.println(s);
        }
    }

    private static void searchStudent() {
        System.out.print("Enter ID to search: ");
        int id = Integer.parseInt(sc.nextLine());
        Student s = studentMap.get(id);
        if (s != null) {
            System.out.println(s);
        } else {
            System.out.println("❌ Student not found.");
        }
    }

    private static void updateStudent() {
        System.out.print("Enter ID to update: ");
        int id = Integer.parseInt(sc.nextLine());
        Student s = studentMap.get(id);
        if (s != null) {
            System.out.print("Enter new Name: ");
            s.setName(sc.nextLine());
            System.out.print("Enter new Age: ");
            s.setAge(Integer.parseInt(sc.nextLine()));
            System.out.print("Enter new Marks: ");
            s.setMarks(Double.parseDouble(sc.nextLine()));
            System.out.println("✅ Student Updated!");
        } else {
            System.out.println("❌ Student not found.");
        }
    }

    private static void deleteStudent() {
        System.out.print("Enter ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        if (studentMap.remove(id) != null) {
            System.out.println("🗑️ Student Deleted.");
        } else {
            System.out.println("❌ Student not found.");
        }
    }

    private static void sortStudentsByMarks() {
        List<Student> list = new ArrayList<>(studentMap.values());
        list.sort(Comparator.comparingDouble(Student::getMarks).reversed());
        System.out.println("--- Sorted by Marks (High to Low) ---");
        list.forEach(System.out::println);
    }

    private static void sortStudentsByName() {
        List<Student> list = new ArrayList<>(studentMap.values());
        list.sort(Comparator.comparing(Student::getName));
        System.out.println("--- Sorted by Name (A-Z) ---");
        list.forEach(System.out::println);
    }

    private static void showStudentCount() {
        System.out.println("📊 Total Students: " + studentMap.size());
    }

    private static void showTopScorer() {
        if (studentMap.isEmpty()) {
            System.out.println("⚠️ No student records.");
            return;
        }
        Student top = Collections.max(studentMap.values(), Comparator.comparingDouble(Student::getMarks));
        System.out.println("🏆 Top Scorer:\n" + top);
    }

    private static void showAverageMarks() {
        if (studentMap.isEmpty()) {
            System.out.println("⚠️ No data to calculate average.");
            return;
        }
        double avg = studentMap.values().stream().mapToDouble(Student::getMarks).average().orElse(0);
        System.out.println("📈 Average Marks: " + avg);
    }
}

