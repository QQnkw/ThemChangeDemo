# ThemChangeDemo
换肤,换字体,换语言,应用不用重启;change skin,change font,change language,app no need to restart
### 换字体

![换字体](https://github.com/QQnkw/ThemChangeDemo/blob/master/image/%E6%8D%A2%E5%AD%97%E4%BD%93.gif)

### 换语言

![换语言](https://github.com/QQnkw/ThemChangeDemo/blob/master/image/%E6%8D%A2%E8%AF%AD%E8%A8%80.gif)

### 换皮肤

![换皮肤](https://github.com/QQnkw/ThemChangeDemo/blob/master/image/%E6%8D%A2%E8%82%A4.gif)

### 换肤重启

![换肤重启](https://github.com/QQnkw/ThemChangeDemo/blob/master/image/%E6%8D%A2%E8%82%A4%E9%87%8D%E5%90%AF.gif)

### 使用

##### 初始化

```kotlin
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.getInstance().init(this)
        SkinManager.getInstance().load()
    }
}
```

##### 加载或重置皮肤,字体,文本

```kotlin
findViewById<Button>(R.id.btn1).setOnClickListener {
            if (SkinManager.getInstance().isExternalSkin) {
                SkinManager.getInstance().resetDefaultSkin()
            } else {
                val path = Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "ThemBlack.skin"
                SkinManager.getInstance().loadSkin(path,null)
            }
        }
        findViewById<Button>(R.id.btn2).setOnClickListener {
            if (SkinManager.getInstance().isExternalFont) {
                SkinManager.getInstance().resetDefaultFont()
            } else {
                val path = Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "msz_regular.ttf"
                SkinManager.getInstance().loadFont(path, null)
            }

        }

        findViewById<Button>(R.id.btn3).setOnClickListener {
            if (SkinManager.getInstance().isExternalLanguage) {
                SkinManager.getInstance().resetDefaultLanguage()
            } else {
                val path = Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "ThemLanguage.skin"
                SkinManager.getInstance().loadLanguage(path,null)
            }
        }
```

##### Activity继承BaseActivity

```kotlin
class MainActivity : BaseActivity() {}
```

##### 布局中根布局引入xmlns:skin="http://schemas.android.com/android/skin",并对需要换肤的view添加skin:change_skin="true"

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:skin="http://schemas.android.com/android/skin"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/white"
    skin:change_skin="true"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/btn1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:background="@color/color_1"
        android:text="@string/load_skin"
        app:layout_constraintEnd_toStartOf="@id/btn2"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        skin:change_skin="true" />

    <Button
        android:id="@+id/btn2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@color/color_2"
        android:text="@string/load_font"
        app:layout_constraintBottom_toBottomOf="@id/btn1"
        app:layout_constraintEnd_toStartOf="@id/btn3"
        app:layout_constraintStart_toEndOf="@id/btn1"
        app:layout_constraintTop_toTopOf="@id/btn1"
        skin:change_skin="true" />

    <Button
        android:id="@+id/btn3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@color/color_3"
        android:text="@string/load_language"
        app:layout_constraintBottom_toBottomOf="@id/btn1"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/btn2"
        app:layout_constraintTop_toTopOf="@id/btn1"
        skin:change_skin="true" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/list_view"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/btn1"
        tools:listitem="@layout/item" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

##### 皮肤包和多语言包原始文件是只有资源文件的APK,防止用户点击安装,更改后缀名为skin;

皮肤包和多语言包,字体文件随意放置SD卡中;加载也从放置路径加载;

##### 皮肤包和多语言包中的资源名称必须和目标应用中需要更换的资源文件名称一致;否则找不到

### 把项目中的ThemBlack.skin和ThemLanguage.skin以及msz_regular.ttf文件放置到SD卡根目录,运行demo,就可以看到效果
