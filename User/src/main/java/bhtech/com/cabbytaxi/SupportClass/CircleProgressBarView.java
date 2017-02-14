package bhtech.com.cabbytaxi.SupportClass;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import bhtech.com.cabbytaxi.R;

/**
 * Created by thanh_nguyen02 on 31/12/2015.
 */
public class CircleProgressBarView extends View {
    private Path mClippingPath;
    private Context mContext;
    private Bitmap mBitmap;
    private float mPivotX;
    private float mPivotY;

    public CircleProgressBarView(Context context) {
        super(context);
        mContext = context;
        initilizeImage();
    }

    public CircleProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initilizeImage();
    }

    private void initilizeImage() {
        mClippingPath = new Path();

        //Top left coordinates of image. Give appropriate values depending on the position you wnat image to be placed
        mPivotX = getScreenGridUnit();
        mPivotY = 0;

        //Adjust the image size to support different screen sizes
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.circle_progress_bar_orange);
        int imageWidth = (int) (getScreenGridUnit() * 20);
        int imageHeight = (int) (getScreenGridUnit() * 20);
        mBitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false);
    }

    public void setClipping(float progress) {
        float angle = (progress);
        mClippingPath.reset();
        //Define a rectangle containing the image
        RectF oval = new RectF(mPivotX, mPivotY, mPivotX + mBitmap.getWidth(), mPivotY + mBitmap.getHeight());
        //Move the current position to center of rect
        mClippingPath.moveTo(oval.centerX(), oval.centerY());
        //Draw an arc from center to given angle
        mClippingPath.addArc(oval, 180, angle);
        //Draw a line from end of arc to center
        mClippingPath.lineTo(oval.centerX(), oval.centerY());
        //Redraw the canvas
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Clip the canvas
        canvas.clipPath(mClippingPath);
        canvas.drawBitmap(mBitmap, mPivotX, mPivotY, null);

    }

    private float getScreenGridUnit() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels / 30;
    }
}
