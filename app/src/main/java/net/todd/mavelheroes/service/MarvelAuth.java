package net.todd.mavelheroes.service;

import java.security.MessageDigest;
import java.util.Date;

public class MarvelAuth {
    private String timestamp;

    private final static String privateKey = "c23e115d66dc8972b9c0bf45d2ea31ebd0ab79bb";
    private final static String publicKey = "d43fe865ff1bdc524b23246960a68b3e";

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
