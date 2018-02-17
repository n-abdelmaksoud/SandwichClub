package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView alsoKnowAsTV, descriptionTV, ingredientsTV, placeOfOriginTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnowAsTV=findViewById(R.id.also_known_tv);
        descriptionTV=findViewById(R.id.description_tv);
        ingredientsTV=findViewById(R.id.ingredients_tv);
        placeOfOriginTV=findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> alsoKnownAsList= sandwich.getAlsoKnownAs();
        if(alsoKnownAsList.size()>0){
            StringBuilder builder= new StringBuilder();
            for(int i=0;i<alsoKnownAsList.size();i++){
               builder.append(alsoKnownAsList.get(i));
               builder.append(",");
            }
            int i= builder.length();
            builder.deleteCharAt(i-1);
            builder.append(".");
            alsoKnowAsTV.setText(builder.toString());
        } else {
            alsoKnowAsTV.setText(getString(R.string.unknown));
        }

        String description =sandwich.getDescription();
        if(description!=null&& !TextUtils.isEmpty(description)){
            descriptionTV.setText(description);
        }else{
            descriptionTV.setText(R.string.unknown);
        }

        List<String> ingredientsList= sandwich.getIngredients();
        if(ingredientsList.size()>0){
            StringBuilder builder=new StringBuilder();
            for(int i=0;i<ingredientsList.size();i++){
                builder.append(ingredientsList.get(i));
                builder.append(",");
            }
           int i= builder.length();
            builder.deleteCharAt(i-1);
            builder.append(".");
            ingredientsTV.setText(builder.toString());
        } else {
            ingredientsTV.setText(getString(R.string.unknown));
        }

        String origin=sandwich.getPlaceOfOrigin();
        if(origin!=null&& TextUtils.isEmpty(origin)) {
            placeOfOriginTV.setText(origin);
        }else {
            placeOfOriginTV.setText(getString(R.string.unknown));
        }

    }
}
