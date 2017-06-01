package ag.it.solution.notifystudent;

import android.app.Activity;
import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ag.it.solution.notifystudent.db.MessageDB;

/**
 * Created by mariano on 27/10/2016.
 */
public class MessageDBTest  {
    private Context ctx;
    private MessageDB db;
    @Before
    public void setUp() throws Exception {
        ctx= new MockContext();
        db = MessageDB.getInstances(ctx);
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
