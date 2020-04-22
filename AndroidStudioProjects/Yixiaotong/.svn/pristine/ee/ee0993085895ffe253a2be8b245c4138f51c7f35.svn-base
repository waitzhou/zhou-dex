package com.zhexinit.yixiaotong.function.home.activity;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.base.Constant;
import com.zhexinit.yixiaotong.function.BaseResp;
import com.zhexinit.yixiaotong.function.ChildrenWarehouse;
import com.zhexinit.yixiaotong.function.home.entity.resp.ScheduleResp;
import com.zhexinit.yixiaotong.function.mine.entity.ChildResp;
import com.zhexinit.yixiaotong.function.mine.entity.UserInfoResp;
import com.zhexinit.yixiaotong.rxjavamanager.interfaces.ResultCallBack;
import com.zhexinit.yixiaotong.utils.DipUtils;
import com.zhexinit.yixiaotong.utils.GsonUtil;
import com.zhexinit.yixiaotong.utils.RecyclerViewDivider;
import com.zhexinit.yixiaotong.utils.SharePerfUtils;
import com.zhexinit.yixiaotong.utils.StringUtils;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerAdapter;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerHolder;
import com.zhexinit.yixiaotong.widget.SharePopupWindow;
import com.zhexinit.yixiaotong.widget.ShowChildListPopupWindow;
import com.zhexinit.yixiaotong.widget.ToolBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:zhousx
 * date:2018/11/12
 * description:课程表
 */
public class ClassScheduleActivity extends BaseActivity implements ShowChildListPopupWindow.PopupItemSelectListener {

