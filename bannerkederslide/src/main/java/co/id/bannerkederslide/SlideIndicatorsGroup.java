package co.id.bannerkederslide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import co.id.bannerkederslide.event.OnSlideChangeListener;
import co.id.bannerkederslide.indicators.*;

import java.util.ArrayList;
import java.util.List;

class SlideIndicatorsGroup extends LinearLayout implements OnSlideChangeListener {
    private static final String TAG = "SlideIndicatorsGroup";
    private final Context context;
    private int slidesCount;
    private Drawable selectedSlideIndicator;
    private Drawable unselectedSlideIndicator;
    private int unselectedColor;
    private int selectedColor;
    private int defaultIndicator;
    private int indicatorSize;
    private boolean mustAnimateIndicators = true;
    private int gravityIndicator;
    private List<IndicatorShape> indicatorShapes = new ArrayList<>();

    public SlideIndicatorsGroup(Context context, Drawable selectedSlideIndicator, Drawable unselectedSlideIndicator, int defaultIndicator, int indicatorSize, boolean mustAnimateIndicators, int gravityIndicator, int selectedColor, int unselectedColor) {
        super(context);
        this.context = context;
        this.selectedSlideIndicator = selectedSlideIndicator;
        this.unselectedSlideIndicator = unselectedSlideIndicator;
        this.defaultIndicator = defaultIndicator;
        this.indicatorSize = indicatorSize;
        this.gravityIndicator = gravityIndicator;
        this.mustAnimateIndicators = mustAnimateIndicators;
        this.selectedColor = ContextCompat.getColor(context, selectedColor);
        this.unselectedColor = ContextCompat.getColor(context, unselectedColor);
        setup();
    }

    public void setSlides(int slidesCount) {
        removeAllViews();
        indicatorShapes.clear();
        this.slidesCount = 0;
        for (int i = 0; i < slidesCount; i++) {
            onSlideAdd();
        }
        this.slidesCount = slidesCount;
    }

    public void onSlideAdd() {
        this.slidesCount += 1;
        addIndicator();
    }

    private void addIndicator() {
        IndicatorShape indicatorShape;
        if (selectedSlideIndicator != null && unselectedSlideIndicator != null) {
            indicatorShape = new IndicatorShape(context, indicatorSize, mustAnimateIndicators) {
                @Override
                public void onCheckedChange(boolean isChecked) {
                    super.onCheckedChange(isChecked);
                    if (isChecked) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            selectedSlideIndicator.setTint(selectedColor);
                        } else {
                            DrawableCompat.setTint(selectedSlideIndicator, selectedColor);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            setBackground(selectedSlideIndicator);
                        } else {
                            setBackgroundDrawable(selectedSlideIndicator);
                        }

                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            selectedSlideIndicator.setTint(unselectedColor);
                        } else {
                            DrawableCompat.setTint(selectedSlideIndicator, unselectedColor);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            setBackground(unselectedSlideIndicator);
                        } else {
                            setBackgroundDrawable(unselectedSlideIndicator);
                        }
                    }
                }
            };

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                selectedSlideIndicator.setTint(unselectedColor);
            } else {
                DrawableCompat.setTint(selectedSlideIndicator, unselectedColor);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                indicatorShape.setBackground(unselectedSlideIndicator);
            } else {
                indicatorShape.setBackgroundDrawable(unselectedSlideIndicator);
            }
            indicatorShapes.add(indicatorShape);
            addView(indicatorShape);

        } else {
            switch (defaultIndicator) {
                case IndicatorShape.SQUARE:
                    indicatorShape = new SquareIndicator(context, indicatorSize, mustAnimateIndicators);
                    indicatorShapes.add(indicatorShape);
                    addView(indicatorShape);
                    break;
                case IndicatorShape.ROUND_SQUARE:
                    indicatorShape = new RoundSquareIndicator(context, indicatorSize, mustAnimateIndicators);
                    indicatorShapes.add(indicatorShape);
                    addView(indicatorShape);
                    break;
                case IndicatorShape.DASH:
                    indicatorShape = new DashIndicator(context, indicatorSize, mustAnimateIndicators);
                    indicatorShapes.add(indicatorShape);
                    addView(indicatorShape);
                    break;

                case IndicatorShape.CIRCLE:
                    indicatorShape = new CircleIndicator(context, indicatorSize, mustAnimateIndicators);
                    indicatorShapes.add(indicatorShape);
                    addView(indicatorShape);
                    break;
                default:
                    break;
            }
        }
    }

    private int getIndicatorGravity() {
        switch (gravityIndicator) {
            case 1:
                return Gravity.BOTTOM | Gravity.END;
            case -1:
                return Gravity.START | Gravity.BOTTOM;
            default:
                return Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        }
    }

    public void setup() {
        setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = getIndicatorGravity();
        int margin = getResources().getDimensionPixelSize(R.dimen.default_indicator_margins) * 2;
        layoutParams.setMargins(isLeft(), 0, isRight(), margin);
        setLayoutParams(layoutParams);
    }

    private int isLeft() {
        if (gravityIndicator == -1) {
            return getResources().getDimensionPixelSize(R.dimen.default_indicator_margins) * 2;
        }
        return 0;
    }

    private int isRight() {
        if (gravityIndicator == 1) {
            return getResources().getDimensionPixelSize(R.dimen.default_indicator_margins) * 2;
        }
        return 0;
    }

    @Override
    public void onSlideChange(int selectedSlidePosition) {
        Log.i(TAG, "onSlideChange: " + selectedSlidePosition);
        for (int i = 0; i < indicatorShapes.size(); i++) {
            if (i == selectedSlidePosition) {
                indicatorShapes.get(i).onCheckedChange(true);
            } else {
                indicatorShapes.get(i).onCheckedChange(false);
            }
        }
    }

    public void setMustAnimateIndicators(boolean shouldAnimate) {
        this.mustAnimateIndicators = shouldAnimate;
        for (IndicatorShape indicatorShape :
                indicatorShapes) {
            indicatorShape.setMustAnimateChange(shouldAnimate);
        }
    }

}
