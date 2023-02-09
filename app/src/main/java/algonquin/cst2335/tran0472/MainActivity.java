package algonquin.cst2335.tran0472;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/** This is the page that simulates a login page
 * @author VyTran
 * @version 1.0
 * @since Version 1.0 (or Java 17)
 */
public class MainActivity extends AppCompatActivity {
    /** This holds the text at the center of the screen */
    private TextView tv = null;
    /** This holds the place that gets the password input */
    private EditText thePasswordText = null;
    /** This holds the button of the screen */
    private Button btn = null;

    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        thePasswordText = findViewById(R.id.editText);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(btn->{
            String password = thePasswordText.getText().toString();
            //looking for UpperCase, LowerCase, Number, and Special character
            if(checkPasswordComplexity(password)){
                tv.setText("Your password meets the requirements");
            }else {
                tv.setText("You shall not pass");
            }
        });
    }

    /**
     * This methods checks if the string has a special case
     * @param c characters to be checked
     * @return true if the char c is one of the case
     */
    private boolean isSpecialCharacter(char c){
        switch (c){
            case '#':
            case '?':
            case '*':
            case '$':
            case '%':
            case '^':
            case '&':
            case '!':
            case '@':
                return true;
            default:
                return false;
        }
    }

    /** This function scans the string to see if it is complex enough
     *
     * @param pw a String that is being checked
     * @return Returns true if the password has Uppercase, Lowercase, Number and Special character, otherwise false
     */
    private boolean checkPasswordComplexity(String pw) {
        boolean isComplexEnough = false;
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0;i < pw.length();i++){
            char c = pw.charAt(i);
            if(Character.isUpperCase(c)){      //call the isSpecialCharacter() on the character at index i of the pw String
                foundUpperCase = true;
            }if(Character.isLowerCase(c)){
                foundLowerCase = true;
            }if(Character.isDigit(c)){
                foundNumber = true;
            }if(isSpecialCharacter(c)){
                foundSpecial = true;
            }
        }
        if(!foundUpperCase){
            Toast.makeText(this, "Missing an Upper Case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "Missing a Lower Case letter", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!foundNumber){
            Toast.makeText(this, "Missing a Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "Missing a Special Case", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;   //only get here if they're all true
        }
    }
}