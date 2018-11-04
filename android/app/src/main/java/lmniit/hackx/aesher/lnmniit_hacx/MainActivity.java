package lmniit.hackx.aesher.lnmniit_hacx;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import lmniit.hackx.aesher.lnmniit_hacx.adapters.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MotherActivity";
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
            doLogin();


        ButterKnife.bind(this);

        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        MainPagerAdapter readerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(readerAdapter);
        viewPager.setCurrentItem(0);

        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));

    }


    void doLogin(){
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.mipmap.ic_launcher_round)
                        .setTheme(R.style.LoginTheme)
                        .build(),
                0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));

            myRef.setValue("1");
            Log.w(TAG,"called");

        }
    }
}
