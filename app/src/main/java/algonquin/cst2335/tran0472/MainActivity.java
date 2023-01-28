package algonquin.cst2335.tran0472;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    ImageView imgView;
    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.flagView);
        sw = findViewById(R.id.spin_switch);

        sw.setOnCheckedChangeListener((btn, isChecked)->{
            if(isChecked){
                RotateAnimation rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotate.setDuration(5000);                   //how long to complete a full circle (5000milliseconds aka 5secs)
                rotate.setRepeatCount(Animation.INFINITE);  //the flag will spin an infinite number of times, you can set it to a number
                rotate.setInterpolator(new LinearInterpolator());   //this method means it's constant spinning motion, not getting faster or slower (linear)
                imgView.startAnimation(rotate);
            }else {
                imgView.clearAnimation();
            }
        });
    }
}