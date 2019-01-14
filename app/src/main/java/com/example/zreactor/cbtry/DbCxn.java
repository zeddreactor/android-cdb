package com.example.zreactor.cbtry;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class DbCxn {

    private String hostnumber;
    private String portnumber;
    private String basepath;
    final static int timeout = 5000;
    private URLConnection dbCxn;

    public DbCxn(){
        System.out.println("Initializing with default params");
        this.hostnumber = "192.168.0.33";
        this.portnumber = "5984";
        this.basepath = getBasepathTemplate();
        this.getParams();
        try {
            setDBconnection();
        } catch(Exception ie) {
            ie.printStackTrace();
        }

    }

    public DbCxn(String hostnumber, String portnumber){
        this.hostnumber = hostnumber;
        this.portnumber = portnumber;
        this.basepath = getBasepathTemplate();

        this.getParams();
        try {
            setDBconnection();
        } catch(Exception ie) {
            ie.printStackTrace();
        }
    }

    public void setHostnumber(String hostnumber){

        this.hostnumber = hostnumber;
        this.setBasepath();
    }

    public void setPortnumber(String portnumber){

        this.portnumber = portnumber;
        this.setBasepath();
    }

    public void setBasepath(){

        this.basepath = getBasepathTemplate();
    }

    private String getBasepathTemplate(){
        basepath = "http://" + this.hostnumber + ":" + this.portnumber + "/";
        return basepath;
    }

    public String[] getParams(){
        String[] params = new String[3];
        params[0] = this.hostnumber;
        params[1] = this.portnumber;
        params[2] = this.basepath;
        System.out.println("getParams() says... params of DbCxn object are: " + Arrays.toString(params));
        return params;
    }


    protected void setDBconnection(){

        try {
            URL urlstring = new URL(this.basepath);
            URLConnection urlCxn = urlstring.openConnection();
            urlCxn.setConnectTimeout(timeout);
            urlCxn.setReadTimeout(timeout);
            this.dbCxn = urlCxn;


        } catch(IOException ie) {
            ie.printStackTrace();
        } finally {
            // disconnect
        }

    }

    protected URLConnection getDBconnection(String url) throws IOException {
        URL urlstring = new URL(url);
        URLConnection urlCxn = urlstring.openConnection();
        urlCxn.setConnectTimeout(timeout);
        urlCxn.setReadTimeout(timeout);
        return urlCxn;
    }

    protected String readDB(String suffix) throws IOException {
        String url = this.basepath + suffix;
        URLConnection cxn = getDBconnection(url);
        cxn.setConnectTimeout(timeout);
        cxn.setReadTimeout(timeout);

        BufferedReader rdr = new BufferedReader(new InputStreamReader(cxn.getInputStream()));
        String inputLine;
        StringBuilder sb = new StringBuilder();

        while ((inputLine = rdr.readLine()) != null) {
            System.out.println(inputLine); // captures output in variable inputLine (has contents)
            System.out.println(rdr.readLine()); // line-reading method, generator? no contents
            sb.append(inputLine);
            System.out.println(sb.toString()); // string builder : method to make string? to string -> makes the string

        }
        rdr.close();
        System.out.println("Expire connection on....." + cxn.getExpiration());

        return sb.toString();

    }

    public void writeDB(String dbname, String jsonstring) throws IOException {

        String data = "{\"_id\": \"awesdata\", \"id\": \"6000\"}";

        URL url = new URL("http", this.hostnumber, Integer.parseInt(this.portnumber), "/"+dbname+"/");
        HttpURLConnection client = (HttpURLConnection) url.openConnection();
        String credentials = "admin:mnr";

        try {

        client.setRequestMethod("POST");
        client.setRequestProperty("Authorization", credentials);
        client.setDoOutput(true);
        client.setRequestProperty("Content-Type", "application/json");
        client.setRequestProperty("Accept", "application/json");
        OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
        osw.write(data);
        osw.flush();
        osw.close();
            // with Oracle HttpURLConnection,
            // need interaction w response to send the content, else will catch it
            // getResponseCode makes interaction with client & tells client to be in streaming mode
            // so response will not be caught, but posted
        System.err.println(client.getResponseCode());

        } catch (Exception ex) {
            System.out.println("We have exception");
            ex.printStackTrace();

        }

        System.out.println("try: " + url + data + client);

    }


    protected static String connectToCDB(String hostnumber, String port) throws IOException {
        if (hostnumber == "") {
            hostnumber = "192.168.0.7";
        }

        if (port == "") {
            port = "5984";
        }

        String url = "http://" + hostnumber + ":" + port + "/_all_dbs";

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

        }
        in.close();

        return sb.toString();

    }


}
