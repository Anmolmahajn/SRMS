import java.util.HashMap;
import java.util.Scanner;

public class LoginSystem {
    private static final HashMap<String, User> users = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);

    static {
        users.put("admin", new User("admin", "admin123", "admin"));
        users.put("student", new User("student", "student123", "student"));
    }

    public static String loginMenu() {
        while (true) {
            System.out.println("\n---- Welcome ----");
            System.out.println("1. Login");
            System.out.println("2. Register New User");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": return login();
                case "2": register();
                    break;
                case "0": System.out.println("Goodbye!"); return null;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static String login() {
        System.out.println("----- Login -----");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            System.out.println("✅ Login successful as " + username + " (" + users.get(username).getRole() + ")");
            return users.get(username).getRole(); // return role
        } else {
            System.out.println("❌ Invalid credentials.");
            return null;
        }
    }

    public static void viewAllUsers() {
        System.out.println("\n--- Registered Users ---");
        for (String username : users.keySet()) {
            User user = users.get(username);
            System.out.println("Username: " + username + " | Role: " + user.getRole());
        }
    }


    public static void register() {
        System.out.println("----- Register New User -----");
        System.out.print("Choose a username: ");
        String username = sc.nextLine();
        if (users.containsKey(username)) {
            System.out.println(" Username already exists. Try another.");
            return;
        }

        System.out.print("Choose a password: ");
        String password = sc.nextLine();

        System.out.print("Enter role (admin/student): ");
        String role = sc.nextLine().toLowerCase();

        if (!role.equals("admin") && !role.equals("student")) {
            System.out.println(" Invalid role. Choose 'admin' or 'student'.");
            return;
        }

        users.put(username, new User(username, password, role));
        System.out.println(" User registered successfully!");
    }
}
