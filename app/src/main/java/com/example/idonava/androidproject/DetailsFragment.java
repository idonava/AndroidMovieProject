package com.example.idonava.androidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.w3c.dom.Text;

public class DetailsFragment extends Fragment {
    static String EXTRA_ITEM_POSITION = "itemPosition";
    MovieModel movieModel;
    ImageView imageBig;
    ImageView imageSmall;
    TextView movieTitle;
    TextView date;
    TextView overviewText;
    Button trailerButton;

    public static DetailsFragment newInstance(int position) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ITEM_POSITION, position);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int itemPosition = getArguments().getInt(EXTRA_ITEM_POSITION);
            movieModel = MovieContent.MOVIES.get(itemPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.details_fragment, container, false);


        imageBig = rootView.findViewById(R.id.mainImage);
        imageSmall = rootView.findViewById(R.id.backgroundImage);
        movieTitle = rootView.findViewById(R.id.movieTitle);
        date = rootView.findViewById(R.id.date);
        overviewText = rootView.findViewById(R.id.overviewText);
        trailerButton = rootView.findViewById(R.id.trailerButton);
//        imageBig.setImageResource(movieModel.getMainImage());
//        imageSmall.setImageResource(movieModel.getMainImage());
//        imageBig.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (!TextUtils.isEmpty(movieModel.getPosterPath())) {
            imageBig.setScaleType(ImageView.ScaleType.CENTER_CROP);
            RequestCreator a = Picasso.get()
                    .load(movieModel.getPosterPath());
            a.into(imageSmall);
            a.into(imageBig);

        }
        movieTitle.setText(movieModel.getMovieTitle());
        date.setText(movieModel.getDate());
        overviewText.setText(movieModel.getOverview());

        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrailer(v);
            }
        });
        return rootView;
    }

    public void openTrailer(View view) {
        String trailerUrl = movieModel.getTrailerUrl();
        System.out.println(trailerUrl);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(browserIntent);
    }
}
