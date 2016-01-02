package com.example.singh.snap_app;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class EditFriends extends ListActivity {

    public  static final String TAG = EditFriends.class.getSimpleName();

    protected List<ParseUser> mUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //it shows loading animation on actionbar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_edit_friends);

    }

    //listview of editfriends Activity
    //Parsecontant is a class for all different fields like -> username,email...
    @Override
    protected void onResume() {
        super.onResume();
        setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseUser> query = new ParseUser().getQuery();
        query.orderByAscending(ParseConstants.KEY_USER);
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                setProgressBarIndeterminateVisibility(true);
                if(e == null)
                {
                    //success
                    mUsers = users;
                    String[] userNames = new String[mUsers.size()];
                    int i=0;
                    for(ParseUser user : mUsers)
                    {
                        userNames[i] = user.getUsername();
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            EditFriends.this,
                            android.R.layout.simple_list_item_checked,
                            userNames);
                    setListAdapter(adapter);
                }
                else
                {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder alert = new AlertDialog.Builder(EditFriends.this);
                    alert.setMessage(e.getMessage());
                    alert.setTitle(R.string.error_dialog);
                    alert.setPositiveButton("OK", null);
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_friends, menu);
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
