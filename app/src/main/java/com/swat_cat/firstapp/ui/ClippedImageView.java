package com.swat_cat.firstapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ClippedImageView extends AppCompatImageView {

    private Path path = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int bgColor;
    public ClippedImageView(Context context, int bgColor) {
        super(context);
        this.bgColor = bgColor;
        init();
    }

    public ClippedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClippedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private  void init(){
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo( 0.0f,getHeight()*0.6f);
        path.lineTo(0.0f,getHeight());
        path.lineTo(getWidth(),getHeight());
        path.lineTo(getWidth(),getHeight()*0.6f);
        path.quadTo(getWidth()/2.0f,getHeight()+100.0f,0.0f,getHeight()*0.6f);
        //path.cubicTo(getWidth()*0.5f,getHeight()/2f, getWidth()*0.3f,getHeight()+100f,0.0f,getHeight()*0.6f);
        canvas.drawPath(path,paint);
    }
}
