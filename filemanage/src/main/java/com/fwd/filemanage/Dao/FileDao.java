package com.fwd.filemanage.Dao;

import android.os.Environment;

import com.fwd.filemanage.entity.FileBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arvin on 2016/6/7.
 */

public class FileDao {
    public static String ROOT="/";
    public static final String TO_ROOT = "返回根目录";
    public static final String TO_UP = "返回上一级目录";
    private boolean isSdcard;//是否是sdcard的标识
    private FileBean copyFile;//复制路径
    private String currentFilePath;//保存路径

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public FileBean getCopyFile() {
        return copyFile;
    }

    public void setCopyFile(FileBean copyFile) {
        this.copyFile = copyFile;
    }

    public boolean isSdcard() {
        return isSdcard;
    }

    public void setSdcard(boolean sdcard) {
        isSdcard = sdcard;
    }

    /**
     * 文件的粘贴（复制，读写）
     * @throws Exception
     */
    public void copyFile() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(copyFile.getPath());
            //获取原来的文件名
            String name = copyFile.getFileName();
            File newFile = new File(currentFilePath,name);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            byte[] buffer = new byte[40*1024];
            int len=0;
            while ((len = fileInputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer,0,len);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    /**
     * 获取SD卡的路径
     * @return
     */
    public String getSdcard(){
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                return Environment.getExternalStorageDirectory().getAbsolutePath();
            }
        return null;
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    public boolean deleteFile(String path){
       File file = new File(path);
        if (file.canRead()){
            return file.delete();
        }
        return false;
    }

    /**
     * 创建文件夹
     * @param path
     */
    public void createFolder(String path){
        File file = new File(path);
        file.mkdirs();
    }
    /**
     * 创建新的文件-全路径
     * @param path
     * @throws Exception
     */
    public void createFile(String path) throws Exception{
        File file = new File(path);
        file.createNewFile();
    }
    /**
     * 获取该目录下的所有文件夹和文件
     * @param path
     * @return
     */
    public  List<FileBean> getCurrentList(String path){
        List<FileBean> list = new ArrayList<>();
        File file = new File(path);
        if(!ROOT.equals(path)){
            FileBean root = new FileBean();
            root.setFileName(TO_ROOT);
            root.setPath(ROOT);
            //得到返回上级目数据
            FileBean up = new FileBean();
            up.setFileName(TO_UP);
            up.setPath(file.getParentFile().getAbsolutePath());
            list.add(root);
            list.add(up);
        }
        //所有文件
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            FileBean fileBean = new FileBean();
            fileBean.setFileName(files[i].getName());
            fileBean.setPath(files[i].getPath());
            list.add(fileBean);
        }
        return list;
    }

}
