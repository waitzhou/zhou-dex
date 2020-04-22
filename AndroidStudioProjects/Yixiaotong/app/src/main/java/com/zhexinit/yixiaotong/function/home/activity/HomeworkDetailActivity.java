package com.zhexinit.yixiaotong.function.home.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.function.BaseResp;
import com.zhexinit.yixiaotong.function.ChildrenWarehouse;
import com.zhexinit.yixiaotong.function.home.entity.resp.HomewordDetailResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.HomeworkResp;
import com.zhexinit.yixiaotong.rxjavamanager.interfaces.ResultCallBack;
import com.zhexinit.yixiaotong.utils.GsonUtil;
import com.zhexinit.yixiaotong.utils.RecyclerViewDivider;
import com.zhexinit.yixiaotong.utils.StringUtils;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerAdapter;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerHolder;
import com.zhexinit.yixiaotong.widget.SharePopupWindow;
import com.zhexinit.yixiaotong.widget.ShowScaleImagePopupWindow;
import com.zhexinit.yixiaotong.widget.ToolBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:zhousx
 * date:2018/11/9
 * description:家庭作业详情界面
 */
public class HomeworkDetailActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    ToolBar toolBar;
    @BindView(R.id.tv_school)
    TextView tvSchool;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_master)
    TextView tvMaster;
    @BindView(R.id.tv_date)
    TextView tvDate;

    private int mClassId;
    private long mHomeworkDate;

    private HomeworkResp mHomeworkResp;
    private List<HomewordDetailResp> mRespList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_homework_detail;
    }

    @Override
    protected void init() {

        initToolbar();
        initData();
        initContent();
        initRecyclerView();
    }

    private void initData() {
        mHomeworkResp = GsonUtil.getInstance().getGson().fromJson(getIntent().getStringExtra("item"),HomeworkResp.class);
        mHomeworkDate = mHomeworkResp.homeworkDate;
        mClassId = mHomeworkResp.classId;
        getDetailData();
    }

    private void getDetailData() {
        Map<String,String> map = new HashMap<>();
        map.put("classId",String.valueOf(mClassId));
        map.put("homeworkDate", StringUtils.longToDate(mHomeworkDate,"yyyy-MM-dd"));
        ChildrenWarehouse.getInstance(this).getHomeworkDetail(map, new ResultCallBack<BaseResp<List<HomewordDetailResp>>>() {
            @Override
            public void onSuccess(BaseResp<List<HomewordDetailResp>> listBaseResp) {
                if(listBaseResp.code == 0){
                    mRespList.addAll(listBaseResp.result);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(String response) {

            }
        });
    }


    private void initToolbar() {
        toolBar.setTitle("作业单");
        toolBar.setBackImage();
        toolBar.back(this);
        toolBar.setRightTypeface(getResources().getString(R.string.tv_share));
        toolBar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareWindow();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initContent() {
        tvSchool.setText(mHomeworkResp.schoolName);
        tvClass.setText(mHomeworkResp.classOtherName+"  "+mHomeworkResp.childName);
        tvMaster.setText(mHomeworkResp.headmaster);
        tvDate.setText(StringUtils.longToDate(mHomeworkDate,"yyyy年MM月dd日 EEEE"));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        RecyclerViewDivider divider=new RecyclerViewDivider(this);
        divider.setLineColor(R.color.bg_color);
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(new CommonRecyclerAdapter<HomewordDetailResp>(this, mRespList,
                R.layout.item_homework_detail_adapter) {
            @Override
            public void convert(CommonRecyclerHolder holder, final HomewordDetailResp item, int position) {
                TextView textView = holder.getView(R.id.item_image);
                TextView tvImageHolder = holder.getView(R.id.item_tv_image_holder);
                holder.setText(R.id.item_tv_content,item.homeworkContent);
                textView.setText(item.subject);
                textView.setBackground(getLessonTag(position));
                tvImageHolder.setVisibility(item.homeworkImage != null && !item.homeworkImage.equals("") ? View.VISIBLE :View.GONE);

                tvImageHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showScaleImage(item.homeworkImage);
                    }
                });
            }
        });
    }

    private void showScaleImage(String url){
        ShowScaleImagePopupWindow popupWindow = new ShowScaleImagePopupWindow(this,toolBar,url);
        popupWindow.show();
    }

    /**
     * 获取课程drawable对象
     */
    private Drawable getLessonTag(int type) {
        Drawable drawable = null;
        switch ((type+1)%12) {
            case 1:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson1);
                break;
            case 2:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson2);
                break;
            case 3:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson3);
                break;
            case 4:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson4);
                break;
            case 5:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson5);
                break;
            case 6:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson6);
                break;
            case 7:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson7);
                break;
            case 8:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson8);
                break;
            case 9:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson9);
                break;
            case 10:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson10);
                break;
            case 11:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson11);
                break;
            case 12:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson12);
                break;
            default:
                drawable = getResources().getDrawable(R.drawable.bg_oval_lesson1);
                break;
        }
        return drawable;
    }

    private void showShareWindow(){
        SharePopupWindow popupWindow = new SharePopupWindow(this,toolBar);
        popupWindow.show();
    }
}
