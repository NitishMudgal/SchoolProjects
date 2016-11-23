<?php	//This file acts as the backend to store new composed message and is called when the compose mail action is used.

	// importing sql connection php
	require('/MUDGAL_mysqli_connect.php');

	$to_user = $_REQUEST["to_user"];
	$from_user = "license01";
	$location = $_REQUEST["location"];
	$date = $_REQUEST["date"];
	$subject = $_REQUEST["subject"];
	$message = $_REQUEST['message'];

	$query = 	"INSERT INTO `message` (`messageID`, `to_user`, `from_user`, `subject`, `message`, `date`, `locationID`)
				VALUES (NULL, '$to_user', '$from_user', '$subject', '$message', '$date', '$location');";
	// run the query
	$result = mysqli_query($dbc, $query);
	echo "result is fine";
?>