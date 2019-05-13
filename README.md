# MediaPicker
Android多媒体图片音频视频可限制大小时间自定义选择器库

项目中许多时候需要选择图片、音视频，并有大小和时间限制，没有找到合适的库，所以自己提供一个满足需求的基础版本。
一.可以选择图片、音频、视频
二.可以限制选择数量、音视频大小、时长
三.可以进行图片、音视频的预览播放

## 一.实现效果
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019051319181356.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3NsMjAxOGdvZA==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190513191836136.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3NsMjAxOGdvZA==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190513192050374.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3NsMjAxOGdvZA==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190513192101200.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3NsMjAxOGdvZA==,size_16,color_FFFFFF,t_70)
可以设置显示主题，默认为深色主题
## 二.引用
1.Add it in your root build.gradle at the end of repositories
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2.Add the dependency

```
dependencies {
	        implementation 'com.github.MingYueChunQiu:MediaPicker:0.1'
	}
```
## 三.使用
### 1.基础使用
最简单的使用，全部为默认配置选择图片
```
MediaPicker.init(MainActivity.this)
                        	.pick();
```
在MediaPicker主要是提供MediaPickerControlable接口实例，默认提供的是MediaPickerControl子类

```
public class MediaPicker {

    public static final MediaPicker INSTANCE;//单例
    private MediaPickerControlable mControl;

    private MediaPicker() {
    }

    static {
        INSTANCE = new MediaPicker();
    }

    public static MediaPickerControlable init(@NonNull Context context) {
        return init(context, new MediaPickerStore(context), null);
    }

    public static MediaPickerControlable init(@NonNull Context context, MediaPickerStoreable store, MediaPickerInterceptable intercept) {
        INSTANCE.mControl = new MediaPickerControl(context, store, intercept);
        return INSTANCE.mControl;
    }

    public MediaPickerControlable getMediaPickerControl() {
        return INSTANCE.mControl;
    }

    public static ImageEngine getImageEngine() {
        return INSTANCE.mControl.getImageEngine();
    }
}
```
在拿到MediaPickerControlable后，设置相关配置，MediaPickerControlable里持有MediaPickerStoreable接口，默认提供的子类实现是MediaPickerStore。

MediaPickerStore主要是用来持有MediaPickerConfig，进行配置设置，提供MediaPickerControlable和MediaPickerStoreable主要是既可以通过传入MediaPickerConfig配置，也可以直接调用MediaPickerControlable进行配置，最终都转换为MediaPickerConfig。

MediaPickerControlable包裹MediaPickerStoreable，使用与实现中间层拦截器，方便实现中间额外操作，所以提供MediaPickerInterceptable接口，默认提供了MediaPickerIntercept空实现子类，可以对所有方法进行拦截监听
### 2.MediaPickerControlable

```
public interface MediaPickerControlable {

    MediaPickerControlable setMediaPickerConfig(MediaPickerConfig config);

    MediaPickerControlable setMediaPickerIntercept(MediaPickerInterceptable intercept);

    MediaPickerControlable setMediaPickerType(MediaPickerType mediaPickerType);

    MediaPickerControlable setMaxSelectMediaCount(int maxSelectMediaCount);

    MediaPickerControlable setLimitSize(long limitSize);

    MediaPickerControlable setLimitDuration(long limitDuration);

    MediaPickerControlable setFilterLimitMedia(boolean filterLimitMedia);

    MediaPickerControlable setColumnCount(int columnCount);

    MediaPickerControlable setStartPreviewByThird(boolean startPreviewByThird);

    MediaPickerControlable setThemeConfig(MediaPickerThemeConfig config);

    MediaPickerControlable setImageEngine(ImageEngine engine);

    ImageEngine getImageEngine();

    MediaPickerStoreable getMediaPickerStore();

    void pick();

    void release();
}
```
### 3.MediaPickerStoreable

```
public interface MediaPickerStoreable {

    MediaPickerStoreable setMediaPickerConfig(MediaPickerConfig config);

    MediaPickerStoreable setMediaPickerType(MediaPickerType mediaPickerType);

    MediaPickerStoreable setMaxSelectMediaCount(int maxSelectMediaCount);

    MediaPickerStoreable setLimitSize(long limitSize);

    MediaPickerStoreable setLimitDuration(long limitDuration);

    MediaPickerStoreable setFilterLimitMedia(boolean filterLimitMedia);

    MediaPickerStoreable setColumnCount(int columnCount);

    MediaPickerStoreable setStartPreviewByThird(boolean startPreviewByThird);

    MediaPickerStoreable setThemeConfig(MediaPickerThemeConfig config);

    MediaPickerStoreable setImageEngine(ImageEngine engine);

    MediaPickerConfig getMediaPickerConfig();

    void pick();

    void release();
}
```
### 4.MediaPickerConfig
MediaPickerStore实现类里会持有MediaPickerConfig

