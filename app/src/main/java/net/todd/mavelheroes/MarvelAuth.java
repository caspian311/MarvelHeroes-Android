package net.todd.mavelheroes;

import java.security.MessageDigest;
import java.util.Date;

public class MarvelAuth {
    private String timestamp;

    private final static String privateKey = "not-shown";
    private final static String publicKey = "not-shown";

    public MarvelAuth() {
        this.timestamp = "" + new Date().getTime();
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        try {
            String combinedKey = getTimestamp() + privateKey + getPublicKey();
            byte[] digest = MessageDigest.getInstance("MD5").digest(combinedKey.getBytes());
            return convert(digest);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String convert(byte[] b) {
        String result = "";

        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
}
