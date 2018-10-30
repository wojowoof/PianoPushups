package com.wojones.pianopushups;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class RhythmPractice extends AppCompatActivity {
    private static String LOGTAG = "rhythmz";

    private static String[] rhythms = new String[] {
            "img_quarter_notes", "img_half_notes", "img_whole_note",
            "img_qqqrq_notes", "img_qqeeq_notes", "img_qqh_notes",
    };
    private static int curMeasure = -1;

    private static int animMSecs = 300;
    private static int smallWeight = 1;
    private static int bigWeight = 6;
    private static int measure_count = 5;
    private static int[] colorz = new int[] {
            Color.RED, Color.GREEN, Color.BLUE, R.color.colorAccent, R.color.colorPrimary,
    };

    // For now: hardcode rhythms, and make only three
    private static String[] measures = new String[] {
            rhythms[0], rhythms[2], rhythms[4], rhythms[5], rhythms[3],
    };
//    private static int[] measure_sizes = new int[] {
//            bigWeight, bigWeight, smallWeight, smallWeight, smallWeight,
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhythm_practice);

        LinearLayout ll = findViewById(R.id.MeasuresLL);
        ll.removeAllViewsInLayout();

        for (int i = 0; i < measure_count; i++) {
            addChildMeasureView(rhythms[i % rhythms.length]);
        }

        layoutMeasures();
    }

    private void addChildMeasureView(String measureImage) {
        LinearLayout ll = findViewById(R.id.MeasuresLL);
        ImageView iv = new ImageView(this);
        iv.setImageResource(getResources().getIdentifier(measureImage, "drawable", getPackageName()));
        LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(0, WRAP_CONTENT);
        lllp.weight = smallWeight;

        ll.addView(iv, lllp);
    }

    private void layoutMeasures() {
        LinearLayout ll = findViewById(R.id.MeasuresLL);

        int nkids = ll.getChildCount();
        if (5 != nkids) {
            Log.w(LOGTAG, "Incorrect child count: " + nkids);
            return;
        }

        for (int i = 0; i < nkids; i++) {
            View v = ll.getChildAt(i);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, WRAP_CONTENT);
            llp.weight = smallWeight;
            Log.i(LOGTAG, "weightfor " + v + ": " + llp.weight);
            v.setLayoutParams(llp);
            v.setBackgroundColor(colorz[i % colorz.length]);
        }
        curMeasure = -1;
        ll.requestLayout();
    }

    private void nextBtnEnabled(boolean torf) {
        Button btn = findViewById(R.id.nextBtn);
        btn.setEnabled(torf);
    }

    private void animateMeasureChanges() {
        final String LOGTAG = "animateMeasures";

        if (curMeasure >= measures.length) {
            Log.w(LOGTAG, "Song is done! measure=" + curMeasure);
            return;
        }
        final LinearLayout ll = findViewById(R.id.MeasuresLL);

        List<Integer> shrinkList = new ArrayList<>();
        List<Integer> growList = new ArrayList<>();

        if (0 > curMeasure) {
            growList.add(0);
            growList.add(1);
        } else {
            shrinkList.add(curMeasure);
            if (curMeasure + 2 < measures.length) {
                growList.add(curMeasure + 2);
            }
        }

        final List<Integer>shrinks = shrinkList;
        final List<Integer>grows = growList;

        ValueAnimator vanim = ValueAnimator.ofFloat(1.0f, 8.0f);
        vanim.setDuration(animMSecs);
        vanim.setInterpolator(new LinearInterpolator());
        vanim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (int i : shrinks) {
                    Log.i(LOGTAG, "shrink " + i);
                    View v1 = ll.getChildAt(i);
                    ((LinearLayout.LayoutParams) v1.getLayoutParams()).weight = 9.0f - (float) animation.getAnimatedValue();
                }
                for (int i : grows) {
                    Log.i(LOGTAG, "Grow " + i);
                    View v2 = ll.getChildAt(i);
                    ((LinearLayout.LayoutParams) v2.getLayoutParams()).weight = 1.0f + (float) animation.getAnimatedValue();
                }
                ll.requestLayout();
            }
        });
        vanim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.i(LOGTAG, "Set current measure to " + (curMeasure + 1));
                curMeasure++;
                /* If song is done, don't enable "next" */
                if (curMeasure < measures.length) {
                    nextBtnEnabled(true);
                }
            }
        });
        Log.i(LOGTAG, "start animation!");
        nextBtnEnabled(false);
        vanim.start();
        Log.i(LOGTAG, "Animating!");
    }

    public void nextMeasure(View vw) {
        animateMeasureChanges();
    }

    public void resetMeasures(View vw) {
        curMeasure = 0;
        layoutMeasures();
        nextBtnEnabled(true);
    }
}
