package com.guolala.zxx.util;

import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.util.Arrays;

/**
 * @Author: pei.nie
 * @Date:2019/4/18
 * @Description:
 */
public class AESUtil {

    public static final int CBC_ISO10126PADDING = 1;
    public static final int CBC_NOPADDING = 2;
    public static final int CBC_PKCS5PADDING = 3;
    public static final int CFB_NOPADDING = 4;
    public static final int CFB_PKCS5PADDING = 5;
    public static final int CFB_ISO10126PADDING = 6;
    public static final int ECB_NOPADDING = 7;
    public static final int ECB_PKCS5PADDING = 8;
    public static final int ECB_ISO10126PADDING = 9;
    public static final int OFB_NOPADDING = 10;
    public static final int OFB_PKCS5PADDING = 11;
    public static final int OFB_ISO10126PADDING = 12;
    public static final int PCBC_NOPADDING = 13;
    public static final int PCBC_PKCS5PADDING = 14;
    public static final int PCBC_ISO10126PADDING = 15;

    private static final IvParameterSpec DEFAULT_IV = new IvParameterSpec(
            new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });

    private static final String ALGORITHM = "AES";

    /**
     * provider: SUN SunRsaSign SunEC SunJSSE SunJCE SunJGSS SunSASL XMLDSig SunPCSC
     * Apple BC
     */
    private static final int DEFAULT_BIT = 256;

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String TRANSFORMATION1 = "AES/CBC/ISO10126Padding";
    private static final String TRANSFORMATION2 = "AES/CBC/NoPadding";
    private static final String TRANSFORMATION3 = "AES/CBC/PKCS5Padding";
    private static final String TRANSFORMATION4 = "AES/CFB/NoPadding";
    private static final String TRANSFORMATION5 = "AES/CFB/PKCS5Padding";
    private static final String TRANSFORMATION6 = "AES/CFB/ISO10126Padding";
    private static final String TRANSFORMATION7 = "AES/ECB/NoPadding";
    private static final String TRANSFORMATION8 = "AES/ECB/PKCS5Padding";
    private static final String TRANSFORMATION9 = "AES/ECB/ISO10126Padding";
    private static final String TRANSFORMATION10 = "AES/OFB/NoPadding";
    private static final String TRANSFORMATION11 = "AES/OFB/PKCS5Padding";
    private static final String TRANSFORMATION12 = "AES/OFB/ISO10126Padding";
    private static final String TRANSFORMATION13 = "AES/PCBC/NoPadding";
    private static final String TRANSFORMATION14 = "AES/PCBC/PKCS5Padding";
    private static final String TRANSFORMATION15 = "AES/PCBC/ISO10126Padding";

    /**
     * 字符串加密
     * @param data 明文字符串
     * @param key  明文密钥
     * @return 加密后的16进制字符串
     */
    public static String encryptData(final String data,final String key){
        try {
            byte[] dataBytes = data.getBytes(DEFAULT_CHARSET);
            byte[] keyBytes = key.getBytes(DEFAULT_CHARSET);
            byte[] encrytBytes = encryptData(dataBytes, keyBytes,DEFAULT_BIT/2);
            return byteToHex(encrytBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 默认是256位，AES/ECB/PKCS5Padding，所以iv向量是null
     * @param content 明文字节数组
     * @param key 密钥字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptData(final byte[] content, final byte[] key) {
        return encryptData(content , key , DEFAULT_BIT);
    }

    /**
     * 默认是AES/ECB/PKCS5Padding，所以iv向量是null
     * @param content 明文字节数组
     * @param key 密钥字节数组
     * @param bit 可自由指定128/192/256
     * @return 密文字节数组
     */
    public static byte[] encryptData(final byte[] content, final byte[] key, final int bit) {
        return encryptData(content , key , bit , null , 8);
    }

    /**
     * 提供偏移因子的AES加密，原理是先对因子做一次AES加密，然后得到的密文作为key再对明文加密
     * @param content 明文字节数组
     * @param key 主key字节数组
     * @param factor 偏移因子字节数组
     * @param bit 可自由指定128/192/256
     * @param iv 偏移向量字节数组
     * @param aesEncode 填充模式
     * @return 密文字节数组
     */
    public static byte[] encryptData(final byte[] content, final byte[] key, final byte[] factor, final int bit, final byte[] iv, final int aesEncode) {
        byte[] newKey = encryptData(factor , key , bit , iv , aesEncode);
        return encryptData(content , newKey , bit , iv , aesEncode);
    }

    /**
     * AES加密算法
     * @param content 明文字节数组
     * @param key 密钥字节数组
     * @param bit 指定的密钥长度
     * @param iv 偏移向量字节数组
     * @param aesEncode 填充模式
     * @return 密文字节数组
     */
    public static byte[] encryptData(final byte[] content, final byte[] key, final int bit, final byte[] iv , final int aesEncode) {

        long s1 = 0;
        long e1 = 0;

        String aesEncodeStr = getAESEncodeString(aesEncode);

        try {

            if (content == null || key == null || aesEncodeStr == null) {
                throw new Exception("The input content is null.");
            }

            if (bit != 128 && bit != 192 && bit != 256) {
                throw new Exception("The specified key length is not 128/192/256.");
            }

            if(key.length != bit/8) {
                throw new Exception("The keyString's length is not equal to specified key length(128/192/256 divide by 8)." + key.length);
            }

            s1 = System.currentTimeMillis();
            Key keysks = new SecretKeySpec(key , ALGORITHM);
            e1 = System.currentTimeMillis();
//            System.out.println("Key keysks = new SecretKeySpec(key , ALGORITHM);" + (e1 - s1));

            s1 = System.currentTimeMillis();
            Cipher cipher = Cipher.getInstance(aesEncodeStr);
            e1 = System.currentTimeMillis();
//            System.out.println("Cipher cipher = Cipher.getInstance(aesEncodeStr);" + (e1 - s1));

            if(aesEncode == 7 || aesEncode == 8 || aesEncode == 9) {
                s1 = System.currentTimeMillis();
                cipher.init(Cipher.ENCRYPT_MODE, keysks);
                e1 = System.currentTimeMillis();
//                System.out.println("cipher.init(Cipher.ENCRYPT_MODE, keysks);" + (e1 - s1));
            }
            else {
                IvParameterSpec ivParameterSpec = null;
                if(iv == null) {
                    ivParameterSpec = DEFAULT_IV;
                }
                else {
                    ivParameterSpec = new IvParameterSpec(iv);
                }
                cipher.init(Cipher.ENCRYPT_MODE, keysks, ivParameterSpec);
            }
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 字符串解密
     * @param data 密文16进制字符串
     * @param key  密钥
     * @return 解密后的原文字符串
     */
    public static String decryprtData(final String data,final String key){
        try {
            byte[] dataBytes = hexToByte(data);
            byte[] keyBytes = key.getBytes(DEFAULT_CHARSET);
            byte[] decrytBytes = decryptData(dataBytes, keyBytes,DEFAULT_BIT/2);
            return new String(decrytBytes, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 默认是256位，AES/ECB/PKCS5Padding，所以iv向量是null
     * @param encryptStr 密文字节数组
     * @param key 密钥字节数组
     * @return 明文字节数组
     */
    public static byte[] decryptData(final byte[] encryptStr, final byte[] key) {
        return decryptData(encryptStr , key , DEFAULT_BIT);
    }

    /**
     * 默认是AES/ECB/PKCS5Padding，所以iv向量是null
     * @param encryptStr 密文字节数组
     * @param key 密钥字节数组
     * @param bit 可自由指定128/192/256
     * @return 明文字节数组
     */
    public static byte[] decryptData(final byte[] encryptStr, final byte[] key, final int bit) {
        return decryptData(encryptStr , key , bit , null , 8);
    }

    /**
     * 提供偏移因子的AES解密，原理是先对因子做一次AES加密，然后得到的密文作为key再对密文解密
     * @param encryptStr 密文字节数组
     * @param key 主key字节数组
     * @param factor 偏移因子字节数组
     * @param bit 可自由指定128/192/256
     * @param iv 偏移向量字节数组
     * @param aesEncode 填充模式
     * @return 明文字节数组
     */
    public static byte[] decryptData(final byte[] encryptStr, final byte[] key, final byte[] factor, final int bit, final byte[] iv,
                                     final int aesEncode) {
        byte[] newKey = encryptData(factor , key , bit , iv , aesEncode);
        return decryptData(encryptStr , newKey, bit , iv , aesEncode);
    }

    /**
     * AES解密
     * @param encryptStr 密文字节数组
     * @param key 密钥字节数组
     * @param bit 可自由指定128/192/256
     * @param iv 偏移向量字节数组
     * @param aesEncode 填充模式
     * @return 明文字节数组
     */
    public static byte[] decryptData(final byte[] encryptStr, final byte[] key, final int bit , final byte[] iv , final int aesEncode) {

        String aesEncodeStr = getAESEncodeString(aesEncode);

        try {

            if (encryptStr == null || key == null || aesEncodeStr == null) {
                throw new Exception("The input content is null.");
            }

            if (bit != 128 && bit != 192 && bit != 256) {
                throw new Exception("The specified key length is not 128/192/256.");
            }

            if(key.length != bit/8) {
                throw new Exception("The keyString's length is not equal to specified key length(128/192/256 divide by 8).");
            }

            Key keysks = new SecretKeySpec(key , ALGORITHM);

            Cipher cipher = Cipher.getInstance(aesEncodeStr);

            if(aesEncode == 7 || aesEncode == 8 || aesEncode == 9) {
                cipher.init(Cipher.DECRYPT_MODE, keysks);
            }
            else {
                IvParameterSpec ivParameterSpec = null;
                if(iv == null) {
                    ivParameterSpec = DEFAULT_IV;
                }
                else {
                    ivParameterSpec = new IvParameterSpec(iv);
                }
                cipher.init(Cipher.DECRYPT_MODE, keysks, ivParameterSpec);
            }

            return cipher.doFinal(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES文件加密，使用AES/ECB/PKCS5Padding
     * @param originPath 源文件路径
     * @param encryptPath 加密后文件路径
     * @param key 密钥字节数组
     * @return 成功true,失败false
     */
    public static boolean encryptFile(String originPath, String encryptPath, final byte[] key) {
        return encryptFile(originPath, encryptPath, key, DEFAULT_BIT);
    }

    /**
     * AES文件加密，使用AES/ECB/PKCS5Padding
     * @param originPath 源文件路径
     * @param encryptPath 加密后文件路径
     * @param key 密钥字节数组
     * @param bit 可自由指定128/192/256
     * @return 成功true,失败false
     */
    public static boolean encryptFile(String originPath, String encryptPath, final byte[] key, final int bit) {
        return encryptFile(originPath, encryptPath, key, bit, null, 8);
    }

    /**
     * AES文件加密，使用AES/ECB/PKCS5Padding
     * @param originPath 源文件路径
     * @param encryptPath 加密后文件路径
     * @param key 密钥字节数组
     * @param bit 可自由指定128/192/256
     * @param aesEncode 填充模式
     * @return 成功true,失败false
     */
    public static boolean encryptFile(String originPath, String encryptPath, final byte[] key, final int bit,final byte[] iv,
                                      final int aesEncode) {

        String aesEncodeStr = getAESEncodeString(aesEncode);

        File originFile = null;
        File encryptfile = null;
        CipherOutputStream cipherOutputStream = null;
        BufferedInputStream bufferedInputStream = null;

        try {

            if (originPath == null || encryptPath == null || key == null || aesEncodeStr == null) {
                throw new Exception("The input content is null.");
            }

            if (bit != 128 && bit != 192 && bit != 256) {
                throw new Exception("The specified key length is not 128/192/256.");
            }

            if(key.length != bit/8) {
                throw new Exception("The keyString's length is not equal to specified key length(128/192/256 divide by 8).");
            }

            originFile = new File(originPath);
            if (!originFile.exists()) {
                throw new NullPointerException("Origin file is empty.");
            }
            encryptfile = new File(encryptPath);
            if (encryptfile.exists()) {
                encryptfile.delete();
            }
            encryptfile.createNewFile();

            Key keysks = new SecretKeySpec(key, ALGORITHM);

            Cipher cipher = Cipher.getInstance(aesEncodeStr);

            if(aesEncode == 7 || aesEncode == 8 || aesEncode == 9) {
                cipher.init(Cipher.ENCRYPT_MODE, keysks);
            }
            else {
                IvParameterSpec ivParameterSpec = null;
                if(iv == null) {
                    ivParameterSpec = DEFAULT_IV;
                }
                else {
                    ivParameterSpec = new IvParameterSpec(iv);
                }
                cipher.init(Cipher.ENCRYPT_MODE, keysks, ivParameterSpec);
            }

            cipherOutputStream = new CipherOutputStream(new FileOutputStream(encryptfile), cipher);
            bufferedInputStream = new BufferedInputStream(new FileInputStream(originFile));

            byte[] buffer = new byte[1024];
            int bufferLength;

            while ((bufferLength = bufferedInputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bufferLength);
            }
            bufferedInputStream.close();
            cipherOutputStream.close();

            return true;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * AES文件解密，使用AES/ECB/PKCS5Padding
     * @param encryptPath 加密后文件路径
     * @param decryptPath 解密后文件路径
     * @param key 密钥字节数组
     * @return 成功true,失败false
     */
    public static boolean decryptFile(String encryptPath, String decryptPath, final byte[] key) {
        return decryptFile(encryptPath, decryptPath, key, DEFAULT_BIT);
    }

    /**
     * AES文件解密，使用AES/ECB/PKCS5Padding
     * @param encryptPath 加密后文件路径
     * @param decryptPath 解密后文件路径
     * @param key 密钥字节数组
     * @param bit 可自由指定128/192/256
     * @return 成功true,失败false
     */
    public static boolean decryptFile(String encryptPath, String decryptPath, final byte[] key, final int bit) {
        return decryptFile(encryptPath, decryptPath, key, bit, null, 8);
    }

    /**
     * AES文件解密，使用AES/ECB/PKCS5Padding
     * @param encryptPath 加密后文件路径
     * @param decryptPath 解密后文件路径
     * @param key 密钥字节数组
     * @param bit 可自由指定128/192/256
     * @param aesEncode 填充模式
     * @return 成功true,失败false
     */
    public static boolean decryptFile(String encryptPath, String decryptPath, final byte[] key, final int bit,final byte[] iv,
                                      final int aesEncode) {

        String aesEncodeStr = getAESEncodeString(aesEncode);

        File encryptFile = null;
        File decryptFile = null;
        BufferedOutputStream outputStream = null;
        CipherInputStream inputStream = null;

        try {

            if (encryptPath == null || decryptPath == null || key == null || aesEncodeStr == null) {
                throw new Exception("The input content is null.");
            }

            if (bit != 128 && bit != 192 && bit != 256) {
                throw new Exception("The specified key length is not 128/192/256.");
            }

            if(key.length != bit/8) {
                throw new Exception("The keyString's length is not equal to specified key length(128/192/256 divide by 8).");
            }

            encryptFile = new File(encryptPath);
            if (!encryptFile.exists()) {
                throw new NullPointerException("Decrypt file is empty.");
            }
            decryptFile = new File(decryptPath);
            if (decryptFile.exists()) {
                decryptFile.delete();
            }
            decryptFile.createNewFile();

            Key keysks = new SecretKeySpec(key, ALGORITHM);

            Cipher cipher = Cipher.getInstance(aesEncodeStr);

            if(aesEncode == 7 || aesEncode == 8 || aesEncode == 9) {
                cipher.init(Cipher.DECRYPT_MODE, keysks);
            }
            else {
                IvParameterSpec ivParameterSpec = null;
                if(iv == null) {
                    ivParameterSpec = DEFAULT_IV;
                }
                else {
                    ivParameterSpec = new IvParameterSpec(iv);
                }
                cipher.init(Cipher.DECRYPT_MODE, keysks, ivParameterSpec);
            }

            outputStream = new BufferedOutputStream(new FileOutputStream(decryptFile));
            inputStream = new CipherInputStream(new FileInputStream(encryptFile), cipher);

            int bufferLength;
            byte[] buffer = new byte[1024];

            while ((bufferLength = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bufferLength);
            }
            inputStream.close();
            outputStream.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 得到数字对应的填充模式字符串
     * @param aesEncode 填充模式数字
     * @return 填充模式字符串
     */
    private static String getAESEncodeString(int aesEncode) {
        switch (aesEncode) {
            case CBC_ISO10126PADDING:
                return TRANSFORMATION1;
            case CBC_NOPADDING:
                return TRANSFORMATION2;
            case CBC_PKCS5PADDING:
                return TRANSFORMATION3;
            case CFB_NOPADDING:
                return TRANSFORMATION4;
            case CFB_PKCS5PADDING:
                return TRANSFORMATION5;
            case CFB_ISO10126PADDING:
                return TRANSFORMATION6;
            case ECB_NOPADDING:
                return TRANSFORMATION7;
            case ECB_PKCS5PADDING:
                return TRANSFORMATION8;
            case ECB_ISO10126PADDING:
                return TRANSFORMATION9;
            case OFB_NOPADDING:
                return TRANSFORMATION10;
            case OFB_PKCS5PADDING:
                return TRANSFORMATION11;
            case OFB_ISO10126PADDING:
                return TRANSFORMATION12;
            case PCBC_NOPADDING:
                return TRANSFORMATION13;
            case PCBC_PKCS5PADDING:
                return TRANSFORMATION14;
            case PCBC_ISO10126PADDING:
                return TRANSFORMATION15;
            default:
                return null;
        }
    }

    /**
     * 二进制转十六进制
     * @param bytes
     * @return
     */
    private static String byteToHex(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制转二进制
     * @param hexStr
     * @return
     */
    private static byte[] hexToByte(final String hexStr){
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


	/*public static void main(String[] args) throws FileNotFoundException, IOException {
        String filePath = "";
		byte[] byteArray = IOUtils.toByteArray(new FileInputStream(filePath));
		byte[] enByte = encryptData(byteArray,"mstest#98%dvjdv3".getBytes("UTF-8"),128);
		byte[] decryptData = decryptData(enByte, "mstest#98%dvjdv3".getBytes("UTF-8"),128);
		String deStr1 = new String(decryptData, "UTF-8");
		System.out.println(deStr1);
		System.out.println(Arrays.equals(byteArray,decryptData));
	}*/
}
