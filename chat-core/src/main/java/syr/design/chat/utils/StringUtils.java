package syr.design.chat.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import syr.design.chat.model.Message;
import syr.design.chat.model.Users;

import java.security.MessageDigest;

public class StringUtils {

    @Value("${salt}")
    private static String salt;

    public static String md5hash(String msg){
        return new Md5Hash(msg, salt).toString();
    }

    /**
     * 设置存储channel时的tag(是用户名还是用户ID)
     */
    public static String getNettyTag(Users users){
        return md5hash(users.getUsername());
    }

    public static String getNettyTag(Message message){
        return md5hash(message.getToUserName());
    }

    public static String simplehash(String msg){
        return new SimpleHash("MD5", msg, salt).toHex();
    }

    private static final String [] HEX_DIGLTS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String md5encode(String string){
        if(string == null || "".equals(string.trim())){
            return null;
        }
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            result = bytearraytohexstring(messageDigest.digest(string.getBytes()));
            return result;
        }catch (Exception e){
            return null;
        }
    }

    private static String bytearraytohexstring(byte [] bytes){
        StringBuilder stringBuffer = new StringBuilder();
        for (byte aByte : bytes) {
            stringBuffer.append(bytetohexstring(aByte));
        }
        return stringBuffer.toString();
    }

    private static String bytetohexstring(byte b){
        int n = b;
        if (n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGLTS[d1] + HEX_DIGLTS[d2];
    }

}
