package algonquin.cst2335.tran0472;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.tran0472.databinding.ActivityMainBinding;

/** This is the page that simulates a login page
 * @author VyTran
 * @version 1.0
 * @since Version 1.0 (or Java 17)
 */
public class MainActivity extends AppCompatActivity {
    //create a Volley object that will connect to a server
    RequestQueue queue = null;
    private String cityName;

    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());  //load xml file on screen

        binding.forcastBtn.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();

            String stringUrl = "";
            try {
                stringUrl = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        URLEncoder.encode(cityName, "UTF-8") +
                        "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
            } catch(UnsupportedEncodingException e) { e.printStackTrace(); }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringUrl, null, (response) -> {
                try {
                    JSONObject coord = response.getJSONObject("coord");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JSONArray weatherArray = null;
                try {
                    weatherArray = response.getJSONArray("weather");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JSONObject position0 = null;
                try {
                    position0 = weatherArray.getJSONObject(0);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String description = position0.getString("description");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String iconName = position0.getString("icon");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JSONObject mainObject = null;
                try {
                    mainObject = response.getJSONObject("main");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    double current = mainObject.getDouble("temp");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    double min = mainObject.getDouble("temp_min");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    double max = mainObject.getDouble("temp_max");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    int humidity = mainObject.getInt("humidity");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }, (error) -> {});
            queue.add(request);



        });
    }
}