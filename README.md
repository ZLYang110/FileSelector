### 介绍

---
1. 可指定选择文件夹还是文件，选择显示文件也可指定后缀名显示获取连接
2. 可通过扫描全盘的方式，选择指定后缀名的文件
3. 支持多选。
4. 支持Activity、Fragment
5. 支持androidX
6. 自定义标题颜色

### Example

---
<img src="https://github.com/ZLYang110/FileSelector/blob/master/screenshot/Screenshot_20200622_135034.jpg" width = "180" height = "320" alt="图片名称"/>     <img src="https://github.com/ZLYang110/FileSelector/blob/master/screenshot/Screenshot_20200622_135013.jpg" width = "180" height = "320" alt="图片名称"/>
<img src="https://github.com/ZLYang110/FileSelector/blob/master/screenshot/Screenshot_20200622_135143.jpg" width = "180" height = "320" alt="图片名称"/>     <img src="https://github.com/ZLYang110/FileSelector/blob/master/screenshot/Screenshot_20200622_135702.jpg" width = "180" height = "320" alt="图片名称"/>
<img src="https://github.com/ZLYang110/FileSelector/blob/master/screenshot/Screenshot_20210322_154231.jpg" width = "180" height = "320" alt="图片名称"/>
---

### 可下载APK直接体验
[Demo.apk](https://github.com/ZLYang110/FileSelector/blob/master/app/release/app-release.apk)


---

### 用法

```
allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}
```

```
//AndroidX
implementation 'com.github.ZLYang110:FileSelector:2.1.3'

 //support
//implementation 'com.github.ZLYang110:FileSelector:1.0.2'
```



##### 一、 在文件浏览器中选择指定文件
```
 /**
     *  设置 onlyShowFolder() 只显示文件夹 后 再设置setFileTypes（）不生效
     *  设置 onlyShowFolder() 只显示文件夹 后 默认设置了onlySelectFolder（）
     *  设置 onlySelectFolder() 只能选择文件夹 后 默认设置了isSingle（）
     *  设置 isSingle() 只能选择一个 后 再设置了setMaxCount（） 不生效
     *
     */
 FileSelector.from(this)
               // .onlyShowFolder()  //只显示文件夹
                //.onlySelectFolder()  //只能选择文件夹
               // .isSingle() // 只能选择一个
                .setMaxCount(5) //设置最大选择数
                .setFileTypes("png", "doc","apk", "mp3", "gif", "txt", "mp4", "zip") //设置文件类型
                .setSortType(FileSelector.BY_NAME_ASC) //设置名字排序
                //.setSortType(FileSelector.BY_TIME_ASC) //设置时间排序
                //.setSortType(FileSelector.BY_SIZE_DESC) //设置大小排序
                //.setSortType(FileSelector.BY_EXTENSION_DESC) //设置类型排序
                .requestCode(1) //设置返回码
                .start();
```

##### 二、 设置只选择文件夹（文件夹默认只能选择一个）

```
  FileSelector.from(this)
                .onlySelectFolder()  //只能选择文件夹
                .requestCode(1) //设置返回码
                .start();
```

##### 三、 设置只显示文件夹（只显示文件夹就只能选择文件夹）

```
 FileSelector.from(this)
                .onlyShowFolder()  //只能选择文件夹
                .requestCode(1) //设置返回码
                .start();
```

##### 四、 只显示图片的文件

```
 FileSelector.from(this)
                .setMaxCount(5) //设置最大选择数
                .setFileTypes( "png","jpg") //设置文件类型
                .requestCode(1) //设置返回码
                .start();
```


##### 五、 自定义标题颜色

```
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
```

##### 六、 接收返回的文件数据，在 ++onActivityResult++ 方法中获取。选中文件以链表方式返回， ++EssFile++ 类为载体


```
  @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(data!=null){
                ArrayList<String> essFileList = data.getStringArrayListExtra(Const.EXTRA_RESULT_SELECTION);
                StringBuilder builder = new StringBuilder();
                for (String file : essFileList) {
                    builder.append(file).append("\n");
                }
                tv_backResult.setText(builder.toString());
            }
        }
    }
```



### 属性列表

---

名称 | 描述 |  默认值
---|---|---
FileTypes | 需要显示的文件类型 | 无
SortType | 排序类型 | 按名字排序 BY_NAME_ASC
isSingle | 是否单选 |false
maxCount | 最大可选中数量 | 10
request_code | 请求码 | 无
onlyShowFolder | 是否仅只显示文件夹  | false
onlySelectFolder | 是否只选择文件夹  | false
setTilteBg | 设置标题背景颜色  | ?attr/colorPrimary
setTitleColor | 设置标题颜色  | 白色
setTitleLiftColor | 设置标题左边箭头颜色  | 白色
setTitleRightColor | 设置标题右边字体颜色  | 白色

# 更新日志

2.1.3
----
  -  优化程序，root路径先显示路径，同步加载文件列表等等

2.1.2
----
  -  解决点击路径无反应

2.1.1
----
  -  添加自定义标题


2.1.0
----
  -  添加Fragment 测试


2.0.0
----
  -  适配Androidx


### THANKS

---

[陈宇明大师兄 BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

[FilePicker](https://github.com/imLibo/FilePicker)


## LICENSE

MIT License

Copyright (c) 2018

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
