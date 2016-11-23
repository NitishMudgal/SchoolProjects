package Project1Task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Nitish
 * This BigPictureModel file acts as the Model in MVC framework for Project1 Task4
 * It provides method which connects to the Smithsonian Bird Pictures and uses screenscraping to 
 * get the pictures and send it to the servlet
 * 
 */
public class BirdPictureModel {
    
    
    private String pictureTag; // The search string of the desired picture
    private String pictureURL = ""; // The URL of the picture image
    private String photoGrapherName; // The name of the photographer     

    /**
     * Creates the searchTag which is used to create the url
     * Arguments.
     * @author Nitish Mudgal
     * @param birdName The tag of the bird to be searched for.
     * 
     */
    public void createSearchTag(String birdName){// Created the birdName searchtag to be appended to the url
        pictureTag = birdName;
        String searchTag = null; // The concatenated string for url creation
        String[] nameParts = birdName.split(" ");
        for(String part : nameParts){
           if(searchTag == null){
               searchTag = part;
           }else{
               searchTag = searchTag+"+"+part;
           }
        }
        doSearch(searchTag);
    }
    
    /**
     * Creates a searchURL which is used to get the response on which scraping is done.
     * Arguments.
     * @author Nitish Mudgal
     * @param searchTag the tag created in createSearhTag().
     */
    
    public void doSearch(String searchTag) {        
        ArrayList<Picture> picURLS = new ArrayList<Picture>();
        String response = "";

        // Create a URL for the desired page
        String searchURL = "http://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/bird.cfm?pix=" + searchTag;
        response = fetch(searchURL);

        /*
         * Find the picture URL to scrape
         *
         * Screen scraping is an art that requires looking at the HTML
         * that is returned creatively and figuring out how to cut out
         * the data that is important to you.
         * 
         * First find the src target that starts with farm
         */
        
        if(!response.equals("")){
            int urlIndex = 0;
            int cutLeft = 0;
            int cutRight = 0;
            int authorIndex = 0;
            int authorLeft = 0;
            int authorRight = 0;
            while (urlIndex != -1){// If not found, then no such photo is available.
                Picture pic = new Picture();
                urlIndex = response.indexOf("src=\"https://ids",cutLeft);
                authorIndex = response.indexOf("&copy;", authorLeft);
                //Getting author name
                if(authorIndex != -1 && urlIndex != -1 ){
                   authorLeft = authorIndex + 7;
                   authorRight = response.indexOf("<",authorLeft);
                   pic.setPhotographerName(response.substring(authorLeft,authorRight));
                   authorIndex++;          
                    //Getting bird picture URL

                   cutLeft = urlIndex+5;  // I don't want the src=" part, so skip 5 characters
                    // Look for the close quote                
                   cutRight = response.indexOf("\"", cutLeft);
                   // Now snip out the part from positions cutLeft to cutRight and add to Arraylist
                   pic.setPictureURL(response.substring(cutLeft, cutRight));               
                   urlIndex++;
                   picURLS.add(pic);
                }            
            }
            for(Picture s : picURLS){
                System.out.println(s.getPhotographerName()+" "+s.getPictureURL());
            }
            Random rIndex = new Random();
            int picIndex = rIndex.nextInt(picURLS.size());
            Picture selected = picURLS.get(picIndex);
            pictureURL = selected.getPictureURL();
            photoGrapherName = selected.getPhotographerName();
        }
    }
    

    /*
     * Return a URL of an image of appropriate size
     * 
     * Arguments
     * @param picsize The string "mobile" or "desktop" indicating the size of
     * photo requested.
     * @return The URL an image of appropriate size.
     */
    public String birdPictureSize(String picsize) {
        int urlFinal = pictureURL.lastIndexOf("?");// get the index of "?"        
        /*
         * To get the pic of right size we need to get change
         * the max_h parameter to max_w and provide 250 for mobile and
         * 500 for desktop
         */
        String url = pictureURL.substring(0, urlFinal+1);// get the url substring till "?"
        String lastUrl = pictureURL.substring(urlFinal+10, pictureURL.length());// remaining url part without "max_h=200"
        if(picsize.equals("mobile")){
          pictureURL = url+"max_w=250"+lastUrl;  
        }else{
           pictureURL = url+"max_w=500"+lastUrl;
        }        
        return pictureURL;
    }

    /*
     * Return the picture tag.  I.e. the search string.
     * 
     * @return The tag that was used to search for the current picture.
     */
    public String getPictureTag() {
        return (pictureTag);
    }
    
     /*
     * Return the picture url.
     * 
     * @return The url of the bird picture.
     */
    public String getPictureUrl(){
        return pictureURL;
    }
    
    /*
     * Return the photographer name.
     * 
     * @return The photographer name.
     */
    
    public String getPhotographerName(){
        return photoGrapherName;
    }

    /*
     * Make an HTTP request to a given URL
     * 
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which 
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            return response;
           // System.out.println("Eeek, an exception");
            // Do something reasonable.  This is left for students to do.
        }finally{
            return response;
        }        
    }
}
