package com.example.fa_mehulbhai_c0849394_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FavouritePlaceActivity extends AppCompatActivity {

    private DatabaseHelperClass databaseHelperClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_place);

        databaseHelperClass = new DatabaseHelperClass(this);

        Button addToFvrt = findViewById(R.id.btnFvrt);
        EditText fvrtPlaceName = findViewById(R.id.edtFavouritePlace);
        TextView fvrtPlaceList = findViewById(R.id.txtFvrtPlace);

        TextView lblLattitude = findViewById(R.id.addlat);
        TextView lblLongitude = findViewById(R.id.addLong);

        Intent i = getIntent();
        Double getLat = i.getDoubleExtra("lattitude",0.0);
        Double getLong = i.getDoubleExtra("longitude",0.0);

        lblLattitude.setText("Latitude : "+String.valueOf(getLat));
        lblLongitude.setText("Longitude : "+String.valueOf(getLong));

        addToFvrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelperClass.addPlaceToDatabase(new ModelClass(lblLattitude.getText().toString(),
                        lblLongitude.getText().toString(),
                        fvrtPlaceName.getText().toString()));
                Intent i = new Intent(FavouritePlaceActivity.this,MapsActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}