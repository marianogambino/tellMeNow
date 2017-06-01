package ag.it.solution.notifystudent;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ag.it.solution.notifystudent.db.MessageDB;

/**
 * Created by mariano on 27/10/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DBTest {
    private Context ctx;
    private MessageDB db;
    @Before
    public void setUp() throws Exception {
        ctx = InstrumentationRegistry.getContext();
        MessageDB db = MessageDB.getInstances(ctx);
    }

    @Test
    public void testSaveUser(){

        db.open();
        db.registerUserSignIn("mgambino");
        String status = db.getUser("mgambino");
        db.close();

        Assert.assertEquals(status , "A");
    }

    @Test
    public void testGetLogin(){
        db.open();
        db.unregisterUser("mgambino");
        String status = db.getUser("mgambino");
        db.close();

        Assert.assertEquals(status , "");
    }

    @Test
    public void isSignIn(){

    }

}
