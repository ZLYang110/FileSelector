package com.zlylib.fileselectorlib.core;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.documentfile.provider.DocumentFile;

import com.zlylib.fileselectorlib.bean.EssDocumentFilter;
import com.zlylib.fileselectorlib.bean.EssFile;
import com.zlylib.fileselectorlib.bean.EssFileCountCallBack;
import com.zlylib.fileselectorlib.bean.EssFileFilter;
import com.zlylib.fileselectorlib.utils.DataTool;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * EssFileCountTask
 * Created by zhangliyang on 2020/6/20.
 */

public class EssFileCountTask extends AsyncTask<Void, Void, Void> {

    private int position;
    private String queryPath;
    private String[] types;
    private EssFileCountCallBack countCallBack;
    private int childFileCount = 0;
    private int childFolderCount = 0;
    private Boolean isSelectFolder = false;
    private String androidDataPath;
    private Context context;

    public EssFileCountTask(Context context, int position, String queryPath, String[] types, Boolean isSelectFolder, EssFileCountCallBack countCallBack) {
        this.position = position;
        this.queryPath = queryPath;
        this.types = types;
        this.isSelectFolder = isSelectFolder;
        this.countCallBack = countCallBack;
        this.androidDataPath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/";
        this.context = context.getApplicationContext();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        boolean isDocument = false;
        List fileList = null;
        if (DataTool.isAndroidR() && queryPath.startsWith(androidDataPath)) {
            DocumentFile file = DataTool.getDocumentFile(context, queryPath);
            DocumentFile[] files = file.listFiles();
            if (files.length == 0) {
                return null;
            }
            fileList = Arrays.asList(files);

            isDocument = true;

        } else {
            File file = new File(queryPath);
            File[] files = file.listFiles(new EssFileFilter(types));
            if (files == null) {
                return null;
            }
            fileList = Arrays.asList(files);
        }

        List<EssFile> tempFileList = isDocument ? EssFile.getEssFileListDocument(fileList,
                new EssDocumentFilter(types), isSelectFolder)
                : EssFile.getEssFileList(fileList, isSelectFolder);

        for (EssFile essFile :
                tempFileList) {
            if (essFile.isDirectory()) {
                childFolderCount++;
            } else {
                childFileCount++;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (countCallBack != null) {
            countCallBack.onFindChildFileAndFolderCount(position, String.valueOf(childFileCount), String.valueOf(childFolderCount));
        }
    }
}
