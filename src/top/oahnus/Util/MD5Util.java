package top.oahnus.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by oahnus on 2016/6/16.
 */
public class MD5Util {
    private static byte[] encode2bytes(String source){
        byte[] result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(source.getBytes());

            result = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String encode2hex(String source){
        source = source.substring(0,source.length()/2)+"jack"+source.substring(source.length()/2);
        byte[] bytes = encode2bytes(source);

        StringBuffer hexString = new StringBuffer();

        for(int i=0;i<bytes.length;i++){
            String hex = Integer.toHexString(0xff&bytes[i]);

            if(hex.length() == 1){
                hexString.append('0');
            }

            hexString.append(hex);
        }
        return hexString.toString();
    }
}
