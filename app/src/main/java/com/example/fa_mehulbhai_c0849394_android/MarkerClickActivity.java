package com.example.fa_mehulbhai_c0849394_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MarkerClickActivity extends AppCompatActivity {

    DatabaseHelperClass databaseHelperClass;
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_click);


        TextView lblLatitude = findViewById(R.id.txtDetailsLat);
        TextView lblLongitude = findViewById(R.id.txtDetailsLong);
        EditText lblPlaceName = findViewById(R.id.edtDetailsPlaceName);
        Button deletePlace = findViewById(R.id.btnDeletePlace);
        Button editPlaceName = findViewById(R.id.btnEditPlace);
        Button applyChanges = findViewById(R.id.btnApplyChanges);

        Intent intent = getIntent();
        Double getLat = intent.getDoubleExtra("latitude", 0.0);
        Double getLong = intent.getDoubleExtra("longitude", 0.0);

        lblLatitude.setText(String.valueOf(getLat));
        lblLongitude.setText(String.valueOf(getLong));
        lblPlaceName.setText(intent.getStringExtra("placeName"));
        Integer testNum = intent.getIntExtra("id", 1);
        num = Math.round(testNum);

        databaseHelperClass = new DatabaseHelperClass(this);

        deletePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num != 0) {
                    databaseHelperClass.deletePlace(new ModelClass(num, lblLatitude.getText().toString()
                            , lblLongitude.getText().toString(),
                            editPlaceName.getText().toString()));

                    Intent intent = new Intent(MarkerClickActivity.this, MapsActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }

                //   Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
            }
        });

        editPlaceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblPlaceName.setFocusable(true);
                lblPlaceName.setEnabled(true);
                applyChanges.setVisibility(View.VISIBLE);
            }
        });

        applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testNum != 0 && !lblLatitude.getText().toString().isEmpty() && !lblLongitude.getText().toString().isEmpty()) {
                    databaseHelperClass.editPlace(new ModelClass(testNum, lblLatitude.toString(), lblLongitude.toString(),
                            lblPlaceName.getText().toString()));

                    Intent intent = new Intent(MarkerClickActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}