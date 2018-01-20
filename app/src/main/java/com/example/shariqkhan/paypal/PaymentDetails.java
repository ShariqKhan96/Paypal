package com.example.shariqkhan.paypal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShariqKhan on 1/20/2018.
 */

public class PaymentDetails extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("amount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String amount) {

        Toast.makeText(this, "Response Id" + response.optString("id"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Response Status" + response.optString("status"), Toast.LENGTH_SHORT).show();
    }
}
