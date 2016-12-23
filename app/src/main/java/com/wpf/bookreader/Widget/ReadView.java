package com.wpf.bookreader.Widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wpf.bookreader.Adapter.PageListAdapter;
import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.DataBase.ChapterManager;
import com.wpf.bookreader.DataInfo.ColorInfo;
import com.wpf.bookreader.DataInfo.ViewInfo;
import com.wpf.bookreader.FontListActivity;
import com.wpf.bookreader.R;
import com.wpf.bookreader.ReadActivity;
import com.wpf.bookreader.Receiver.BatteryBroadcastReceiver;
import com.wpf.bookreader.Receiver.TimeBroadcastReceiver;
import com.wpf.bookreader.Utils.GetPageList;
import com.wpf.bookreader.Utils.GetStringByUrl;
import com.wpf.bookreader.Utils.SaveInfo;
import com.wpf.bookreader.View.ViewBase;
import com.wpf.bookreader.View.View_ActionBar;
import com.wpf.bookreader.View.View_ActionSet;
import com.wpf.bookreader.View.View_DetailSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 王朋飞 on 12-19-0019.
 *
 */

public class ReadView extends LinearLayout implements
        View.OnClickListener,
        ViewPager.OnPageChangeListener,
        PageListAdapter.OnInitNextChapter,
        PageView.OnClickListener,
        View_ActionSet.OnCloseListener,
        View_DetailSet.OnSizeChaneListener,
        View_DetailSet.OnRequestWriteSettingListener,
        View_DetailSet.OnColorChangeListener,
        BatteryBroadcastReceiver.OnBatteryChangeListener,
        TimeBroadcastReceiver.OnTimeChangeListener{

    private BatteryBroadcastReceiver receiverBattery;
    private TimeBroadcastReceiver receiverTime;
    private View backView;
    private LinearLayout white_space;
    private ViewBase actionBar;
    public ViewBase actionSet;
    public ViewBase actionSetDetail;

    private NoScrollViewPager pageList;
    private PageListAdapter pageListAdapter;
    private ArrayList<ChapterInfo> bookChapterList = new ArrayList<>();
    private ViewInfo viewInfo = new ViewInfo();
    private ColorInfo colorInfo = new ColorInfo();
    private int curChapterPosition;
    private boolean isActionShow;

    public ReadView(Context context) {
        this(context,null);
    }

    public ReadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ReadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.readview,this,false);
        pageList = (NoScrollViewPager) view.findViewById(R.id.pageList);
        pageList.setScrollable(true);
