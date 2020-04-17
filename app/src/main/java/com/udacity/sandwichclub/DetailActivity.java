package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    ImageView ingredientsIv = null;
    TextView originTv = null;
    TextView descriptionTv = null;
    TextView ingredientsTv = null;
    TextView alsoKnownAsTv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);

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
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // check if the value received on Json is not empty like a "" string
        if(!sandwich.getMainName().isEmpty()) {
            setTitle(sandwich.getMainName());
        } else {
            setTitle(getString(R.string.sandwich));
        }

        ingredientsIv.setVisibility(View.VISIBLE);
        Picasso.with(this)
            .load(sandwich.getImage())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(ingredientsIv);

        if(!sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTv.setText(convertListToCommaString(sandwich.getAlsoKnownAs()));
        } else {
            alsoKnownAsTv.setText(R.string.unknown);
        }

        if(!sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            originTv.setText(R.string.unknown);
        }

        if(!sandwich.getDescription().isEmpty()) {
            descriptionTv.setText(sandwich.getDescription());
        } else {
            descriptionTv.setText(R.string.unknown);
        }

        if(!sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setText(convertListToCommaString(sandwich.getIngredients()));
        } else {
            ingredientsTv.setText(R.string.unknown);
        }
    }

    private String convertListToCommaString(List<String> stringList) {
        StringBuilder csvBuilder = new StringBuilder();
        String lastItem = stringList.get(stringList.size() - 1);
        for(String item : stringList){
            if(stringList.size() > 1 && !item.equals(lastItem)) {
                csvBuilder.append(item);
                csvBuilder.append(", ");
            } else {
                csvBuilder.append(item);
            }
        }

        return csvBuilder.toString();
    }
}
