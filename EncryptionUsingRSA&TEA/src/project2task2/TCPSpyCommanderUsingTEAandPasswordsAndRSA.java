package project2task2;

import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Nitish
 * TCPSpyCommanderUsingTEAandPasswordsAndRSA is the server class which opens a connection
 * and waits for the clients to connect and upload their locations.
 * It firstly decrypts the RSA key to get the TEA key and then uses the TEA key to decrypt the data
 * provided by the client of the spies
 *
 */

public class TCPSpyCommanderUsingTEAandPasswordsAndRSA {
	HashMap<String, SpyInfo> spyList = new HashMap<String,SpyInfo>();// Stores the spy list
	public static void main (String args[]) {
        try{
            System.out.println("Welcome Sean Beggs.");
            TCPSpyCommanderUsingTEAandPasswordsAndRSA commander = new TCPSpyCommanderUsingTEAandPasswordsAndRSA();
            commander.init();// initializes the spy information
            System.out.println("Waiting for spies to visit...");
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                    Socket clientSocket = listenSocket.accept();
                    Connection c = new Connection(clientSocket, commander.spyList);
            }
        }catch(IOException e){
            System.out.println("Listen socket:"+e.getMessage());
        }
    }
    /**
     * init()
     * initializes the data about the spies and stores it in the HashMap spyList.
     * The spyList is used to generate KML file
     */
    private void init(){// Initializes the spy info and stored them for construction of .kml file    	
    	SpyInfo spy1 = new SpyInfo("seanb", "1a2a2bb2bcc5831b9b5e605d74104f0f","salt", "-79.945289,40.44431,0.00000", "Spy Commander");
    	SpyInfo spy2 = new SpyInfo("mikem", "95d8e111a0afb5766fae1c6e9914211f", "salt", "-79.956264,40.441068,0.00000", "Spy");
    	SpyInfo spy3 = new SpyInfo("joem", "9324c6b4b2569511f63267dfc8bf6bf2", "salt", "-79.945389,40.444216,0.00000", "Spy");
    	SpyInfo spy4 = new SpyInfo("jamesb", "a8577591b7fd7eaafb80a9df4da91751","salt", "-79.940450,40.437394,0.00000", "Spy");
    	spyList.put(spy1.getUserName(), spy1);
    	spyList.put(spy2.getUserName(), spy2);
    	spyList.put(spy3.getUserName(), spy3);
    	spyList.put(spy4.getUserName(), spy4);
    	KMLFileWriter.fileWriter(spyList);
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    HashMap<String, SpyInfo> spyList;
    static int spyCount = 1;
    public Connection (Socket aClientSocket, HashMap<String, SpyInfo> spyList) {
    	this.spyList = spyList;
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream( clientSocket.getInputStream());
            out =new DataOutputStream( clientSocket.getOutputStream());
            this.start();
        } catch(IOException e) {
            System.out.println("Connection:"+e.getMessage());
        }
    }

    public void run(){    	
    	String message = "";
    	String sysMessage = "";
    	boolean listChanged = false;
        try {        	
    		byte[] rsaEncodedTEAKey = new byte[250];
    		// countTEAKey stores the number of bytes read
            int countTEAKey = in.read(rsaEncodedTEAKey);// read rsaEncodedTEAKey from stream and save it in spyData
            //As the rsaEncodedTeaKey consists of elements which have no value we store its value in
            // a finalRSAEncodedTEAKey with length equal to the countTEAKey
            byte[] finalRSAEncodedTEAKey = new byte[countTEAKey];
            for(int i=0;i<countTEAKey;i++){
            	finalRSAEncodedTEAKey[i] = rsaEncodedTEAKey[i];
            }
            //Decode RSA key to get the TEAKey
            BigInteger decodedTEAKey = decodeTEAKey(finalRSAEncodedTEAKey);
            // asking for second request when the server get the RSA decrypted
            out.writeUTF("Done");
            //reading the data passed in the second request
            byte[] encodedTEAData = new byte[250];
            int countData =  in.read(encodedTEAData);
            byte[] finalEncodedData = new byte[countData];
            for(int j=0;j<countData;j++){
            	finalEncodedData[j] = encodedTEAData[j];
            }
            TEA teaDecoder = new TEA(decodedTEAKey.toByteArray());
            String data = new String((teaDecoder.decrypt(finalEncodedData)));
            String[] splitData = data.split(" ");// split the concatenated data into array containing the credentials
            if(spyList.containsKey(splitData[0])){//checks if the spy username is valid or not
            	for(Map.Entry<String,SpyInfo> entry : spyList.entrySet() ){
            		if(entry.getKey().equals(splitData[0])){
            			//compares the password hash saved in the list with the password provided and converted into MD5
            			if(entry.getValue().getHash().equals(PasswordHash.convertMD5(splitData[1]))){
            				entry.getValue().setLocation(splitData[2]);
            				listChanged = true;// KML file is written only if the listChanged is true
            				sysMessage = "Got visit "+ spyCount++ + " from "+ entry.getKey();
            				message = "Thank you. Your location was securely transmitted to Intelligence Headquarters";
            			}else{
            				sysMessage = "Got visit "+ spyCount++ + " from "+ entry.getKey()+". Illegal Password attempt. This may be an attack";
            				message = "Not a valid user-id or password.";
                        } 
            		}            			
            	}
            }else{
            	sysMessage = "Got illegal visit "+ spyCount++ + " from "+ splitData[0]+". This may be an attack";
            	message = "Not a valid user-id or password.";
            }
            if(listChanged){
            	KMLFileWriter.fileWriter(spyList);
            }
            System.out.println(sysMessage);
            out.write(message.getBytes());
        }catch (EOFException e){
            System.out.println("EOF:"+e.getMessage());
        }catch(IOException e) {
            System.out.println("readline:"+e.getMessage());
        } finally{ 
            try {
                clientSocket.close();
            }catch (IOException e){
            /*close failed*/
            }
        }
    }
    /**
     * decodeTEAKey is used to decode RSA key and get the TEA Key used for the decoding of
     * data provided by client spy
     * @param rsaEncodedTEAKey
     * @return BigInteger clear which is the TEAKey
     */

    private BigInteger decodeTEAKey(byte[] rsaEncodedTEAKey){
    	// Each public and private key consists of an exponent and a modulus
    	// n is the modulus for both the private and public keys
        BigInteger n = new BigInteger("552509289412642367674389249403604359379446550832431135" +
        		"349604293662968808530078780779342656035211401010514425671288536241538459077503" +
        		"1087345177360206606629678292013455055079922747805585411493780104207566013225190" +
        		"679662383827688839037757483051");
        // d is the exponent of the private key 
        BigInteger d = new BigInteger("3426321397120498513362303412463385320338048235184959061732" +
        		"8558986603564881333386954596707549297437722003337485897292658970478120300939254018" +
        		"76941747013377091029264783157400017204999263039147333648184100700995731673530960082" +
        		"756432938836277825");
        // c refers to the BigInteger generated through rsaEncodedTEAKey
        BigInteger c = new BigInteger(rsaEncodedTEAKey);
        //To decrypt the encodedTEAKey compute M = C^d (mod n)
        BigInteger clear = c.modPow(d, n);
        return clear;
    }
}