package com.zlylib.fileselectorlib.bean;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.FileFilter;

/**
 * EssFileFilter
 * 不显示隐藏文件、文件夹
 */
public class EssDocumentFilter {
    private String[] mTypes;

    public EssDocumentFilter(String[] types) {
        this.mTypes = types;
    }


    public boolean accept(EssFile file) {
        if (file.isDirectory()) {
            return true;
        }
        if (mTypes != null && mTypes.length > 0) {
            for (String mType : mTypes) {
                String name = file.getName();
                if (name == null) {
                    name = "";
                }
                if ((name.endsWith(mType.toLowerCase()) || name.endsWith(mType.toUpperCase()))) {
//                if (FileUtils.getMimeType(file.getAbsolutePath()).equalsIgnoreCase(MimeTypeMap.getSingleton().getMimeTypeFromExtension(mType))) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
