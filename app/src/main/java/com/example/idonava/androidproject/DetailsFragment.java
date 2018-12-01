package com.example.idonava.androidproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailsFragment extends Fragment {
     static String EXTRA_ITEM_POSITION = "itemPosition";
    MovieModel movieModel;
    ImageView imageBig;
    ImageView imageSmall;
    TextView movieTitle;
    TextView date;
    TextView overviewText;

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
        imageBig.setImageResource(movieModel.getMainImage());
        imageSmall.setImageResource(movieModel.getMainImage());
        imageBig.setScaleType(ImageView.ScaleType.CENTER_CROP);
        movieTitle.setText(movieModel.getMovieTitle());
        date.setText(movieModel.getDate());
        overviewText.setText(movieModel.getOverview());
        return rootView;
    }
}
