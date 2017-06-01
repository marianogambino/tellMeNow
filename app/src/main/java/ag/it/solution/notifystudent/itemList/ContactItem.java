package ag.it.solution.notifystudent.itemList;

/**
 * Created by mariano on 03/08/2016.
 */
public class ContactItem {

    private String username;
    private String token;
    private String avatar;
    private String nickname;

    public ContactItem(){}

    public ContactItem(String username, String token, String image, String nickname){
        this.username = username;
        this.token = token;
        this.avatar = image;
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "ContactItem{" +
                "username='" + username + '\'' +
                "nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
