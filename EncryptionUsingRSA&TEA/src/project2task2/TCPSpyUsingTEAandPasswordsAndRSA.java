package project2task2;

import java.math.BigInteger;
import java.net.*;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

/**
 * 
 * @author Nitish
 * TCPSpyUsingTEAandPasswordsAndRSA encrypts the TEA key using RSA key and sends it to the server
 * When the server responds with the "Done" msg, the client sends the values of the spies and the location
 *
 *
 */

public class TCPSpyUsingTEAandPasswordsAndRSA {
    public static void main (String args[]) {
        Socket s = null;
        TCPSpyUsingTEAandPasswordsAndRSA spy = new TCPSpyUsingTEAandPasswordsAndRSA();
        String[] credits = spy.getCredentials(); //prompts for credentials
        try{
            int serverPort = 7896;
            s = new Socket("localhost", serverPort); // Creation of socket on localhost   
            DataInputStream in = new DataInputStream( s.getInputStream());
            DataOutputStream out =new DataOutputStream( s.getOutputStream());
            //generating random 16 byte key (BigInteger) for TEA encryption
            BigInteger key = spy.generateTEAKey();
            //converting BigInteger key to byte array
            byte[] byteKey = key.toByteArray();
            //RSA encoding of byteKey and sending it to the server
            byte[] rsaEncodedTEAKey = spy.encodingRSA(byteKey);
            out.write(rsaEncodedTEAKey);
            // encoding message by passing the credentials and the byteKey to encode the message
            byte[] spyData = spy.encodedMsg(credits, byteKey);
            //reads the response from server and sends the data key
            if(in.readUTF().equals("Done")){
            	out.write(spyData);// sends rsaEncodedData to the server
                byte[] commandResponse = new byte[250];//initialize a byte array to store response
                // count stores the number of bytes read
                int count = in.read(commandResponse);//read response data to commandResponse from the streamin.read();
                if(count != -1){
                	byte[] finalResponse = new byte[count];
                    for(int i=0;i<count;i++){
                    	finalResponse[i] = commandResponse[i];
                    }
                    System.out.println(new String(finalResponse)) ;
                }    
            }                     
        }catch (UnknownHostException e){
            System.out.println("Socket:"+e.getMessage());
        }catch (EOFException e){
            System.out.println("EOF:"+e.getMessage());
        }catch (IOException e){
            System.out.println("readline:"+e.getMessage());
        }finally {
            if(s!=null)
                try {
                    s.close();
                }catch (IOException e){
                    System.out.println("close:"+e.getMessage());
                }
        }
     }
    /**
     * generateTEAKey() generated the TEA key(BigInteger) using random function which is 128 bits in length 
     * @return BigInteger byteKey
     */
    private BigInteger generateTEAKey(){
    	int bitLength = 128;
    	Random ra = new Random();
    	//Creates byteKey which is a BigInteger of size 128 bits
    	BigInteger byteKey = new BigInteger(bitLength, ra);
    	return byteKey;
    }
    
    /**
     * getCredentials() asks for the credentials from the spy and saves it in an array
     * @return String[] credentials
     */
    private String[] getCredentials(){
    	String[] credentials = new String[3];
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Enter your ID:");
    	credentials[0] = sc.next();
    	System.out.println("Enter your password:");
    	credentials[1] = sc.next();
    	System.out.println("Enter your location:");
    	credentials[2] = sc.next();
    	return credentials;
    }
    
    /**
     * encodedMsg concatenates the spy information and encrypts it using TEA Key
     * @param msg spy input to be passed to the server
     * @param byteKey randomly generated TEA key
     * @return byte[] b, encrypted byte[]
     */
    private byte[] encodedMsg(String[] msg, byte[] byteKey){
    	//String[] codedMsg = new String[3];
    	String spyInput = msg[0]+" "+msg[1]+" "+msg[2];
    	byte[] b;
    	// creation of TEA object
    	TEA tea;	
		tea = new TEA(byteKey);
		b = tea.encrypt(spyInput.getBytes());
    	return b;    	
    }
    /*
     * encodingRSA uses the public RSA key of server and encrypts the TEA key passed to the server
     * @returns byte[] 
     */
    private byte[] encodingRSA(byte[] spyData){
    	// Each public and private key consists of an exponent and a modulus
    	// n is the modulus for both the private and public keys
        BigInteger n = new BigInteger("552509289412642367674389249403604359379446550832431135" +
        		"349604293662968808530078780779342656035211401010514425671288536241538459077503" +
        		"1087345177360206606629678292013455055079922747805585411493780104207566013225190" +
        		"679662383827688839037757483051");
        BigInteger e = new BigInteger("65537"); // e is the exponent of the public key
     	// Encode the spyData.
        BigInteger m = new BigInteger(spyData);
        // c is the RSA encoded msg. To encrypt a message M compute C = M^e (mod n)
        BigInteger c = m.modPow(e, n);
        return c.toByteArray();
        
    }
}
