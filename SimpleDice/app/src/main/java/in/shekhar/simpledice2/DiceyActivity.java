package in.shekhar.simpledice2;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class DiceyActivity extends AppCompatActivity implements View.OnClickListener {

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
                roll();
                vibrate(50);
        }
    }

    private void roll(){
        TextView result_display = (TextView) findViewById(R.id.result);
        Random r = new Random();
        int value = -1;
        while(value < 0){
            int i_val = r.nextInt();
            value = i_val%6;
            Log.d(TAG,"intermediate value is " + i_val);
            Log.d(TAG,"still rolling, value is" + value + " which might not be final");
        }
        Log.i(TAG, "roll: value is " + value);
        Log.i(TAG, "roll: display value will be " + (value+1));
        if(value == 0){
            result_display.setText(R.string.value1);
        }else if(value == 0){
            result_display.setText(R.string.value1);
        }else if(value == 1){
            result_display.setText(R.string.value2);
        }else if(value == 2){
            result_display.setText(R.string.value3);
        }else if(value == 3){
            result_display.setText(R.string.value4);
        }else if(value == 4){
            result_display.setText(R.string.value5);
        }else if(value == 5){
            result_display.setText(R.string.value6);
        }else{
            result_display.setText(R.string.error);
        }

    }

    private void vibrate(long milliSec){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Log.d(TAG,"vibrating the device for " + milliSec + " milli secs");
        v.vibrate(milliSec);
    }
}
