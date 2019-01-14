package com.example.zreactor.cbtry;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class clickSubmitButton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.submittodb);
        final EditText txtedit = findViewById(R.id.doctosave);
        final TextView mtext = findViewById(R.id.subtextview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String inputtext = txtedit.getText().toString();
                mtext.setText("Submitted text: " + inputtext + " for the win");
                Log.i("From app", inputtext);

            }
        });




//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public static String connectToCDB(String hostnumber, String port) throws MalformedURLException, IOException {
        if (hostnumber == "") {
            hostnumber = "192.168.0.30";
        }

        if (port == "") {
            port = "5984";
        }

        String url = "http://" + hostnumber + ":" + port + "/_all_dbs";
//        Log.i("URL string....", url);

        URL urlstring = new URL(url);
        URLConnection urlConnection = urlstring.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String inputLine;
        StringBuilder sb = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine); // captures output in variable inputLine (has contents)
            System.out.println(in.readLine()); // line-reading method, generator? no contents
            sb.append(inputLine);
            System.out.println(sb.toString()); // string builder : method to make string? to string -> makes the string

//        while ((inputLine = in.readLine()) != null) {
//            sb.append(inputLine);
//            System.out.println(inputLine);
        }
        in.close();

        return sb.toString();

    }

}
