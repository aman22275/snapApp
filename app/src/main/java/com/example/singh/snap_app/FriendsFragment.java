package com.example.singh.snap_app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by singh on 11/15/2015.
 */
public class FriendsFragment extends ListFragment {


    public static final String TAG = EditFriends.class.getSimpleName();
    protected List<ParseUser> mFriends;
    protected ParseUser mCurrentUser = new ParseUser();
    protected ParseRelation<ParseUser> mFriendsRelation;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

        getActivity().setProgressBarIndeterminateVisibility(true);
        ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
        //the below code will extract all your friends from server
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e)
            {
                mFriends = friends;
                  getActivity().setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    String[] usernames = new String[mFriends.size()];
                    int i = 0;
                    for (ParseUser user : mFriends) {
                        usernames[i] = user.getUsername();
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getListView().getContext(),
                            android.R.layout.simple_list_item_1,
                            usernames);
                    setListAdapter(adapter);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getListView().getContext());
                    builder.setMessage(e.getMessage())
                            .setTitle("Some Error !")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                //we are override this lines of code now .
                // we work on EDITFRIENDS activity for these lines of code
               /* if(e == null)
                {
                    //match

                    for(int i=0 ; i< mUsers.size();i++ )
                    {
                        ParseUser user =  mUsers.get(i);

                        for(ParseUser friend : friends)
                        {
                            if(friend.getObjectId().equals(user.getObjectId()))
                            {
                                getListView().setItemChecked(i, true);
                            }
                        }
                    }
                }
                else
                {
                    Log.e(TAG, e.getMessage());
                }*/
            }
        });



    }

}


