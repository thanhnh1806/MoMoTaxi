package bhtech.com.cabbytaxi.RateDriver;

import android.app.Fragment;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SupportClass.CircleProgressBarView;
import bhtech.com.cabbytaxi.SupportClass.OnSwipeTouchListener;
import bhtech.com.cabbytaxi.object.PhoneObject;

public class RateTaxiView extends Fragment implements View.OnClickListener {
    private RateTaxiInterface.Delegate delegate;
    private RateTaxiInterface.Datasource datasource;

    public void setDelegate(RateTaxiInterface.Delegate delegate) {
        this.delegate = delegate;
    }

    public void setDatasource(RateTaxiInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    private float HD_SCREEN_WIDTH = 720;
    private float HD_SCREEN_HEIGHT = 1280;
    private double RADIAN_TO_DEGREES = 57.2957795;
    private float HALF_CIRCLE = 360 / 2;
    private float DESIGN_SCREEN_HEIGHT = 1136;
    private float START_Y_DESIGN_SCREEN_HEIGHT = 666;
    private float MAX_STOP_Y = 457;
    private float MIN_STOP_X = 77;
    private float MAX_STOP_X = 640;
    private float MIN_RATE = 15;
    private float MAX_RATE = 167;
    private float NEEDLE_WIDTH = 12;
    private float needleLength = 303;

    private ImageView drawLine;
    private TextView tvAddAsFavouriteDriver, tvAddAsFavouriteDriverGray, tvDone;
    private TextView tvCarNumber, tvDriverName;
    private LinearLayout btnShare;
    private FrameLayout layoutKim;
    private CircleProgressBarView progress;


    private float differentError = 0;
    private double startX, startY;
    private double stopX = 218;
    private double stopY = 309;
    private double angle = 59.39496;
    private float phoneScreenWidth, phoneScreenHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rate_taxi_view, container, false);
        tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
        tvCarNumber = (TextView) v.findViewById(R.id.tvCarNumber);
        tvAddAsFavouriteDriver = (TextView) v.findViewById(R.id.tvAddAsFavouriteDriver);
        tvAddAsFavouriteDriverGray = (TextView) v.findViewById(R.id.tvAddAsFavouriteDriverGray);
        tvDone = (TextView) v.findViewById(R.id.tvDone);
        btnShare = (LinearLayout) v.findViewById(R.id.btnShare);
        drawLine = (ImageView) v.findViewById(R.id.drawLineImageView);
        layoutKim = (FrameLayout) v.findViewById(R.id.layoutKim);

        tvAddAsFavouriteDriver.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        btnShare.setOnClickListener(this);

        progress = (CircleProgressBarView) v.findViewById(R.id.ProgressBar);
        progress.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        phoneScreenWidth = PhoneObject.getDefaultDisplayWidth(getActivity());
        phoneScreenHeight = PhoneObject.getDefaultDisplayHeight(getActivity());

        Log.w("PhoneScreenWidth", String.valueOf(phoneScreenWidth));
        Log.w("phoneScreenHeight", String.valueOf(phoneScreenHeight));

        NEEDLE_WIDTH = NEEDLE_WIDTH * (phoneScreenWidth / HD_SCREEN_WIDTH);
        MAX_STOP_Y = MAX_STOP_Y * (phoneScreenHeight / HD_SCREEN_HEIGHT);
        MIN_STOP_X = MIN_STOP_X * (phoneScreenWidth / HD_SCREEN_WIDTH);
        MAX_STOP_X = MAX_STOP_X * (phoneScreenWidth / HD_SCREEN_WIDTH);
        needleLength = needleLength * (phoneScreenHeight / HD_SCREEN_HEIGHT);

        startX = phoneScreenWidth / 2;
        startY = phoneScreenHeight - (START_Y_DESIGN_SCREEN_HEIGHT * (phoneScreenHeight / DESIGN_SCREEN_HEIGHT)) + 4 * NEEDLE_WIDTH;
        stopX = stopX * (phoneScreenWidth / HD_SCREEN_WIDTH);
        stopY = stopY * (phoneScreenWidth / HD_SCREEN_HEIGHT);

