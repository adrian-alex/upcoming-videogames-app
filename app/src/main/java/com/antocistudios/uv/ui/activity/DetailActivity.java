package com.antocistudios.uv.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.antocistudios.business.entity.VideoGame;
import com.antocistudios.moviecatalog.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adrian Antoci.
 *
 */
public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_IMAGE = "extraImage";
    private static final String EXTRA_TITLE = "extraTitle";
    private static final String EXTRA_DESCRIPTION = "extraDescription";

    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.toolBar) Toolbar mToolbar;
    @Bind(R.id.imageView) ImageView mImageView;
    @Bind(R.id.title) TextView mTextViewTitle;
    @Bind(R.id.description) TextView mTextViewDescription;


    public static void navigate(AppCompatActivity activity, View transitionImage, VideoGame viewModel) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, viewModel.getPosterURL());
        intent.putExtra(EXTRA_TITLE, viewModel.getTitle());
        intent.putExtra(EXTRA_DESCRIPTION, viewModel.getDescription());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @SuppressWarnings("ConstantConditions")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.detail_activity);

        ButterKnife.bind(this);

        setupViews();
    }

    private void setupViews() {
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);
        String itemDescription =  getIntent().getStringExtra(EXTRA_DESCRIPTION);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });

        mTextViewTitle.setText(itemTitle);
        mTextViewDescription.setText(itemDescription);
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        int primary = getResources().getColor(R.color.colorPrimary);
        int accent = getResources().getColor(R.color.colorAccent);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(accent));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primary));
        supportStartPostponedEnterTransition();
    }

}
