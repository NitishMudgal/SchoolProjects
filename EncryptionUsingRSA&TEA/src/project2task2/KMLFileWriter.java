package project2task2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Nitish
 * This class writes KML File.
 * createContent() method reads the value from the Hashmap containing the list of spies and
 * writes it in ./SecretAgents.kml 
 *
 */

public class KMLFileWriter {	
	public static void fileWriter(HashMap<String, SpyInfo> spyList){
		String kmlContent = createContent(spyList);
		try{
			FileWriter fileWriter = new FileWriter("./SecretAgents.kml");
	        fileWriter.write(kmlContent);
	        fileWriter.flush();
	        fileWriter.close();
		}catch(IOException e){
			
		}
	}
	/**
	 * createContent()
	 * @param list consists of spy information
	 * @return String content which is filled in the kml file
	 */
	private static String createContent(HashMap<String, SpyInfo> list){
		String kmlelement = "";
		String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
	            "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"+
				"<Document>\n+" +
				"<Style id=\"style1\">\n" +
				"<IconStyle>\n<Icon>\n" +
				"<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/bluedot.png</href>\n" +
				"</Icon>\n</IconStyle>\n</Style>\n";
		//loop fills the content from spy list and puts it in the string
		for(Map.Entry<String, SpyInfo> entry : list.entrySet()){
			kmlelement = "\t<Placemark>\n" +
	                "\t<name>"+entry.getKey()+"</name>\n" +
	                "\t<description>"+entry.getValue().getTitle()+"</description>\n" +
	                "<styleUrl>#style1</styleUrl>" +
	                "\t<Point>\n" +
	                "\t\t<coordinates>"+entry.getValue().getLocation()+ "</coordinates>\n" +
	                "\t</Point>\n" +
	                "\t</Placemark>\n";
			content = content + kmlelement;					
		}
		content =  content + "</Document>\n" +
				   "</kml>";		
		return content;
	}
}
