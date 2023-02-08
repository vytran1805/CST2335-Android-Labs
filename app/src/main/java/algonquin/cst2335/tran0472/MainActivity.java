package algonquin.cst2335.tran0472;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import algonquin.cst2335.tran0472.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    @Override   //1) When run the program, this starts first
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
//    The first parameter is a String called the TAG. This should be the class name, and the second
//    parameter is the message
        Log.w( TAG, "In onCreate() - Loading Widgets" );

        // save the MyData file to save login email address so that the next time they run the application, the email address can be pre-filled
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE); //create a SharedPreferences obj
        String emailAddress = prefs.getString("LoginName","");  //first time load the app, it will be pre-filled with an empty string
        // create Editor obj that stores email table on the disk
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("LoginName",emailAddress); //save the input to MyData file that we have opened above

        variableBinding.emailEditText.setText(emailAddress);    //set the text of EditText to be the emailAddress variable
        // float hi = prefs.getFloat("Hi",0);   //get value of name "Hi" in MyData.xml file (/data/data/cst2335.tran0472/shared_prefs)
        // int num = prefs.getInt("Age",0); //if name is not found then return 0
        editor.apply();

//        Set on click listener for the login button to go from MainActivity page to SecondActivity page
        variableBinding.button.setOnClickListener(btn->{
            //Intent is "where you want to go next?" function
            Intent nextPage = new Intent(MainActivity.this,SecondActivity.class);   //in the parenthesis is: 1) where we are now (MainActivity), 2) Which activity you want to go next (SeconActivity)
            // this function like a spreadsheet (populate table)
            String email = variableBinding.emailEditText.getText().toString();
            nextPage.putExtra("EmailAddress",email);
            // Actually makes the transition
            startActivity(nextPage);
        });

    }

    @Override   // 2) The app is now visible
    protected void onStart() {
        super.onStart();
        Log.w( TAG, "In onStart() - The application is now visible on screen" );
    }

    @Override   // 3) now responds to user input
    protected void onResume() {
        super.onResume();
        Log.w( TAG, "In onResume() - The application is now responding to user input" );
    }

    @Override   // Opposite to onResume(), stop responding to user input
    protected void onPause() {
        super.onPause();
        Log.w( TAG, "In onPause() - The application no longer responds to user input" );
    }

    @Override   // Opposite to onStart(), no longer visible
    protected void onStop() {
        super.onStop();
        //store the data in the SharedPreference in the onStop() method
        // when the user restart the application, onStop() will be called and data will be stored
        SharedPreferences prefs = getSharedPreferences("MyData",Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = prefs.edit();
        EditText emailEdit = findViewById(R.id.emailEditText);
        myEdit.putString("LoginName",emailEdit.getText().toString());
        myEdit.apply();
        Log.w( TAG, "In onStop() - The application is no longer visible" );
    }

    @Override   // Opposite to onCreate(), memory is garbage collected
    protected void onDestroy() {
        super.onDestroy();
        Log.w( TAG, "In onDestroy() - Any memory used by the application is freed" );
    }

}