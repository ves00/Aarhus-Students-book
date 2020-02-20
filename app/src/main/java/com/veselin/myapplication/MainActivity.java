package com.veselin.myapplication;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView List;                                  // we make a Listview called list
    DataBase database;                                      // we make a variable with database type
    //-----------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // we make it show activity_main when we start up the app
//-----------------------------------------------------------------------------------
        database = new DataBase(this);                      //creating new database from database file
        ArrayList aList = database.getAllStudents();         // making an alist variable with arraylist type which runs "getAllStudents" function from DataBase.java
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, aList);   // We make an arrayadapter type variable called array adapter which will have students names
        // and create a new array list with built in android "simple_List_item_1" layout
//-----------------------------------------------------------------------------------
        List = (ListView)findViewById(R.id.listView1);      //We call out ListView1 from our activity_main.xml
        List.setAdapter(arrayAdapter);                      // Setting the data behind the ListView variable called "List"
        //---------------------------------------------------------------------------

        List.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);      // we set up a listener, so when we click on saved previous
                // data on the bottom of the app it opens it by starting a
                // new data bundle and intent

                Intent intent = new Intent(getApplicationContext(),display_Students.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }

        });

    }

    //----------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // We create an options menu which is located on top when we run the app and contains "add new" item
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //----------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
/* This section trigers when options menu is openes and the add new is pressed. and it opens display_Students.xml file
 with no inserted information*/
        switch(item.getItemId()) {
            case R.id.item1:
                Bundle dataBundle = new Bundle();
                Intent intent = new Intent(getApplicationContext(),display_Students.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            /*case R.id.search:
                Log.d("search clicked","test");
                // I need to do the search code
                // get the string that user put in the search bar
                // then do for a loop through the arraylist and check each element
                //if they match the search criteria / compare the name with search
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void browseWeb(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://www.google.com"));
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------
    public void showMap(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:26.934,-80.106"));
        PackageManager packageManager = getPackageManager();
        java.util.List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        //Check if we have any apps that can handle the geo urls
        if (activities.size()>0)
            startActivity(intent); //yes, start the app chooser.
        else
        { //no, notify user about this
            Toast toast = Toast.makeText(getApplicationContext(), "No Map Activity registered", Toast.LENGTH_LONG);
            toast.show();
        }
    }
//----------------------------------------------------------------------------------

}
