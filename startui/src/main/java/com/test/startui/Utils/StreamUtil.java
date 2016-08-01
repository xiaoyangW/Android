package com.test.startui.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by acer1 on 2016/6/25.
 */

public class StreamUtil {


    /**
     *将InputStream转换为String
     * @param is
     * @return 转换后的String
     * @throws IOException
     */
    public static String streamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer sb = new StringBuffer();
        try {
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return null;
    }

}
