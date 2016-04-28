package in.shekhar.sudoku;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class Sudoku extends AppCompatActivity implements View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sudoku);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Set up click listeners for all the buttons
        View continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);
        View newButton = findViewById(R.id.new_game_button);
        newButton.setOnClickListener(this);
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View backGrngColor = findViewById(R.id.change_color);
        backGrngColor.setOnClickListener(this);
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sudoku, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.about_button:
                i = new Intent(this, About.class);
                startActivity(i);
                showToast("About Button Clicked");
                break;
            case R.id.new_game_button:
            case R.id.continue_button:
                i = new Intent(this, NewGame.class);
                startActivity(i);
                showToast("New Game Button Clicked");
                break;
            case R.id.change_color:
                Log.d("Button clicked", "Change Color Button Clicked");
                changeColor();
                break;
            case R.id.exit_button:
                finish();
                break;


        }
    }

    private void changeColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

//        Ignoring the change in button color and Toast for click
//        showToast("Changing color");
//        Button changeColorButton = (Button) findViewById(R.id.change_color);
//        changeColorButton.setBackgroundResource(R.color.white);

        LinearLayout ll = (LinearLayout) findViewById(R.id.content_main);
        ll.setBackgroundColor(color);
        Log.d("Button clicked", "Color Change complete");
        //setContentView(R.layout.content_sudoku);
    }

    private void showToast(String text) {
        Context context = getApplicationContext();
        if (text.length() <= 0) {
            text = "Some thing activated";
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
