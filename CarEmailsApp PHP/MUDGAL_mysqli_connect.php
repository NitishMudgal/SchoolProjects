<?php  #Script – MUDGAL_mysqli_connect.php (mysqli_connect)

	//Set the database access information as constants:
	DEFINE ('DB_USER', 'root');
	DEFINE ('DB_PASSWORD', '');
	DEFINE ('DB_HOST', 'localhost');
	DEFINE ('DB_NAME', 'homework');

	//Make the connection:
	$dbc = @mysqli_connect (DB_HOST, DB_USER, DB_PASSWORD, DB_NAME)
			OR die ('Could not connect to MySQL: ' . Mysqli_connect_error() );

	//Set the encoding:
	mysqli_set_charset($dbc, 'utf8');

?>
