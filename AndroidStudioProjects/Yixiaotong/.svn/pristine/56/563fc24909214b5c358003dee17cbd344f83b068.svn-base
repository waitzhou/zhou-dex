package com.zhexinit.yixiaotong.function.home;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.base.Constant;
import com.zhexinit.yixiaotong.event.MainRadioChangeEvent;
import com.zhexinit.yixiaotong.function.BaseResp;
import com.zhexinit.yixiaotong.function.UserWarehouse;
import com.zhexinit.yixiaotong.function.home.fragment.CampusFragment;
import com.zhexinit.yixiaotong.function.home.fragment.HomeFragment;
import com.zhexinit.yixiaotong.function.home.fragment.NoBindChildFragment;
import com.zhexinit.yixiaotong.function.home.fragment.PositionFragment;
import com.zhexinit.yixiaotong.function.home.fragment.UserFragment;
import com.zhexinit.yixiaotong.function.mine.entity.CheckUpdateResp;
import com.zhexinit.yixiaotong.function.mine.entity.UserInfoResp;
import com.zhexinit.yixiaotong.rxjavamanager.event.RxBus;
import com.zhexinit.yixiaotong.rxjavamanager.interfaces.ResultCallBack;
import com.zhexinit.yixiaotong.utils.Config;
import com.zhexinit.yixiaotong.utils.FragmentTabUtil;
import com.zhexinit.yixiaotong.utils.GsonUtil;
import com.zhexinit.yixiaotong.utils.LogUtils;
import com.zhexinit.yixiaotong.utils.SharePerfUtils;
import com.zhexinit.yixiaotong.widget.UpdateDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:zhousx
 * date:2018/11/5
 * description:首页主页面
 */
public class MainActivity extends BaseActivity implements FragmentTabUtil.RadioGroupCheckChanged {

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;

    List<Fragment> mFragments;
    boolean isNoChild;
    FragmentTabUtil util;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initRadioChangeEnent();
        initData();
        util = new FragmentTabUtil(getSupportFragmentManager(), mFragments, R.id.container, mRadioGroup, this);

        CheckUpdate();
        LogUtils.e("渠道===" + Config.getAppMetaData(this, "UMENG_CHANNEL"));
    }


    private void initData() {
        mFragments = new ArrayList<>();
        UserInfoResp userInfoResp = GsonUtil.getInstance().getGson().
                fromJson(SharePerfUtils.getString(Constant.KEY.USER_INFO), UserInfoResp.class);
        isNoChild = userInfoResp.userChildren == null || userInfoResp.userChildren.size() == 0;
        if (isNoChild) {
            mFragments.add(NoBindChildFragment.newInstance());
            mFragments.add(NoBindChildFragment.newInstance());
            mFragments.add(NoBindChildFragment.newInstance());
        } else {
            mFragments.add(HomeFragment.newInstance(this));
            mFragments.add(PositionFragment.newInstance());
            mFragments.add(CampusFragment.newInstance());
        }
        mFragments.add(UserFragment.newInstance());
    }

    private Disposable dispose;

    private void initRadioChangeEnent() {
        dispose = RxBus.getInstance().registerEvent(MainRadioChangeEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MainRadioChangeEvent>() {
                    @Override
                    public void accept(MainRadioChangeEvent event) {
                        //if (mFragments.get(event.position-1).isAdded())mRadioGroup.check(event.position);
                        // util.onCheckedChanged(mRadioGroup,event.position);
                        RadioButton button = (RadioButton) mRadioGroup.getChildAt(event.position - 1);
                        button.toggle();
                    }
                });
    }

    private int mCurrentFragmentPosition;

    @Override
    public void onRgCheckChanged(int position) {
        mCurrentFragmentPosition = position;
    }

    public void refreshPage() {
        Log.e("", "refreshPage: -------------------->");
        if (!isNoChild) {
            HomeFragment homeFragment = (HomeFragment) mFragments.get(0);
            if (homeFragment.isAdded())
                homeFragment.refreshPage();
            PositionFragment positionFragment = (PositionFragment) mFragments.get(1);
            if (positionFragment.isAdded())
                positionFragment.refreshPage();
        }
    }

    private void CheckUpdate() {
        Map<String, Object> map = new HashMap<>();
        map.put("platform", "android");
        map.put("project", 1);//项目 1：家长端，2：教师端
        map.put("version", Config.getVersionName(this));
        UserWarehouse.getInstance(this).checkUpdate(map, new ResultCallBack<BaseResp<CheckUpdateResp>>() {
            @Override
            public void onSuccess(BaseResp<CheckUpdateResp> baseResp) {
                if (baseResp.code == 0 && baseResp.result != null) {
                    if (baseResp.result.forceUpdate != 2 && SharePerfUtils.getBoolean(Constant.KEY.NOT_UPDATE, false))
                        return;//非强制更新只提示一次
                    UpdateDialog.create(MainActivity.this, baseResp.result).show();
                }
            }

            @Override
            public void onFail(String response) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!dispose.isDisposed())
            dispose.dispose();
    }
}
