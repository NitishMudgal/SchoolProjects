<?php require('/MUDGAL_mysqli_connect.php'); ?> <!-- To connect to the db --> 
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <!-- The above 3 meta tags *must* come first in the head; 
	    any other head content must come *after* these tags -->
	    <title>CARS</title>
	    <!-- Bootstrap -->
	    <link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/font-awesome.min.css" rel="stylesheet">
	    <link rel="stylesheet" href="css/twemoji-awesome.css">
		<link href="css/styles.css" rel="stylesheet">		
	</head>
	<body>
		<nav class="navbar navbar-inverse navbar-fixed-top inverse-navbar">
	        <div class="container">
	        	<div class="row row-header">
	            	<div class="navbar-header col-xs-6 col-sm-3">
	                	<a class="navbar-brand brand-navbar" href="MUDGAL_home.php">CARS</a>
	            	</div>
					<div class="navbar-header col-xs-12 col-sm-9 header-navbar" >
	            		<form class="form-inline" role ="search" action="MUDGAL_home.php" method="GET">
							<div class="form-group col-xs-12">
								<input type="text" class="form-control control-input col-xs-8" id="searchterm" name="searchterm" placeholder="Enter license id">
								<button type="submit" class="btn btn-default" name="submit">
									<span class="glyphicon glyphicon-search"></span>
								</button>
							</div>
						</form>
					</div>            
	        	</div>
			</div>
		</nav>
		<div class="container main-container col-xs-12 col-sm-12">
			<div class="row row-content">
				<div class="col-xs-12 col-sm-2 compose-button">
					<div class="col-sm-offset-2" style="margin-left:2.5em;">
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#composemail">Compose Mail</button>
					</div>
					<ul class="nav nav-pills nav-stacked" style="margin-top:1em;">
						<li class="active">
							<a href="#" class="btn btn-primary">
                                <i class="fa fa-home"></i> <span>Home</span>
                            </a>
						<li>
							<a href="MUDGAL_inbox.php" class="btn btn-primary">
                                <i class="fa fa-envelope-o"></i> <span>Inbox</span>
                            </a>
                        </li>
                    </ul>
				</div>
				<div class="modal" id="composemail" tabindex="-1" role="dialog" aria-labelledby="modallabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
								</button>
								<h3 class="modal-title" id="modallabel">Compose</h3>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
									<div class="form-group">
										<label for="licenseplateID" class="col-sm-2 control-label">LicenseID</label>
										<div class="col-sm-10">
										<input type="text" class="form-control" id="licenseplateID" name="to_user" placeholder="Enter License Plate Number">
										</div>
									</div>
									<div class="form-group">
										<label for="location" class="col-sm-2 control-label">Location</label>
										<div class="col-sm-10">
											<!--<input type="text" class="form-control" id="location" name="location" placeholder="Enter Location">-->
											<select class="form-control" id="location" name="location">
								                <option value="" disabled selected>Enter Location</option>
								                <option value="1">NewYork</option>
								                <option value="2">Pittsburgh</option>
								                <option value="3">San Francisco</option>
								                <option value="4">Chicago</option>
								                <option value="5">Raleigh</option>
								                <option value="6">Washinton DC</option>
								                <option value="7">New Delhi</option>
								                <option value="8">Mumbai</option>
								            </select>
										</div>
									</div>
									<div class="form-group">
										<label for="date" class="col-xs-12 col-sm-2 control-label">Date</label>
										<div class="col-xs-6 col-sm-5 col-md-4">
											<input type="date" class="form-control" id="date" name="date" placeholder="">
										</div>
									</div>
									<div class="form-group">
										<label for="subject" class="col-sm-2 control-label">Subject</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="subject" name="subject" placeholder="">
										</div>
									</div>
									<div class="form-group">
										<label for="message" class="col-sm-2 control-label">Message</label>
										<div class="col-sm-10">
											<textarea class="form-control" id="message" name="message" rows="9"></textarea>
										</div>
										<div class="col-xs-6 col-sm-5 col-md-6 col-sm-offset-2">											
											<div class="btn-group">
												<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
													Select Emotion <span class="caret"></span>
												</button>
												<ul class="dropdown-menu list-inline" role="menu">
													<li value="smile" onclick="selectEmoji(':-&#41')"><i class="twa twa-smile"></i></li>
													<li value="wink" onclick="selectEmoji(';-&#41')"><i class="twa twa-wink"></i></li>
													<li value="frowning" onclick="selectEmoji(':-&#40;')"><i class="twa twa-frowning"></i></li>
													<li value="kissing" onclick="selectEmoji(':-*')"><i class="twa twa-kissing"></i></li>
													<li value="angry" onclick="selectEmoji('x-&#40;')"><i class="twa twa-angry"></i></li>
												</ul>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-2 col-sm-10">
											<button type="button" onclick="sendMail(this.form)" class="btn btn-primary">Send Mail</button>
										</div>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn btn-default">Close</button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 primary content" id="messagefeed">
					<div class="container inbox-container">						
						<div class="panel-group col-xs-12 col-sm-10" id="accordian" role="tablist" aria-multiselectable="true">
							<div class="header">
								<h3>Message Feed</h3>
							</div>
							<?php
								if ($_SERVER['REQUEST_METHOD'] == 'GET' && isset($_REQUEST['submit'])) {									
									$from_user = $_REQUEST['searchterm'];
									// creating query to retrieve result
									$query = "	SELECT 	u1.firstname AS from_user, u1.licenseID AS from_license,
														u2.firstname AS to_user, u2.licenseID AS to_license,
														m.subject, l.locationName, m.date, m.message
												FROM 	user AS u1 INNER JOIN message AS m ON u1.licenseID = m.from_user
														INNER JOIN user AS u2 ON m.to_user = u2.licenseID
														INNER JOIN location AS l ON l.locationID = m.locationID
												WHERE 	u1.licenseID = '$from_user'
												ORDER BY m.date;";
								
								} else {
									// creating query to retrieve result
									$query = "	SELECT 	u1.firstname AS from_user, u1.licenseID AS from_license,
														u2.firstname AS to_user, u2.licenseID AS to_license,
														m.subject, l.locationName, m.date, m.message
												FROM 	user AS u1 INNER JOIN message AS m ON u1.licenseID = m.from_user
														INNER JOIN user AS u2 ON m.to_user = u2.licenseID
														INNER JOIN location AS l ON l.locationID = m.locationID
												ORDER BY m.date;";
								}								
								// run the query
								$result = mysqli_query($dbc, $query);
								$collapsed = ""; // to set class in HTML
								$count = 1;
								$in = "in";
								$active = "active";
								if (mysqli_error($dbc)) {
									echo "<h1> Please provide the right licenseID </h1>";
								}
								while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
										$from = $row['from_user'];
										$to = $row['to_user'];
										$fromLicense = $row['from_license'];
										$toLicense = $row['to_license'];
										$subject = $row['subject'];
										$location = $row['locationName'];
										$date = $row['date'];
										$message = $row['message'];
							?>
							<div class="panel panel-default">
								<div class="panel-heading <?php echo $active; ?>" role="tab" id="heading<?php echo $count; ?>">
									<h4 class="panel-title">
										<a href="#mail<?php echo $count; ?>" onclick="mailClick(<?php echo $count; ?>)" role="button" class="<?php echo $collapsed;?> clicked" data-toggle="collapse" data-parent="#accordian" aria-expanded="false" aria-controls="mail<?php echo $count; ?>">
							<?php 		echo $subject; 	?>
										</a>
									</h4>
								</div>
								<div class="panel-collapse collapse <?php 	echo $in; 	?>" id="mail<?php echo $count; ?>" role="tabpanel" arialabelby="heading<?php echo $count; ?>">
									<div class="panel-body">
										<div class="row">
											<ul class="list-inline">
											  <li class="pull-left"><strong><?php echo $date ?></strong></li>
											  <li class="pull-right"><strong><?php echo $location ?></strong></li>
											</ul>
										</div>
										<div class="row">
											<dl class="dl-horizontal">
											  <dt>From:</dt>
											  <dd><?php echo $from; ?> (<?php echo $fromLicense; ?>)</dd>
											  <dt>Message:</dt>
											  <dd><?php echo $message; ?></dd>
											  <dt>To:</dt>
											  <dd><?php echo $to; ?> (<?php echo $toLicense; ?>)</dd>
											</dl>
										</div>										
									</div>
								</div>
							</div>
							<?php 
										$collapsed = "collapsed";
										$count++;
										$in = "";
										$active = "";
								}	
							?>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="js/bootstrap.min.js"></script>
		<script src="js/script.js"></script>
	</body>
</html>
