package developerworks.ajax.store;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nitish
 * Catalog is the class which contains all the items available for selection in a static HashMap
 */
public class Catalog {

  private static Map<String,Item> items; // to create an inventory of the items.

  static { // static definition of the items HashMap and storing different Item objects.
   items = new HashMap<String,Item>(); 
   items.put("hat001",new Item("hat001","Hat","Stylish bowler hat (SALE!)",1999));
   items.put("dog001",new Item("dog001","Dog","Chocolate labrador puppy",7999));
   items.put("sou001",new Item("sou001","Soup","Can of tasty tomato soup",199));
   items.put("cha001",new Item("cha001","Chair","Swivelling office chair", 4999));
   items.put("str001",new Item("str001","String","Metric tonne of bailing twine", 1999));
   items.put("qua001",new Item("qua001","Quark","Everyone's favorite sub-atomic particle", 49));
  }
 /**
   * getAllItems
   * Gives all the items stored inside the Catalog
   * @return Collection<Item>
   */
  public Collection<Item> getAllItems() {
    return items.values();
  }
  /**
   * containsItem
   * checks if the item is in the catalog
   * @param itemCode
   * @return boolean
   */
  public boolean containsItem(String itemCode) {
    return items.containsKey(itemCode);
  }
 /**
   * getItem
   * Returns the item requested.
   * @param itemCode
   * @return 
   */
  public Item getItem(String itemCode) {
    return items.get(itemCode);
  }

}
