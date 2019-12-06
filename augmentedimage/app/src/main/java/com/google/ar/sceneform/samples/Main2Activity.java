package com.google.ar.sceneform.samples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.ar.sceneform.samples.augmentedimage.AugmentedImageActivity;
import com.google.ar.sceneform.samples.augmentedimage.R;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button = findViewById(R.id.start_button);
        button.setOnClickListener(v -> {
                Intent i = new Intent(getApplicationContext(), AugmentedImageActivity.class);
                startActivity(i);
        });



    }
}
