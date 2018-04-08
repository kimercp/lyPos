package com.kimersoft.lynqpos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.morefun.ypossdk.emv.card.CardProc;
import com.morefun.ypossdk.misc.Handware;
import com.morefun.ypossdk.misc.Misc;
import com.morefun.ypossdk.pub.Api;
import com.morefun.ypossdk.pub.CommEnum;
import com.morefun.ypossdk.pub.listener.PrintWriteListener;
import com.morefun.ypossdk.pub.listener.StartCSwiperListener;
import com.morefun.ypossdk.pub.param.PrintWriteParam;
import com.morefun.ypossdk.pub.param.StartCSwiperParam;
import com.morefun.ypossdk.pub.result.PrintWriteResult;
import com.morefun.ypossdk.pub.result.StartCSwiperResult;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    // Initialize the Api object
    private Api mfapi;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        makeFullscreen();

        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);

        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);

        mfapi = Api.Create(TestActivity.this);
        initAidCpak(this);
    }


    private static void initAidCpak(Context ctx){
        //(new File(Misc.m_app_data_path)).mkdirs();
        Misc.m_app_data_path = ctx.getFilesDir().getAbsolutePath() + "/";
        Misc.CreatInitDataFile(ctx, R.raw.f_capk_dat,  Misc.m_app_data_path + "F_capk.dat");
        Misc.CreatInitDataFile(ctx, R.raw.f_capk_idx,  Misc.m_app_data_path + "F_capk.idx");
        Misc.CreatInitDataFile(ctx, R.raw.f_tmaid_dat,  Misc.m_app_data_path + "F_tmaid.dat");
        Misc.CreatInitDataFile(ctx, R.raw.f_tmaid_idx,  Misc.m_app_data_path + "F_tmaid.idx");
    }

    @Override
    protected void onDestroy() {
        mfapi.Destory();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                Toast.makeText(this, "Magnetic (MagFlag) card reader", Toast.LENGTH_SHORT).show();
                magneticCardReader();
                break;
            case R.id.button3:
                contactLessCardReader();
                Toast.makeText(this, "ContactLess (RfFlag) card reader", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                chipCardReader();
                Toast.makeText(this, "Chip (IcFlag) card reader", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button5:
                Toast.makeText(this, "SerialNumber "+ mfapi.GetSn(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.button6:
                Toast.makeText(this, "CUPDeviceSn "+mfapi.GetCUPDeviceSn(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.button7:
                Toast.makeText(this, "Version "+mfapi.GetVersion(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.button8:
                allOfCardReader();
                Toast.makeText(this, "allOfCardReader", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private  void magneticCardReader(){
        alertDialog = new AlertDialog.Builder(this,R.style.AlertDialog_AppCompat)
                .setTitle("Card reader")
                .setMessage("Executing,Please wait")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mfapi.StopScanner();
                        mfapi.Cancel();
                    }
                })
                .show();

        StartCSwiperParam sp = new StartCSwiperParam();
        sp.setAmount("000000000100");//Setting amount,Units
        sp.setTransType(CommEnum.SDK_TRANS_TYPE.SDK_FUNC_SALE);//Transaction Type
        sp.setTrackenc(true);//Whether to enable track encryption

        sp.setMagFlag(true);//Use magnetic stripe card (card mode 2)
        sp.setIcFlag(false);//Use an IC card (card mode 3)
        sp.setRfFlag(false); // set true for contact less (card mode 4)

        mfapi.StartCSwiper( sp , new StartCSwiperListener() {
            @Override
            public void OnReturn(StartCSwiperResult ret) {
                StringBuilder sb = new StringBuilder();
                sb.append( "result: " + ret.getError().toString() + "\n" );


                sb.append( "function result description : " + ret.getProcRetName() + "\n\n"  );

                sb.append( "Card mode: " + ret.getCardType() + "\n" );

                sb.append( "Card Type Name :" + ret.getCardTypeName() + "\n\n" );

                sb.append( "Main account number: " + ret.getPan() + "\n"  );

                String expiryDate = ret.getExpData();
                sb.append( "Card is valid: Year 20" + expiryDate.substring(0,2) + " Month: " +expiryDate.substring(2,4)  + "\n" );
                sb.append( "Service code: " + ret.getServiceCode() + "\n\n"   );

                sb.append( "track one length: " + ret.getTrack1Len() + "\n"  );
                sb.append( "Track one message: " + ret.getsTrack1()  + "\n\n" );
                sb.append( "track two length: " + ret.getTrack2Len() + "\n"  );
                sb.append( "Track two message: " + ret.getsTrack2()  + "\n\n" );
                sb.append( "track three length: " + ret.getTrack3Len() + "\n"  );
                sb.append( "Track three message: " + ret.getsTrack3() + "\n\n"  );

                sb.append( "Card serial number: " + ret.getPansn() + "\n\n"  );

                sb.append( "IC card data:" + Misc.hex2asc(ret.getTlvData() , ret.getDatalen()*2, 0 )  + "\n"   );

                //ac.blockmsg(id,sb.toString());


                new AlertDialog.Builder(TestActivity.this, R.style.AlertDialog_AppCompat)
                        .setTitle("Retrive data")
                        .setMessage(sb.toString())
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeFirstAlertDialog();
                                CardProc.CardProcEnd();
                            }
                        })
                        .show();
            }
        });
    }

    private void chipCardReader() {
        alertDialog = new AlertDialog.Builder(this,R.style.AlertDialog_AppCompat)
                .setTitle("Card reader")
                .setMessage("Executing,Please wait")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mfapi.StopScanner();
                        mfapi.Cancel();
                    }
                })
                .show();

        StartCSwiperParam sp = new StartCSwiperParam();
        sp.setAmount("000000000100");//Setting amount,Units
        sp.setTransType(CommEnum.SDK_TRANS_TYPE.SDK_FUNC_SALE);//Transaction Type
        sp.setTrackenc(true);//Whether to enable track encryption

        sp.setMagFlag(false);//Use magnetic stripe card (card mode 2)
        sp.setIcFlag(true);//Use an IC card (card mode 3)
        sp.setRfFlag(false); // set true for contact less (card mode 4)

        mfapi.StartCSwiper( sp , new StartCSwiperListener() {
            @Override
            public void OnReturn(StartCSwiperResult ret) {
                StringBuilder sb = new StringBuilder();
                sb.append( "result: " + ret.getError().toString() + "\n" );


                sb.append( "function result description : " + ret.getProcRetName() + "\n\n"  );

                sb.append( "Card mode: " + ret.getCardType() + "\n" );

                sb.append( "Card Type Name :" + ret.getCardTypeName() + "\n\n" );

                sb.append( "Main account number: " + ret.getPan() + "\n"  );

                String expiryDate = ret.getExpData();
                sb.append( "Card is valid: Year 20" + expiryDate.substring(0,2) + " Month: " +expiryDate.substring(2,4)  + "\n" );
                sb.append( "Service code: " + ret.getServiceCode() + "\n\n"   );

                sb.append( "track one length: " + ret.getTrack1Len() + "\n"  );
                sb.append( "Track one message: " + ret.getsTrack1()  + "\n\n" );
                sb.append( "track two length: " + ret.getTrack2Len() + "\n"  );
                sb.append( "Track two message: " + ret.getsTrack2()  + "\n\n" );
                sb.append( "track three length: " + ret.getTrack3Len() + "\n"  );
                sb.append( "Track three message: " + ret.getsTrack3() + "\n\n"  );

                sb.append( "Card serial number: " + ret.getPansn() + "\n\n"  );

                sb.append( "IC card data:" + Misc.hex2asc(ret.getTlvData() , ret.getDatalen()*2, 0 )  + "\n"   );

                new AlertDialog.Builder(TestActivity.this, R.style.AlertDialog_AppCompat)
                        .setTitle("Retrive data")
                        .setMessage(sb.toString())
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeFirstAlertDialog();
                                CardProc.CardProcEnd();
                            }
                        })
                        .show();
            }
        });
    }

    private void contactLessCardReader() {
        alertDialog = new AlertDialog.Builder(this,R.style.AlertDialog_AppCompat)
                .setTitle("Card reader")
                .setMessage("Executing,Please wait")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mfapi.StopScanner();
                        mfapi.Cancel();
                    }
                })
                .show();

        StartCSwiperParam sp = new StartCSwiperParam();
        sp.setAmount("000000000100");//Setting amount,Units
        sp.setTransType(CommEnum.SDK_TRANS_TYPE.SDK_FUNC_SALE);//Transaction Type
        sp.setTrackenc(true);//Whether to enable track encryption
        sp.setMagFlag(false);//Use magnetic stripe card (card mode 2)
        sp.setIcFlag(false);//Use an IC card (card mode 3)
        sp.setRfFlag(true); // set true for contact less (card mode 4)

        mfapi.StartCSwiper( sp , new StartCSwiperListener() {
            @Override
            public void OnReturn(StartCSwiperResult ret) {
                StringBuilder sb = new StringBuilder();
                sb.append( "result: " + ret.getError().toString() + "\n" );
                sb.append( "function result description : " + ret.getProcRetName() + "\n\n"  );
                sb.append( "Card mode: " + ret.getCardType() + "\n" );
                sb.append( "Card Type Name :" + ret.getCardTypeName() + "\n\n" );
                sb.append( "Main account number: " + ret.getPan() + "\n"  );
                String expiryDate = ret.getExpData();
                sb.append( "Card is valid: Year 20" + expiryDate.substring(0,2) + " Month: " +expiryDate.substring(2,4)  + "\n" );
                sb.append( "Service code: " + ret.getServiceCode() + "\n\n"   );
                sb.append( "track one length: " + ret.getTrack1Len() + "\n"  );
                sb.append( "Track one message: " + ret.getsTrack1()  + "\n\n" );
                sb.append( "track two length: " + ret.getTrack2Len() + "\n"  );
                sb.append( "Track two message: " + ret.getsTrack2()  + "\n\n" );
                sb.append( "track three length: " + ret.getTrack3Len() + "\n"  );
                sb.append( "Track three message: " + ret.getsTrack3() + "\n\n"  );
                sb.append( "Card serial number: " + ret.getPansn() + "\n\n"  );
                sb.append( "IC card data:" + Misc.hex2asc(ret.getTlvData() , ret.getDatalen()*2, 0 )  + "\n"   );

                new AlertDialog.Builder(TestActivity.this, R.style.AlertDialog_AppCompat)
                        .setTitle("Retrive data")
                        .setMessage(sb.toString())
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeFirstAlertDialog();
                                //CardProc.CardProcEnd();
                            }
                        })
                        .show();
            }
        });
    }

    private void closeFirstAlertDialog() {
        if (alertDialog != null){
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    private void allOfCardReader() {
        alertDialog = new AlertDialog.Builder(this,R.style.AlertDialog_AppCompat)
                .setTitle("Card reader")
                .setMessage("Executing,Please wait")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mfapi.StopScanner();
                        mfapi.Cancel();
                    }
                })
                .show();

        StartCSwiperParam sp = new StartCSwiperParam();
        sp.setAmount("000000000100");//设置金额,单位分
        sp.setTransType(CommEnum.SDK_TRANS_TYPE.SDK_FUNC_SALE);//交易类型
        sp.setTrackenc(true);//是否启用磁道加密
        sp.setIcFlag(true);//使用IC卡
        sp.setMagFlag(true);//使用磁条卡
        sp.setRfFlag(true);

        //自定义tag列表
        List<byte[]> tags = new ArrayList<byte[]>();
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x27} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x10} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x37} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x36} );
        tags.add( new byte[]{ (byte)0x95} );
        tags.add( new byte[]{ (byte)0x9A} );
        tags.add( new byte[]{ (byte)0x9C} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x02} );
        tags.add( new byte[]{ (byte)0x5F ,(byte) 0x2A} );
        tags.add( new byte[]{ (byte)0x82} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x1A} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x03} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x33} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x34} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x35} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x1E} );
        tags.add( new byte[]{ (byte)0x84} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x09} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x41} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x63} );
        tags.add( new byte[]{ (byte)0x9F ,(byte) 0x26} );
        sp.setTags( tags );

        mfapi.StartCSwiper( sp , new StartCSwiperListener() {
            @Override
            public void OnReturn(StartCSwiperResult ret) {
                StringBuilder sb = new StringBuilder();
                sb.append( "结果:" + ret.getProcRetName() + "\n" );
                sb.append( "用卡方式:" + ret.getCardTypeName() + "\n" );
                sb.append( "主账号:" + ret.getPan() + "\n"  );
                sb.append( "卡有效期:" + ret.getExpData()  + "\n" );
                sb.append( "服务代码:" + ret.getServiceCode() + "\n"   );
                sb.append( "一磁道长度:" + ret.getTrack1Len() + "\n"  );
                sb.append( "磁道一信息:" + ret.getsTrack1()  + "\n" );
                sb.append( "二磁道长度:" + ret.getTrack2Len() + "\n"  );
                sb.append( "磁道二信息:" + ret.getsTrack2()  + "\n" );
                sb.append( "三磁道长度:" + ret.getTrack3Len() + "\n"  );
                sb.append( "磁道三信息:" + ret.getsTrack3() + "\n"  );
                sb.append( "卡片序列号:" + ret.getPansn() + "\n"  );
                sb.append( "IC卡数据:" + Misc.hex2asc(ret.getTlvData() , ret.getDatalen()*2, 0 )  + "\n"   );

                blockmsg(sb.toString());
                // CardProc.CardProcEnd();
            }
        });
    }

    private void blockmsg(String msg) {
        closeFirstAlertDialog();
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Card reader").setMessage(msg);

        DialogInterface.OnClickListener r = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, final int arg1) {
                CardProc.CardProcEnd();
                // TODO Auto-generated method stub
            }
        };
        dlg.setPositiveButton("确认", r);
        dlg.show();
    }




    public static String getprintstytle(int s){
        StringBuilder sb = new StringBuilder();

        if(s == 1){	//16
            sb.append(Handware.Manager.Print_cn_font_size(1));
            sb.append(Handware.Manager.Print_en_font_size(1));
            sb.append(Handware.Manager.Print_cn_font_zoom(1, 1));
            sb.append(Handware.Manager.Print_en_font_zoom(1, 1));
        }
        else if(s == 2){	//24
            sb.append(Handware.Manager.Print_cn_font_size(0));
            sb.append(Handware.Manager.Print_en_font_size(6));
            sb.append(Handware.Manager.Print_cn_font_zoom(2, 2));
            sb.append(Handware.Manager.Print_en_font_zoom(1, 1));
        }
        else if(s == 3){	//32
            sb.append(Handware.Manager.Print_cn_font_size(0));
            sb.append(Handware.Manager.Print_en_font_size(6));
            sb.append(Handware.Manager.Print_cn_font_zoom(2, 4));
            sb.append(Handware.Manager.Print_en_font_zoom(1, 2));
        }
        else if(s == 4){	//48
            sb.append(Handware.Manager.Print_cn_font_size(0));
            sb.append(Handware.Manager.Print_en_font_size(6));
            sb.append(Handware.Manager.Print_cn_font_zoom(4, 4));
            sb.append(Handware.Manager.Print_en_font_zoom(2, 2));
        }
        else if(s == 5){	//8-16
            sb.append(Handware.Manager.Print_cn_font_size(1));
            sb.append(Handware.Manager.Print_en_font_size(5));
            sb.append(Handware.Manager.Print_cn_font_zoom(1, 1));
            sb.append(Handware.Manager.Print_en_font_zoom(1, 1));
        }

        return sb.toString();
    }

    private String getprintitem(String head, String val)
    {
        StringBuffer sb = new StringBuffer();
        sb.append( getprintstytle(1) );
        sb.append(head);
        sb.append("\r\n");

        sb.append( getprintstytle(2));
        sb.append(val);
        sb.append("\r\n");
        return sb.toString();
    }

    private void Print() {
        StringBuilder sbprint = new StringBuilder();
        //打印浓度
        sbprint.append( Handware.Manager.Print_heat_factor(15) );
        //设置字体大小
        sbprint.append( getprintstytle(4) );
        //设置字体居中
        sbprint.append( Handware.Manager.Print_align(1) ) ;
        sbprint.append( "银联POS签购单\r\n");
        //设置左对齐
        sbprint.append( Handware.Manager.Print_align(0));
        sbprint.append( getprintstytle(2));
        sbprint.append( "==============================\r\n");

        sbprint.append( getprintitem("商户名称(MERCHANT NAME)", "测试商户") );
        sbprint.append( getprintitem("商户编号(MERCHANT NO.)", "20321545656687") );
        sbprint.append( getprintitem("终端编号(TERMINAL NO.)", "25689753"));
        sbprint.append( getprintitem("操作员号(OPERATOR NO.)", "01"));
        sbprint.append( getprintitem("发卡行号(ISS NO)", "1544"));
        sbprint.append( getprintitem("收单行号(ACQ NO)", "0121"));

        sbprint.append( getprintstytle(1) ) ;
        sbprint.append("卡号(CARD NUMBER)\r\n");

        sbprint.append(getprintstytle(3) );
        sbprint.append("62179390*****3426");
        sbprint.append("\r\n");


        sbprint.append(getprintstytle(1) );
        sbprint.append("交易类型(TRANS TYPE)");
        sbprint.append("\r\n");
        sbprint.append(getprintstytle(3) );
        sbprint.append("消费(S)");
        sbprint.append("\r\n");

        sbprint.append(getprintitem("有效期(EXP DATE)", "2029"));

        sbprint.append(getprintitem("批次号(BATCH NO)", "000012"));
        sbprint.append(getprintitem("凭证号(VOUCHER NO)", "000001"));
        sbprint.append(getprintitem("授权码(AUTH NO)", "56890547"));

        sbprint.append(getprintitem("交易日期/时间(DATE/TIME)", "2016-05-23 16:50:32"));
        sbprint.append(getprintitem("交易参考号(REFER NO)", "365897453214"));

        sbprint.append(getprintstytle(1));
        sbprint.append("金额(AMOUNT)");
        sbprint.append("\r\n");

        sbprint.append(getprintstytle(3));
        sbprint.append("RMB ");
        sbprint.append( "10.00");
        sbprint.append("\r\n");

        sbprint.append(getprintstytle(2));
        sbprint.append("==============================\r\n");

        sbprint.append(getprintstytle(1));
        //输出空行
        sbprint.append( Handware.Manager.Print_row_space(24));
        sbprint.append("持卡人签名(CARD HOLDER SIGNATURE)\r\n");
        sbprint.append( Handware.Manager.Print_row_space(72));
        sbprint.append("--------------------------------------------\r\n");

        sbprint.append("本人确认以上交易，同意将其记入本卡账户 I ACKNOWLEDGE	SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICES\r\n");

        sbprint.append(getprintstytle(2));
        sbprint.append("商户存根(MERCHANT COPY)\r\n");

        sbprint.append(Handware.Manager.Print_row_space(24));

        sbprint.append(getprintstytle(1));
        sbprint.append("---X---X---X---X---X--X--X--X--X--X--X--X--X--\r\n");

        sbprint.append(Handware.Manager.Print_row_space(108));


        //打印输出
        //ac.showwait("提示" , "正在打印" );
        Handware.Manager.PrintInit();
        PrintWriteParam pwparam = new PrintWriteParam();
        pwparam.setPrintdata( sbprint.toString() );
        mfapi.PrintWrite(  pwparam , new PrintWriteListener(){
            @Override
            public void OnReturn(PrintWriteResult ret) {
                //获取打印结果
                int printret = ret.getRet();
                //ac.closewait();
                if (printret != 0 )	{
                    //ac.blockmsg( "提示","打印机缺纸,请装纸"  );
                }
            }
        });

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
