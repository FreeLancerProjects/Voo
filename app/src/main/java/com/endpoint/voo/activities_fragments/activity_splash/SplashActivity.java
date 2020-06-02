package com.endpoint.voo.activities_fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.endpoint.voo.R;
import com.endpoint.voo.activities_fragments.activity_home.client_home.activity.ClientHomeActivity;
import com.endpoint.voo.activities_fragments.activity_sign_in.activity.SignInActivity;
import com.endpoint.voo.activities_fragments.intro_slider.MainScreen;
import com.endpoint.voo.language.Language_Helper;
import com.endpoint.voo.models.UserModel;
import com.endpoint.voo.preferences.Preferences;
import com.endpoint.voo.singletone.UserSingleTone;
import com.endpoint.voo.tags.Tags;


public class SplashActivity extends AppCompatActivity {

    private FrameLayout fl;
    private ImageView logo,ic_splash;
    private Preferences preferences;

    private String current_lang;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language_Helper.updateResources(base,Language_Helper.getLanguage(base)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread myThread = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(1200);
                    Intent intent = new Intent(getApplicationContext(), MainScreen.class);

                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();



        preferences = Preferences.getInstance();
        logo = findViewById(R.id.imgLogo);
        ic_splash = findViewById(R.id.imgSplash);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade);
        logo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                String session = preferences.getSession(SplashActivity.this);

                if (session.equals(Tags.session_login))
                {
                    UserModel userModel = preferences.getUserData(SplashActivity.this);
                    UserSingleTone userSingleTone = UserSingleTone.getInstance();
                    userSingleTone.setUserModel(userModel);

                    Intent intent = new Intent(SplashActivity.this, ClientHomeActivity.class);
                    startActivity(intent);
                    finish();
                }else
                    {
                        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.translate);
        ic_splash.startAnimation(animation2);
    }
}
