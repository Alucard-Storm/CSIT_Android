package com.storm.exp4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Experiment 5: Enhanced Drawing Graphics
 * This activity demonstrates:
 * 1. Custom View creation
 * 2. Drawing basic shapes (Rectangle, Circle, Line, Triangle, Text)
 * 3. Using Canvas and Paint with Anti-aliasing for better visuals
 */
public class Graphics extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView(this));
    }

    /**
     * Custom View class for drawing graphics
     */
    private class CustomView extends View {
        private Paint paint;

        public CustomView(Context context) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true); // Enable anti-aliasing for smooth edges
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Draw a red rectangle
            paint.setColor(Color.RED);
            canvas.drawRect(100, 100, 300, 200, paint);

            // Draw a blue circle
            paint.setColor(Color.BLUE);
            canvas.drawCircle(200, 350, 100, paint);

            // Draw a green line
            paint.setColor(Color.GREEN);
            paint.setStrokeWidth(5);
            canvas.drawLine(100, 500, 300, 500, paint);

            // Draw a yellow triangle
            paint.setColor(Color.YELLOW);
            Path path = new Path();
            path.moveTo(200, 600);
            path.lineTo(100, 750);
            path.lineTo(300, 750);
            path.close();
            canvas.drawPath(path, paint);

            // Draw text labels
            paint.setColor(Color.BLACK);
            paint.setTextSize(40);
            canvas.drawText("Rectangle", 320, 150, paint);
            canvas.drawText("Circle", 320, 370, paint);
            canvas.drawText("Line", 320, 510, paint);
            canvas.drawText("Triangle", 320, 700, paint);
        }
    }
}
