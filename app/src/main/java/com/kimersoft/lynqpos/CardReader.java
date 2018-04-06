package com.kimersoft.lynqpos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.morefun.ypossdk.emv.card.CardProc;
import com.morefun.ypossdk.misc.Misc;
import com.morefun.ypossdk.pub.Api;
import com.morefun.ypossdk.pub.CommEnum;
import com.morefun.ypossdk.pub.listener.StartCSwiperListener;
import com.morefun.ypossdk.pub.param.StartCSwiperParam;
import com.morefun.ypossdk.pub.result.StartCSwiperResult;

import java.util.ArrayList;
import java.util.List;

public class CardReader extends AppCompatActivity {

    // Initialize the Api object
    private Api mfapi;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_reader);
        makeFullscreen();

        mfapi = Api.Create(CardReader.this);
        initAidCpak(this);

        allOfCardReader();
    }

    private static void initAidCpak(Context ctx) {
        //(new File(Misc.m_app_data_path)).mkdirs();
        Misc.m_app_data_path = ctx.getFilesDir().getAbsolutePath() + "/";
        Misc.CreatInitDataFile(ctx, R.raw.f_capk_dat, Misc.m_app_data_path + "F_capk.dat");
        Misc.CreatInitDataFile(ctx, R.raw.f_capk_idx, Misc.m_app_data_path + "F_capk.idx");
        Misc.CreatInitDataFile(ctx, R.raw.f_tmaid_dat, Misc.m_app_data_path + "F_tmaid.dat");
        Misc.CreatInitDataFile(ctx, R.raw.f_tmaid_idx, Misc.m_app_data_path + "F_tmaid.idx");
    }

    @Override
    protected void onDestroy() {
        mfapi.Destory();
        super.onDestroy();
    }

    private void allOfCardReader() {
        StartCSwiperParam sp = new StartCSwiperParam();
        sp.setAmount("000000000100");//设置金额,单位分
        sp.setTransType(CommEnum.SDK_TRANS_TYPE.SDK_FUNC_SALE);//交易类型
        sp.setTrackenc(true);//是否启用磁道加密
        sp.setIcFlag(true);//使用IC卡
        sp.setMagFlag(true);//使用磁条卡
        sp.setRfFlag(true);

        //自定义tag列表
        List<byte[]> tags = new ArrayList<byte[]>();
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x27});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x10});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x37});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x36});
        tags.add(new byte[]{(byte) 0x95});
        tags.add(new byte[]{(byte) 0x9A});
        tags.add(new byte[]{(byte) 0x9C});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x02});
        tags.add(new byte[]{(byte) 0x5F, (byte) 0x2A});
        tags.add(new byte[]{(byte) 0x82});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x1A});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x03});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x33});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x34});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x35});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x1E});
        tags.add(new byte[]{(byte) 0x84});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x09});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x41});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x63});
        tags.add(new byte[]{(byte) 0x9F, (byte) 0x26});
        sp.setTags(tags);

        mfapi.StartCSwiper(sp, new StartCSwiperListener() {
            @Override
            public void OnReturn(StartCSwiperResult ret) {
                StringBuilder sb = new StringBuilder();
                sb.append("结果:" + ret.getProcRetName() + "\n");
                sb.append("用卡方式:" + ret.getCardTypeName() + "\n");
                sb.append("主账号:" + ret.getPan() + "\n");
                sb.append("卡有效期:" + ret.getExpData() + "\n");
                sb.append("服务代码:" + ret.getServiceCode() + "\n");
                sb.append("一磁道长度:" + ret.getTrack1Len() + "\n");
                sb.append("磁道一信息:" + ret.getsTrack1() + "\n");
                sb.append("二磁道长度:" + ret.getTrack2Len() + "\n");
                sb.append("磁道二信息:" + ret.getsTrack2() + "\n");
                sb.append("三磁道长度:" + ret.getTrack3Len() + "\n");
                sb.append("磁道三信息:" + ret.getsTrack3() + "\n");
                sb.append("卡片序列号:" + ret.getPansn() + "\n");
                sb.append("IC卡数据:" + Misc.hex2asc(ret.getTlvData(), ret.getDatalen() * 2, 0) + "\n");

                //Toast.makeText(CardReader.this, sb.toString(), Toast.LENGTH_SHORT).show();
                displayCardData(sb.toString());
                CardProc.CardProcEnd();
                //finish();
            }
        });
    }

    private void displayCardData(String msg) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Card reader").setMessage(msg);
        DialogInterface.OnClickListener r = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, final int arg1) {
                CardProc.CardProcEnd();
                mfapi.StopScanner();
                mfapi.Cancel();
                finish();
            }
        };
        dlg.setPositiveButton(R.string.ok, r);
        dlg.show();
    }

    // hide UI action bar and make the app fullscreen
    public void makeFullscreen() {
        getSupportActionBar().hide();
        // API 19 (Kit Kat)
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        } else {
            if (Build.VERSION.SDK_INT > 10) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }
}
