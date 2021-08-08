package com.example.skaitas;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout expandableview,expandableview2,expandableview3;
    Button arrowbtn,arrowbtn2,arrowbtn3;
    CardView cardview,cardview2,cardview3;
    SliderView sliderView;
    int[] images = {R.drawable.slider1,R.drawable.slider2,R.drawable.slider3};

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        sliderView = findViewById(R.id.image_slider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        expandableview = findViewById(R.id.expandableView);
        arrowbtn = findViewById(R.id.arrowbtn);
        cardview = findViewById(R.id.cardView);
        arrowbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(expandableview.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardview, new AutoTransition());
                    expandableview.setVisibility(View.VISIBLE);
                    arrowbtn.setBackgroundResource(R.drawable.uparrow);
                }else{
                    TransitionManager.beginDelayedTransition(cardview, new AutoTransition());
                    expandableview.setVisibility(View.GONE);
                    arrowbtn.setBackgroundResource(R.drawable.downarrow);
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_Services:
                        startActivity(new Intent(getApplicationContext(),Services.class) );
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_Team:
                        startActivity(new Intent(getApplicationContext(),Team.class) );
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_Career:
                        startActivity(new Intent(getApplicationContext(),Career.class) );
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        expandableview2 = findViewById(R.id.expandableView2);
        arrowbtn2 = findViewById(R.id.arrowbtn2);
        cardview2 = findViewById(R.id.cardView2);
        arrowbtn2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(expandableview2.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardview2, new AutoTransition());
                    expandableview2.setVisibility(View.VISIBLE);
                    arrowbtn2.setBackgroundResource(R.drawable.uparrow);
                }else{
                    TransitionManager.beginDelayedTransition(cardview2, new AutoTransition());
                    expandableview2.setVisibility(View.GONE);
                    arrowbtn2.setBackgroundResource(R.drawable.downarrow);
                }
            }
        });

        expandableview3 = findViewById(R.id.expandableView3);
        arrowbtn3 = findViewById(R.id.arrowbtn3);
        cardview3 = findViewById(R.id.cardView3);
        arrowbtn3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(expandableview3.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardview3, new AutoTransition());
                    expandableview3.setVisibility(View.VISIBLE);
                    arrowbtn3.setBackgroundResource(R.drawable.uparrow);
                }else{
                    TransitionManager.beginDelayedTransition(cardview3, new AutoTransition());
                    expandableview3.setVisibility(View.GONE);
                    arrowbtn3.setBackgroundResource(R.drawable.downarrow);
                }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.abt:
                Intent intent4 = new Intent(this, About.class);
                this.startActivity(intent4);
                return true;
            case R.id.contact:
                Intent intent1 = new Intent(this, Contactus.class);
                this.startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}