//        pageList.setOffscreenPageLimit(5);
        pageListAdapter = new PageListAdapter(((ReadActivity)getContext()).getSupportFragmentManager(),viewInfo,this);
        pageListAdapter.setOnInitNextChapter(this);
        pageList.setAdapter(pageListAdapter);
        pageList.addOnPageChangeListener(this);
        backView = view.findViewById(R.id.backView);
        FrameLayout actionView = (FrameLayout) view.findViewById(R.id.actionView);
        white_space = (LinearLayout) view.findViewById(R.id.white_space);
        actionBar = new View_ActionBar(getContext()).initView(actionView);
        actionSet = new View_ActionSet(getContext()).initView(actionView);
        actionSetDetail = new View_DetailSet(getContext()).initView(actionView);
        white_space.setOnClickListener(this);
        ((View_ActionSet)actionSet).setOnCloseListener(this);
        ((View_DetailSet)actionSetDetail).setOnRequestWriteSettingListener(this);
        ((View_DetailSet)actionSetDetail).setOnSizeChaneListener(this);
        ((View_DetailSet)actionSetDetail).setOnColorChangeListener(this);
        getViewInfo();
        addView(view);
    }

    private void getViewInfo() {
        viewInfo.setTypefaceName(SaveInfo.getFontName(getContext()));
        viewInfo.setTextSize(sp2px(getContext(),16));
        viewInfo.setTextColor(colorInfo.getColorText());
        viewInfo.setSpacingVertical(30);
        viewInfo.setTime(getTime());
    }

    public void initView() {
        viewInfo.setWidth(pageList.getWidth());
        viewInfo.setHeight(pageList.getHeight());
        viewInfo.setPaddingTop(viewInfo.getHeight()/20);
        viewInfo.setPaddingLeft(viewInfo.getWidth()/30);
        viewInfo.setPaddingBottom(viewInfo.getHeight()/40);
        ((View_DetailSet)actionSetDetail).setTextSize(viewInfo.getTextSize());
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @SuppressLint("SimpleDateFormat")
    private String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    private void initPage(int chapterPosition) {
        final ChapterInfo chapterInfo = bookChapterList.get(chapterPosition);
        new GetStringByUrl().getChapterContentByUrl(chapterInfo.getUrl(), chapterInfo.getChapterName(), new GetStringByUrl.OnTextFinish() {
            @Override
            public void onSuccess(String textInfo) {
                chapterInfo.setChapterPageContent(textInfo);
                ChapterManager.saveChapter(chapterInfo);
                initPageList(chapterInfo);
            }
        });
    }

    private void initPageList(final ChapterInfo chapterInfo) {
//        pageList.setScrollable(false);
        new GetPageList().getAPageText(getContext(), chapterInfo.getChapterPageContent(),
                viewInfo, new GetPageList.OnTextFinish() {
                    @Override
                    public void onSuccess(List<String> pageTextList) {
                        chapterInfo.setChapterPageList(pageTextList);
                        pageListAdapter.notifyDataSetChanged();
                        pageList.setCurrentItem(pageListAdapter.getPageCount(0,curChapterPosition),false);
//                        pageList.setScrollable(true);
                    }
        });
    }

    public ReadView setChapterInfo(ArrayList<ChapterInfo> bookChapterList) {
        this.bookChapterList = bookChapterList;
        pageListAdapter.setChapterInfoList(bookChapterList);
        return this;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        curChapterPosition = pageListAdapter.getChapterPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void requestWriteSetting() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !Settings.System.canWrite(getContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
            getContext().startActivity(intent);
        }
    }

    public void onResume() {
        ((View_DetailSet)actionSetDetail).onResume();
        //电量变化监听
        receiverBattery = new BatteryBroadcastReceiver();
        receiverBattery.setOnBatteryChangeListener(this);
        IntentFilter intentFilterBattery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getContext().registerReceiver(receiverBattery, intentFilterBattery);

        //时间变化监听
        receiverTime = new TimeBroadcastReceiver();
        receiverTime.setOnTimeChangeListener(this);
        IntentFilter intentFilterTime = new IntentFilter(Intent.ACTION_TIME_TICK);
        getContext().registerReceiver(receiverTime,intentFilterTime);
    }

    public void onPause() {
        getContext().unregisterReceiver(receiverBattery);
        getContext().unregisterReceiver(receiverTime);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1 && resultCode == FontListActivity.RESULT_OK) {
            String fontPathName = data.getStringExtra("FontName");
            changFont(fontPathName);
            String fontName = fontPathName;
            if(fontPathName.contains("/"))
                fontName = fontPathName.split("/")[1];
            if(fontName.contains("."))
                fontName = fontName.split("\\.")[0];
            ((View_DetailSet)actionSetDetail).refreshFont(fontName);
        }
    }

    @Override
    public void batteryChange(int battery) {
        viewInfo.setBattery(battery);
        if(pageListAdapter != null) pageListAdapter.setViewInfo(viewInfo);
    }

    @Override
    public void timeChange(String time) {
        viewInfo.setTime(time);
        if(pageListAdapter != null) pageListAdapter.setViewInfo(viewInfo);
    }

    @Override
    public void onOtherActionClose() {
        notShowAction();
        white_space.setVisibility(View.VISIBLE);
        actionSetDetail.showAction();
    }

    @Override
    public void change(ColorInfo colorInfo) {
        backView.setBackgroundColor(colorInfo.getColorBack());
        viewInfo.setTextColor(colorInfo.getColorText());
        pageListAdapter.setViewInfo(viewInfo);
    }

    @Override
    public void request() {
        requestWriteSetting();
    }

    @Override
    public void change(int size) {
        viewInfo.setTextSize(size);
        initPageList(bookChapterList.get(pageListAdapter.getChapterPosition(pageList.getCurrentItem())));
        for(ChapterInfo chapterInfo : bookChapterList) {
            if(!chapterInfo.getChapterPageList().isEmpty())
                initPageList(chapterInfo);
        }
    }

    private void changFont(String fontPathName) {
        viewInfo.setTypefaceName(fontPathName);
        initPageList(bookChapterList.get(pageListAdapter.getChapterPosition(pageList.getCurrentItem())));
        for(ChapterInfo chapterInfo : bookChapterList) {
            if(!chapterInfo.getChapterPageList().isEmpty())
                initPageList(chapterInfo);
        }
    }

    @Override
    public void onClick() {
        if(!isActionShow) showAction();else notShowAction();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.white_space:
                notShowAction();
                break;
        }
    }

    private void showAction() {
        actionBar.showAction();
        actionSet.showAction();
        white_space.setVisibility(View.VISIBLE);
        isActionShow = true;
    }

    public void notShowAction() {
        actionBar.notShowAction();
        actionSet.notShowAction();
        white_space.setVisibility(View.GONE);
        actionSetDetail.notShowAction();
        isActionShow = false;
    }

    @Override
    public void startInit(int chapterPosition) {
        initPage(chapterPosition);
    }

    public ReadView setCurChapterPosition(int curChapterPosition) {
        this.curChapterPosition = curChapterPosition;
        pageList.setCurrentItem(curChapterPosition,false);
        return this;
    }
}
