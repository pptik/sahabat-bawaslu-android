package id.pptik.ilham.sahabatbawaslu.features.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ilham on 27/02/18.
 */

public class RectangleView extends View{
    public RectangleView(Context context) {
        super(context);

        init(null);
    }

    public RectangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RectangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public RectangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        /*paint.setShadowLayer(12, 0, 0, Color.DKGRAY);
        paint.setShader(new LinearGradient(0, getHeight()*10/100, 0, getHeight(), Color.parseColor("#FAD961"), Color.parseColor("#FD7E16"), Shader.TileMode.MIRROR));*/
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(30, 50, 200, 350, paint);
        //canvas.drawCircle(getWidth()/2,0,getWidth()*70/100,paint);
        setLayerType(LAYER_TYPE_SOFTWARE, paint);

    }
}
