<%-- 
    Document   : index
    Created on : Sep 12, 2016, 1:54:05 PM
    Author     : Nitish
--%>

<%@ page import="java.util.*" %>
<%@ page import="developerworks.ajax.store.*" %>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" language="javascript" src="ajax1.js"></script>
        <script type="text/javascript" language="javascript" src="cart.js"></script>
    </head>
    <body>
        <div style="float: left; width: 500px">
            <h2>Catalog</h2>
            <table border="1">
              <thead><th>Name</th><th>Description</th><th>Price</th><th></th></thead>
              <tbody>
              <%
                // Create a new Catalog class object. The getAllItems method in the object is then called and the iterator
                // of the list is assigned to a Iterator<Item> reference.
                // for loop works till we have items left in the list.
                for (Iterator<Item> I = new Catalog().getAllItems().iterator() ; I.hasNext() ; ) {
                  Item item = I.next();
              %>
              <!-- The item description is then added to table rows. -->
                <tr>
                    <td><%= item.getName() %></td>
                    <td><%= item.getDescription() %></td>
                    <td><%= item.getFormattedPrice() %></td>
                    <td><button onclick="addToCart('<%= item.getCode() %>')">Add to Cart</button></td>
                    <td><button onclick="deleteFromCart('<%= item.getCode() %>')">Delete from Cart</button></td>
                </tr>
                <% } %>
              </tbody>
            </table>
        </div>
        <!-- The below div contains the Cart and its content -->
        <div style="position: absolute; top: 0px; right: 0px; width: 250px">
        <h2>Cart Contents</h2>
        <ul id="contents">
        <%  // Getting the cart ohject stored in the session and reading it to fill the cart
            Cart cart = (Cart)session.getAttribute("cart");
            if(cart != null) {
                HashMap<Item, Integer> map = cart.getContents();
                for (Map.Entry<Item, Integer> mapEntry : map.entrySet()) {    
             %>
             <li>  <%= mapEntry.getKey().getName() %> x <%= mapEntry.getValue() %> </li>
             <% }} %>    
        </ul>
        Total cost: <span id="total">${cart.getTotal()}</span>
        </div>
    </body>
</html>
