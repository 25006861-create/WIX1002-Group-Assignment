import java.io.*;
import java.util.ArrayList;
import java.security.MessageDigest;

public class Login {

    private String filePath;
    // filePath: Stores the location of UserData.txt.

    private ArrayList<User> userList = new ArrayList<>();

    // Call this in the main method:
    public Login(String filePath) {
        this.filePath = filePath;
        loadUsers();
    }

    // For the first time (call once) only:
    private void loadUsers() {
        userList.clear();
        try (BufferedReader input = new BufferedReader(new FileReader(filePath))) {
            String line;
            ArrayList<String> buffer = new ArrayList<>();

            while ((line = input.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines.

                buffer.add(line.trim()); // Add line to temporary list.

                if (buffer.size() == 3) {
                    userList.add(new User(buffer.get(0), buffer.get(1), buffer.get(2)));
                    buffer.clear(); // Empty buffer for the next person.
                }
            }
        } catch (IOException e) {
            System.out.println("User file not found. A new one will be created upon registration.");
        }
    }

    // For GUI:
    public User login(String email, String passwordInput) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                String storedPassword = user.getPassword();

                if (storedPassword.equals(passwordInput)) {
                    return user;
                }

                String hashedInput = hashPassword(passwordInput);
                if (storedPassword.equals(hashedInput)) {
                    return user;
                }
            }
        }
        return null;
    }
}
