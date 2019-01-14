package com.example.zreactor.cbtry;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class DbCxnTest {

    final String host = "192.168.0.33";
    final String port = "5984";

    DbCxn db = new DbCxn(host, port);
    final static String alldbs = "[\"_replicator\",\"_users\",\"asha-fusion-dev\",\"mytst\",\"newdb\"]";

    @Before public void runBefore() throws IOException{
        System.out.println("runBefore().....");

        String result = db.readDB("_all_dbs");
        System.out.println("Our DB output: " + result);
    }

    @Test
    public void noParamsConstruct() {
        // Test that it constructs properly without any parameters passed
        System.out.println("noParamsConstruct()...");
        DbCxn dbwnoparams = new DbCxn();

        try {
            String response = dbwnoparams.readDB("_all_dbs");
            assertEquals(response, alldbs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void staticScopeTest() {
        int result = DbCxn.timeout;
        System.out.println("Static variable scope test(): " + Integer.toString(result));
        assertEquals(5000, DbCxn.timeout);
    }

    @Test
    public void paramsConstruct() {
        // Test that it constructs properly with parameters passed for host, port
        System.out.println("paramsConstruct()...");
        try {
            System.out.println("connectWorked()..." + host +":"+ port);
            String response = db.connectToCDB(host, port);
//            String correct = "[\"_replicator\",\"_users\",\"asha-fusion-dev\",\"mytst\",\"newdb\"]\n";
            assertEquals(response, alldbs);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void setParams() {
        System.out.println("setParams().....");
        DbCxn noparams = new DbCxn();

        try {
            noparams.setHostnumber("192.168.0.7");
            noparams.setPortnumber("5984");
            assertEquals("http://192.168.0.7:5984/", noparams.getParams()[2]);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void readDBtest() throws IOException{
        assertTrue(db.readDB("_all_dbs") != null);
        assertTrue(db.readDB("newdb/") != null);

        System.out.println("Outputs:" + db.readDB("_all_dbs") + db.readDB("newdb/"));
    }

    @Test
    public void writeDBtest() throws IOException{
        System.out.println("write DB test...");
        db.writeDB("newdb", "fine");
    }





}

