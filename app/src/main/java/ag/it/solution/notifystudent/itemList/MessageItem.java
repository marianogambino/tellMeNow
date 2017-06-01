package ag.it.solution.notifystudent.itemList;

import java.util.Date;

/**
 * Created by mariano on 07/09/2016.
 */
public class MessageItem {

    private  Integer id;
    private String message;
    private String status;
    private String action;
    private String username;
    private String usernameTo;
    private String currentDate;

    public MessageItem(){}

    public MessageItem(String message, String user, String action, String usernameTO ){
        this.message = message;
        this.username = user;
        this.action = action;
        this.status = "P";
        this.usernameTo = usernameTO;


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
