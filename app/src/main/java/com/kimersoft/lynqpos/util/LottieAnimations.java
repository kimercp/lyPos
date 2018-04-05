package com.kimersoft.lynqpos.util;

import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by Gadour on 07/12/2017.
 */

public class LottieAnimations {

    public void StartSignInLoading(LottieAnimationView animation, View bgdView){

        bgdView.setVisibility(View.VISIBLE);
        animation.playAnimation();

    }
    public void StoppingSignInLoading(LottieAnimationView animation, View bgdView){
        bgdView.setVisibility(View.GONE);
        animation.cancelAnimation();
    }
}
