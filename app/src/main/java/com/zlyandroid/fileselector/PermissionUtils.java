package com.zlyandroid.fileselector;

import android.Manifest;
import android.content.Context;

import androidx.annotation.NonNull;

import com.zlylib.mypermissionlib.MyPermission;
import com.zlylib.mypermissionlib.RequestInterceptor;
import com.zlylib.mypermissionlib.RequestListener;
import com.zlylib.mypermissionlib.RuntimeRequester;


/**
 * @author zhangliyang
 * dec  在申请多个权限时，本框架采用排队方式申请，即先申请第一个权限，第一个申请成功后再进行下一个的申请流程，第一个失败则为本次申请失败。
 * GitHub: https://github.com/ZLYang110
 *
 */
public class PermissionUtils {



    public static final class PermissionGroup {
        public static final String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };


    }


    public static RuntimeRequester request(RequestListener listener, Context context , String[] permissions) {
        return MyPermission.with(context)
                .runtime(1)
                .permissions(permissions)
                .onBeforeRequest(new RequestInterceptor<String>() {
                    @Override
                    public void intercept(@NonNull String data, @NonNull Executor executor) {
                        // TODO 在每个权限申请之前调用，多次回调。可弹窗向用户说明下面将进行某个权限的申请。
                        executor.execute();
                    }
                })
                .onBeenDenied(new RequestInterceptor<String>() {
                    @Override
                    public void intercept(@NonNull String data, @NonNull Executor executor) {
                        // TODO 在每个权限被拒后调用，多次回调。可弹窗向用户说明为什么需要该权限，否则用户可能在下次申请勾选不再提示。
                        executor.execute();
                    }
                })
                .onGoSetting(new RequestInterceptor<String>() {
                    @Override
                    public void intercept(@NonNull String data, @NonNull Executor executor) {
                        // TODO 在每个权限永久被拒后调用（即用户勾选不再提示），多次回调。可弹窗引导用户前往设置打开权限，调用executor.execute()会自动跳转设置。
                        executor.execute();
                    }
                })
                .request(listener);
    }


}
