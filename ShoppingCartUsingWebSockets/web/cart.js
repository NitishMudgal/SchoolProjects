/*
 * This js file contains methods providing functional methods.
 */

// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * addToCart(itemCode)
 * Adds the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add
 */
function addToCart(itemCode) {
    console.log("inside add");
    //Creating a javascript object to be sent to Web Socket End Point
    var object = {
        "type" : "add", 
        "item_code" : itemCode //This itemcode determines which action is taken by the endpoint.
    };
    // Calling the sendText function defined in cartwebsocket.js
    sendText(object);
}
/*
 * deleteFromCart(itemCode)
 * Deletes the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add
 */
function deleteFromCart(itemCode) {
    console.log("inside delete");
    var object = {
        "type" : "delete", 
        "item_code" : itemCode
    };
    sendText(object);
}


/*
 * Update shopping-cart area of page to reflect contents of cart
 * described in JSON document.
 */
function updateCart(cart) { 
 var generated = cart.cart_generated;
 if (generated > lastCartUpdate) {
   // checking the timestamp of the cart data response .
   lastCartUpdate = generated; // updating the timestamp.
   var contents = document.getElementById("contents");
   contents.innerHTML = "";

   var items = cart.items;
   for (var I = 0 ; I < items.length ; I++) {

     var item = items[I];
     var name = item.name;
     var quantity = item.quantity;

     var listItem = document.createElement("li");
     listItem.appendChild(document.createTextNode(name+" x "+quantity));
     contents.appendChild(listItem); // printing the element to the contents unsorted list.
   }

 }

 document.getElementById("total").innerHTML = cart.total; // appending cart total to total div.
}
