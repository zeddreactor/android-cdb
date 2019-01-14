package com.example.zreactor.cbtry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Database;
import com.couchbase.lite.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static Database db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.submittodb);
        final EditText txtedit = findViewById(R.id.doctosave);
        final TextView mtext = findViewById(R.id.subtextview);

        // initialize DB
        try {

            final Database dbase = didStart();
            this.db = dbase;

        } catch (CouchbaseLiteException e){
            Log.i(null, "We had a Couchbase exception");
            e.printStackTrace();
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String inputtext = txtedit.getText().toString();
                try {
                    final String inputtext2 = connectToCDB("", "");
                    mtext.setText("Submitted text: " + inputtext2 + " for the win");
                    Log.i("From app", inputtext);

                } catch (Exception e) {
                    Log.i("connecttocdb exceptions", e.getStackTrace().toString());

                }

//                mtext.setText("Submitted text: " + inputtext + " for the win");
//                Log.i("From app", inputtext);
//
//                MutableDocument mutableDoc = new MutableDocument().setString("input", inputtext);

//                if (db != null) {
//                    try {
//                        db.save(mutableDoc);
//                        Log.i("CouchbaseIO", inputtext + " saved!");
//                    } catch (CouchbaseLiteException e) {
//                        Log.i("CouchbaseLiteException", e.getStackTrace().toString());
//                    }
//                }
            }
        });
    }

    public String connectToCDB(String hostnumber, String port) throws MalformedURLException, IOException {
        if (hostnumber == "") {
            hostnumber = "192.168.0.30";
        }

        if (port == "") {
            port = "5984";
        }

        String url = "http://" + hostnumber + ":" + port + "/_all_dbs";
        Log.i("URL string....", url);

        URL urlstring = new URL(url);
        URLConnection urlConnection = urlstring.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine);
            System.out.println(inputLine);
        }
        in.close();

        Log.i("OUTPUT LINE", inputLine);

        return inputLine;

    }

//    public void connectToCouch(String doctext) throws MalformedURLException, IOException {
//        URL url = new URL("https://localhost:5984");
//        URLConnection urlConnection = url.openConnection();
//        InputStream in = urlConnection.getInputStream();
//
//        try {
//            urlConnection.setReadTimeout(3000);
//            urlConnection.setConnectTimeout(3000);
//            urlConnection.setDoInput(true);
//            urlConnection.connect();
//        } catch (e) {
//
//        }
//
//
//
//
//
//
//    }

    public Database didStart() throws CouchbaseLiteException {


        DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
        Database database = new Database("mydb", config);

        return database;
    }


//            // new document in the couch DB
//            MutableDocument mutableDoc = new MutableDocument()
//                    .setFloat("version", 2.0F)
//                    .setString("type", "SDK");
//
//            database.save(mutableDoc);
//
//            // update an existing couchDB doc
//            mutableDoc = database.getDocument(mutableDoc.getId()).toMutable();
//            mutableDoc.setString("language", "Java");
//            database.save(mutableDoc);
//            Document document = database.getDocument(mutableDoc.getId());
//
//            Log.i(TAG, "Document ID :: " + document.getId());
//            Log.i("MainActivity - didStart", "Learning " + document.getString("language"));




}
