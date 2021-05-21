package com.zlylib.fileselectorlib.core;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.documentfile.provider.DocumentFile;

import com.zlylib.fileselectorlib.bean.EssDocumentFilter;
import com.zlylib.fileselectorlib.bean.EssFile;
import com.zlylib.fileselectorlib.bean.EssFileFilter;
import com.zlylib.fileselectorlib.bean.EssFileListCallBack;
import com.zlylib.fileselectorlib.utils.DataTool;
import com.zlylib.fileselectorlib.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * EssFileListTask
 * Created by 李波 on 2018/3/5.
 */

public class EssFileListTask extends AsyncTask<Void, Void, List<EssFile>> {

    private List<EssFile> mSelectedFileList;
    private String queryPath;
    private String[] types;
    private int mSortType;
    private EssFileListCallBack callBack;
    private Boolean isSelectFolder = false;
    private String androidDataPath;
    private Context context;

    public EssFileListTask(Context context, List<EssFile> mSelectedFileList, String queryPath, String[] types, int mSortType, Boolean isSelectFolder, EssFileListCallBack fileCallBack) {
        this.mSelectedFileList = mSelectedFileList;
        this.queryPath = queryPath;
        this.types = types;
        this.mSortType = mSortType;
        this.isSelectFolder = isSelectFolder;
        this.callBack = fileCallBack;
        this.androidDataPath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/";
        this.context = context.getApplicationContext();
    }

    @Override
    protected List<EssFile> doInBackground(Void... voids) {
        boolean isDocument = false;
        List fileList = null;
        if (DataTool.isAndroidR() && queryPath.startsWith(androidDataPath)) {
            DocumentFile file = DataTool.getDocumentFile(context, queryPath);
            DocumentFile[] files = file.listFiles();
            if (files.length == 0) {
                return new ArrayList<>();
            }
            fileList = Arrays.asList(files);

            isDocument = true;

        } else {
            File file = new File(queryPath);
            File[] files = file.listFiles(new EssFileFilter(types));
            if (files == null) {
                return new ArrayList<>();
            }
            fileList = Arrays.asList(files);
        }

        List<EssFile> tempFileList = isDocument ? EssFile.getEssFileListDocument(fileList,
                new EssDocumentFilter(types), isSelectFolder)
                : EssFile.getEssFileList(fileList, isSelectFolder);

        if (mSortType == FileUtils.BY_NAME_ASC) {
            Collections.sort(tempFileList, new FileUtils.SortByName());
        } else if (mSortType == FileUtils.BY_NAME_DESC) {
            Collections.sort(tempFileList, new FileUtils.SortByName());
            Collections.reverse(tempFileList);
        } else if (mSortType == FileUtils.BY_TIME_ASC) {
            Collections.sort(tempFileList, new FileUtils.SortByTime());
        } else if (mSortType == FileUtils.BY_TIME_DESC) {
            Collections.sort(tempFileList, new FileUtils.SortByTime());
            Collections.reverse(tempFileList);
        } else if (mSortType == FileUtils.BY_SIZE_ASC) {
            Collections.sort(tempFileList, new FileUtils.SortBySize());
        } else if (mSortType == FileUtils.BY_SIZE_DESC) {
            Collections.sort(tempFileList, new FileUtils.SortBySize());
            Collections.reverse(tempFileList);
        } else if (mSortType == FileUtils.BY_EXTENSION_ASC) {
            Collections.sort(tempFileList, new FileUtils.SortByExtension());
        } else if (mSortType == FileUtils.BY_EXTENSION_DESC) {
            Collections.sort(tempFileList, new FileUtils.SortByExtension());
            Collections.reverse(tempFileList);
        }

        for (EssFile selectedFile :
                mSelectedFileList) {
            for (int i = 0; i < tempFileList.size(); i++) {
                if (selectedFile.getAbsolutePath().equals(tempFileList.get(i).getAbsolutePath())) {
                    tempFileList.get(i).setChecked(true);
                    break;
                }
            }
        }
        return tempFileList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<EssFile> essFileList) {
        if (callBack != null) {
            callBack.onFindFileList(queryPath, essFileList);
        }
    }
}
