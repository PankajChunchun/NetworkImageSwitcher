package com.example.pankajkumar.networkimageswitcher;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageSwitcher;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Pankaj Kumar on 25/05/18.
 * pankaj@kloojj.com
 * EAT | DRINK | CODE
 */
public class NetworkImageSwitcher extends ImageSwitcher {

    public interface ImageStateListener {

        void onStartLoading();

        void onStopLoading();
    }

    private ImageStateListener mImageStateListener;

    public NetworkImageSwitcher(Context context) {
        super(context);
    }

    public NetworkImageSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setImage(String fromUrl) {

        if (mImageStateListener != null) {
            mImageStateListener.onStartLoading();
        }

        AppCompatImageView image = (AppCompatImageView) this.getNextView();
        Glide.with(getContext().getApplicationContext())
                .load(fromUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable final GlideException e, final Object model,
                            final Target<Drawable> target, final boolean isFirstResource) {

                        if (mImageStateListener != null) {
                            mImageStateListener.onStopLoading();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(final Drawable resource, final Object model,
                            final Target<Drawable> target, final DataSource dataSource,
                            final boolean isFirstResource) {

                        if (mImageStateListener != null) {
                            mImageStateListener.onStopLoading();
                        }
                        return false;
                    }
                })
                .into(image);

        showNext();
    }

    public void setImageStateListener(
            final ImageStateListener imageStateListener) {
        mImageStateListener = imageStateListener;
    }
}