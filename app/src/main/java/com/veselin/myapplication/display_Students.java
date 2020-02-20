package com.veselin.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class display_Students extends Activity {
    private DataBase dbase;     //creating a database variable called dbase
    Button btnUpdate;           //creating a button variable called btnUpdate
    Button btnShare;           //creating a button variable  called btnShare
    TextView name;             //creating Textview variables
    TextView cpr;
    TextView email;
    TextView skype;
    TextView street;
    TextView field;
    int id_To_Update = 0;
//------------------------------------------------------------------------------------------------
    //all what this section makes is letting us view the stored data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_students);      //we make it show activity_display_students.xmll on the screen

        // we declare the empty fields in activity_display_students.xmll to textview variables we made before
        name = (TextView) findViewById(R.id.editTextName);
        cpr = (TextView) findViewById(R.id.editTextCpr);
        email = (TextView) findViewById(R.id.editTextEmail);
        skype = (TextView)  findViewById(R.id.editTextSkype);
        street = (TextView) findViewById(R.id.editTextStreet);
        field = (TextView) findViewById(R.id.editTextField);
        btnUpdate=(Button)findViewById(R.id.button2);
        btnShare=(Button)findViewById(R.id.button3);
        btnShare.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void  onClick(View v)
            {
                Intent shareintent = new Intent();
                shareintent.putExtra(Intent.EXTRA_TEXT, "Your Text Here");
                shareintent.setType("text/plain");
                startActivity(shareintent);
            }
        });
        dbase = new DataBase(this);     //setting new database

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                Cursor rs = dbase.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
// we get the data from the cursor function in the DataBase.java file and we update current variables "string nam" "string cprnumber" etc
                String nam = rs.getString(rs.getColumnIndex(DataBase.STUDENTS_COLUMN_NAME));
                String cprnumber = rs.getString(rs.getColumnIndex(DataBase.STUDENTS_COLUMN_CPR));
                String emai = rs.getString(rs.getColumnIndex(DataBase.STUDENTS_COLUMN_EMAIL));
                String skyp = rs.getString(rs.getColumnIndex(DataBase.STUDENTS_COLUMN_SKYPE));
                String stree = rs.getString(rs.getColumnIndex(DataBase.STUDENTS_COLUMN_STREET));
                String fie = rs.getString(rs.getColumnIndex(DataBase.STUDENTS_COLUMN_FIELD));
                UpdateStudents();
                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);      //save button declaration from activity_display_students.xmll
                b.setVisibility(View.INVISIBLE);
                //---------------------------------------------------------------------------------

                name.setText(nam);      // we make sure that we can see what we typed in the database to be previewed as text.
                name.setFocusable(false);   // this section doesn't let the already installed database texts table to be edited.
                name.setClickable(false);

                cpr.setText(cprnumber);     // we make sure that we can see what we typed in the database to be previewed as text.
                cpr.setFocusable(true);     // this section lets the already installed database texts table to be edited.
                cpr.setClickable(true);

                email.setText(emai);
                email.setFocusable(true);
                email.setClickable(true);

                skype.setText(skyp);
                skype.setFocusable(true);
                skype.setClickable(true);

                street.setText(stree);
                street.setFocusable(true);
                street.setClickable(true);

                field.setText(fie);
                field.setFocusable(true);
                field.setClickable(true);
                //---------------------------------------------------------------------------------

            }
        }
    }
   //-----------------------------------------------------------------------------------------------
    public void UpdateStudents(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isUpdate = dbase.updateStudents(name.getText().toString(), cpr.getText().toString(), // if we typed name, phone number etc it shows "Student info added successfully" on the screen
                                email.getText().toString(), skype.getText().toString(), street.getText().toString(),
                                field.getText().toString());
                        if(isUpdate==true){
                            Toast.makeText(getApplicationContext(), "Student info updated successfully",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);  // goes back to MainActivity.java
                        startActivity(intent);
                    }
                }

        );
    }
    //----------------------------------------------------------------------------------------------
    public void run(View view) {          // we use this function in activity_display_students on button1 which are paired and trigger when you click on it
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
                        if(dbase.insertStudents(name.getText().toString(), cpr.getText().toString(),        // if we typed name, phone number etc it shows "Student info added successfully" on the screen
                        email.getText().toString(), skype.getText().toString(), street.getText().toString(),
                        field.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Student info added successfully",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);     // goes back to MainActivity.java
                startActivity(intent);
            }
        }       //----------------------------------------------------------------------------------


    //start the phone chooser.
    public void makeCall(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_DIAL,
                Uri.parse("tel:+4550336798"));
        startActivity(intent);
    }
}
