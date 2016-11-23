/**
 * @author Nitish
 * PasswordHash class adds "salt" to the requested string and creates a MD5 hexstring
 */
package project2task2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
	/**
	 * convertMD5()
	 * @param requestedString
	 * @return String result which is the converted hex string in MD5 format
	 * @exception NoSuchAlgorithmException, Exception
	 *  
	 */
	public static String convertMD5(String requestedString){
        try {
        	requestedString +="salt";
            String result = new String();
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            byte[] array = mDigest.digest(requestedString.getBytes());
            result = getHexString(array);
            return result;
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (Exception e){
        	System.out.println(e);
        }
        return null;
    }
	
	/**
	 * getHexString 
	 * @param byteCode
	 * @return result hexString generated from the MD5 hash
	 * @throws Exception
	 */
	public static String getHexString(byte[] byteCode) throws Exception {
        String result = "";
        for (int i=0; i < byteCode.length; i++) {
            result += Integer.toString((byteCode[i] & 0xff) + 0x100, 16).substring( 1 );
        }
        return result;
    }
}
