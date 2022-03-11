package com.example.tdytest.util;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class BitmapUtil {
    //Mat转Bitmap
    public static Bitmap matToBitmap(Mat mat) {
        Bitmap resultBitmap = null;
        if (mat != null) {
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);
            resultBitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
            if (resultBitmap != null)
                Utils.matToBitmap(mat, resultBitmap, true);
        }
        return resultBitmap;
    }
}
