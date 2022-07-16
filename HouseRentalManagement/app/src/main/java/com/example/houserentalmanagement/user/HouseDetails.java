package com.example.houserentalmanagement.user;

import static android.Manifest.permission.CALL_PHONE;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.houserentalmanagement.R;

public class HouseDetails extends AppCompatActivity {

    TextView tv_houseDesc,address,rooms,rent;
    ImageView iv_houseImage;
    Button  btn_viewLocation, btn_call, btn_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details2);

        Intent intent = getIntent();
        String houseId = intent.getStringExtra("houseId");
        String noOfRoom = intent.getStringExtra("noOfRoom");
        String rentPerRoom = intent.getStringExtra("rentPerRoom");
        String houseDescription = intent.getStringExtra("houseDescription");
        String houseLocation = intent.getStringExtra("houseLocation");
        String houseImage = intent.getStringExtra("houseImage");
        String userId = intent.getStringExtra("userId");
        String ownerContact=intent.getStringExtra("ownerContact");

        tv_houseDesc = findViewById(R.id.tv_houseDesc);
        address=findViewById(R.id.tv_houseAddress);
        rooms=findViewById(R.id.tv_houseRooms);
        rent=findViewById(R.id.tv_houseRent);
        iv_houseImage = findViewById(R.id.iv_houseImage);


        btn_viewLocation = findViewById(R.id.btn_viewLocation);
        btn_call = findViewById(R.id.btn_call);
        btn_message = findViewById(R.id.btn_message);

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ownerContact));
                startActivity(intent);
            }
        });

        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", ownerContact);
                smsIntent.putExtra("sms_body","Message");
                startActivity(smsIntent);

            }
        });

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
                intent1.putExtra("ownerContact",ownerContact);
                startActivity(intent1);
            }
        });


    }
}