// The name of the wsURI of the endpoint and declaration the WebSocket. 
// The wsURI URI scheme is part of the WebSocket protocol and specifies the path to the endpoint.
var wsUri = "ws://" + document.location.host + document.location.pathname + "websocketendpoint";
// This script will initiate the session handshake with the server when cartwebsocket.js is loaded by the browser.
var websocket = new WebSocket(wsUri);

websocket.onerror = function(evt) { onError(evt) };

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
// Assigning onMessage(evt) method to websocket onmessage parameter. This function is called when
// cartwebscoekt gets response.
websocket.onmessage = function(evt) { onMessage(evt) };

// sendText(json) sends the json string to the endpoint.
function sendText(json) {
    console.log("sending text: " + json);
    websocket.send(JSON.stringify(json));
}
// onMessage is called when the endpoint sends a response back to the client peer.               
function onMessage(evt) {
    console.log("received: " + evt.data);
    tableDiv = document.getElementById("outputTable");
    json = JSON.parse(evt.data);
    if(tableDiv.innerHTML == "") {
        drawCatalogTable(json.catalog);
    }    
    updateCart((json.cart != null)? json.cart : json);
}
// drawCatalogTable accesses the data from the response and then creates a table to show the items availble
// to put in the shopping cart.
function drawCatalogTable(data) {
    tableElem = document.createElement('table');
    tableElem.border = '1';
    
    tableHeader = document.createElement('thead');
    tHeadData1 = document.createElement('th');
    tHeadData2 = document.createElement('th');
    tHeadData3 = document.createElement('th');
    tHeadData4 = document.createElement('th');
    tHeadData5 = document.createElement('th');
    
    tHeadData1.innerHTML = "Name";
    tHeadData2.innerHTML = "Description";
    tHeadData3.innerHTML = "Price";
    
    tableHeader.appendChild(tHeadData1);
    tableHeader.appendChild(tHeadData2);
    tableHeader.appendChild(tHeadData3);
    tableHeader.appendChild(tHeadData4);
    tableHeader.appendChild(tHeadData5);
    
    tableElem.appendChild(tableHeader);
    
    tableBody = document.createElement('tbody');
    tableElem.appendChild(tableBody);
    
    items = data.items;
    
    for(i = 0; i<items.length; i++){
        tableRow = document.createElement('tr');
        tData1 = document.createElement('td');
        tData2 = document.createElement('td');
        tData3 = document.createElement('td');
        tData4 = document.createElement('td');
        tData5 = document.createElement('td');
        tData1.innerHTML = items[i].name;
        tData2.innerHTML = items[i].description;
        tData3.innerHTML = items[i].price;
        tData4.innerHTML = "<button onclick=addToCart(\""+ items[i].itemCode + "\")>Add to Cart</button>";
        tData5.innerHTML = "<button onclick=deleteFromCart(\""+ items[i].itemCode + "\")>Delete From Cart</button>";
        
        tableRow.appendChild(tData1);
        tableRow.appendChild(tData2);
        tableRow.appendChild(tData3);
        tableRow.appendChild(tData4);
        tableRow.appendChild(tData5);  
        
        tableBody.appendChild(tableRow);
    }        
    tableDiv = document.getElementById('outputTable');
    tableDiv.appendChild(tableElem);
    
}


