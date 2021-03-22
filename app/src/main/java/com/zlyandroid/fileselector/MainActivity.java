package com.zlyandroid.fileselector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zlylib.fileselectorlib.FileSelector;
import com.zlylib.fileselectorlib.bean.EssFile;
import com.zlylib.fileselectorlib.utils.Const;
import com.zlylib.mypermissionlib.RequestListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView tv_fragment,tv_open,tv_showFolder,tv_onlyFolder,tv_onlyMP4,tv_onlyIMG,tv_openColor,tv_backResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_fragment = findViewById(R.id.tv_fragment);
        tv_open = findViewById(R.id.tv_open);
        tv_backResult = findViewById(R.id.tv_backResult);
        tv_showFolder = findViewById(R.id.tv_showFolder);
        tv_onlyFolder = findViewById(R.id.tv_onlyFolder);
        tv_onlyMP4 = findViewById(R.id.tv_onlyMP4);
        tv_onlyIMG = findViewById(R.id.tv_onlyIMG);
        tv_openColor = findViewById(R.id.tv_openColor);
        tv_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downPermission(0);
            }
        });
        tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downPermission(1);
            }
        });
        tv_showFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downPermission(2);
            }
        });
        tv_onlyFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downPermission(3);
            }
        });
        tv_onlyMP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downPermission(4);
            }
        });
        tv_onlyIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downPermission(5);
            }
        });
        tv_openColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downPermission(6);
            }
        });
    }
    private void downPermission(final int i) {
         PermissionUtils.request(new RequestListener() {
            @Override
            public void onSuccess() {
                if(i == 0){
                    MainFragment.start(getApplicationContext());
                }else if(i == 1){
                    openFileSelector();
                }else if(i == 2){
                    openOnlyShowFolder();
                }else if(i == 3){
                    openOnlyFolder();
                }else if(i == 4){
                    openOnlymp3();
                }else if(i == 5){
                   openOnlyIMG();
                }else if(i == 6){
                    openOustomizeTitle();

                }

            }

            @Override
            public void onFailed() {
            }
        }, MainActivity.this, PermissionUtils.PermissionGroup.PERMISSIONS_STORAGE);
    }
    public static List<String> getFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            s.add(files[i].getAbsolutePath());
        }
        Log.e("list",s.toString());
        return s;
    }


    /**
     *  设置 onlyShowFolder() 只显示文件夹 后 再设置setFileTypes（）不生效
     *  设置 onlyShowFolder() 只显示文件夹 后 默认设置了onlySelectFolder（）
     *  设置 onlySelectFolder() 只能选择文件夹 后 默认设置了isSingle（）
     *  设置 isSingle() 只能选择一个 后 再设置了setMaxCount（） 不生效
     *
     */

    public void openFileSelector( ) {
        FileSelector.from(this)
               // .onlyShowFolder()  //只显示文件夹
                //.onlySelectFolder()  //只能选择文件夹
               // .isSingle() // 只能选择一个
                .setMaxCount(5) //设置最大选择数
                .setFileTypes("png","jpg", "doc","apk", "mp3", "gif", "txt", "mp4", "zip") //设置文件类型
                .setSortType(FileSelector.BY_NAME_ASC) //设置名字排序
                //.setSortType(FileSelector.BY_TIME_ASC) //设置时间排序
                //.setSortType(FileSelector.BY_SIZE_DESC) //设置大小排序
                //.setSortType(FileSelector.BY_EXTENSION_DESC) //设置类型排序
                .requestCode(1) //设置返回码
                .start();
    }

    public void openOnlyFolder( ) {
        FileSelector.from(this)
                .onlySelectFolder()  //只能选择文件夹
                .requestCode(1) //设置返回码
                .start();
    }
    public void openOnlyShowFolder( ) {
        FileSelector.from(this)
                .onlyShowFolder()  //只能选择文件夹
                .requestCode(1) //设置返回码
                .start();
    }
    public void openOnlymp3( ) {
        FileSelector.from(this)
                .setMaxCount(5) //设置最大选择数
                .setFileTypes( "mp4") //设置文件类型
                .requestCode(1) //设置返回码
                .start();
    }
    public void openOnlyIMG( ) {
        FileSelector.from(this)
                .setMaxCount(5) //设置最大选择数
                .setFileTypes( "png","jpg") //设置文件类型
                .requestCode(1) //设置返回码
                .start();
    }

    public void openOustomizeTitle( ) {
        FileSelector.from(this)
                .setTilteBg(R.color.titleBg) //不填写默认是： ?attr/colorPrimary
                .setTitleColor(R.color.themeRed)//不填写默认白色
                .setTitleLiftColor(R.color.text_accent)//不填写默认白色
                .setTitleRightColor(R.color.face_text)//不填写默认白色
                .setMaxCount(5) //设置最大选择数
                .setFileTypes("png","jpg", "doc","apk", "mp3", "gif", "txt", "mp4", "zip") //设置文件类型
                .setSortType(FileSelector.BY_NAME_ASC) //设置名字排序
                .requestCode(1) //设置返回码
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (data.getStringArrayListExtra(Const.EXTRA_RESULT_SELECTION) != null) {
                    ArrayList<String> essFileList = data.getStringArrayListExtra(Const.EXTRA_RESULT_SELECTION);
                    StringBuilder builder = new StringBuilder();
                    for (String file :
                            essFileList) {
                        builder.append(file).append("\n");
                    }
                    tv_backResult.setText(builder.toString());
                }
            }
        }
    }
}
