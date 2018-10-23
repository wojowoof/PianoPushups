package com.wojones.pianopushups;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

class SquareView extends TextView {
    private static final String LOGTAG = "SquareView";
    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareView(Context context, AttributeSet attrs, int defstyle) {
        super(context, attrs, defstyle);
    }

    public void onMeasure(int wMsrSpec, int hMsrSpec) {
        super.onMeasure(wMsrSpec, hMsrSpec);
        Log.v(LOGTAG, "onMsr [View name]: " + wMsrSpec + ", " + hMsrSpec + " (" + getMeasuredWidth()
                + ", " + getMeasuredHeight() + ")");
        Log.v("[View name] " + LOGTAG, "specs [View name]: w=" + MeasureSpec.toString(wMsrSpec) + ", h=" +
                MeasureSpec.toString(hMsrSpec));
        int sz = Math.min(Math.max(getMeasuredWidth(), wMsrSpec), Math.max(getMeasuredHeight(), hMsrSpec));
        Log.v(LOGTAG, "chuz [View name]:" + sz);
        setMeasuredDimension(sz, sz);
    }
}
