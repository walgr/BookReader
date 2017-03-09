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

import com.socks.library.KLog;
import com.wpf.bookreader.Adapter.PageListAdapter;
import com.wpf.bookreader.BookReaderApplication;
import com.wpf.bookreader.DataBase.BookInfo;
import com.wpf.bookreader.DataBase.BookManager;
import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.DataBase.ChapterManager;
import com.wpf.bookreader.DataBase.UserSettingInfo;
import com.wpf.bookreader.DataBase.UserSettingManager;
import com.wpf.bookreader.DataInfo.ColorInfo;
import com.wpf.bookreader.DataInfo.ViewInfo;
import com.wpf.bookreader.R;
import com.wpf.bookreader.ReadActivity;
import com.wpf.bookreader.Receiver.BatteryBroadcastReceiver;
import com.wpf.bookreader.Receiver.TimeBroadcastReceiver;
import com.wpf.bookreader.Utils.GetStringByUrl;
import com.wpf.bookreader.Utils.LogTime;
import com.wpf.bookreader.Utils.PageListManager;
import com.wpf.bookreader.Utils.Tools;
import com.wpf.bookreader.View.ViewBase;
import com.wpf.bookreader.View.View_ActionBar;
import com.wpf.bookreader.View.View_ActionSet;
import com.wpf.bookreader.View.View_DetailSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.wpf.bookreader.Utils.Tools.px2sp;
import static com.wpf.bookreader.Utils.Tools.sp2px;

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
        TimeBroadcastReceiver.OnTimeChangeListener {

    private BatteryBroadcastReceiver receiverBattery;
    private TimeBroadcastReceiver receiverTime;
    private View backView;
    private ViewBase actionBar;
    public ViewBase actionSet;
    public ViewBase actionDetailSet;

    private NoScrollViewPager pageList;
    private PageListAdapter pageListAdapter;
    private List<ChapterInfo> bookChapterList = new ArrayList<>();
    private List<ChapterInfo> initChapterList = new ArrayList<>();
    private UserSettingInfo userSettingInfo = UserSettingManager.getUserSettingInfo((long) 0);
    private ViewInfo viewInfo = new ViewInfo();
    private BookInfo bookInfo = new BookInfo();
    private int curChapterPosition, curPagePosition;
    private boolean isActionShow, isBackResult;

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
        pageListAdapter = new PageListAdapter(((ReadActivity)getContext()).getSupportFragmentManager(),viewInfo,this);
        pageListAdapter.setOnInitNextChapter(this);
        pageList.setAdapter(pageListAdapter);
        pageList.addOnPageChangeListener(this);
        backView = view.findViewById(R.id.backView);
        FrameLayout actionView = (FrameLayout) view.findViewById(R.id.actionView);
        LinearLayout white_space = (LinearLayout) view.findViewById(R.id.white_space);
        actionBar = new View_ActionBar(this).initView(actionView);
        actionSet = new View_ActionSet(this).initView(actionView);
        actionDetailSet = new View_DetailSet(this).initView(actionView);
        white_space.setOnClickListener(this);
        ((View_ActionSet)actionSet).setOnCloseListener(this);
        ((View_DetailSet) actionDetailSet).setOnRequestWriteSettingListener(this);
        ((View_DetailSet) actionDetailSet).setOnSizeChaneListener(this);
        ((View_DetailSet) actionDetailSet).setOnColorChangeListener(this);
        getViewInfo();
        addView(view);
    }

    private void getViewInfo() {
        viewInfo.setTypefaceName(userSettingInfo.getFontName());
        viewInfo.setTextSize(sp2px(getContext(),userSettingInfo.getTextSize()));
        viewInfo.setTextColor(BookReaderApplication.colorInfoList
                .get(userSettingInfo.getColorPos()).getColorText());
        viewInfo.setSpacingVertical(30);
        viewInfo.setTime(getTime());
    }

    public void initView() {
        viewInfo.setWidth(pageList.getWidth());
        viewInfo.setHeight(pageList.getHeight());
        viewInfo.setPaddingTop(viewInfo.getHeight()/20);
        viewInfo.setPaddingLeft(viewInfo.getWidth()/30);
        viewInfo.setPaddingBottom(viewInfo.getHeight()/40);
        ((View_DetailSet) actionDetailSet).setTextSize(viewInfo.getTextSize());
    }

    public ReadView setBackResult(boolean backResult) {
        isBackResult = backResult;
        return this;
    }

    public boolean isBackResult() {
        return isBackResult;
    }

    @SuppressLint("SimpleDateFormat")
    private String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    private LogTime logTime = new LogTime();
    private void initPage(int chapterPosition) {
        final ChapterInfo chapterInfo = bookChapterList.get(chapterPosition);
        if(initChapterList.contains(chapterInfo)) return;
        initChapterList.add(chapterInfo);
        KLog.a("开始获取章节内容");
        logTime.start();
        new GetStringByUrl().getChapterContentByUrl(chapterInfo, new GetStringByUrl.OnTextFinish() {
            @Override
            public void onSuccess(String textInfo) {
                KLog.a("获取章节内容成功"+"用时："+logTime.getUseTime());
                chapterInfo.setChapterPageContent(textInfo);
                ChapterManager.saveChapter(chapterInfo);
                initPageList(chapterInfo);
            }
        });
    }

    private void initPageList(final ChapterInfo chapterInfo) {
//        pageList.setScrollable(false);
        KLog.a("开始章节分页");
        logTime.start();
        new PageListManager().getPageText(getContext(), chapterInfo.getChapterPageContent(),
                viewInfo, new PageListManager.OnTextFinish() {
                    @Override
                    public void onSuccess(List<String> pageTextList) {
                        KLog.a("章节分页成功"+"用时："+logTime.getUseTime());
                        chapterInfo.setChapterPageList(pageTextList);
                        pageListAdapter.notifyDataSetChanged();
                        pageList.setCurrentItem(pageListAdapter.getPageCount(0,curChapterPosition) + curPagePosition,false);
                        initChapterList.remove(chapterInfo);
//                        pageList.setScrollable(true);
                    }
        });
    }

    public ReadView setChapterList(List<ChapterInfo> bookChapterList) {
        this.bookChapterList = bookChapterList;
        if(pageListAdapter != null)
            pageListAdapter.setChapterInfoList(bookChapterList);
        return this;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        curChapterPosition = pageListAdapter.getChapterPosition(position);
        curPagePosition = pageListAdapter.getPagePosition(position,curChapterPosition);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void requestWriteSetting() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !Settings.System.canWrite(getContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
            ((ReadActivity)getContext()).startActivityForResult(intent,0);
        }
    }

    public void onResume() {
        ((View_DetailSet) actionDetailSet).onResume();
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
        if(receiverBattery == null || receiverTime == null) return;
        getContext().unregisterReceiver(receiverBattery);
        getContext().unregisterReceiver(receiverTime);
    }

    public void onDestroy() {
        saveBookInfo();
    }

    private void saveBookInfo() {
        if(bookInfo == null) return;
        bookInfo.setChapterPosition(curChapterPosition);
        bookInfo.setPagePosition(curPagePosition);
        BookManager.saveBook(bookInfo);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1 && resultCode == RESULT_OK) {
            String fontPathName = data.getStringExtra("FontName");
            changFont(fontPathName);
            String fontName = fontPathName;
            if(fontPathName.contains("/"))
                fontName = fontPathName.split("/")[1];
            if(fontName.contains("."))
                fontName = fontName.split("\\.")[0];
            ((View_DetailSet) actionDetailSet).refreshFont(fontName);
        } else if(requestCode == 2 && resultCode == RESULT_OK) {
            if(data == null) return;
            int position = data.getIntExtra("Position",-1);
            setCurChapterPosition(position);
            isBackResult = true;
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
    }

    @Override
    public void colorChange(int position) {
        ColorInfo colorInfo = BookReaderApplication.colorInfoList.get(position);
        backView.setBackgroundColor(colorInfo.getColorBack());
        viewInfo.setTextColor(colorInfo.getColorText());
        pageListAdapter.setViewInfo(viewInfo);
        userSettingInfo.setColorPos(position);
        UserSettingManager.saveUserSettingInfo(userSettingInfo);
    }

    @Override
    public void request() {
        requestWriteSetting();
    }

    @Override
    public void sizeChange(int size) {
        viewInfo.setTextSize(size);
        initPageList(bookChapterList.get(pageListAdapter.getChapterPosition(pageList.getCurrentItem())));
        for(ChapterInfo chapterInfo : bookChapterList) {
            if(!Tools.isEmpty(chapterInfo.getChapterPageList()))
                initPageList(chapterInfo);
        }
        userSettingInfo.setTextSize(px2sp(getContext(),size));
        UserSettingManager.saveUserSettingInfo(userSettingInfo);
    }

    private void changFont(String fontPathName) {
        viewInfo.setTypefaceName(fontPathName);
        initPageList(bookChapterList.get(pageListAdapter.getChapterPosition(pageList.getCurrentItem())));
        for(ChapterInfo chapterInfo : bookChapterList) {
            if(!Tools.isEmpty(chapterInfo.getChapterPageList()))
                initPageList(chapterInfo);
        }
        userSettingInfo.setFontName(fontPathName);
        UserSettingManager.saveUserSettingInfo(userSettingInfo);
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
        isActionShow = true;
    }

    public void notShowAction() {
        actionBar.notShowAction();
        actionSet.notShowAction();
        actionDetailSet.notShowAction();
        isActionShow = false;
    }

    @Override
    public void startInit(int chapterPosition) {
        KLog.a();
        initPage(chapterPosition);
    }

    public ReadView setCurChapterPosition(int curChapterPosition) {
        this.curChapterPosition = curChapterPosition;
        this.curPagePosition = 0;
        pageList.setCurrentItem(pageListAdapter.getPageCount(0,curChapterPosition) + curPagePosition,false);
        return this;
    }

    public ReadView setCurPagePosition(int curPagePosition) {
        this.curPagePosition = curPagePosition;
        pageListAdapter.setStart(true);
        pageList.setCurrentItem(pageListAdapter.getPageCount(0,curChapterPosition) + curPagePosition,false);
        return this;
    }

    public ReadView setBookInfo(BookInfo bookInfo) {
        if(bookInfo == null) return this;
        this.bookInfo = bookInfo;
        return this;
    }
}
