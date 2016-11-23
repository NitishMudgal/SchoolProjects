package edu.cmu.cart;

import java.math.BigDecimal;

/**
 * @author Nitish
 * Item is the pojo class which contains Item information to be stored and getters and setters.
 */
public class Item {
  private String code;
  private String name;
  private String description;
  private int price;
  // Constructor
  public Item(String code,String name,String description,int price) {
    this.code=code;
    this.name=name;
    this.description=description;
    this.price=price;
  }
  // getters and setters.
  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getPrice() {
    return price;
  }
   /**
   * getFormattedPrice
   * gives the formatted price string
   * @return String
   */
  public String getFormattedPrice() {
    return "$"+new BigDecimal(price).movePointLeft(2);
  }
  /**
   * equals check of the two item objects are similar or not.
   * @param o any kind of object
   * @return boolean
   */
  public boolean equals(Object o) {
    if (this == o) return true;
    if (this == null) return false;
    if (!(o instanceof Item)) return false;
    return ((Item)o).getCode().equals(this.code);
  }
}
