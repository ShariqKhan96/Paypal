package com.example.shariqkhan.paypal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("");


    @Override
    protected void onDestroy() {
        Intent intent= new Intent(this, PayPalService.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);



        //ON BUTTON CLICK startPayment

    }

public void doPayment()
{

    PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf("500")), "USD"
            ,"Donate for Charity", PayPalPayment.PAYMENT_INTENT_SALE);
    Intent intent = new Intent(this, PaymentActivity.class);
    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
    startActivityForResult(intent, PAYPAL_REQUEST_CODE);

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE)
        {

            if (resultCode == RESULT_OK)
            {

                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null)
                {

                    try{

                        String result = confirmation.toJSONObject().toString();
                        startActivity(new Intent(this, PaymentDetails.class)
                        .putExtra("PaymentDetails", result)
                                .putExtra("amount", "500$")
                        );
                    }catch (Exception  e)
                    {
                        Log.e("ExcepionMessage",e.getMessage());
                    }
                }
            }
        }else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        else if (resultCode == Activity.RESULT_CANCELED)
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }
}
