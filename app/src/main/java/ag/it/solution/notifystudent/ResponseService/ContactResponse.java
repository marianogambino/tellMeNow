package ag.it.solution.notifystudent.ResponseService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariano on 03/11/2016.
 */
public class ContactResponse extends Response {

    private List<User> users = new ArrayList<User>();


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
