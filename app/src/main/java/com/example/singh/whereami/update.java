package com.example.singh.whereami;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class update extends ActionBarActivity {

    EditText et1,et2,et3,et4,et5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
      /*  et4=(EditText)findViewById(R.id.et4);
        et5=(EditText)findViewById(R.id.et5);
*/
    }

    public void save(View v){

        SharedPreferences sp=getSharedPreferences("mydata",MODE_PRIVATE);
        SharedPreferences.Editor write=sp.edit();
        write.putString("phone1",et1.getText().toString());
        write.putString("phone2",et2.getText().toString());
        write.putString("phone3",et3.getText().toString());
     /*   write.putString("phone4",et4.getText().toString());
        write.putString("phone5",et5.getText().toString());
       */
        write.apply();
        Toast.makeText(getBaseContext(),"You Are Safe Now",Toast.LENGTH_SHORT).show();
    }


    public void contact(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 111);
    }

    public void contact1(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 112);
    }

    public void contact2(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 113);
    }



    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        switch (reqCode) {
            case (111):
                if (resultCode == Activity.RESULT_OK) {

                    Uri uri = data.getData();
                    Cursor contactCursor = getContentResolver().query(uri,
                            new String[]{ContactsContract.Contacts._ID}, null, null,
                            null);
                    String id = null;
                    if (contactCursor.moveToFirst()) {
                        id = contactCursor.getString(contactCursor
                                .getColumnIndex(ContactsContract.Contacts._ID));
                    }
                    contactCursor.close();
                    String phoneNumber = null;
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ? ",
                            new String[]{id}, null);
                    if (phoneCursor.moveToFirst()) {
                        phoneNumber = phoneCursor
                                .getString(phoneCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        et1.setText(phoneNumber);
                    }
                    phoneCursor.close();
                    break;
                }


            case (112):
                if (resultCode == Activity.RESULT_OK) {

                    Uri uri = data.getData();
                    Cursor contactCursor = getContentResolver().query(uri,
                            new String[]{ContactsContract.Contacts._ID}, null, null,
                            null);
                    String id = null;
                    if (contactCursor.moveToFirst()) {
                        id = contactCursor.getString(contactCursor
                                .getColumnIndex(ContactsContract.Contacts._ID));
                    }
                    contactCursor.close();
                    String phoneNumber = null;
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ? ",
                            new String[]{id}, null);
                    if (phoneCursor.moveToFirst()) {
                        phoneNumber = phoneCursor
                                .getString(phoneCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        et2.setText(phoneNumber);
                    }
                    phoneCursor.close();
                    break;
                }


            case (113):
                if (resultCode == Activity.RESULT_OK) {

                    Uri uri = data.getData();
                    Cursor contactCursor = getContentResolver().query(uri,
                            new String[]{ContactsContract.Contacts._ID}, null, null,
                            null);
                    String id = null;
                    if (contactCursor.moveToFirst()) {
                        id = contactCursor.getString(contactCursor
                                .getColumnIndex(ContactsContract.Contacts._ID));
                    }
                    contactCursor.close();
                    String phoneNumber = null;
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ? ",
                            new String[]{id}, null);
                    if (phoneCursor.moveToFirst()) {
                        phoneNumber = phoneCursor
                                .getString(phoneCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        et3.setText(phoneNumber);
                    }
                    phoneCursor.close();
                    break;
                }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
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
}
