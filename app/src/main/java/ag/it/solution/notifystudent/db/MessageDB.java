package ag.it.solution.notifystudent.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ag.it.solution.notifystudent.itemList.MessageItem;


/**
 * Created by mariano on 07/10/2016.
 */
public class MessageDB {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private static final String sesionActive ="A";
    private static final String sesionInactive ="I";

    private static final String T_LOGIN = "LOGIN";
    private static final String C_ID = "id";
    private static final String C_USERNAME = "username";
    private static final String C_STATUS = "status";
    private String[] allColumnsLogin = {C_ID, C_USERNAME, C_STATUS};

    private static final String T_MESSAGE = "MESSAGE";
    private static final String C_ID_MESSAGE = "id";
    private static final String C_CONTACT_MESSAGE = "contact";
    private static final String C_TYPE = "type"; //Enviado o recibido
    private static final String C_STATUS_MESSAGE = "status"; // pendienteLectura o pendienteEnvio
    private static final String C_MESSAGE = "message";
    private static final String C_DATE_MESSAGE = "date_messadge"; //fecha - hora de registro o envio del mensaje
    private String[] allColumnsMessage = {C_ID_MESSAGE, C_CONTACT_MESSAGE, C_MESSAGE, C_TYPE, C_STATUS_MESSAGE, C_DATE_MESSAGE};

    private static final String T_CONTACT = "CONTACT";
    private static final String C_ID_CONTACT = "id";
    private static final String C_USERNAME_CONTACT = "username";
    private static final String C_NICKNAME = "nickname";
    private static final String C_TOKEN = "token"; //token
    private String[] allColumnsContact = {C_ID_CONTACT, C_USERNAME_CONTACT, C_NICKNAME, C_TOKEN};


    private static MessageDB messageDB = new MessageDB();

    public static MessageDB getInstances(Context ctx){
        messageDB.setContext(ctx);
        return messageDB;
    }

    private MessageDB(){}

    private void setContext(Context ctx){ this.context = ctx;}

    public void open(){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public String isSigIn(){

        String select = "SELECT *  from LOGIN where status = 'A'";
        Cursor c= db.rawQuery(select, null);
        System.out.println( "Cursor de login = " + c.getCount());


        if (c.moveToFirst()) {

            int colIndex = c.getColumnIndex(C_USERNAME);
            System.out.println( "IS SIGNG usuario = " + c.getString(colIndex));
            return c.getString(colIndex);

        }
        return null;

    }

    public void registerUserSignIn(String username){
        ContentValues valores = new ContentValues();
        valores.put(C_USERNAME, username);
        valores.put(C_STATUS, sesionActive);
        System.out.println( "inserto la sesion : " + sesionActive + " de: " + username);
        db.insert(T_LOGIN, null, valores);

    }

    public void unregisterUser(String username) {
        ContentValues valores = new ContentValues();
        valores.put(C_USERNAME, username);
        db.delete(T_LOGIN, C_USERNAME, new String[]{username});
        System.out.println( "unregisterUser = " + username);
    }

    public String getUser(String username){
        ContentValues valores = new ContentValues();

        Cursor cLogin = db.query(T_LOGIN, allColumnsLogin,
                C_USERNAME + "=?", new String[]{username}, null, null, null);

        System.out.println( "Cursor de login en saveLogin = " + cLogin.getCount());

        String status = null;
        if(cLogin.moveToFirst()){
            valores.put(C_STATUS, sesionActive );
            int colIndex = cLogin.getColumnIndex(C_STATUS);
            System.out.println( "se obtuvo el estado del usuario : " + username + " con el estado: " + sesionActive );
            status =  cLogin.getString(colIndex);
        }
        cLogin.close();
        return status;

    }


    private String getDateTime(Date dateTimeMessage) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(dateTimeMessage);
    }

    public void saveMessage(String contact, String status, String message, String type, Date dateTimeMessage){
        ContentValues valores = new ContentValues();

        valores.put(C_CONTACT_MESSAGE, contact);
        valores.put(C_STATUS_MESSAGE, status);
        valores.put(C_MESSAGE, message);
        valores.put(C_TYPE, type);
        valores.put(C_DATE_MESSAGE, getDateTime(dateTimeMessage));
        db.insert(T_MESSAGE, null, valores);
        System.out.println( "inserto el mensaje : " + message + "  - del usuario: " + contact);
    }

