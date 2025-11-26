package model;

public class Util {

    public static String md5(String senha) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] arr = md.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : arr) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch(Exception e) {
            return null;
        }
    }
}
