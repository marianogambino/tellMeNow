package ag.it.solution.notifystudent.restConnection;

/**
 * Created by mariano on 01/11/2016.
 */
public class UrlServices {

    public static final String DOMAIN = "http://tellmenow.ddns.net/"; //"http://10.0.3.2/"; // 191.84.74.118 10.0.3.2

    public static final String LOGIN =
            DOMAIN + "rest_api_phalcon_mysql/api/addUser";

    public static final String CONCTACS =
            DOMAIN + "rest_api_phalcon_mysql/api/getUsers";

    public static final String SEND_MESSAGE =
            DOMAIN + "rest_api_phalcon_mysql/api/sendMessage";
}
