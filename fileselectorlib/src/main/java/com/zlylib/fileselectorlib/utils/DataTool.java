package com.zlylib.fileselectorlib.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.UriPermission;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTool {
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    public static long getLong(String str) {
        if (str == null) return 0;
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }

    public static String getTime(long time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(time);
        return formatter.format(curDate);
    }


    public static String root = Environment.getExternalStorageDirectory().getPath() + "/";

    public static String treeToPath(String path) {
        String path2 = null;
        if (path.contains("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata/document/primary")) {
            path2 = path.replace("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata/document/primary%3A", root);
            path2 = path2.replace("%2F", "/");
        }
        return path2;
    }


    //判断是否已经获取了Data权限，改改逻辑就能判断其他目录，懂得都懂
    public static boolean isGrant(Context context) {
        for (UriPermission persistedUriPermission : context.getContentResolver().getPersistedUriPermissions()) {
            if (persistedUriPermission.isReadPermission() && persistedUriPermission.getUri().toString().equals("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata")) {
                return true;
            }
        }
        return false;
    }

    //直接返回DocumentFile
    public static DocumentFile getDocumentFilePath(Context context, String path, String sdCardUri) {
        DocumentFile document = DocumentFile.fromTreeUri(context, Uri.parse(sdCardUri));
        String[] parts = path.split("/");
        for (int i = 3; i < parts.length; i++) {
            document = document.findFile(parts[i]);
        }
        return document;
    }

    //转换至uriTree的路径
    public static String changeToUri(String path) {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        String path2 = path.replace("/storage/emulated/0/", "").replace("/", "%2F");
        return "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata/document/primary%3A" + path2;
    }

    public static DocumentFile getDocumentFile(Context context, String path) {
        return DocumentFile.fromTreeUri(context, Uri.parse(DataTool.changeToUri(path)));
    }


    //获取指定目录的权限
    public static Intent startFor(String path) {
        String uri = changeToUri(path);
        Uri parse = Uri.parse(uri);
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
        intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                        | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, parse);
        }
        return intent;
    }

    public static Intent startForData(Context context) {
        DocumentFile document = DocumentFile.fromTreeUri(context,
                Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata"));
        Uri uri = document.getUri();
        Intent intent1 = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent1.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
        }
        return intent1;
    }

    public static boolean isAndroidR() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }


    public static String getFileName(Context context, Uri uri) {
        if (uri == null) return "";

        String fileName = "";
        try {
            String path = uri.getPath();
            File file = new File(path == null ? "" : path);
            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme()) && file.exists()) {
                fileName = file.getName();
            } else {
                DocumentFile documentFile = DocumentFile.fromSingleUri(context, uri);
                if (documentFile != null) {
                    fileName = documentFile.getName();
                } else {
                    fileName = uri.toString().substring(uri.toString().lastIndexOf("/") + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static void hideKeyboard(final View view) {
        try {
            InputMethodManager imm =
                    (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
