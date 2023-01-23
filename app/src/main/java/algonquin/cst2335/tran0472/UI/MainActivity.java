package algonquin.cst2335.tran0472.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.tran0472.data.MainViewModel;
import algonquin.cst2335.tran0472.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainViewModel model;    //Lab2 (4/6): declare the model object as a class variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());  //loads XML on screen

    // Call the widgets by ID
//        TextView myText = findViewById(R.id.textView);
//        Button btn = findViewById(R.id.myButton);
//        EditText myEdit = findViewById(R.id.myEditText);

    // Call the widgets by ViewBinding
        TextView myText = variableBinding.textView;
        Button btn = variableBinding.myButton;
        EditText myEdit = variableBinding.myEditText;

        String editString = myEdit.getText().toString();
        //myText.setText("Your edit text has: "+editString);

//        Lab2 (4/6): Connect the AppCompatActivity to the ViewModel class
        model = new ViewModelProvider(this).get(MainViewModel.class);
        //myText.setText(model.editString);
//        Lab2 (3/6): Lambda Functions
        btn.setOnClickListener(click-> {
            model.editString.postValue(variableBinding.myEditText.getText().toString());
            myText.setText("Your edit text has: " + model.editString);
        });
        model.editString.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                myText.setText("Your edit text has "+s);
            }
        });
    }
}