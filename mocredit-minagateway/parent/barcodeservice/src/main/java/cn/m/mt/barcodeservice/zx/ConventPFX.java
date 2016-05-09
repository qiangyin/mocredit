package cn.m.mt.barcodeservice.zx;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;

public class ConventPFX {   
    public static final String PKCS12 = "PKCS12";   
    public static final String JKS = "JKS";   
    public static final String PFX_KEYSTORE_FILE = "c:\\test.pfx";//pfx文件位置   
    public static final String KEYSTORE_PASSWORD = "123456";//导出为pfx文件的设的密码  
    public static final String JKS_KEYSTORE_FILE = "d:\\emaytest.jks"; //jks文件位置
    /**
     * keytool -import -v -trustcacerts -storepass mocredit -alias 7a56d1f2922c8c06a4a38ec2b1d6cd80_4dee9f3c-4521-41d0-a601-c0b462b8a187 -file c:/test.cer -keystore d:/zxtest.jks
     * clientt替换成alias 的别名
     */
    public static void coverTokeyStore() {   
        try {   
            KeyStore inputKeyStore = KeyStore.getInstance("PKCS12");   
            FileInputStream fis = new FileInputStream(PFX_KEYSTORE_FILE);   
            char[] nPassword = null;   
            if ((KEYSTORE_PASSWORD == null)   
                    || KEYSTORE_PASSWORD.trim().equals("")) {   
                nPassword = null;   
            } else {   
                nPassword = KEYSTORE_PASSWORD.toCharArray();   
            }   
            inputKeyStore.load(fis, nPassword);   
            fis.close();   
            KeyStore outputKeyStore = KeyStore.getInstance("JKS");   
            outputKeyStore.load(null, KEYSTORE_PASSWORD.toCharArray());   
            Enumeration<String> enums = inputKeyStore.aliases();   
            while (enums.hasMoreElements()) { // we are readin just one   
                                                // certificate.   
                String keyAlias = (String) enums.nextElement();   
                System.out.println("alias=[" + keyAlias + "]");   
                if (inputKeyStore.isKeyEntry(keyAlias)) {   
                    Key key = inputKeyStore.getKey(keyAlias, nPassword);   
                    Certificate[] certChain = inputKeyStore   
                            .getCertificateChain(keyAlias);   
                    outputKeyStore.setKeyEntry(keyAlias, key, KEYSTORE_PASSWORD   
                            .toCharArray(), certChain);   
                }   
            }   
            FileOutputStream out = new FileOutputStream(JKS_KEYSTORE_FILE);   
            outputKeyStore.store(out, nPassword);   
            out.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
    
    public static void main(String[] args) {   
        coverTokeyStore();    // pfx to jks   
    }   
}