    /**
     *
     * @param contact
     * @return
     */
    public List<MessageItem> readMessage(String contact, String type){
        Cursor cursor;
        List<MessageItem> messages = new ArrayList<MessageItem>();

        if(type == null) {
            cursor = db.query(T_MESSAGE, allColumnsMessage,
                    C_CONTACT_MESSAGE + "=? ", new String[]{contact}, null, null, null);
        }else{
            cursor = db.query(T_MESSAGE, allColumnsMessage,
                    C_CONTACT_MESSAGE + "=? " + C_TYPE + "=? ", new String[]{contact, type}, null, null, null);
        }


        if (cursor.moveToFirst())
        {
            do{
               // this.updateNotification(cursor.getInt(cursor.getColumnIndex(C_NOTI_ID)), "U", idUsuario);
                MessageItem message = cursorToMessage(cursor);
                messages.add(message);
                System.out.println( "se lee el mensaje : " + message + " - del usuario: " + contact);

            }while (cursor.moveToNext());

        }

        cursor.close();
        return messages;
    }

    public void updateMessage(int idMessage, String status) {

        ContentValues valores = new ContentValues();
        System.out.println( "updateo la notificacion = " + idMessage);
        valores.put(C_STATUS_MESSAGE, status);
        db.update(T_MESSAGE, valores, C_ID_MESSAGE + "=" + idMessage , null);
    }


    /**
     *
     * @param cursor
     * @return
     */
    private MessageItem cursorToMessage(Cursor cursor) {
        MessageItem message = new MessageItem();
        message.setId(cursor.getInt(0));
        message.setUsernameTo(cursor.getString(1));
        message.setMessage(cursor.getString(2));
        message.setAction(cursor.getString(3));
        message.setStatus(cursor.getString(4));
        message.setCurrentDate(cursor.getString(5));
        return message;
    }

    public void updateMessage(){

    }


    public void registerContact(){

    }

    public void getContacts(){

    }

    public int getIdUsuario() {
        Cursor c= db.rawQuery("SELECT "+ C_ID +" from login where status = 'A'", null);
        System.out.println( "Cursor de id usuario = " + c.getCount());
        if (c.moveToFirst()) {
            int colIndex = c.getColumnIndex(C_ID);
            System.out.println( "Cursor de id usuario = " + c.getCount() + " id usuario: " + c.getString(colIndex) );
            return c.getInt(colIndex);
        }
        return 0;
    }

    public void logout(){
        int idUsuario = this.getIdUsuario();
        ContentValues valores = new ContentValues();
        System.out.println( "sesion inactiva para el usuario = " + idUsuario);
        valores.put(C_STATUS, sesionInactive);
        db.update(T_LOGIN, valores, C_ID + "=" + idUsuario, null);
    }

    public void close() {
        dbHelper.close();
    }


    /**
     * Created by mariano on 07/10/2016.
     */
    private class DBHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "tellMeNowDB.db";
        private static final int DB_VERSION = 1;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //Login
            db.execSQL("CREATE TABLE " + T_LOGIN +
                    " ( " + C_ID +   " integer primary key autoincrement, "
                    + C_USERNAME + " TEXT, " + C_STATUS + " TEXT )");


            //MESSAGE
            db.execSQL("CREATE TABLE " + T_MESSAGE +
                    " ( " + C_ID_MESSAGE +   " integer primary key autoincrement, "
                    + C_CONTACT_MESSAGE + " TEXT, " +  C_MESSAGE + "TEXT, " + C_TYPE + " TEXT, "
                    + C_STATUS_MESSAGE + " TEXT, "+ C_DATE_MESSAGE + " DATETIME)");


            //CONTACT
            db.execSQL("CREATE TABLE " + T_CONTACT +
                    " ( " + C_ID_CONTACT +   " integer primary key autoincrement, "
                    + C_USERNAME_CONTACT + " TEXT, " + C_NICKNAME + " TEXT, "
                    + C_TOKEN + " TEXT )");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DBHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS " + T_LOGIN);
            db.execSQL("DROP TABLE IF EXISTS " + T_MESSAGE);
            db.execSQL("DROP TABLE IF EXISTS " + T_CONTACT);

            onCreate(db);
        }
    }


}





