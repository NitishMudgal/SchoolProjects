package developerworks.ajax.servlet;

import developerworks.ajax.store.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nitish
 * CartServlet accepts HTTP Post method request from the client side and processes the request.
 * 
 */
public class CartServlet extends HttpServlet {

  /**
   * Updates Cart, and outputs JSON representation of contents
   */
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {

    Enumeration headers = req.getHeaderNames(); // reading the header from the request.
    while (headers.hasMoreElements()) {
      String header  =(String) headers.nextElement();
      System.out.println(header+": "+req.getHeader(header));
    }

    Cart cart = getCartFromSession(req); // accessing cart object stored in the session.

    String action = req.getParameter("action"); // reading the type of action requested.
    String item = req.getParameter("item"); // reading the item code.
    
    if ((action != null)&&(item != null)) { // null value check.

      if ("add".equals(action)) {
        cart.addItem(item); // calls the addItem method in Cart class.

      } else if ("remove".equals(action)) {
        cart.removeItems(item); // calls the removeItem method in Cart class.

      }
    }

    String cartJSON = cart.toJSON(); // Cart class has toJSON() method which returns JSON of the content stored.
    res.setContentType("application/json");
    res.setCharacterEncoding("utf-8");
    res.getWriter().write(cartJSON); // sending the cart xml to the client.
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
    // Bounce to post, for debugging use
    // Hit this servlet directly from the browser to see XML
    doPost(req,res);
  }
 /*
  * getCartFromSession
  * req - HttpServletRequest from the client machine.
  */
  private Cart getCartFromSession(HttpServletRequest req) {

    HttpSession session = req.getSession(true); // reading the session and storing it.
    Cart cart = (Cart)session.getAttribute("cart"); // getting the stored cart object in session.
   
    if (cart == null) {
      cart = new Cart();
      session.setAttribute("cart", cart); // setting cart in session if it is not available.
    }

    return cart;
  }
}
