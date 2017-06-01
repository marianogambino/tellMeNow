package ag.it.solution.notifystudent.restConnection;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ag.it.solution.notifystudent.ResponseService.ContactResponse;
import ag.it.solution.notifystudent.ResponseService.LoginResponse;
import ag.it.solution.notifystudent.ResponseService.Message;
import ag.it.solution.notifystudent.ResponseService.MessageResponse;
import ag.it.solution.notifystudent.ResponseService.User;

/**
 * Created by mariano on 01/11/2016.
 */
public class RestService {

    private RestConnection rest;

    private RestService( ){
        this.rest = new RestConnection();
    }

    public static RestService getInstance(){
        return new RestService();
    }

    /**
     *
     * @param usuario
     * @param password TODO: a futuro hay que encriptarla y enviarla al servicio rest
     * @return
     * @throws JsonProcessingException
     */
    public LoginResponse login(String usuario, String password, String mail, String token){
        LoginResponse response = null;
        String login = null;
        try {

            LoginResponse user = new LoginResponse();
            user.getUser().setUsername(usuario);
            user.getUser().setPassword(password);
            user.getUser().setEmail(mail);
            user.getUser().setToken(token);

            Map<String, String> vars = new HashMap<>();
            vars.put("username", usuario);
            vars.put("password", password);
            vars.put("token", token);
            vars.put("email", mail);

            response = rest.getRestTemplate().postForObject(UrlServices.LOGIN, user.getUser(), LoginResponse.class, vars  );

        }catch(Exception e){
            Log.v("LoginResponse: ", e.getMessage() );
        }
        return response;
    }

    public ContactResponse getContacts(String usuario){
        ContactResponse response = null;
        String login = null;
        try {
            ContactResponse c = new ContactResponse();

            Map<String, String> vars = new HashMap<>();
            vars.put("username", usuario);

            User u = new User();
            u.setUsername(usuario);

            response = rest.getRestTemplate().postForObject( UrlServices.CONCTACS, u, ContactResponse.class, vars  );

        }catch(Exception e){
            Log.v("ContactResponse: ", e.getMessage() );
        }
        return response;
    }

    public MessageResponse sendMessage(String usuario, String token, String message){
        MessageResponse response = null;
        String login = null;
        try {
            MessageResponse c = new MessageResponse();

            Map<String, String> vars = new HashMap<>();
            vars.put("username", usuario);
            vars.put("token", token);
            vars.put("message", message);

            Message m = new Message();
            m.setUsername(usuario);
            m.setToken(token);
            m.setMessage(message);

            response = rest.getRestTemplate().postForObject( UrlServices.SEND_MESSAGE, m, MessageResponse.class, vars  );

        }catch(Exception e){
            Log.v("ContactResponse: ", e.getMessage() );
        }
        return response;
    }
}
