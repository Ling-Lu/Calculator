package com.james.calculator;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.TypedValue;

public class CustomEditText extends AppCompatEditText {
    private Snackbar snackbar;

    protected boolean invalid;
    private float minTextSize;
    private float maxTextSize;

    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        maxTextSize = this.getTextSize();
        if (maxTextSize < 25) {
            maxTextSize = 30;
        }
        minTextSize = 20;
    }

    private void refitText(String text, int textWidth) {
        if (textWidth > 0) {
            int availableWidth = textWidth - this.getPaddingLeft()
                    - this.getPaddingRight();
            float trySize = maxTextSize;

            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
            while ((trySize > minTextSize)
                    && (this.getPaint().measureText(text) > availableWidth)) {
                trySize -= 1;
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }
                this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        }
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        refitText(text.toString(), this.getWidth());

        if (snackbar == null) {
            try {
                snackbar = Snackbar.make(this, "Invalid Equation", Snackbar.LENGTH_INDEFINITE);
            } catch (Exception e) {
            }
        }

        invalid = false;
        if (snackbar != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Calculator.contextCalc(getText().toString(), getContext()) ;
                    } catch(Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!snackbar.isShown()) {
                                    snackbar.show();
                                }
                                invalid = true;
                            }
                        });
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!invalid && snackbar != null && snackbar.isShown()) {
                                snackbar.dismiss();
                                try {
                                    snackbar = Snackbar.make(CustomEditText.this, "Invalid Equation", Snackbar.LENGTH_INDEFINITE);
                                } catch (Exception e) {
                                }
                            }
                        }
                    });
                }
                private Activity getActivity() {
                    Context context = getContext();
                    while (context instanceof ContextWrapper) {
                        if (context instanceof Activity) {
                            return (Activity)context;
                        }
                        context = ((ContextWrapper)context).getBaseContext();
                    }
                    return null;
                }
            }.start();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(this.getText().toString(), w);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        refitText(this.getText().toString(), parentWidth);
    }
}
