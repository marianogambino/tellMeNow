package ag.it.solution.notifystudent.ResponseService;

/**
 * Created by mariano on 08/11/2016.
 */
public class Message {
    private String username;
    private String token;
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
