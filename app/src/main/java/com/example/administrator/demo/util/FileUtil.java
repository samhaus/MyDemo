package com.example.administrator.demo.util;

import android.content.Context;
import android.os.Environment;


import com.example.administrator.demo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @version 1.0
 *          文件处理工具
 */
public class FileUtil {
    private final static String TAG = "FileUtil";


    /**
     * 保存缓存文件路径
     *
     * @param path
     */
    public static boolean createCachePath(String path, Context context) {
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = path;
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (StringUtil.isEmpty(savePath)) {
            UIHelper.toastMessageShort(context, R.string.task_img_storage_err, null);
            return false;
        }
        return true;
    }


    /**
     * Delete file
     *
     * @param filePath filePath =
     *                 android.os.Environment.getExternalStorageDirectory().getPath()
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file == null || !file.exists() || file.isDirectory()) {
            if (file == null)
                LogUtil.e(TAG, "为空");
            if (!file.exists())
                LogUtil.e(TAG, "不存在");
            if (file.isDirectory())
                LogUtil.e(TAG, "路径");
            return false;
        }
        return file.delete();
    }

    /**
     * 获取当前根文件夹下面的所有的图片路径，但是在应用中不适合这样操作，有些重复，先在此保留
     * 之所以说重复，是因为，可以根据listFiles将路径直接保存在Image集合中，不需要多此一举，先生成集合再返回去一个个加
     *
     * @param path
     * @return
     */
    public static ArrayList<String> getFilePathList(File path) {
        ArrayList<String> filePathList = new ArrayList<>();
        for (int i = 0; i < path.listFiles().length; i++) {
            String filePath = path.listFiles()[i].getAbsolutePath();
            String prefix = filePath.substring(filePath.lastIndexOf(".") + 1);//获取文件名的后缀
            if (prefix.equals("jpg") || prefix.equals("png") || prefix.equals("jpeg") || prefix.equals("gif")) {
                LogUtil.e("FileUtil", "文件后缀：" + prefix + " 路径：" + filePath);
                //添加
                filePathList.add("file://" + filePath);
            }
        }

        return filePathList;
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     */
    public static String getFileName(String filePath) {
        if (StringUtil.isEmpty(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);//分离出文件名
    }


    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileFormat(String fileName) {
        if (StringUtil.isEmpty(fileName))
            return "";

        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * sd卡是否存在
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    /**
     * 如果文件路径不存在，则创建
     *
     * @return
     */
    public static File fileNotExistAndMkdir(String filePath) {
        File file = null;
        file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    /**
     * 文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean fileIsExist(String filePath) {
        if (StringUtil.isEmpty(filePath))
            return false;
        File file = null;
        file = new File(filePath);
        return file.exists();
    }

    /**
     * @param fromFile 被复制的文件
     * @param toFile   复制的目录文件
     * @param rewrite  是否重新创建文件
     *                 <p>
     *                 <p>文件的复制操作方法
     */
    public static void copyfile(File fromFile, File toFile, Boolean rewrite) {

        if (!fromFile.exists()) {
            return;
        }

        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * 对应 设置->应用->应用详情里面的"清除数据"
     *
     * @param context
     * @param type    文件类型(通过Environment获取)
     * @return
     */
    public static String getSaveDataPath(Context context, String type) {
        return context.getExternalFilesDir(type).getAbsolutePath();
    }

    /**
     * 获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     * 对应 设置->应用->应用详情里面的"清除缓存"
     *
     * @param context
     * @return
     */
    public static String getCachePath(Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }


    /**
     * 换算文件大小
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format("%.2f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format("%.2f KB", f);
        } else
            return String.format("%d B", size);
    }




}
