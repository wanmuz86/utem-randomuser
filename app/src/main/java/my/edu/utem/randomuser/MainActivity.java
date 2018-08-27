package my.edu.utem.randomuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView nameTextView, emailTextView, addressTextView, phoneTextView, dobTextView;
    ImageView imageView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        addressTextView = findViewById(R.id.addressTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        dobTextView = findViewById(R.id.dobTextView);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.pbLoading);


    }

    public void getUser(View view) {

        // Instantiate the RequestQueue.
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://randomuser.me/api";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject personObject = jsonObject.getJSONArray("results").getJSONObject(0);
                            String firstName = personObject.getJSONObject("name").getString("first");
                            String lastName = personObject.getJSONObject("name").getString("last");

                            String title = personObject.getJSONObject("name").getString("title");
                            String email = personObject.getString("email");
                            String phone = personObject.getString("phone");
                            String dob = personObject.getJSONObject("dob").getString("date");


                            String street = personObject.getJSONObject("location").getString("street");
                            String postCode = personObject.getJSONObject("location").getString("postcode");
                            String city = personObject.getJSONObject("location").getString("city");
                            String state = personObject.getJSONObject("location").getString("state");
                            String picUrl = personObject.getJSONObject("picture").getString("large");
                            nameTextView.setText(String.format("%s %s %s", title, firstName, lastName));
                            emailTextView.setText(email);
                            phoneTextView.setText(phone);
                            dobTextView.setText(dob);
                            addressTextView.setText(String.format("%s, %s, %s, %s", street, city, postCode, state));
                            Glide.with(MainActivity.this).load(picUrl).into(imageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                nameTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