    @BindView(R.id.tool_bar)
    ToolBar toolBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.tv_place_holder)
    TextView tvPlaceHolder;
    @BindView(R.id.rl_place_holder)
    RelativeLayout rlPlaceHolder;
    @BindView(R.id.btn_place_holder)
    Button btnPlaceHolder;

    private List<String> mStrings = new ArrayList<>();

    private ScheduleResp mScheduleResp;
    private int mSelectPosition;
    UserInfoResp mInfoResp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_class_schedule;
    }

    @Override
    protected void init() {

        initToolbar();
        initContent();
        initData();
    }

    private void initContent() {
        mInfoResp = GsonUtil.getInstance().getGson().fromJson(SharePerfUtils.getString(Constant.KEY.USER_INFO), UserInfoResp.class);
        for (int i = 0; i < mInfoResp.userChildren.size(); i++) {
            ChildResp childResp = mInfoResp.userChildren.get(i);
            childResp.childName = childResp.childName.concat("的课程表");
        }
        tvContent.setText(mInfoResp.userChildren.get(mSelectPosition).childName);
        tvDown.setVisibility(mInfoResp.userChildren.size()>1?View.VISIBLE:View.GONE);
        tvDown.setTypeface(Typeface.createFromAsset(getAssets(), getString(R.string.tv_path)));
    }

    private void initData() {
        getScheduleData();
    }

    private void getScheduleData() {
        showProgressDialog();
        UserInfoResp infoResp = GsonUtil.getInstance().getGson().fromJson(SharePerfUtils.
                getString(Constant.KEY.USER_INFO), UserInfoResp.class);
        if (infoResp.userChildren == null)
            return;
        Map<String, String> map = new HashMap<>();
        map.put("childId", infoResp.userChildren.get(mSelectPosition).childId);
        ChildrenWarehouse.getInstance(this).getClassSchueduleData(map, new ResultCallBack<BaseResp<ScheduleResp>>() {
            @Override
            public void onSuccess(BaseResp<ScheduleResp> homeworkRespBaseResp) {
                if (homeworkRespBaseResp.code == 0) {
                    if (homeworkRespBaseResp.result != null && homeworkRespBaseResp.result.classSchedule.size() > 0) {
                        mScheduleResp = homeworkRespBaseResp.result;
                        //initClassPosition();
                        doCompareData();
                    } else
                        loadFail(1);
                } else {
                    loadFail(1);
                    //showToast(homeworkRespBaseResp.message);
                }
                hideProgressDialog();
            }

            @Override
            public void onFail(String response) {
                hideProgressDialog();
                loadFail(2);
            }
        });
    }

    private void doCompareData() {
        mStrings.clear();
        mStrings.add("");
        mStrings.add("星期一");
        mStrings.add("星期二");
        mStrings.add("星期三");
        mStrings.add("星期四");
        mStrings.add("星期五");
        mStrings.add("星期六");
        mStrings.add("星期日");
        initRecyclerView();
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        final RecyclerViewDivider divider = new RecyclerViewDivider(this);
        divider.setOrientation(RecyclerViewDivider.HORIZONTAL_LIST);
        divider.setLineColor(R.color.bg_color);
        if (recyclerView.getAdapter() == null) {
            recyclerView.addItemDecoration(divider);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new CommonRecyclerAdapter<String>(this, mStrings, R.layout.item_class_schedule) {
                @Override
                public void convert(CommonRecyclerHolder holder, String item, int position) {
                    ViewGroup.LayoutParams layoutParams = holder.mItemView.getLayoutParams();
                    if (position == 0)
                        layoutParams.width = DipUtils.dp2px(ClassScheduleActivity.this, 22);
                    else
                        layoutParams.width = (DipUtils.getScreenWidth(ClassScheduleActivity.this) - DipUtils.dp2px(ClassScheduleActivity.this, 22)) / 7;
                    holder.setText(R.id.item_title, item);
                    RecyclerView recyclerView = holder.getView(R.id.item_recyclerView);
                    int weekPosition = StringUtils.getWeekPosition(System.currentTimeMillis());
                    setInnerAdapter(recyclerView, position, position == weekPosition);
                }

                private void setInnerAdapter(RecyclerView recyclerView, final int column, final boolean isCurrentPosition) {
                    List<ScheduleResp.ClassSchedule> strings = new ArrayList<>();
                    if (column == 0) {
                        for (int i = 1; i <= (mScheduleResp.amClassSize + mScheduleResp.pmClassSize); i++) {
                            ScheduleResp schedule = new ScheduleResp();
                            ScheduleResp.ClassSchedule classSchedule = schedule.getClassSchedule();
                            strings.add(classSchedule);
                        }
                    } else {
                        for (int i = 0; i < (mScheduleResp.amClassSize + mScheduleResp.pmClassSize); i++) {
                            ScheduleResp schedule = new ScheduleResp();
                            ScheduleResp.ClassSchedule classSchedule = schedule.getClassSchedule();
                            strings.add(classSchedule);
                        }

                        if (column-1>=mScheduleResp.classSchedule.size())return;
                        List<ScheduleResp.ClassSchedule> classCourseInners = mScheduleResp.classSchedule.get(column - 1);
                        for (int i = 0; i < classCourseInners.size(); i++) {
                            strings.set(classCourseInners.get(i).classNumber - 1, classCourseInners.get(i));
                        }
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(ClassScheduleActivity.this) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    });
                    recyclerView.setAdapter(new CommonRecyclerAdapter<ScheduleResp.ClassSchedule>(ClassScheduleActivity.this, strings, R.layout.item_inner_class_schedule) {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void convert(CommonRecyclerHolder holder, ScheduleResp.ClassSchedule item, int position) {
                            TextView className = holder.getView(R.id.item_inner_text);

                            if (mScheduleResp.amClassSize != 0 && mScheduleResp.amClassSize == position) {
                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) className.getLayoutParams();
                                layoutParams.setMargins(0, DipUtils.dip2px(ClassScheduleActivity.this, 20), 0, 0);
                                className.setLayoutParams(layoutParams);
                            }

                            if (column == 0)
                                className.setText((position + 1) + "");
                            else className.setText(item.courseName);
                            if (isCurrentPosition)
                                className.setBackgroundColor(ContextCompat.getColor(ClassScheduleActivity.this, R.color.bg_week));
                        }
                    });
                }
            });
        } else {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void initToolbar() {
        toolBar.setTitle("");
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

    /**
     * 展示孩子列表
     */
    private void showChildPopup() {
        ShowChildListPopupWindow popupWindow = new ShowChildListPopupWindow(this, tvDown, mInfoResp.userChildren, mSelectPosition, 120, this);
        popupWindow.show();
    }

    private void showShareWindow() {
        SharePopupWindow popupWindow = new SharePopupWindow(this, toolBar);
        popupWindow.show();
    }

    @Override
    public void popupItemSelectListener(int position, String deviceId) {
        mSelectPosition = position;
        mStrings.clear();
        tvContent.setText(mInfoResp.userChildren.get(position).childName);
        rlPlaceHolder.setVisibility(View.GONE);
        getScheduleData();
    }

    @OnClick({R.id.tv_content, R.id.tv_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_content:
            case R.id.tv_down:
                showChildPopup();
                break;
        }
    }

    /**
     * 加载失败
     *
     * @param type 1 : 无数据   2: 无网络
     */
    private void loadFail(int type) {
        rlPlaceHolder.setVisibility(View.VISIBLE);
        if (type == 1) {
            tvPlaceHolder.setText("暂无课程表记录");
            tvPlaceHolder.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.ic_empty, 0, 0);
            btnPlaceHolder.setVisibility(View.GONE);
        } else {
            tvPlaceHolder.setText("抱歉，网络不给力");
            tvPlaceHolder.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.ic_not_link_net, 0, 0);
            btnPlaceHolder.setVisibility(View.VISIBLE);
            btnPlaceHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getScheduleData();
                    rlPlaceHolder.setVisibility(View.GONE);
                }
            });
        }
    }
}
