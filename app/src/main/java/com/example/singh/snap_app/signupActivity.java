package com.example.singh.snap_app;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signupActivity extends AppCompatActivity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected EditText mEmail;
    protected Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUsername = (EditText)findViewById(R.id.username_signup);
        mPassword = (EditText)findViewById(R.id.password_signup);
        mEmail = (EditText)findViewById(R.id.email_signup);
        mSignupButton = (Button)findViewById(R.id.button_signup);
        mSignupButton.setOnClickListener(new View.OnClickListener() {
          // @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();

                username = username.trim();
                password = password.trim();
                email = email.trim();

                if(username.isEmpty() || password.isEmpty() || email.isEmpty())
                {
                 /*   AlertDialog.Builder builder = new AlertDialog.Builder(signupActivity.this);
                    builder.setTitle(R.string.signup_dialog_title);
                    builder.setMessage(R.string.signup_dialog_error_message);
                    builder.setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.create();
*/
                    Toast.makeText(getBaseContext(),"Fill form",Toast.LENGTH_LONG).show();
                }
                else
                {
                    //create user in backenend
                    ParseUser newuser = new ParseUser();
                    newuser.setUsername(String.valueOf(username));
                    newuser.setPassword(String.valueOf(password));
                    newuser.setEmail(String.valueOf(email));

                    newuser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                Intent i = new Intent(signupActivity.this, loginActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
