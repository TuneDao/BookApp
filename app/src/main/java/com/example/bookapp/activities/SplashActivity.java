package com.example.bookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.bookapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    //firebase auth
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, DashboardUserActivity.class));
                finish();
                /*checkUser();*/
            }
        },2000);
    }

//    private void checkUser() {
//        //nếu login tài khoản trước đó thì khi khởi động lại thì vẫn tài khoản đó
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if(firebaseUser == null){
//            startActivity(new Intent(SplashActivity.this,MainActivity.class));
//            finish();
//        }else{
//            //user login
//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//            ref.child(firebaseUser.getUid())
//                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            // lấy tài khoản khách hàng
//                            String userType =""+ snapshot.child("Tài khoản").getValue();
//                            if(userType.equals("khách hàng")){
//                                //đây là tài khoản khách hang thì mở trang chính
//                                startActivity(new Intent(SplashActivity.this,DashboardUserActivity.class));
//                                finish();
//
//                            } else if (userType.equals("quản trị viên")) {
//                                //đây là tài khoản quản trị viên thì mở trang quản trị viên
//                                startActivity(new Intent(SplashActivity.this,DashboardAdminActivity.class));
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//        }
//    }
}