package com.example.idonava.androidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.idonava.androidproject.DownloadService.DownloadActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class DetailsFragment extends Fragment {
    static String EXTRA_ITEM_POSITION = "itemPosition";
    MovieModel movieModel;
    ImageView imageBig;
    ImageView imageSmall;
    TextView movieTitle;
    TextView date;
    TextView overviewText;
    Button trailerButton;
    ImageButton downloadButton;
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
    public void downloadOnClick(View view) {
        DownloadActivity.startActivity(getContext(), movieModel);
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
        downloadButton=rootView.findViewById(R.id.downloadButton);
        if (!TextUtils.isEmpty(movieModel.getPosterPath())) {
            imageBig.setScaleType(ImageView.ScaleType.CENTER_CROP);
        System.out.println("[ido] MENI : " + MovieModel.urlImagePath + movieModel.getPosterPath());
            RequestCreator a = Picasso.get()
                    .load(MovieModel.urlImagePath + movieModel.getPosterPath());
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
        downloadButton.setOnClickListener((v) -> downloadOnClick(v));

        return rootView;
    }

    public void openTrailer(View view) {
        String trailerUrl = movieModel.getTrailerUrl();
        System.out.println(trailerUrl);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(browserIntent);
    }
}
