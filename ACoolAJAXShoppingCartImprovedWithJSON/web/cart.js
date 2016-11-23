// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * Adds the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add
 */

function addToCart(itemCode) {

 var req = newXMLHttpRequest();

 req.onreadystatechange = getReadyStateHandler(req, updateCart);
 
 req.open("POST", "cart.do", true);
 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 req.send("action=add&item="+itemCode);
}

function deleteFromCart(itemCode) {

 var req = newXMLHttpRequest();

 req.onreadystatechange = getReadyStateHandler(req, updateCart);
 
 req.open("POST", "cart.do", true);
 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 req.send("action=remove&item="+itemCode);
}


/*
 * Update shopping-cart area of page to reflect contents of cart
 * described in JSON document.
 */
function updateCart(cartJSON) {
 var cart = JSON.parse(cartJSON);
 var generated = cart.cart_generated;
 // checking the timestamp of the cart data response .
 if (generated > lastCartUpdate) {
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
