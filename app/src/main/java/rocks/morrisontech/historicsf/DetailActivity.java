package rocks.morrisontech.historicsf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import rocks.morrisontech.historicsf.entity.LandmarkEntity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        LandmarkEntity landmarkEntity = (LandmarkEntity) getIntent().getSerializableExtra("MyClass");

        TextView buildingNameTextView = (TextView) findViewById(R.id.buildingNameTextView);
        buildingNameTextView.setText(landmarkEntity.getName());

        ImageView thumbnailImageView = (ImageView) findViewById(R.id.buildingImageView);
        String imageWebpageUrl = landmarkEntity.getThumbnail();

        // parse image url from String of HTML using Jsoup
        String imageSourceUrl = JsoupHelper.getImageSourceUrl(imageWebpageUrl);

        // tell user image could not be downloaded
        if (imageSourceUrl == null) {
            thumbnailImageView.setImageResource(R.drawable.ic_photo_black_48px);
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

}