        calculateAngle();

        layoutKim.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                stopX = event.getX();
                stopY = event.getY();
                calculateAngle();
                return super.onTouch(v, event);
            }

            @Override
            public void onSwipeRight() {
            }

            @Override
            public void onSwipeLeft() {
            }

            @Override
            public void onSwipeTop() {
            }

            @Override
            public void onSwipeBottom() {
            }
        });
        return v;
    }

    private void calculateAngle() {
        Double doi = calculateDistance(stopX - stopX, stopY - startY);
        Double huyen = calculateDistance(stopX - startX, stopY - startY);

        Double ab = calculateDistance(startX, startY, stopX, startY);
        Double cd = (needleLength / huyen) * ab;

        double x;
        if (stopX < startX) {
            x = (startX - cd);
        } else {
            x = (startX + cd);
        }

        Double ef = calculateDistance(startX, startY, startX, stopY);
        Double gh = (needleLength / huyen) * ef;
        double y;
        if (stopY < startY) {
            y = (startY - gh);
        } else {
            y = (startY + gh);
        }
        stopX = x;
        stopY = y;

        if (stopY > MAX_STOP_Y) {
            if (stopY < startY) {
                stopY = MAX_STOP_Y;
                if (stopX < startX) {
                    stopX = MIN_STOP_X;
                    angle = MIN_RATE;
                    drawRate();
                } else {
                    stopX = MAX_STOP_X;
                    angle = MAX_RATE;
                    drawRate();
                }
            } else {
                //Do nothing
            }
        } else {
            if (stopX < startX) {
                angle = (Math.asin(doi / huyen) * RADIAN_TO_DEGREES);
                angle = angle - HALF_CIRCLE / angle - differentError;
            } else {
                angle = (Math.asin(doi / huyen) * RADIAN_TO_DEGREES);
                angle = HALF_CIRCLE - (angle - HALF_CIRCLE / angle - differentError);
            }

            drawRate();

            if (angle < 45) {
                datasource.setRateDriver(0);
            } else if (angle < 90) {
                datasource.setRateDriver(1);
            } else if (angle < 120) {
                datasource.setRateDriver(2);
            } else {
                datasource.setRateDriver(3);
            }
        }
    }

    private void drawRate() {
        progress.setClipping((float) angle);
        drawLine.setImageDrawable(new NeedleDrawable());
    }

    private Double calculateDistance(double toadoX, double toadoY) {
        return Math.sqrt(Math.pow(toadoX, 2) + Math.pow(toadoY, 2));
    }

    private Double calculateDistance(double startX, double startY, double stopX, double stopY) {
        return Math.sqrt(Math.pow(startX - stopX, 2) + Math.pow(startY - stopY, 2));
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.onCreateViewFinish();
    }

    public void reloadView() {
        tvDriverName.setText(datasource.getDriverName());
        tvCarNumber.setText(datasource.getCarNumber());
    }

    public void reloadViewOnButtonAddClick() {
        tvAddAsFavouriteDriver.setVisibility(View.GONE);
        tvAddAsFavouriteDriverGray.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddAsFavouriteDriver:
                delegate.onButtonAddAsFavouriteDriverClick();
                break;
            case R.id.btnShare:
                delegate.onButtonShareClick();
                break;
            case R.id.tvDone:
                delegate.onButtonDoneClick();
                break;
        }
    }

    private class NeedleDrawable extends Drawable {
        @Override
        public void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(1);

            Path path = new Path();
            for (int i = 0; i < HALF_CIRCLE * 2; i++) {
                canvas.drawLine((float) stopX, (float) stopY, (float) (startX + NEEDLE_WIDTH * Math.sin(i)),
                        (float) (startY - NEEDLE_WIDTH * Math.cos(i)), paint);
            }
            path.close();
        }

        @Override
        public int getOpacity() {
            return 0;
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        delegate = null;
        datasource = null;
    }
}
