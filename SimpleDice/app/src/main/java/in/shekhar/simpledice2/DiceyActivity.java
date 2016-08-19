package in.shekhar.simpledice2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class DiceyActivity extends AppCompatActivity implements View.OnClickListener {

    boolean doubleBackToExitPressedOnce = false;

    private static final String TAG = DiceyActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicey);

        View rollButton = (View)findViewById(R.id.roll_button);
        rollButton.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.roll_button:
                //rolling(200);
                roll();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        // Add a snackbar rather than a toast

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //private methods for the app
    private void roll(){
        TextView result_display = (TextView) findViewById(R.id.result);
        ImageView iv = (ImageView) findViewById(R.id.diceImage);
        Random r = new Random();
        int value = -1;
        final long timeToRoll=100;
        while(value < 0){
            int i_val = r.nextInt();
            value = i_val%6;
            Log.d(TAG,"intermediate value is " + i_val);
            Log.d(TAG, "still rolling, value is" + value + " which might not be final");
        }
        Log.i(TAG, "roll: value is " + value);
        Log.i(TAG, "roll: display value will be " + (value + 1));
        result_display.setVisibility(View.GONE);
        rolling(timeToRoll, value);
    }

    private void displayRes(int value){
        TextView result_display = (TextView) findViewById(R.id.result);
        TextView dummy = (TextView) findViewById(R.id.resultDummy);
        dummy.setVisibility(View.VISIBLE);
        iv.setVisibility(View.VISIBLE);
        if(value == 0){
            result_display.setText(R.string.value1);
            iv.setImageResource(R.drawable.dice1);
        }else if(value == 1){
            result_display.setText(R.string.value2);
            iv.setImageResource(R.drawable.dice2);
        }else if(value == 2){
            result_display.setText(R.string.value3);
            iv.setImageResource(R.drawable.dice3);
        }else if(value == 3){
            result_display.setText(R.string.value4);
            iv.setImageResource(R.drawable.dice4);
        }else if(value == 4){
            result_display.setText(R.string.value5);
            iv.setImageResource(R.drawable.dice5);
        }else if(value == 5){
            result_display.setText(R.string.value6);
            iv.setImageResource(R.drawable.dice6);
        }else{
            dummy.setText(R.string.error);
        }
    }
    private void vibrate(long milliSec){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Log.d(TAG,"vibrating the device for " + milliSec + " milli secs");
        v.vibrate(milliSec);
    }

    //needed delaction here as its needed in run method
    ImageView iv;
    private void rolling(long milliSec, final int result){
        final TextView dummy = (TextView) findViewById(R.id.resultDummy);
        iv = (ImageView) findViewById(R.id.diceImage);;
        if (result != 3) {
            iv.setImageResource(R.drawable.dice4);
        } else {
            iv.setImageResource(R.drawable.dice2);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                displayRes(result);
                vibrate(50);
            }
        }, milliSec);
    }
}
