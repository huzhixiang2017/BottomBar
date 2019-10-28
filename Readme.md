## WechatRadioBar
### 效果图
![](http://ww1.sinaimg.cn/mw690/b5405c76gw1f3qb7xo3wug20ax0ad4a1.gif)
### 用法
####Gradle
Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
}
```

Add the dependency

```groovy
dependencies {
	        compile 'com.github.leibnik:WechatRadioBar:1.0'
	}
```

#### xml
```xml
<io.github.leibnik.wechatradiobar.WechatRadioGroup
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_alignParentBottom="true">

        <io.github.leibnik.wechatradiobar.WechatRadioButton
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:gravity="center"
                    android:paddingTop="3dp"
                    android:text="发现"
                    android:textColor="#555"
                    android:textSize="13sp"
                    app:defocus_icon="@drawable/discover"
                    app:focus_color="#50ba26"
                    app:focus_icon="@drawable/discover_green"
                    app:icon_height="25dp"
                    app:icon_width="25dp" />

                <io.github.leibnik.wechatradiobar.WechatRadioButton
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:gravity="center"
                    android:paddingTop="3dp"
                    android:text="我"
                    android:textColor="#555"
                    android:textSize="13sp"
                    app:defocus_icon="@drawable/about_me"
                    app:focus_color="#50ba26"
                    app:focus_icon="@drawable/about_me_green"
                    app:icon_height="25dp"
                    app:icon_width="25dp" />
</io.github.leibnik.wechatradiobar.WechatRadioGroup>
```

* `app:focus_color`:选中状态文字的颜色
* `app:focus_icon`:选中状态的图标
* `app:defocus_icon`:非选中状态的图标
* `app:icon_width`:设置图标宽度
* `app:icon_height`:设置图标高度

#### java
```java
viewPager = (ViewPager) findViewById(R.id.viewpager);
gradualRadioGroup = (WechatRadioGroup) findViewById(R.id.radiogroup);
viewPager.setAdapter(adapter);
// 关键代码
gradualRadioGroup.setViewPager(viewPager);
```


