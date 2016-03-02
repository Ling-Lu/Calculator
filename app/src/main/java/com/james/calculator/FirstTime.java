package com.james.calculator;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.ViewPager;

import com.alexandrepiveteau.library.tutorial.CustomAction;
import com.alexandrepiveteau.library.tutorial.TutorialActivity;
import com.alexandrepiveteau.library.tutorial.TutorialFragment;
import com.example.james.calculator.R;


public class FirstTime extends TutorialActivity {

    private int[] BACKGROUND_COLORS = {Color.parseColor("#009688"), Color.parseColor("#0D47A1"), Color.parseColor("#212121"), Color.parseColor("#F44336"), Color.parseColor("#2196F3")};
    @Override
    public String getIgnoreText() {
        return "skip";
    }
    @Override
    public int getCount() {
        return 5;
    }
    @Override
    public int getBackgroundColor(int position) {
        return BACKGROUND_COLORS[position];
    }
    @Override
    public int getNavigationBarColor(int position) {
        return BACKGROUND_COLORS[position];
    }
    @Override
    public int getStatusBarColor(int position) {
        return BACKGROUND_COLORS[position];
    }
    @Override
    public TutorialFragment getTutorialFragmentFor(int position) {
        switch (position) {
            case 0:
                return new TutorialFragment.Builder()
                        .setTitle("Card Calculator")
                        .setDescription("This calculator attempts to improve on the standard design of android calculators.")
                        .setImageResource(R.mipmap.calc512)
                        .setImageResourceBackground(R.mipmap.ic_rect)
                        .build();
            case 1:
                return new TutorialFragment.Builder()
                        .setTitle("Quality Design")
                        .setDescription("The design of this calculator is based on Google\'s Material Design specifications.")
                        .setImageResource(R.mipmap.hicalculatorfore)
                        .setImageResourceBackground(R.mipmap.hicalculator)
                        .build();
            case 2:
                return new TutorialFragment.Builder()
                        .setTitle("User Interaction")
                        .setDescription("Each card is a seperate equation, and is designed to be easy to use and interact with.")
                        .setImageResource(R.mipmap.touch_fg)
                        .setImageResourceBackground(R.mipmap.touch_bg)
                        .build();
            case 3:
                return new TutorialFragment.Builder()
                        .setTitle("Future Updates")
                        .setDescription("The status of any future updates can be found in this community on google+.")
                        .setImageResource(R.mipmap.ic_update)
                        .setImageResourceBackground(R.mipmap.ic_white_rect)
                        .setCustomAction(new CustomAction.Builder(Uri.parse("https://plus.google.com/communities/104611531954046446240"))
                                .setIcon(R.mipmap.ic_action_social_people)
                                .build())
                        .build();
            case 4:
                return new TutorialFragment.Builder()
                        .setTitle("Feedback")
                        .setDescription("Any feedback about this app is greatly appreciated. Please take some time to rate it on the play store.")
                        .setImageResource(R.mipmap.rate_fg)
                        .setImageResourceBackground(R.mipmap.rate_bg)
                        .setCustomAction(new CustomAction.Builder(Uri.parse("https://play.google.com/store/apps/details?id=com.james.calculator"))
                                .setIcon(R.mipmap.ic_play_store)
                                .build())
                        .build();
            default:
                return new TutorialFragment.Builder()
                        .setTitle("")
                        .setDescription("")
                        .setImageResource(R.mipmap.ic_launcher, false)
                        .build();
        }
    }

    @Override
    public boolean isNavigationBarColored() {
        return true;
    }

    @Override
    public boolean isStatusBarColored() {
        return true;
    }

    @Override
    public ViewPager.PageTransformer getPageTransformer() {
        return TutorialFragment.getParallaxPageTransformer(2.5f);
    }
}
