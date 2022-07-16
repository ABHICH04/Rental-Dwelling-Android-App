package com.example.houserentalmanagement.houseOwner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.houserentalmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HouseDetails extends AppCompatActivity {

    TextView tv_houseDesc,address,rooms,rent;
    ImageView iv_houseImage;
    Button  btn_viewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);

        Intent intent = getIntent();
        String houseId = intent.getStringExtra("houseId");
        String noOfRoom = intent.getStringExtra("noOfRoom");
        String rentPerRoom = intent.getStringExtra("rentPerRoom");
        String houseDescription = intent.getStringExtra("houseDescription");
        String houseLocation = intent.getStringExtra("houseLocation");
        String houseImage = intent.getStringExtra("houseImage");
        String userId = intent.getStringExtra("userId");

        tv_houseDesc = findViewById(R.id.tv_houseDesc);
        address=findViewById(R.id.tv_houseAddress);
        rooms=findViewById(R.id.tv_houseRooms);
        rent=findViewById(R.id.tv_houseRent);
        iv_houseImage = findViewById(R.id.iv_houseImage);
        iv_houseImage = findViewById(R.id.iv_houseImage);


        btn_viewLocation = findViewById(R.id.btn_viewLocation);


        tv_houseDesc.setText("Facilities: "+houseDescription);
        address.setText("Address: "+houseLocation);
        rooms.setText("Number Of Rooms: "+noOfRoom);
        rent.setText("Rent per room: "+rentPerRoom);


        Glide.with(HouseDetails.this).load(houseImage).into(iv_houseImage);


        btn_viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HouseDetails.this, ViewLocation.class);
                intent1.putExtra("houseId", houseId);
                intent1.putExtra("noOfRoom", noOfRoom);
                intent1.putExtra("rentPerRoom", rentPerRoom);
                intent1.putExtra("houseDescription", houseDescription);
                intent1.putExtra("houseLocation", houseLocation);
                intent1.putExtra("houseImage", houseImage);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
            }
        });



    }
}