package edu.alfaisal.mustard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TimelineActivity extends Activity {
    RestAdapter adapter;                //API Adapter
    ConnectionAPI connection;           //API Connection object
    SharedPreferences accessTokenSP;    //Access Token
    String apiURL;                      //API Address
    String accessToken;                 //AccessToken
    ListView postDisplayList;           //Posts Display List

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Initialization of Timeline objects
        accessTokenSP = PreferenceManager.getDefaultSharedPreferences(this);
        accessToken = "Bearer "+ accessTokenSP.getString("accessToken", null);
        postDisplayList = (ListView)findViewById(R.id.postList);


        //Creating the connection
        apiURL = "http://hackathonapi.xyz:8080/api";
        adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(apiURL).build();
        connection = adapter.create(ConnectionAPI.class);


        //Fetching posts
        connection.fetch(accessToken, new Callback<List<Post>>() {
            @Override
            public void success(List<Post> posts, Response response) {
                Toast.makeText(getApplicationContext(), "Successful FETCH", Toast.LENGTH_SHORT).show();
                //Displaying the posts
                displayPosts(posts);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "FAILED FETCH", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Displaying the posts of the users
    public void displayPosts(List<Post> posts){
        Toast.makeText(getApplicationContext(), "DISPLAYING POSTS", Toast.LENGTH_SHORT).show();
        CustomAdapter displayAdapter = new CustomAdapter(this, posts);
        postDisplayList.setAdapter(displayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.post) {
            startActivity(new Intent(TimelineActivity.this, PostActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