```
private MediaPickerType mediaPickerType;//多媒体选择类型

    private int maxSelectMediaCount;//最多可选择多媒体个数

    private long limitSize;//限制大小（单位B）

    private long limitDuration;//限制时长（毫秒）

    private boolean filterLimitMedia;//是否过滤超出限制的多媒体信息

    private int columnCount;//一行列数

    private int loadAnimation;//Item加载动画

    private boolean startPreviewByThird;//以第三方应用方式打开预览多媒体

    private MediaPickerThemeConfig themeConfig;//主题配置

    private ImageEngine engine;//图片加载引擎
```
可以通过startPreviewByThird来设置是否通过调用第三方应用来预览，默认是库自带的预览效果，目前设置后只有视频可以打开调用其他第三方应用预览。

在MediaPickerConfig里可以配置界面主题深色和浅色，默认为深色，同时也可以自定义设置

```
public class MediaPickerThemeConfig：

	private int themeType;//主题类型（浅色或深色，请设置此类时一定要配置涉及到状态栏配置，默认为深色）

    private @ColorInt
    int topBackgroundColor;//顶部背景颜色

    private @ColorInt
    int bottomBackgroundColor;//底部背景颜色

    private @ColorInt
    int topTextColor;//顶部文字颜色

    private @ColorInt
    int bottomTextColor;//底部文字颜色

    private @DrawableRes
    int backIconResId;//返回Icon资源ID

    private @DrawableRes
    int upTriangleIconResId;//向上三角Icon资源ID

    private @DrawableRes
    int downTriangleIconResId;//向下三角Icon资源ID
```
MediaPickerThemeConfig默认提供了buildLightTheme和buildDarkTheme

```
/**
         * 创建默认浅色主题配置信息
         *
         * @return 返回浅色主题配置信息对象
         */
        public MediaPickerThemeConfig buildLightTheme() {
            mConfig.topBackgroundColor = Color.WHITE;
            mConfig.bottomBackgroundColor = Color.parseColor("#33000000");
            mConfig.topTextColor = Color.BLACK;
            mConfig.bottomTextColor = Color.BLACK;
            mConfig.backIconResId = R.drawable.mp_back_dark;
            mConfig.upTriangleIconResId = R.drawable.mp_up_triangle_dark;
            mConfig.downTriangleIconResId = R.drawable.mp_down_triangle_dark;
            mConfig.themeType = Constants.ThemeTypeConstants.TYPE_LIGHT;
            return mConfig;
        }

        /**
         * 创建默认深色主题配置信息
         *
         * @return 返回深色主题配置信息对象
         */
        public MediaPickerThemeConfig buildDarkTheme() {
            mConfig.topBackgroundColor = Color.parseColor("#2C2C34");
            mConfig.bottomBackgroundColor = Color.parseColor("#2C2C34");
            mConfig.topTextColor = Color.WHITE;
            mConfig.bottomTextColor = Color.WHITE;
            mConfig.backIconResId = R.drawable.mp_back_light;
            mConfig.upTriangleIconResId = R.drawable.mp_up_triangle_light;
            mConfig.downTriangleIconResId = R.drawable.mp_down_triangle_light;
            mConfig.themeType = Constants.ThemeTypeConstants.TYPE_DARK;
            return mConfig;
        }
```
### 5.图片引擎
用户可以设置ImageEngine

```
 MediaPicker.init(MainActivity.this)
                        .setImageEngine()
                        .pick();
```
ImageEngine 接口如下，默认提供了GlideEngine，如果和项目有冲突，可以用自己的图片引擎替换
```
public interface ImageEngine {

    /**
     * 显示图片
     *
     * @param context       上下文
     * @param file          显示图片
     * @param placeholderId 占位图资源
     * @param errorId       错误图资源
     * @param imageView     显示控件
     */
    void showImage(Context context, File file,
                   @DrawableRes int placeholderId, @DrawableRes int errorId, ImageView imageView);

    /**
     * 显示图片
     *
     * @param fragment      界面
     * @param file          显示图片
     * @param placeholderId 占位图资源
     * @param errorId       错误图资源
     * @param imageView     显示控件
     */
    void showImage(Fragment fragment, File file,
                   @DrawableRes int placeholderId, @DrawableRes int errorId, ImageView imageView);
}
```
## 四.总结
目前用户所需要使用的源码都在上面，其他预览相关代码就暂不介绍，库刚出来，如果有什么疑问或建议欢迎提出，Github地址：https://github.com/MingYueChunQiu/MediaPicker.git ， 码云地址：https://gitee.com/MingYueChunQiu/MediaPicker.git ，感谢你的支持，如果不介意请Github点个star，谢谢。
