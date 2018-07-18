package com.sansan.myapplication.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;  
import java.io.IOException;  
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
  
import android.content.Context;
import android.os.Environment;  
import android.text.TextUtils;  
/** 
 *  
 * Title: FileUtils.java 
 * Description: 对sd卡的文件相关操作 
 * @author Liusong 
 * @date 2015-1-12 
 * @version V1.0 
 */  
public class FileUtils {  
	private static final String TAG = FileUtils.class.getSimpleName();
    /** 
     * 判断sdcrad是否已经安装 
     * @return boolean true安装 false 未安装 
     */  
    public static boolean isSDCardMounted(){  
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());  
    }  
      
    /** 
     * 得到sdcard的路径 
     * @return 
     */  
    public static String getSDCardRoot(){  
        System.out.println(isSDCardMounted()+Environment.getExternalStorageState());  
        if(isSDCardMounted()){  
            return Environment.getExternalStorageDirectory().getAbsolutePath();  
        }  
        return "";  
    }  
    /** 
     * 创建文件的路径及文件 
     * @param path 路径，方法中以默认包含了sdcard的路径，path格式是"/path...." 
     * @param filename 文件的名称 
     * @return 返回文件的路径，创建失败的话返回为空 
     */  
    public static String createMkdirsAndFiles(String path, String filename) {  
        if (TextUtils.isEmpty(path)) {  
            throw new RuntimeException("路径为空");  
        }  
        path = getSDCardRoot()+path;  
        File file = new File(path);  
        if (!file.exists()) {  
            try {  
                file.mkdirs();  
            } catch (Exception e) {  
                throw new RuntimeException("创建文件夹不成功");  
            }  
        }   
        File f = new File(file, filename);  
        if(!f.exists()){  
            try {  
                f.createNewFile();  
            } catch (IOException e) {  
                throw new RuntimeException("创建文件不成功");  
            }  
        }  
        return f.getAbsolutePath();  
    }  
      
    /** 
     * 把内容写入文件 
     * @param path 文件路径 
     * @param text 内容 
     */  
    public static void write2File(String path,String text,boolean append){  
        BufferedWriter bw = null;  
        try {  
            //1.创建流对象  
            bw = new BufferedWriter(new FileWriter(path,append));  
            //2.写入文件  
            bw.write(text);  
            //换行刷新  
            bw.newLine();  
            bw.flush();  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            //4.关闭流资源  
            if(bw!= null){  
                try {  
                    bw.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
    /** 
     * 删除文件 
     * @param path 
     * @return 
     */  
    public static boolean deleteFile(String path){  
        if(TextUtils.isEmpty(path)){  
            throw new RuntimeException("路径为空");  
        }  
        File file = new File(path);  
        if(file.exists()){  
            try {  
                file.delete();  
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return false;  
    }  
    
    

    public static void fileChannelCopy(File s, File t) {
		LogUtils.d(TAG,"fileChannelCopy");
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fi!=null)
				fi.close();
				if(in!=null)
				in.close();
				if(fo!=null)
				fo.close();
				if(out!=null)
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	

	public static void copyFileUsingFileStreams(File source, File dest)
			throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new BufferedInputStream(new FileInputStream(source));
			output = new BufferedOutputStream(new FileOutputStream(dest));
			byte[] buf = new byte[2048];
			int i;
			while ((i = input.read(buf)) != -1) {
				output.write(buf, 0, i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(input!=null)
					input.close();
					if(output!=null)
					output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
	}
	
	public static void mapApkCopy(File source, File dest,Context context){
		int index = 0,max=3;
		while(index<max){
//			CommandResult copyApkToData = copyApkToData(apkFile);
			long start = System.currentTimeMillis();    
			fileChannelCopy(source, dest);
			long end = System.currentTimeMillis();   
			LogUtils.d(TAG,"Time taken by FileChannels Copy = " + (end - start));
			LogUtils.d(TAG, "Map source size="+source.length()+" dest size="+dest.length()+" index="+index);
			
			if(source.length()==dest.length()){
				break;
			}else{
				index++;
				if(index==max&&source.length()==dest.length()){
					LogUtils.d(TAG,"mapApkCopy error ");
				};
			}
			
		}
	}
}  