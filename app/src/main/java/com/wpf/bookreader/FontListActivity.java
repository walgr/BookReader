package com.wpf.bookreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wpf.bookreader.Adapter.FontListAdapter;
import com.wpf.bookreader.Adapter.OnItemClickListener;
import com.wpf.bookreader.Utils.GetFontsList;
import com.wpf.bookreader.Utils.SaveInfo;
import com.wpf.bookreader.Widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FontListActivity extends AppCompatActivity implements
        OnItemClickListener {

    public static int RESULT_OK = 1;
    private RecyclerView fontList;
    private FontListAdapter fontListAdapter;
    private List<String> fontNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_list);

        fontList = (RecyclerView) findViewById(R.id.font_list);
        fontList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        fontList.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        fontNameList = Arrays.asList(GetFontsList.getAssetList(this, "fonts"));
        fontListAdapter = new FontListAdapter(fontNameList);
        fontListAdapter.setOnItemClickListener(this);
        fontList.setAdapter(fontListAdapter);
    }

    @Override
    public void onClick(int position) {
        String fontName = position>0?"fonts/"+fontNameList.get(position-1):"系统默认";
        setResult(RESULT_OK,getIntent().putExtra("FontName",fontName));
        SaveInfo.saveFontName(this,fontName);
        finish();
    }
}
