package edu.cmu.cart;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.simple.JSONObject;

/**
 * This class acts as the endpoint for the websocket transmission. It receives message from the client and 
 * opens a peer to peer connection. It receives messages from one session and broadcasts it to all the other
 * connected sessions.
 * @author Nitish
 */
@ServerEndpoint(value = "/websocketendpoint", 
        encoders = {CartEncoder.class},
        decoders = {CartDecoder.class})
public class CartWebSocket {
    // sessions stores all the sessions connected to the endpoint .
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    
    // @OnMessage is invoked each time that the client receives a WebSocket message.
    @OnMessage
    public void broadcastFigure(Cart cart, Session presentSession) throws IOException, EncodeException {
        System.out.println("Inside on message");
        System.out.println("broadcastFigure: " + cart);
        for (Session session : sessions) {
                session.getBasicRemote().sendObject(cart);
        }
    }
    //@OnOpen is invoked when the client connects with the endpoint or when other session is connected.
    /**
     * onOpen sends the catalog and catalog items to all the sessions when they first time make connection with the endpoint.
     * @param session 
     */
    @OnOpen
    public void onOpen (Session session) {
        sessions.add(session);
        Catalog catalog = new Catalog();
        Cart cart = new Cart();
        JSONObject catalogObj = catalog.toJSON();
        JSONObject cartObj = cart.toJSON();
        JSONObject mergedObj = new JSONObject();
        mergedObj.put("catalog", catalogObj);
        mergedObj.put("cart", cartObj);
        sendCatalogToAll(mergedObj.toString());
        try{
            session.getBasicRemote().sendObject(mergedObj.toString());
        } catch (IOException e){
            e.printStackTrace();
        } catch (EncodeException ex) {
            Logger.getLogger(CartWebSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //@OnClose is invoked when session is disconnected.
    @OnClose
    public void onClose (Session session) {
        sessions.remove(session);
    }
    /**
     * sendCatalogToAll sends catalog and cart to all the sessions connected to the endpoint.
     * @param catalog 
     */
    private void sendCatalogToAll(String catalog){
        for(Session s : sessions){
            try {
                s.getBasicRemote().sendObject(catalog);
            } catch (IOException e){
            e.printStackTrace();
            } catch (EncodeException ex) {
                Logger.getLogger(CartWebSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
