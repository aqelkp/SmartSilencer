package in.aqel.smartsilencer;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.aqel.smartsilencer.Services.MotionSensorService;
import in.aqel.smartsilencer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        setContentView(R.layout.activity_main);
        User user = new User("aqel", "ahammad");
        binding.setUser(user);
        binding.textView.setTextColor(Color.BLUE);
        user.lastName = "I am changed";

        Intent intent = new Intent(this, MotionSensorService.class);
        startService(intent);
    }
}
