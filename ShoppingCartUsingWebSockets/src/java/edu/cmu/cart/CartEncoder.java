package edu.cmu.cart;

import java.util.Map;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * CartEncoder class encodes Cart as a JSON string for sending to the endpoint.
 * @author Nitish
 */
public class CartEncoder implements Encoder.Text<Cart>{

    @Override
    public String encode(Cart cart) throws EncodeException {
        System.out.println("inside encode");
        return cart.toJSON().toString(); // Converting cart json to string to be sent to all the sessions.
        
        //return jsonObject.toString();
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
