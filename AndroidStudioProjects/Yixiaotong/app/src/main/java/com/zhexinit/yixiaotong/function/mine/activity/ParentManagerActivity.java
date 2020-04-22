package com.zhexinit.yixiaotong.function.mine.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.function.BaseResp;
import com.zhexinit.yixiaotong.function.ChildrenWarehouse;
import com.zhexinit.yixiaotong.function.home.fragment.UserFragment;
import com.zhexinit.yixiaotong.function.mine.entity.ParentResp;
import com.zhexinit.yixiaotong.rxjavamanager.interfaces.ResultCallBack;
import com.zhexinit.yixiaotong.utils.CanScrollLayoutManager;
import com.zhexinit.yixiaotong.utils.GlideCricleTransform;
import com.zhexinit.yixiaotong.utils.StringUtils;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerAdapter;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerHolder;
import com.zhexinit.yixiaotong.widget.CommonDialog;
import com.zhexinit.yixiaotong.widget.NoLoadMoreFooter;
import com.zhexinit.yixiaotong.widget.ToolBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by:xukun
 * date:2018/11/15
 * description:
 */
public class ParentManagerActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    ToolBar toolBar;
    @BindView(R.id.recycler_view)
    RecyclerView recycleView;
    @BindView(R.id.line_add_parent)
    LinearLayout line_add_parent;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout refreshLayout;

    private String child_id; //intent 传过来的childId
    private List<ParentResp> parentList = new ArrayList<>();
    private boolean primaryAccount;//是否对这个孩子是主账号


    @Override
    protected int getLayoutId() {
        return R.layout.activity_parent_manager;
    }

    @Override
    protected void init() {
        child_id = getIntent().getStringExtra(UserFragment.CHILD_ID);
        primaryAccount = getIntent().getBooleanExtra("primaryAccount", false);
        toolBar.back(this);
        toolBar.setBackImage();
        toolBar.setTitle("家长管理");
        line_add_parent.setVisibility(primaryAccount ? View.VISIBLE : View.GONE);

        setVectogram();
        initRecyclerView();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getChildParent();
            }
        });
        refreshLayout.setRefreshFooter(new NoLoadMoreFooter(this,refreshLayout));
    }

    private void setVectogram() {
        Typeface icon = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.tv_path));
        TextView text_add = findViewById(R.id.text_add);
        text_add.setTypeface(icon);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChildParent();
    }

    //初始化recycleView
    CommonRecyclerAdapter adapter;

    private void initRecyclerView() {
        recycleView.setLayoutManager(new CanScrollLayoutManager(this));
        adapter = new CommonRecyclerAdapter<ParentResp>(this, parentList, R.layout.item_parent) {
            @Override
            public void convert(CommonRecyclerHolder holder, final ParentResp item, final int position) {

                TextView name = holder.itemView.findViewById(R.id.text_name);
                TextView phone = holder.itemView.findViewById(R.id.text_phone);
                TextView main = holder.itemView.findViewById(R.id.text_main);
                ImageView image = holder.itemView.findViewById(R.id.img_icon);
                name.setText(item.relation);
                phone.setText(item.userMobile);
                main.setVisibility(item.primaryAccount ? View.VISIBLE : View.GONE);

                Glide.with(ParentManagerActivity.this)
                        .load(item.userIcon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(getResources().getDrawable(R.mipmap.ic_main_parent_default))
                        .transform(new GlideCricleTransform(ParentManagerActivity.this))
                        .into(image);

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (primaryAccount && !item.primaryAccount)
                            doDeleteParent(item.userId, position);
                        return false;
                    }
                });
            }
        };
        recycleView.setAdapter(adapter);
    }

    @OnClick({R.id.line_add_parent})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_add_parent:
                startActivity(new Intent(ParentManagerActivity.this, AddParentActivity.class).putExtra(UserFragment.CHILD_ID, child_id));
                break;
        }
    }

    private void doDeleteParent(final String userId, final int position) {
        final CommonDialog dialog = new CommonDialog(this, "", "是否删除该家长");
        dialog.setSumbitClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteParent(userId, position);
            }
        });
        dialog.noTitle();
        dialog.show();
    }

    //获取家长信息列表
    private void getChildParent() {
        if (StringUtils.isEmpty(child_id)) return;
        Map<String, Object> map = new HashMap<>();
        map.put("childId", child_id);
        ChildrenWarehouse.getInstance(this).getChildParent(map, new ResultCallBack<BaseResp<List<ParentResp>>>() {
            @Override
            public void onSuccess(BaseResp<List<ParentResp>> baseResp) {
                if (baseResp.code == 0) {
                    parentList.clear();
                    parentList.addAll(baseResp.result);
                    adapter.notifyDataSetChanged();
                } else showToast(baseResp.message);
                refreshLayout.finishRefresh();
            }

            @Override
            public void onFail(String response) {
                showToast(response);
                refreshLayout.finishRefresh();
            }
        });
    }

    //删除单个家长
    private void deleteParent(String userId, final int position) {
        if (StringUtils.isEmpty(child_id)) return;
        showProgressDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("childId", child_id);
        map.put("userId", userId);
        ChildrenWarehouse.getInstance(this).deleteParent(map, new ResultCallBack<BaseResp>() {
            @Override
            public void onSuccess(BaseResp baseResp) {
                if (baseResp.code == 0) {
                    getChildParent();
                }
                hideProgressDialog();
                showToast(baseResp.message);
            }

            @Override
            public void onFail(String response) {
                hideProgressDialog();
                showToast(response);
            }
        });
    }

}
