package edu.cmu.andrew.nmudgal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Nitish
 * Model Class acts as the model for the web service which interacts with Merriam Webster REST API and parses the xml response
 * and gives the requires data in xml form and forwards it to the android app.
 */
public class Model {
   
    /**
     * This function takes the search string input from android app and calls the api and parses the data to forward
     * the require response
     * @param searchString User Input string
     * @return 
     */
    public Synonym modifyResponse(String searchString){
        String xml = fetchXML(searchString);
        Synonym syn = parseXML(xml);
        return syn;
    }
    
    /**
     * Calls the REST api and fetches the xml response from it
     * @param searchString User Input string
     * @return 
     */
    public String fetchXML(String searchString){
        String response = "";
        try {
            URL url = new URL("http://www.dictionaryapi.com/api/v1/references/thesaurus/xml/" +searchString+"?key=e292963f-5bb0-4e60-9fa9-a482327e10a2");
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which 
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str = "";
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");            
        }
        return response;
    }
    
    /**
     * Parses the input xmlString into required xml to be forwarded to android app
     * @param xmlString
     * @return 
     */
    private Synonym parseXML(String xmlString){
        Synonym syn = new Synonym(); // Creates an object of Synonym class which stores different values
        Document wordSynonyms = getDocument(xmlString); // Initializes an instance of Document class used to generate xml.
        wordSynonyms.getDocumentElement().normalize();
        
        NodeList nlSearchTerm = wordSynonyms.getElementsByTagName("hw");
        // if "hw" tag doesnot have value this means there is no word returned from the api
        if(nlSearchTerm != null && nlSearchTerm.getLength() != 0){ 
            Node title = nlSearchTerm.item(0);
            if(title != null){
                syn.setTitle(title.getTextContent());
            }      
            //Checking for other tags if present or not
            NodeList nlType = wordSynonyms.getElementsByTagName("fl");
            Node type = nlType.item(0);
            if(type != null){                
                syn.setType(type.getTextContent());
            }

            NodeList nlMeaning = wordSynonyms.getElementsByTagName("mc");
            Node meaning = nlMeaning.item(0);
            if(meaning != null){
                syn.setMeaning(meaning.getTextContent());
            }

            NodeList nlExample = wordSynonyms.getElementsByTagName("vi");
            Node example = nlExample.item(0);
            if(example != null){
                syn.setExample(example.getTextContent());
            }

            NodeList nlSynonyms = wordSynonyms.getElementsByTagName("syn");
            Node synonyms = nlSynonyms.item(0);
            if(synonyms != null){
                syn.setSynonyms(synonyms.getTextContent());
            }

            NodeList nlRelational = wordSynonyms.getElementsByTagName("rel");
            Node related = nlRelational.item(0);
            if(related != null){
                syn.setRelated(related.getTextContent());
            }

            NodeList nlAntonyms = wordSynonyms.getElementsByTagName("ant");
            Node antonyms = nlAntonyms.item(0);
            if(antonyms != null){
                syn.setAntonyms(antonyms.getTextContent());
            }
            // if there is no word, the api will return some suggestions which are converted to xml
        }else{
            NodeList nlSuggestions = wordSynonyms.getElementsByTagName("suggestion");
            ArrayList<String> suggestions = new ArrayList<>();
            for(int i = 0; i<nlSuggestions.getLength(); i++){
                suggestions.add(nlSuggestions.item(i).getTextContent());
            }
            syn.setSuggestions(suggestions);// setting suggestions from the api to synonyms object          
        }
        return syn;        
    }
    /**
     * Initializes Document class with string 
     * @param xmlString
     * @return 
     */
    private Document getDocument(String xmlString) {
        System.out.println(xmlString);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document wordSynonyms = null;
        try {
            builder = factory.newDocumentBuilder();
            wordSynonyms = builder.parse(new InputSource(new StringReader( xmlString )));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordSynonyms;
    }
    /**
     * Takes synonym object and uses it to generate xml document
     * @param syn
     * @return 
     */
    public String toXML(Synonym syn) {
        String xmlString = "";
        try {
            DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlBuilder;
            Document xmlDoc = null;
            xmlBuilder = xmlFactory.newDocumentBuilder();
            xmlDoc = xmlBuilder.newDocument();
            Element root = xmlDoc.createElement("synonym");
            xmlDoc.appendChild(root);
            // Check to see if the api has returned meaning of the searched word or not and setting those meanings in xml doc
            if(syn.getSuggestions().size() == 0){
                Element title = xmlDoc.createElement("title");
                title.setTextContent(syn.getTitle());
                Element type = xmlDoc.createElement("type");
                type.setTextContent(syn.getType());
                Element meaning = xmlDoc.createElement("meaning");
                meaning.setTextContent(syn.getMeaning());
                Element example = xmlDoc.createElement("example");
                example.setTextContent(syn.getExample());
                Element synEl = xmlDoc.createElement("synonyms");
                synEl.setTextContent(syn.getSynonyms());
                Element related = xmlDoc.createElement("related");
                related.setTextContent(syn.getRelated());
                Element antonyms = xmlDoc.createElement("antonyms");
                antonyms.setTextContent(syn.getAntonyms());
                //appending chid nodes to main node 
                root.appendChild(title);
                root.appendChild(type);
                root.appendChild(meaning);
                root.appendChild(synEl);
                root.appendChild(example);
                root.appendChild(related);
                root.appendChild(antonyms);
            }else{
                Element suggestions = xmlDoc.createElement("suggestions");
                String suggest = syn.getSuggestions().get(0);
                for(int i = 1; i < syn.getSuggestions().size(); i++){
                    suggest += ", " + syn.getSuggestions().get(i);                           ;
                }
                suggestions.setTextContent(suggest);
                root.appendChild(suggestions);
            }           
            
            TransformerFactory transformerFac = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFac.newTransformer();
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            StringWriter writer = new StringWriter();
            try {
                transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
                xmlString = writer.getBuffer().toString();
            } catch (TransformerException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return xmlString;
    }
}
