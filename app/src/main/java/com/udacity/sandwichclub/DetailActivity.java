package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

    private void populateUI(Sandwich sw) {

        /** Also Known As TextView */
        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAsList = sw.getAlsoKnownAs();
        if (alsoKnownAsList.size() != 0) {
            String aka = alsoKnownAsList.toString();
            String trimmedAka = aka.substring(1, aka.length() - 1);
            alsoKnownAsTv.setText(trimmedAka);
        } else {
            alsoKnownAsTv.setText(R.string.detail_error_message);
        }

        /** Ingredient TextView */
        TextView ingredientTv = findViewById(R.id.ingredients_tv);
        List<String> ingredientList = sw.getIngredients();
        if (ingredientList.size() != 0) {
            String ingre = ingredientList.toString();
            String trimmedIngre = ingre.substring(1, ingre.length() - 1);
            ingredientTv.setText(trimmedIngre);
        } else {
            ingredientTv.setText(R.string.detail_error_message);
        }

        /** Place of origin TextView */
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        String origin = sw.getPlaceOfOrigin();
        if (origin.length() != 0) {
            placeOfOriginTv.setText(origin);
        } else {
            placeOfOriginTv.setText("Unknown");
        }

        /** Description TextView */
        TextView descriptionTv = findViewById(R.id.description_tv);
        String description = sw.getDescription();
        descriptionTv.setText(description);

    }
}
