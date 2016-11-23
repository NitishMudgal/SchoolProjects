package edu.cmu.cart;

import java.util.*;
import java.math.BigDecimal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Nitish
 * Cart class is the class for Cart which stored the item chosen by the user.
 */
public class Cart {
  // contents is the HashMap which stores the item and the quantity.
  private static HashMap<Item,Integer> contents;
  private static String cartTotal;
  // static declaration assigns value to the private static variables defined in the Cart class.
  // These variable retain their value for every instance of the Cart class.
  static {
   contents = new HashMap<Item,Integer>();
   cartTotal = "$0.00";   
  }
  
  public HashMap<Item, Integer> getContents() {
      return contents;
  }

  /**
   * Adds a named item to the cart
   * @param itemCode The code of the item to add to the cart
   * @return void
   */
  public void addItem(String itemCode) {

    Catalog catalog = new Catalog(); // create a new catalog class.

    if (catalog.containsItem(itemCode)) { // check if the catalog has the itemCode and add the item in the cart.
      Item item = catalog.getItem(itemCode);

      int newQuantity = 1;
      if (contents.containsKey(item)) {
        Integer currentQuantity = contents.get(item);
        newQuantity += currentQuantity.intValue();
      }

      contents.put(item, new Integer(newQuantity));
    }
  }

  /**
   * removeItems
   * Removes the named item from the cart
   * @param itemCode Name of item to remove
   */
  public void removeItems(String itemCode) {

    contents.remove(new Catalog().getItem(itemCode));
  }

  /**
   * toJSON
   * Reads the content of the cart and generates an JSON document to be sent to the client
   * @return JSON representation of cart contents
   */
  public JSONObject toJSON() {
    JSONObject object = new JSONObject(); //creating a json object.
    object.put("objectType", "cart");
    object.put("cart_generated", System.currentTimeMillis());
    object.put("total", getCartTotal());
    
    JSONArray list = new JSONArray();
    for (Iterator<Item> I = contents.keySet().iterator() ; I.hasNext() ; ) {
      Item item = I.next();
      JSONObject jsonItem = new JSONObject();
      int itemQuantity = contents.get(item).intValue();
      jsonItem.put("item_code", item.getCode());
      jsonItem.put("name", item.getName());
      jsonItem.put("quantity", itemQuantity);
      list.add(jsonItem);
   }
    object.put("items", list);
    return object;
  }
  /**
  * getCartTotal
  * returns the total value of the cart in string format.
  * @return String
  */
  private String getCartTotal() {
    int total = 0;

    for (Iterator<Item> I = contents.keySet().iterator() ; I.hasNext() ; ) {
      Item item = I.next();
      int itemQuantity = contents.get(item).intValue();

      total += (item.getPrice() * itemQuantity);
    }
    cartTotal = "$"+new BigDecimal(total).movePointLeft(2);
    return cartTotal;
  }
  /**
   * getter for cartTotal.
   * @return 
   */
  public String getTotal() {
      return getCartTotal();
  }
}
