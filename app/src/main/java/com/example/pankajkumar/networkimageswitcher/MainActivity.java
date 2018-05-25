package com.example.pankajkumar.networkimageswitcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;
import com.example.pankajkumar.networkimageswitcher.NetworkImageSwitcher.ImageStateListener;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private NetworkImageSwitcher mImageSwitcher;
    private Button mBtnNextImage;
    private Button mBtnPrevImage;
    private Animation mAnimSlideFromLeft;
    private Animation mAnimSlideToRight;
    private Animation mAnimSlideFromRight;
    private Animation mAnimSlideToLeft;

    private static final String[] IMAGE_URLS = {"https://tineye.com/images/widgets/mona.jpg",
            "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
            "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
            "https://homepages.cae.wisc.edu/~ece533/images/baboon.png",
            "https://homepages.cae.wisc.edu/~ece533/images/pool.png"};

    private static int switcherInc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView() {

        // Init views
        mProgressBar = findViewById(R.id.image_switcher_progress);
        mImageSwitcher = findViewById(R.id.image_switcher);
        mBtnNextImage = findViewById(R.id.btn_switch_to_next);
        mBtnPrevImage = findViewById(R.id.btn_switch_to_prev);

        mAnimSlideFromLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_from_left);
        mAnimSlideToRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_to_right);
        mAnimSlideFromRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_from_right);
        mAnimSlideToLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_to_left);

        // Init ImageSwitcher
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                AppCompatImageView imageView = new AppCompatImageView(MainActivity.this);
                return imageView;
            }
        });
    }

    private void initListener() {
        // Init listeners
        mBtnPrevImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mImageSwitcher.setInAnimation(mAnimSlideFromRight);
                mImageSwitcher.setOutAnimation(mAnimSlideToLeft);
                // mImageSwitcher.setImageResource(R.drawable.local_image);
                mImageSwitcher.setImage(IMAGE_URLS[switcherInc]);
                moveIndex(-1);
            }
        });

        mBtnNextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mImageSwitcher.setInAnimation(mAnimSlideFromLeft);
                mImageSwitcher.setOutAnimation(mAnimSlideToRight);
                // mImageSwitcher.setImageResource(R.drawable.local_image);
                mImageSwitcher.setImage(IMAGE_URLS[switcherInc]);
                moveIndex(1);
            }
        });

        mImageSwitcher.setImageStateListener(new ImageStateListener() {
            @Override
            public void onStartLoading() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopLoading() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void moveIndex(int toWhat) {
        switcherInc = switcherInc + toWhat;
        if (switcherInc < 0) {
            switcherInc = IMAGE_URLS.length - 1;
        } else if (switcherInc >= IMAGE_URLS.length) {
            switcherInc = 0;
        }
    }
}
