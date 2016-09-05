package com.dachen.dgroupdoctorcompany.views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dachen.dgroupdoctorcompany.R;
import com.dachen.dgroupdoctorcompany.activity.AddSignInActivity;
import com.dachen.dgroupdoctorcompany.activity.MenuWithFABActivity;
import com.dachen.dgroupdoctorcompany.activity.SelectAddressActivity;
import com.dachen.dgroupdoctorcompany.utils.DataUtils.SinUtils;
import com.dachen.dgroupdoctorcompany.utils.HtmlTextViewEdit;
import com.dachen.medicine.common.utils.SharedPreferenceUtil;
import com.dachen.medicine.view.CustomDialog;

/**
 * Created by Burt on 2016/8/27.
 */
public class CustomButtonFragment  extends Fragment {
    TextView a;
    TextView b;
    TextView c;
    TextView d;
    long sixTime;
    FloatingActionButton leftCenterButton;
    MenuWithFABActivity activity;
    public FloatingActionMenu circleMenu;
    TextView tv_alertnotsign;
    public void setActivity(MenuWithFABActivity activity) {
            this.activity = activity;
        sixTime = com.dachen.medicine.common.utils.TimeUtils.getTime(6, 0);
    }
    public CustomButtonFragment() {
        this.activity = activity;
    }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_menu_with_custom_action_button, container, false);
            tv_alertnotsign = (TextView) rootView.findViewById(R.id.tv_alertnotsign);
            tv_alertnotsign.setText(HtmlTextViewEdit.getNotSignAlert());
            tv_alertnotsign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
                }
            });
            // Our action button is this time just a regular view!

            // Add some items to the menu. They are regular views as well!
            a = new TextView(getActivity());a.setGravity(Gravity.CENTER);
            a.setBackgroundResource(R.drawable.other_icon);
             b = new TextView(getActivity()); b.setGravity(Gravity.CENTER);
            b.setBackgroundResource(R.drawable.visit_icon);
            // c = new TextView(getActivity()); c.setGravity(Gravity.CENTER);
          //  c.setBackgroundResource(R.drawable.class_icon);
             d = new TextView(getActivity()); d.setGravity(Gravity.CENTER);
            d.setBackgroundResource(R.drawable.work_icon);
            final int subsize = getResources().getDimensionPixelSize(R.dimen.subs_action_button_size);
              final FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(subsize, subsize);
            a.setLayoutParams(tvParams);
            b.setLayoutParams(tvParams);
         // c.setLayoutParams(tvParams);
            d.setLayoutParams(tvParams);
            int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);

            int redActionButtonMargin = getResources().getDimensionPixelOffset(R.dimen.action_button_margin);
            ImageView fabIconStar = new ImageView(activity);
            final FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
            final FloatingActionButton.LayoutParams smallParams = new FloatingActionButton.LayoutParams(subsize, subsize);
            smallParams.setMargins(redActionButtonMargin,
                    redActionButtonMargin,
                    redActionButtonMargin,
                    redActionButtonMargin);
            starParams.setMargins(redActionButtonMargin,
                    redActionButtonMargin,
                    redActionButtonMargin,
                    redActionButtonMargin);
            fabIconStar.setLayoutParams(starParams);
            int redActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_margin);
            int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
            FloatingActionButton.LayoutParams fabIconStarParams =
                    new FloatingActionButton.LayoutParams(redActionButtonContentSize, redActionButtonContentSize);
            fabIconStarParams.setMargins(redActionButtonContentMargin,
                    redActionButtonContentMargin,
                    redActionButtonContentMargin,
                    redActionButtonContentMargin);

            leftCenterButton = new FloatingActionButton.Builder(activity)
                    .setContentView(fabIconStar, fabIconStarParams)
                    .setBackgroundDrawable(R.drawable.sign_flow_icon)
                    .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
                    .setLayoutParams(starParams)
                    .build();



         circleMenu = new FloatingActionMenu.Builder(getActivity())
        .setStartAngle(-158) // A whole circle!
        .setEndAngle(-22)
        .setRadius(getResources().getDimensionPixelSize(R.dimen.radius_large))
         .addSubActionView(a)
         .addSubActionView(d)
        .addSubActionView(b)
        .attachTo(leftCenterButton)
                    .build();
            circleMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                @Override
                public void onMenuOpened(FloatingActionMenu menu) {
                    leftCenterButton.setBackgroundResource(R.drawable.sing_close);
                    leftCenterButton.setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER, smallParams) ;
                    tv_alertnotsign.setVisibility(View.GONE);
                    //activity.timeStamp; 1472594400000  1472594400000
                    long nowTime = activity.timeStamp;

                    long twentyTime = com.dachen.medicine.common.utils.TimeUtils.getTime(0, 0);
                    long yesdayworktime = activity.ytdayWorkTime+2;
                    long yesdayworkofftime = activity.ytdayOffTime+3;
                    boolean haveworksign = haveSignWork();
                    if (sixTime <= nowTime)
                              {
                        if (haveworksign) {
                            d.setBackgroundResource(R.drawable.class_icon);
                        } else {
                            d.setBackgroundResource(R.drawable.work_icon);
                        }
                    } else if (sixTime > nowTime&&
                            nowTime>=twentyTime) {
                        if (yesdayworktime==0){
                            d.setBackgroundResource(R.drawable.work_icon);
                        }else if(yesdayworktime!=0&&(yesdayworktime<yesdayworkofftime)){
                            d.setBackgroundResource(R.drawable.work_icon);
                        }else if(yesdayworktime!=0&&(yesdayworktime>yesdayworkofftime)){
                           if (haveworksign) {
                                //说明打过下班卡了
                                d.setBackgroundResource(R.drawable.work_icon);
                            } else {
                                if (SharedPreferenceUtil.getLong(activity,sixTime+"",0)==0){
                                    tv_alertnotsign.setVisibility(View.VISIBLE);
                                }
                                //说明没有打过下班卡
                                d.setBackgroundResource(R.drawable.class_icon);
                            }
                        }
                    }
                }
                @Override
                public void onMenuClosed(FloatingActionMenu menu) {
                     leftCenterButton.setLayoutParams(starParams);
                    leftCenterButton.setBackgroundResource(R.drawable.sign_flow_icon);
                }
            });
            a.setOnClickListener(new myOnclickListener());
             b.setOnClickListener(new myOnclickListener());
           // c.setOnClickListener(new myOnclickListener());
            d.setOnClickListener(new myOnclickListener());
            return rootView;
        }
     class myOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            activity.compareDistance(activity.longitude,activity.latitude,activity);
            if(v  == a){
                if (activity.lengh<=activity.allowDistance&&activity.lengh>=0&&!TextUtils.isEmpty(activity.address)){

                    Intent intent = new Intent(activity, AddSignInActivity.class);
                    intent.putExtra("name", activity.address);
                    intent.putExtra("longitude", activity.longitude);
                    intent.putExtra("latitude", activity.latitude);
                    intent.putExtra("mode", AddSignInActivity.MODE_WORKING);
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(activity,SelectAddressActivity.class);
                    intent.putExtra("mode",AddSignInActivity.MODE_WORKING);
                    intent.putExtra("poi",activity.POI);
                    intent.putExtra("time",activity.timeStamp);//传入服务器时间
                    intent.putExtra("distance",activity.distance);
                    intent.putExtra("latitude",activity.latitude);
                    intent.putExtra("longitude",activity.longitude);
                    intent.putExtra("city",activity.city);
                    startActivity(intent);


                }
            }else if(v  == b){
                if (activity.lengh<=activity.allowDistance&&activity.lengh>=0&&!TextUtils.isEmpty(activity.address)){
                    SinUtils.signDefaultvisit(activity, activity.address,
                            activity.longitude, activity.latitude , activity.address,activity.allAddress,  "拜访", false,4);
                }else {
                   // SinUtils.signDefault(activity, activity.address, activity.latitude + "," + activity.longitude, "拜访", false);
                    choicePlace();
                }
            }else  if(v  == d){
                if (activity.mDataLists.size()>0){

                    workSing("下班", AddSignInActivity.SIGN_OFFWORKING);
                }else {
                    workSing("上班",AddSignInActivity.SIGN_WORKING);
                }
                }
            circleMenu.close(true);
            }

        }
    public void workSing(String work,int type){

        if (activity.lengh<=activity.allowDistance&&activity.lengh>=0&&!TextUtils.isEmpty(activity.address)){

            SinUtils.signDefault(activity,activity.address,activity.latitude+","+activity.longitude,work,1,-1);
        }else {
            SinUtils.signDefault(activity, activity.address, activity.latitude + "," + activity.longitude,
                    work, 1, type);
        }
    }

    public void choicePlace(){
        Intent intent2 = new Intent(activity,SelectAddressActivity.class);
        intent2.putExtra("mode",AddSignInActivity.MODE_VISIT);
        intent2.putExtra("type","signle");
        intent2.putExtra("poi",activity.POI);
        intent2.putExtra("time",activity.timeStamp);//传入服务器时间
        intent2.putExtra("distance",activity.distance);
        intent2.putExtra("latitude",activity.latitude);
        intent2.putExtra("longitude",activity.longitude);
        intent2.putExtra("city",activity.city);
        startActivity(intent2);
    }
    public boolean haveSignWork(){
        if (null!=activity.mDataLists&&activity.mDataLists.size()>0){
            for (int i=0;i<activity.mDataLists.size();i++){
                if (activity.mDataLists.get(i).tag!=null&&activity.mDataLists.get(i).tag.size()>0){
                    if (activity.mDataLists.get(i).tag.get(0).equals("上班")||activity.mDataLists.get(i).tag.get(0).equals("下班")){
                        return true;
                    }
                    ;
                }
            }
        }
        return false;
    }
    public void showAlertDialog(){
        final com.dachen.medicine.view.CustomDialog dialog = new CustomDialog(activity);
        dialog.showDialog("", "确认昨天忘记了下班签到", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dimissDialog();
                tv_alertnotsign.setVisibility(View.GONE);
                SharedPreferenceUtil.putLong(activity, sixTime + "", sixTime);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dimissDialog();
            }
        });
    }
}
