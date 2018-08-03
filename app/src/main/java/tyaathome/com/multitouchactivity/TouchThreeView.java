package tyaathome.com.multitouchactivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyaathome on 2018/07/31.
 */
public class TouchThreeView extends View {

    private List<Path> pathList = new ArrayList<>();
    private List<Path> finishedPathList = new ArrayList<>();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int currentPointIndex = 0;

    public TouchThreeView(Context context) {
        super(context);
        init(context);
    }

    public TouchThreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Path path : pathList) {
            canvas.drawPath(path, paint);
        }
        for(Path path : finishedPathList) {
            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                Path path = new Path();
                path.moveTo(event.getX(), event.getY());
                pathList.add(path);
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                currentPointIndex++;
                Path path = new Path();
                // 当down的index是之前抬起的那个位置则将添加至之前抬起的index位置
                if(currentPointIndex != event.getActionIndex()) {
                    path.moveTo(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
                    pathList.add(event.getActionIndex(), path);
                } else {
                    path.moveTo(event.getX(currentPointIndex), event.getY(currentPointIndex));
                    pathList.add(currentPointIndex, path);
                }
                invalidate();
                Log.e("TouchThreeView", "DOWN: " + pathList.size());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.e("TouchThreeView", "MOVE: " + event.getActionIndex());
                for (int i = 0; i < event.getPointerCount(); i++) {
                    pathList.get(i).lineTo(event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                currentPointIndex--;
                int index = event.getActionIndex();
                if(pathList.size() > index) {
                    finishedPathList.add(pathList.remove(index));
                }
                Log.e("TouchThreeView", "UP: " + pathList.size());
                break;
            case MotionEvent.ACTION_UP:
                if(pathList.size() > 0) {
                    finishedPathList.add(pathList.remove(0));
                }
                pathList.clear();
                break;
        }
        return true;
    }
}
