package edu.cmu.cart;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * CartDecoder class decodes web socket messages (JSON) to the POJO class, Cart class in this scenario
 * @author Nitish
 */
public class CartDecoder implements Decoder.Text<Cart> {
    /**
     * Decodes the json object sent from the session and then checks for the type of request and forwards it
     * to the right method.
     * @param string
     * @return Cart
     * @throws DecodeException 
     */
    @Override
    public Cart decode(String string) throws DecodeException {
        
        System.out.println("decoding: " + string);
        JsonObject jsonObject = Json.createReader(new StringReader(string)).readObject();
        System.out.println(jsonObject.getString("item_code"));
        if(jsonObject.getString("type").equals("add")){
            new Cart().addItem(jsonObject.getString("item_code")); // Adds item to the cart
        }else if(jsonObject.getString("type").equals("delete")) {
            new Cart().removeItems(jsonObject.getString("item_code")); // Deletes item from the cart.
        }        
        return new Cart();
    }
    /**
     * Checks the possibility of the request being converted to a suitable JSON object.
     * @param string
     * @return boolean
     */
    @Override
    public boolean willDecode(String string) {
        System.out.println("inside will Decode");
        try {
            Json.createReader(new StringReader(string)).readObject();
            return true;
        } catch (JsonException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
    
}
