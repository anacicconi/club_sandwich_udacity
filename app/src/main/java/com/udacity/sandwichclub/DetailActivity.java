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
    TextView originTvLabel = null;
    TextView descriptionTv = null;
    TextView descriptionTvLabel = null;
    TextView ingredientsTv = null;
    TextView ingredientsTvLabel = null;
    TextView alsoKnownAsTvLabel = null;
    TextView alsoKnownAsTv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        originTvLabel = findViewById(R.id.origin_tv_label);
        descriptionTv = findViewById(R.id.description_tv);
        descriptionTvLabel = findViewById(R.id.description_tv_label);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        ingredientsTvLabel = findViewById(R.id.ingredients_tv_label);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        alsoKnownAsTvLabel = findViewById(R.id.also_known_tv_label);

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
        setTitle(sandwich.getMainName());

        if(!sandwich.getImage().isEmpty()) {
            ingredientsIv.setVisibility(View.VISIBLE);
            Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        }

        if(!sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTvLabel.setVisibility(View.VISIBLE);
            alsoKnownAsTv.setVisibility(View.VISIBLE);
            alsoKnownAsTv.setText(convertListToCommaString(sandwich.getAlsoKnownAs()));
        }

        if(!sandwich.getPlaceOfOrigin().isEmpty()) {
            originTvLabel.setVisibility(View.VISIBLE);
            originTv.setVisibility(View.VISIBLE);
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if(!sandwich.getDescription().isEmpty()) {
            descriptionTvLabel.setVisibility(View.VISIBLE);
            descriptionTv.setVisibility(View.VISIBLE);
            descriptionTv.setText(sandwich.getDescription());
        }

        if(!sandwich.getIngredients().isEmpty()) {
            ingredientsTvLabel.setVisibility(View.VISIBLE);
            ingredientsTv.setVisibility(View.VISIBLE);
            ingredientsTv.setText(convertListToCommaString(sandwich.getIngredients()));
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
