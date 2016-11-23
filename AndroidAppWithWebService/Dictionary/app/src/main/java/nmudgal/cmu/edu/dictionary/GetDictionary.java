package nmudgal.cmu.edu.dictionary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Nitish on 4/3/2016.
 */
public class GetDictionary {
    Dictionary diction = null;

    /*
     * search is the public GetDictionary method.
     * Its arguments are the search term, and the Dictionary object that called it.  This provides a callback
     * path such that the synonymReady method in that object is called when the xml is available from the search.
     */
    public void search(String searchTerm, Dictionary diction) {
        this.diction = diction;
        new AsyncFlickrSearch().execute(searchTerm);
    }

    /*
     * AsyncTask provides a simple way to use a thread separate from the UI thread in which to do network operations.
     * doInBackground is run in the helper thread.
     * onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsyncFlickrSearch extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            System.out.println(urls[0]);
            return search(urls[0]);
        }

        protected void onPostExecute(String synonyms) {
            diction.synonymReady(synonyms);
        }

        /*
         * Search Web Service hosted on cloud for the searchTerm argument, and return a xml that can be showed in the resultView
         */
        private String search(String searchTerm) {
            StringBuilder result = new StringBuilder();
            Document doc = getRemoteXML("https://warm-chamber-91456.herokuapp.com/servlet?searchstring=" + searchTerm);
            NodeList root = doc.getElementsByTagName("synonym");
            if(root != null && root.getLength() != 0){
                NodeList child = root.item(0).getChildNodes();
                for(int i = 0; i<child.getLength(); i++){
                    if(!child.item(i).getTextContent().isEmpty() && !child.item(i).getTextContent().equals("\n") ){
                        result.append("\n\n");
                        result.append(child.item(i).getNodeName().toUpperCase() + ": ");
                        result.append(child.item(i).getTextContent());
                    }
                }
            }
            return result.toString();
        }

        /*
         * Given a url that will request XML, return a Document with that XML, else null
         */
        private Document getRemoteXML(String url) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource(url);
                return db.parse(is);
            } catch (Exception e) {
                System.out.print("Yikes, hit the error: " + e);
                return null;
            }
        }
    }
}
