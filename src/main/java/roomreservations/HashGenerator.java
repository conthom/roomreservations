package roomreservations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Class to get hashed passwords using BCryptPassawordEncoder for inserting into the database
public class HashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("conthom: " + encoder.encode("passforme"));
        System.out.println("admin: " + encoder.encode("admin123"));
    }
}