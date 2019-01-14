package com.example.zreactor.cbtry;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.Scanner;

public class ConnectToCDBTest {

    public String connectToCDB(String hostnumber, String port) throws IOException {
        if (hostnumber == "") {
            hostnumber = "192.168.0.30";
        }

        if (port == "") {
            port = "5984";
        }

        String url = "http://" + hostnumber + ":" + port + "/_all_dbs";

        URL urlstring = new URL(url);
        HttpURLConnection cxn = (HttpURLConnection) urlstring.openConnection();
        cxn.setRequestMethod("GET");

        StringBuilder sb = new StringBuilder();
        String inputLine;

        try {
            InputStream in = cxn.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();

            } else {
                return null;
            }
        }
        finally {
            cxn.disconnect();
        }
    }


    @Test
    public void connectWorked() {
        try {
            String inputtext2 = connectToCDB("192.168.0.7", "");
            String correct = "[\"_replicator\",\"_users\",\"asha-fusion-dev\",\"mytst\",\"newdb\"]\n";
            assertEquals(correct, inputtext2);
            System.out.println("Testing url worked");

        } catch (Exception e){
            e.printStackTrace();

        }

    }

}
