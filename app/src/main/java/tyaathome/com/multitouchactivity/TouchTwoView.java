package tyaathome.com.multitouchactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tyaathome on 2018/07/31.
 */
public class TouchTwoView extends View {

    private Bitmap bitmap;
    private static final int IMAGE_SIZE = (int) Utils.dp2px(200);
    private float offsetX, offsetY;
    private float downX, downY;
    private float preOffsetX, preOffsetY;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TouchTwoView(Context context) {
        super(context);
        init(context);
    }

    public TouchTwoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        bitmap = Utils.getAvatar(getResources(), IMAGE_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(offsetX, offsetY);
        canvas.translate((getWidth() - bitmap.getWidth()) / 2f, (getHeight() - bitmap.getHeight()) / 2f);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float sumX = 0, sumY = 0;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                preOffsetX = offsetX;
                preOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                sumX = 0;
                sumY = 0;
                for(int i = 0; i < event.getPointerCount(); i++) {
                    sumX += event.getX(i);
                    sumY += event.getY(i);
                }
                downX = sumX/(float)event.getPointerCount();
                downY = sumY/(float)event.getPointerCount();
                preOffsetX = offsetX;
                preOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                sumX = 0;
                sumY = 0;
                for(int i = 0; i < event.getPointerCount(); i++) {
                    sumX += event.getX(i);
                    sumY += event.getY(i);
                }
                float x = sumX/(float)event.getPointerCount();
                float y = sumY/(float)event.getPointerCount();
                offsetX = preOffsetX + x - downX;
                offsetY = preOffsetY + y - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                sumX = 0;
                sumY = 0;
                int count = event.getPointerCount()-1;
                for(int i = 0; i < event.getPointerCount(); i++) {
                    if(i == event.getActionIndex()) {
                        continue;
                    }
                    sumX += event.getX(i);
                    sumY += event.getY(i);
                }
                downX = sumX/(float)count;
                downY = sumY/(float)count;
                preOffsetX = offsetX;
                preOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_UP:
                preOffsetX = offsetX;
                preOffsetY = offsetY;
                downX = 0;
                downY = 0;
                break;
        }
        return true;
    }
}
