package in.shekhar.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by shekhar on 4/7/2016.
 */
public class About extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        TextView aboutLL = (TextView) findViewById(R.id.about_content);
        aboutLL.setBackgroundColor(222);

    }
}
