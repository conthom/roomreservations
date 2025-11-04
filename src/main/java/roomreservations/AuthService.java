package roomreservations;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import roomreservations.data.UsersRepository;
import roomreservations.model.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class AuthService {
    private final UsersRepository usersRepository;
    private final HttpSession session;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UsersRepository usersRepository, HttpSession session) {
        this.usersRepository = usersRepository;
        this.session = session;
    }

    public boolean authenticate(String username, String password) {
        Optional<Users> userOpt =  usersRepository.findByUsername(username);
        if (userOpt.isPresent()){
            Users user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("loggedInAs", user);
                return true;
            }
        }
        return false;
    }
    public Users getLoggedInUser(){
        return (Users) session.getAttribute("loggedInAs");
    }
    public void logout() {
        session.invalidate();
    }
}