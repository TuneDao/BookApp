package com.example.bookapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bookapp.BooksUserFragment;
import com.example.bookapp.databinding.ActivityDashboardUserBinding;
import com.example.bookapp.models.ModelCategory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DashboardUserActivity extends AppCompatActivity {

    //to show in tabs
    public ArrayList<ModelCategory> categoryArrayList;
    public ViewPagerAdapter viewPagerAdapter;

    private ImageSlider imageSlider;

    // view binding
    private ActivityDashboardUserBinding binding;

    // firebase Auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //slider
        imageSlider = binding.imageSlider;
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://designs.vn/wp-content/images/18-04-2013/1A.jpg",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://upload.wikimedia.org/wikibooks/vi/c/c0/B%C3%ACa_s%C3%A1ch_Harry_Potter_ph%E1%BA%A7n_2.jpg",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://marketplace.canva.com/EAD4741RYvA/1/0/1003w/canva-t%E1%BB%91i-%E1%BA%A3nh-b%C3%ACa-s%C3%A1ch-t%E1%BB%B1-truy%E1%BB%87n-7RowszlcylI.jpg",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://trangtridecor.net/wp-content/uploads/2022/11/trang-tri-bia-sach-1.jpg",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://upload.wikimedia.org/wikibooks/vi/3/32/B%C3%ACa_s%C3%A1ch_Harry_Potter_ph%E1%BA%A7n_4.jpg",ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        setUpViewPagerAdapter(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        //click vào logout
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(DashboardUserActivity.this,MainActivity.class));
                finish();
            }
        });
        //handle click, open profile
        binding.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardUserActivity.this,ProfileActivity.class));
            }
        });
        binding.bookcaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardUserActivity.this,DashboardUserActivity.class));
            }
        });

    }

    private void setUpViewPagerAdapter(ViewPager viewPager){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,this);

        categoryArrayList = new ArrayList<>();

        //load categories from firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear before adding to list
                categoryArrayList.clear();

                /*Load Categories e.g:All,Most Viewed,Most Download*/
                //Add data to models
                ModelCategory modelAll= new ModelCategory("01","Tất cả","",1);
                ModelCategory modelMostViewed= new ModelCategory("02","Lượt xem nhiều nhất","",1);
                ModelCategory modelDownloaded= new ModelCategory("03","Lượt tải nhiều nhất","",1);
                //add models to list
                categoryArrayList.add(modelAll);
                categoryArrayList.add(modelMostViewed);
                categoryArrayList.add(modelDownloaded);
                //add data to view pager adapter
                viewPagerAdapter.addFragment(BooksUserFragment.newInstance(
                        ""+modelAll.getId(),
                        ""+modelAll.getCategory(),
                        ""+modelAll.getUid()),modelAll.getCategory());
                viewPagerAdapter.addFragment(BooksUserFragment.newInstance(
                        ""+modelMostViewed.getId(),
                        ""+modelMostViewed.getCategory(),
                        ""+modelMostViewed.getUid()),modelMostViewed.getCategory());
                viewPagerAdapter.addFragment(BooksUserFragment.newInstance(
                        ""+modelDownloaded.getId(),
                        ""+modelDownloaded.getCategory(),
                        ""+modelDownloaded.getUid()),modelDownloaded.getCategory());
                //refesh List
                viewPagerAdapter.notifyDataSetChanged();

                //Now load from firebase
                for(DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    ModelCategory model = ds.getValue(ModelCategory.class);
                    //add data to list
                    categoryArrayList.add(model);
                    //add data to viewPagerAdapter
                    viewPagerAdapter.addFragment(BooksUserFragment.newInstance(
                            ""+model.getId(),
                            ""+model.getCategory(),
                            ""+modelAll.getUid()),model.getCategory());
                    //refesh list
                    viewPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //set adapter to view pager
        viewPager.setAdapter(viewPagerAdapter);
    }
    public class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<BooksUserFragment> fragmentList = new ArrayList<>();
        private ArrayList<String> fragmentTitleList = new ArrayList<>();
        private Context context;

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior,Context context) {
            super(fm, behavior);
            this.context = context;
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        private void addFragment(BooksUserFragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

    private void checkUser() {
        //tài khoản hiện tại
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            //không login , quay lại màn hình chính
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}