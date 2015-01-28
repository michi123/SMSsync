package com.example.michal.smssync;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import Client.Client;
import Contact.ContactList;
import Contact.GetContactsDemo;


public class MainActivity extends Activity {
    private Client c;
    TextView lala;
    private boolean isOpen = false;
    private static final int CONTACTS_LOADER_ID = 1;

    public void setOpen(boolean open){
        this.isOpen = open;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        c = Client.getInst();
        c.setActivity(this);





        while (!isOpen){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void loginToServer(View v){
        c.send("loginDevice(+420 775 761 325)");
    }

    public void sendtoDB(View v){// {"text":"Toto je pokusná zpráva","to":"+420 775 163 795","time":"Nov 29, 2014 10:44:10 PM","from":"+420 775 163 795","contact":{"phone":{"number":"+420 775 163 795"}}}
        //(name.getText().toString())+","+String.valueOf(surname.getText().toString());
        //EditText text = (EditText) findViewById(R.id.text);
        //EditText to = (EditText) findViewById(R.id.to);
        //EditText sendTime = (EditText) findViewById(R.id.sendTime);
        //EditText from = (EditText) findViewById(R.id.from);
        //EditText contact = (EditText) findViewById(R.id.contact);
        String s;
        s= String.valueOf("{\"text\":\"Michal Kostelecký zkouška na hodině\",\"to\":\"+420 775 163 795\",\"time\":\"Nov 29, 2014 10:44:10 PM\",\"from\":\"+420 775 163 795\",\"contact\":{\"phone\":{\"number\":\"+420 123 456 789\"}}}");
        c.send(s);
        System.out.println("Odeslano: "+s);


    }


    public void logout (View v){
        c.send("logoutDevice()");

    }

    public void open(View v){
       // c = Client.getInst();
      //  c.setActivity(this);
      //  System.out.println("Connection is open");
        isOpen=true;


    }

    public void close(){//dodelat
       // c.close();
        isOpen=false;
        c.send("logoutDevice()");
        System.out.println("logoutDevice()");

    }

    public void message(String message){
        String[] zpravy;

    }
    public void readSMS(View v){
        c.send("getDB");
    }

    public void sendSMS(View v) {
        System.out.println("Metoda sendSMS");
        Log.i("Send SMS", "");

        String phoneNo = "725657989";
        String message = "Zkouska aplikace do predmetu ROPR, Michal Kostelecky";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void loadSMS(View v){
        //nacteni sms od kontaktu z telefonu
        Uri uri = Uri.parse("content://sms/");

        ContentResolver contentResolver = getContentResolver();

        String phoneNumber = "0123456789";
        String sms = "address='"+ phoneNumber + "'";
        Cursor cursor = contentResolver.query(uri, new String[] { "_id", "body" }, sms, null,   null);

        System.out.println ( cursor.getCount() );

        while (cursor.moveToNext())
        {
            String strbody = cursor.getString( cursor.getColumnIndex("body") );
            System.out.println ( strbody );
        }
    }

    public void saveSMStoTelephone(View v){
        //Ukladani sms do odeslanych
        ContentValues values = new ContentValues();

        values.put("address", "0123456789");

        values.put("body", "Zkouška na hodině!");

        getContentResolver().insert(Uri.parse("content://sms/sent"), values);
        System.out.println("sms ulozena");
    }

    public void loadContact(View v){
        //nacteni vsech kontaktu
        GetContactsDemo contactsDemo = new GetContactsDemo();
        contactsDemo.readContacts( getContentResolver() );
    }
    public void connect(View v){

    }




   private void connectController(){

       final Timer timer = new Timer();
       timer.scheduleAtFixedRate(new TimerTask() {
           int i = 10;
           public void run() {
               if(i==0){
                   i=10;
               }else{
                   i--;
               }
               System.out.println(i);
               if (i ==0){

                   if(isOpen==true){

                       lala.setText("pripojen");
                       System.out.println("pripojen");
                   }else{

                       lala.setText("Odpojen");
                       System.out.println("odpojen");
                   }
               }
           }
       }, 0, 1000);


   }

/*

    public void listView(){
        String[] myItems= {"blue", "green", "purple", "red"};
        for (int i= 0;i<4;i++){
            System.out.println("1 "+myItems[i]);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListView list = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.da_item,myItems);
        System.out.println("list!!!!");
        list.setAdapter(adapter);
    }
*/


}

