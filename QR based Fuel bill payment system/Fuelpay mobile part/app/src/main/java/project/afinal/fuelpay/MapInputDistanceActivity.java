package project.afinal.fuelpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by ASUS on 2/4/2018.
 */

public class MapInputDistanceActivity extends Activity{
    EditText etfuel;
    TextView txtFuel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_input_distance);
        initialize();
    }

    public void initialize() {
        etfuel = findViewById(R.id.etfuel);
        txtFuel =findViewById(R.id.txtFuel);

        etfuel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Change the TextView background color
                //tv.setBackgroundColor(Color.YELLOW);

                // Get the EditText text and display it on TextView
                txtFuel.setText(etfuel.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
    }
    public void searchOnMap(View v) {
        Bundle localBundle = new Bundle();
        localBundle.putString("currentFuel", etfuel.getText().toString());
        Intent intent = new Intent(getBaseContext(), MapDistanceReminderActivity.class);
        intent.putExtras(localBundle);
        startActivity(intent);
    }
}
