package roomreservations;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import roomreservations.data.UsersRepository;
import roomreservations.model.Users;

@Service
public class AuthService {
    private final UsersRepository usersRepository;
    private final HttpSession session;

    public AuthService(UsersRepository usersRepository, HttpSession session) {
        this.usersRepository = usersRepository;
        this.session = session;
    }

    public boolean authenticate(String username, String password) {
        Users user =  usersRepository.findByUsernameAndPassword(username, password);
        if (user != null){
            session.setAttribute("loggedInAs", user);
            return true;
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