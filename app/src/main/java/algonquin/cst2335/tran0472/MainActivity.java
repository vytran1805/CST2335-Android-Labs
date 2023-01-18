package algonquin.cst2335.tran0472;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.tran0472.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());  //loads XML on screen

    // Call the widgets by ID
        TextView myText = findViewById(R.id.textView);
        Button btn = findViewById(R.id.myButton);
        EditText myEdit = findViewById(R.id.myEditText);

        String editString = myEdit.getText().toString();
        myText.setText("Your edit text has: "+editString);
    }
}