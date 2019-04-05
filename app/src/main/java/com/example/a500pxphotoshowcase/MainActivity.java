package com.example.a500pxphotoshowcase;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.a500pxphotoshowcase.Fragments.GalleryFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportFragmentManager().findFragmentByTag(GalleryFragment.TAG) == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new GalleryFragment(), GalleryFragment.TAG)
                    .commit();
        }


    }
}
