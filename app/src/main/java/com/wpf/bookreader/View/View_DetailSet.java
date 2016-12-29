package com.wpf.bookreader.View;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wpf.bookreader.Adapter.ColorListAdapter;
import com.wpf.bookreader.Adapter.OnItemClickListener;
import com.wpf.bookreader.BookReaderApplication;
import com.wpf.bookreader.DataInfo.ColorInfo;
import com.wpf.bookreader.FontListActivity;
import com.wpf.bookreader.R;
import com.wpf.bookreader.ReadActivity;
import com.wpf.bookreader.Utils.FontManager;
import com.wpf.bookreader.Utils.ScreenBrightnessManager;
import com.wpf.bookreader.Widget.ReadView;

import java.util.List;

import static com.wpf.bookreader.Utils.Tools.px2sp;
import static com.wpf.bookreader.Utils.Tools.sp2px;

/**
 * Created by 王朋飞 on 11-29-0029.
 * 详细设置界面
 */

public class View_DetailSet extends ViewBase implements
        SeekBar.OnSeekBarChangeListener,
        CheckBox.OnCheckedChangeListener,
        OnItemClickListener {

    private OnRequestWriteSettingListener onRequestWriteSettingListener;
    private OnSizeChaneListener onSizeChaneListener;
    private OnColorChangeListener onColorChangeListener;
    private int textSize,min = 5;
    private Button button_font;
    private TextView textView_Size;
    private SeekBar seekBar_Screen;
    private CheckBox checkBox_Auto;
    private ColorListAdapter colorListAdapter;

    public View_DetailSet(ReadView readView) {
        this.context = readView.getContext();
    }

    @Override
    protected void init() {
        super.init();
        textView_Size = (TextView) getView().findViewById(R.id.textView_size);
        Button button_sub = (Button) getView().findViewById(R.id.button_sub);
        Button button_add = (Button) getView().findViewById(R.id.button_add);
        button_font = (Button) getView().findViewById(R.id.button_font);
        seekBar_Screen = (SeekBar) getView().findViewById(R.id.seekBar_screen);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.list_color);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        colorListAdapter = new ColorListAdapter(getColorList());
        colorListAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(colorListAdapter);

        seekBar_Screen.setMax(250-min);
        seekBar_Screen.setOnSeekBarChangeListener(this);
        checkBox_Auto = (CheckBox) getView().findViewById(R.id.checkbox_auto);
        checkBox_Auto.setOnCheckedChangeListener(this);
        button_sub.setOnClickListener(this);
        button_add.setOnClickListener(this);
        button_font.setOnClickListener(this);
        textView_Size.setText(String.valueOf(textSize));
        onResume();
    }

    public void onResume() {
        seekBar_Screen.setProgress(ScreenBrightnessManager.getScreenBrightness(context));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && Settings.System.canWrite(getActivity())) {
            checkBox_Auto.setChecked(ScreenBrightnessManager.isAutoScreenBrightness(context));
        }
        String fontName = FontManager.getFontName(getActivity());
        if(fontName.contains("/"))
            fontName = fontName.split("/")[1];
        if(fontName.contains("."))
            fontName = fontName.split("\\.")[0];
        button_font.setText(String.format("%s>", fontName));
    }

    private List<ColorInfo> getColorList() {
        return BookReaderApplication.colorInfoList;
    }

    public void refreshFont(String typefaceName) {
        button_font.setText(String.format("%s>", typefaceName));
    }

    @Override
    public void getAnimation() {
        mAnimation_Start = AnimationUtils.loadAnimation(context, R.anim.actionset_up);
        mAnimation_End = AnimationUtils.loadAnimation(context,R.anim.actionset_down);
    }

    @Override
    public ViewBase initView(ViewGroup parent) {
        if(mView == null) {
            mView = parent.findViewById(R.id.my_action_detailed_set);
            whiteSpace = parent.findViewById(R.id.white_space);
            init();
        }
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sub:
                if(textSize < 14) return;
                textSize -= 4;
            case R.id.button_add:
                if(textSize > 30) return;
                textSize += 2;
                if(onSizeChaneListener != null) onSizeChaneListener.sizeChange(sp2px(getActivity(), textSize));
                textView_Size.setText(String.valueOf(textSize));
                break;
            case R.id.button_font:
                getActivity().startActivityForResult(new Intent(getActivity(), FontListActivity.class), 1);
                break;
        }
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = px2sp(getActivity(), textSize);
        textView_Size.setText(String.valueOf(this.textSize));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(checkBox_Auto.isChecked()) {
            seekBar.setProgress(ScreenBrightnessManager.getScreenBrightness(context));
            return;
        }
        int progress = seekBar.getProgress()+min;
        changeScreenBrightness(progress);
    }

    private void changeScreenBrightness(int brightness) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.screenBrightness = brightness / 255f;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.System.canWrite(getActivity())) {
            checkBox_Auto.setChecked(!checkBox_Auto.isChecked());
            if(onRequestWriteSettingListener != null)
                onRequestWriteSettingListener.request();
            return;
        }
        ScreenBrightnessManager.setAutoScreenBrightness(getActivity(), b);
        if (b) {
            int progress = ScreenBrightnessManager.getScreenBrightness(getActivity());
            seekBar_Screen.setProgress(progress);
            changeScreenBrightness(progress);
        }
    }

    private AppCompatActivity getActivity() {
        return ((ReadActivity)context);
    }

    public void setOnSizeChaneListener(OnSizeChaneListener onSizeChaneListener) {
        this.onSizeChaneListener = onSizeChaneListener;
    }

    public void setOnRequestWriteSettingListener(OnRequestWriteSettingListener onRequestWriteSettingListener) {
        this.onRequestWriteSettingListener = onRequestWriteSettingListener;
    }

    @Override
    public void onClick(int position) {
        colorListAdapter.setSelectPos(position);
        onColorChangeListener.colorChange(position);
    }

    public View_DetailSet setOnColorChangeListener(OnColorChangeListener onColorChangeListener) {
        this.onColorChangeListener = onColorChangeListener;
        return this;
    }

    public interface OnRequestWriteSettingListener {
        void request();
    }

    public interface OnSizeChaneListener {
        void sizeChange(int size);
    }

    public interface OnColorChangeListener {
        void colorChange(int position);
    }
}
