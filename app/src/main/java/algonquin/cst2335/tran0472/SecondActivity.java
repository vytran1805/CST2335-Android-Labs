package algonquin.cst2335.tran0472;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2335.tran0472.databinding.ActivityMainBinding;
import algonquin.cst2335.tran0472.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivitySecondBinding secondBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        //ActivitySecondBinding


        setContentView(secondBinding.getRoot());

        //How to get the table
        Intent dataFromPage1 = getIntent();
        String emailAddress = dataFromPage1.getStringExtra("EmailAddress");
        //get the Welcome string from strings.xml + input emailAddress then set to the textView to display
        secondBinding.textView.setText(getString(R.string.welcome,emailAddress));   //display the email to the Second page, format "Welcome, +address"
        Log.d("Second activity","Email Address is "+ emailAddress);

        //save the value of the phone number that is currently entered in the EditText on that page
        SharedPreferences prefs = getSharedPreferences("MyData",Context.MODE_PRIVATE);  //fetching the stored data from the SharedPreference
        String phone = prefs.getString("PhoneNumber","");
        secondBinding.editTextPhone.setText(phone); //setting the fetched data in the EditText
        //make a phone call
        secondBinding.secondPageButton1.setOnClickListener(btn->{
            Intent call = new Intent(Intent.ACTION_DIAL);
            String phoneNumber = secondBinding.editTextPhone.getText().toString();
            //the phone needs to know which number to call. For that, your store the phone number in the Intent
            call.setData(Uri.parse("tel: "+phoneNumber));
            startActivity(call);    //call the number
        });

        //  check if the picture existed, then load it immediately on screen
        File sandbox = new File(getFilesDir(), "Picture.png");   //points to your sandbox: /data/data/algonquin.cst2335.tran0472
        if(sandbox.exists()){
            Bitmap theImage = BitmapFactory.decodeFile(sandbox.getAbsolutePath().toString());
            secondBinding.imageView.setImageBitmap(theImage);
        }
        // take a picture with the phone
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                                //Parameters: 1) pass in the function call above is called an ActivityResultContract:
        //get data back from the next Activity (get picture)                  2) pass in is a callback function to receive data from the next activity
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override   //this function means when the camera has disappeared,
            // it calls onActivityResult(), passes an intent
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {     //If the result is RESULT_OK, then the camera activity sends back a Bitmap in the Intent data object
                    //our picture is stored in here: data
                    Intent data = result.getData();     //intent is like a table: variable here is "data" and value is our picture (BITMAP)
                    Bitmap thumbnail = data.getParcelableExtra("data"); //the picture is saved as a Bitmap object
                    //save the bitmap object to the disk using the code

                    FileOutputStream fOut = null;

                    //this below automatically opens a file in sandbox
                    try {
                        fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                        thumbnail.compress(Bitmap.CompressFormat.PNG,100,fOut);    //compresses png into your sandbox
                        fOut.flush();
                        fOut.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    secondBinding.imageView.setImageBitmap(thumbnail);  //take the returned Bitmap object and set that to be the src parameter of the ImageView
                }
            }
        });
        //display the picture once the button is clicked
        secondBinding.secondPageButton2.setOnClickListener(btn->{
            cameraResult.launch(cameraIntent);
        });

        //go back to the previous page (MainActivity)
        secondBinding.goBack.setOnClickListener(btn->{
            //send data to the previous page
            //Intent dataTable = new Intent();    //no need from/to cause there is only 1 direction to go back
            finish();   //return onPause(), onStop() of this page
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        //store the data in the SharedPreference in the onPause() method
        // when the user closes the application, onPause() will be called and data will be stored
        SharedPreferences prefs = getSharedPreferences("MyData",Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = prefs.edit();
        EditText phoneNumber = findViewById(R.id.editTextPhone);
        myEdit.putString("PhoneNumber",phoneNumber.getText().toString());
        myEdit.apply();
    }
}