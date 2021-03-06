package com.zhexinit.yixiaotong.function.home.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.event.MainRadioChangeEvent;
import com.zhexinit.yixiaotong.event.VacateEvent;
import com.zhexinit.yixiaotong.function.BaseResp;
import com.zhexinit.yixiaotong.function.ChildrenWarehouse;
import com.zhexinit.yixiaotong.function.home.entity.req.VacateReq;
import com.zhexinit.yixiaotong.function.home.entity.resp.VacateResp;
import com.zhexinit.yixiaotong.rxjavamanager.event.RxBus;
import com.zhexinit.yixiaotong.rxjavamanager.interfaces.ResultCallBack;
import com.zhexinit.yixiaotong.utils.GlideCricleTransform;
import com.zhexinit.yixiaotong.utils.StringUtils;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerAdapter;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerHolder;
import com.zhexinit.yixiaotong.widget.ToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:zhousx
 * date:2018/11/7
 * description:请假首页
 */
public class VacateHomeActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tool_bar)
    ToolBar mToolBar;

    @BindView(R.id.tv_place_holder)
    TextView tvPlaceHolder;
    @BindView(R.id.rl_place_holder)
    RelativeLayout rlPlaceHolder;
    @BindView(R.id.btn_place_holder)
    Button btnPlaceHolder;

    private int mCurrentPage = 1;
    private int PAGE_COUNT = 10;

    private List<VacateResp> mRespList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vacate_home;
    }

    @Override
    protected void init() {
        initToolbar();
        initContent();
        showProgressDialog();
        getVacateData();
        initVacateEvent();
    }

    private Disposable dispose;

    private void initVacateEvent() {
        dispose = RxBus.getInstance().registerEvent(VacateEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VacateEvent>() {
                    @Override
                    public void accept(VacateEvent event) {
                        smartRefreshLayout.autoRefresh();
                    }
                });
    }


    private void getVacateData() {
        VacateReq req = new VacateReq();
        req.page = mCurrentPage;
        req.rows = PAGE_COUNT;
        ChildrenWarehouse.getInstance(this).getVacateData(req, new ResultCallBack<BaseResp<List<VacateResp>>>() {
            @Override
            public void onSuccess(BaseResp<List<VacateResp>> listBaseResp) {
                hideProgressDialog();
                stopRefreshingAndLoadMore();
                if (listBaseResp.code == 0) {
                    if (listBaseResp.result != null && listBaseResp.result.size() != 0) {
                        mRespList.addAll(listBaseResp.result);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    } else {
                        loadFail(1);
                    }
                } else {
                    loadFail(2);
                }
            }

            @Override
            public void onFail(String response) {
                hideProgressDialog();
                showToast(response);
                stopRefreshingAndLoadMore();
                loadFail(2);
            }
        });
    }

    /**
     * toolbar设置
     */
    private void initToolbar() {
        mToolBar.setTitle("");
        mToolBar.setBackImage();
        mToolBar.back(this);
        mToolBar.setRightTv("发起请假");
        mToolBar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(VacateHomeActivity.this, VacateActivity.class), 100);
            }
        });
    }

    /**
     * 内容设置
     */
    private void initContent() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        initRecyclerView();
    }

    /**
     * 设置recyclerView
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonRecyclerAdapter<VacateResp>(this, mRespList, R.layout.item_vacate_home) {
            @Override
            public void convert(CommonRecyclerHolder holder, final VacateResp item, int position) {
                holder.setText(R.id.item_tv_title, item.childName.concat("的请假"));
                TextView tvMonth = holder.getView(R.id.item_tv_month);
                holder.setText(R.id.item_tv_start_time, "开始时间：".concat(StringUtils.longToDate(item.longStartTime, "yyyy-MM-dd HH:mm")));
                holder.setText(R.id.item_tv_end_time, "结束时间：".concat(StringUtils.longToDate(item.longEndTime, "yyyy-MM-dd HH:mm")));
                holder.setText(R.id.item_tv_time, StringUtils.getTimeDescription(item.modifyTime));
                TextView tvDesc = holder.getView(R.id.item_tv_desc);
                ImageView ivImage = holder.getView(R.id.item_iv);
                int currentMonth = StringUtils.getMonth(item.modifyTime);
                int month = StringUtils.getMonth(System.currentTimeMillis());
                if (position == 0) {
                    tvMonth.setVisibility(View.VISIBLE);
                    if (month == currentMonth) {
                        tvMonth.setText("本月请假");
                    } else {
                        tvMonth.setText(StringUtils.longToDate(item.modifyTime, "yyyy年MM月"));
                    }
                } else {
                    if (mRespList.size() > 1) {
                        int lastMonth = StringUtils.getMonth(mRespList.get(position - 1).modifyTime);
                        if (lastMonth == currentMonth) {
                            tvMonth.setVisibility(View.GONE);
                        } else {
                            tvMonth.setVisibility(View.VISIBLE);
                            tvMonth.setText(StringUtils.longToDate(item.modifyTime, "yyyy年MM月"));
                        }
                    }
                }
                Glide.with(VacateHomeActivity.this)
                        .load(item.icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.ic_my_child_default)
                        .transform(new GlideCricleTransform(VacateHomeActivity.this))
                        .into(ivImage);
                setTextStatus(tvDesc, item.masterTeacher, item.status);
                holder.mItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(VacateHomeActivity.this, VacateDetailActivity.class);
                        intent.putExtra("item", new Gson().toJson(item));
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void setTextStatus(TextView tv, String teacherName, int status) {
        switch (status) {
            case 2:
                tv.setText("审批通过");
                tv.setTextColor(ContextCompat.getColor(this, R.color.text_green));
                break;
            case 1:
                tv.setText(teacherName.concat("老师-班主任").concat("    审批中"));
                tv.setTextColor(ContextCompat.getColor(this, R.color.text_yellow));
                break;
            case 3:
                tv.setText("拒绝");
                tv.setTextColor(ContextCompat.getColor(this, R.color.text_red));
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        mRespList.clear();
        getVacateData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        getVacateData();
    }

    private void stopRefreshingAndLoadMore() {
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            mCurrentPage = 1;
            mRespList.clear();
            getVacateData();
        }
    }

    /**
     * 加载失败
     *
     * @param type 1 : 无数据   2: 无网络
     */
    private void loadFail(int type) {
        if (mRespList.size() != 0)
            return;
        rlPlaceHolder.setVisibility(View.VISIBLE);
        if (type == 1) {
            tvPlaceHolder.setText("抱歉，暂无数据");
            tvPlaceHolder.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.ic_empty, 0, 0);
            btnPlaceHolder.setVisibility(View.GONE);
        } else {
            tvPlaceHolder.setText("抱歉，网络不给力");
            tvPlaceHolder.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.ic_not_link_net, 0, 0);
            btnPlaceHolder.setVisibility(View.VISIBLE);
            btnPlaceHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rlPlaceHolder.setVisibility(View.GONE);
                    getVacateData();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!dispose.isDisposed())
            dispose.dispose();
    }
}
