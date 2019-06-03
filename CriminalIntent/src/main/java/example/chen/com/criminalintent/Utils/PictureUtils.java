package example.chen.com.criminalintent.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * 照片处理类
 */
public class PictureUtils {

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1; //缩放像素的倍数
        if (srcHeight > destHeight || srcWidth > destWidth) {
            float heightScale = srcHeight / destHeight;
            float widthScale = srcWidth / destWidth;
            inSampleSize = Math.round(heightScale > widthScale ? heightScale : widthScale);
        }
        
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 按照屏幕尺寸缩放图像
     * @param path
     * @param activity
     * @return
     */
    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point point = new Point();

        activity.getWindowManager().getDefaultDisplay()
                .getSize(point);

        return getScaledBitmap(path, point.x, point.y);
    }
}
