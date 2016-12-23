package com.wpf.bookreader.Widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.wpf.bookreader.DataInfo.ViewInfo;

/**
 * Created by 王朋飞 on 11-25-0025.
 * 小说View
 */

public class PageView extends View implements
        View.OnClickListener {

    private ViewInfo viewInfo = new ViewInfo();
    private String title = "";
    private String text = "";
    private String oldFontName = "";
    private int width_battery,height_battery;

    private Canvas canvas;
    private TextPaint paintContent = new TextPaint();
    private TextPaint paintTitle = new TextPaint();
    private TextPaint paintTime = new TextPaint();
    private Paint paintBatteryStroke = new Paint();
    private Paint paintBattery = new Paint();
    private Path pathStroke = new Path();
    private Rect rect = new Rect();

    private OnClickListener onClickListener;

    public PageView(Context context) {
        this(context,null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
        init();
    }

    private void init() {
        paintContent.setDither(true);
        paintContent.setStyle(Paint.Style.FILL_AND_STROKE);
        paintContent.setAntiAlias(true);
        paintContent.setSubpixelText(true);

        paintTitle.setStyle(Paint.Style.FILL_AND_STROKE);
        paintTitle.setAntiAlias(true);
        paintTitle.setTextSize(42);

        paintTime.setStyle(Paint.Style.FILL_AND_STROKE);
        paintTime.setTextSize(42);
        paintTime.setAntiAlias(true);

        paintBatteryStroke.setAntiAlias(true);
        paintBatteryStroke.setStrokeWidth(3);
        paintBatteryStroke.setStyle(Paint.Style.STROKE);

        paintBattery.setAntiAlias(true);
        paintBattery.setStrokeWidth(1);
        paintBattery.setStyle(Paint.Style.FILL);

        width_battery = 50;
        height_battery = (int) getTextHeight(paintTime)+4;

    }

    private void reInit() {
        paintContent.setTextSize(viewInfo.getTextSize());
        paintContent.setColor(viewInfo.getTextColor());
        if(!oldFontName.equals(viewInfo.getTypefaceName()))
            paintContent.setTypeface("系统默认".equals(viewInfo.getTypefaceName())?Typeface.DEFAULT:
                Typeface.createFromAsset(getContext().getAssets(), viewInfo.getTypefaceName()));
        oldFontName = viewInfo.getTypefaceName();

        paintTitle.setColor(viewInfo.getTextColor());

        paintTime.setColor(viewInfo.getTextColor());

        paintBattery.setColor(viewInfo.getTextColor());

        paintBatteryStroke.setColor(viewInfo.getTextColor());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        drawTitle();
        drawBattery();
        drawTime();
        drawText(text);
    }

    private void drawText(String text) {
        if(text.isEmpty()) return;
        String[] strings = text.split("\n");
        int i = 1;
        for(String string : strings) {
            if(string.isEmpty()) i++;
            int start = 0,len = 1;
            while (start + len <= string.length()) {
                len = getLineLen(string,start);
                canvas.drawText(string, start, start += len, viewInfo.getPaddingLeft(),
                        viewInfo.getPaddingTop() + (i++) * (getTextHeight(paintContent)+viewInfo.getSpacingVertical()), paintContent);
                len = 1;
            }
        }
    }

    private void drawTitle() {
        canvas.drawText(title,viewInfo.getPaddingLeft(),
                viewInfo.getPaddingTop()/2 + getTextHeight(paintTitle),paintTitle);
    }

    private void drawTime() {
        canvas.drawText(viewInfo.getTime(), getWidth()/2-getTextWidth(paintTime,viewInfo.getTime())/2,getHeight()-getTextHeight(paintTime)/2,paintTime);
    }

    private void drawBattery() {
        if(pathStroke.isEmpty()) {
            int left = viewInfo.getPaddingLeft();
            int right = (int) (left+width_battery-paintBatteryStroke.getStrokeWidth());
            rect.left = (int) (left+paintBatteryStroke.getStrokeWidth());
            rect.top = (int) (getHeight()-getTextHeight(paintTime)/2-height_battery+paintBatteryStroke.getStrokeWidth());
            rect.right = (int) (right * ((float)viewInfo.getBattery()/100));
            rect.bottom = (int) (getHeight()-getTextHeight(paintTime)/2-paintBatteryStroke.getStrokeWidth());
            int width = 3;
            pathStroke.moveTo(rect.left-width,rect.top-width);
            pathStroke.lineTo(right+width,rect.top-width);
            pathStroke.lineTo(right+width,rect.top + height_battery/6);
            pathStroke.lineTo(right+width+5,rect.top + height_battery/6);
            pathStroke.lineTo(right+width+5,rect.bottom - height_battery/6);
            pathStroke.lineTo(right+width,rect.bottom - height_battery/6);
            pathStroke.lineTo(right+width,rect.bottom+width);
            pathStroke.lineTo(rect.left-width,rect.bottom+width);
            pathStroke.lineTo(rect.left-width,rect.top-width);
        }
        canvas.drawPath(pathStroke,paintBatteryStroke);
        canvas.drawRect(rect,paintBattery);
    }

    private int getLineLen(String string,int start) {
        int len = 1;
        while (getTextWidth(paintContent,string.substring(start, start+len)) <= viewInfo.getWidth()) if (start+len++>=string.length()) break;
        return len-1;
    }

    public void setText(String text) {
        this.text = text;
    }

    //文字宽度
    private float getTextWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    //文字高度
    private float getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics= paint.getFontMetrics();
        return -fontMetrics.ascent-fontMetrics.descent;
    }

    @Override
    public void onClick(View view) {
        if(onClickListener !=null) onClickListener.onClick();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setViewInfo(ViewInfo viewInfo) {
        this.viewInfo = viewInfo;
        reInit();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public interface OnClickListener {
        void onClick();
    }
}
