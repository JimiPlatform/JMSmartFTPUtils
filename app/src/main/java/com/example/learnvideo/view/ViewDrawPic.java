package com.example.learnvideo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.learnvideo.R;

public class ViewDrawPic extends View {
    private Canvas canvas;
    private Context context;

    public ViewDrawPic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.splash);

        Matrix matrix = new Matrix();
//        //缩放
        matrix.setScale(0.3f, 0.3f);
        canvas.drawBitmap(bitmap, matrix, null);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(80);
        canvas.drawText("欢迎来到音视频学习！", 100, 300, paint);
    }
}
