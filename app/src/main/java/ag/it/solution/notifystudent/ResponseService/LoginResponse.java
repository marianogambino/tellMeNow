package ag.it.solution.notifystudent.ResponseService;

/**
 * Created by mariano on 01/11/2016.
 */
public class LoginResponse extends Response{


    private User user;

    public LoginResponse(){
        this.user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}


