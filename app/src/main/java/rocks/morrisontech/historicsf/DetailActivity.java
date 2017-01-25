package rocks.morrisontech.historicsf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import rocks.morrisontech.historicsf.entity.LandmarkEntity;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView designationLinkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        LandmarkEntity landmarkEntity = (LandmarkEntity) getIntent().getSerializableExtra("MyClass");

        TextView buildingNameTextView = (TextView) findViewById(R.id.buildingNameTextView);
        buildingNameTextView.setText(landmarkEntity.getName());

        ImageView thumbnailImageView = (ImageView) findViewById(R.id.buildingImageView);
        String imageWebpageUrl = landmarkEntity.getThumbnail();

        TextView yearBuiltTextView = (TextView) findViewById(R.id.year_built);
        yearBuiltTextView.setText(landmarkEntity.getYear_built());

        TextView architectTextView = (TextView) findViewById(R.id.architect_textView);
        architectTextView.setText(landmarkEntity.getArchitect());

        designationLinkTextView = (TextView) findViewById(R.id.designationLinkTextView);
        designationLinkTextView.setText(landmarkEntity.getDesignatio());
        designationLinkTextView.setOnClickListener(this);

        // set intent to open browser with link



        // parse image url from String of HTML using Jsoup
        String imageSourceUrl = JsoupHelper.getImageSourceUrl(imageWebpageUrl);

        // tell user image could not be downloaded
        if (imageSourceUrl == null) {
            Toast.makeText(this, "Could not download image", Toast.LENGTH_SHORT).show();
        }

        // todo: create high resolution images option to download full-size images
        Picasso.with(getApplicationContext())
                .load(imageSourceUrl)
                .resize(300, 300)
                .centerInside()
                .placeholder(R.drawable.ic_photo_black_48px)
                .into(thumbnailImageView);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(designationLinkTextView.getText())));
        startActivity(intent);
    }
}