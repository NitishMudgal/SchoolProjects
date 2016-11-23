function selectEmoji(emotion) {
	console.log("inside");
	console.log(emotion);
	var emo = "";
	var id = document.getElementById("message");
	id.value += " "+emotion;
}

function mailClick(id) {
	$('.panel-heading').removeClass("active");
	$('#heading'+id).addClass("active");
}

function sendMail(form) {
	var to_user = form[0].value;
	var location = form[1].value;
	var date = form[2].value;
	var subject = form[3].value;
	var message = form[4].value;
	$.ajax({
    url: 	'MUDGAL_composemail.php?to_user='+to_user+'&location='+location+'&date='+date+'&message='+message+'&subject='+subject+
    		'&from_user=license01',
	beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Negotiate");
	},		
	success: function(data) {
			mailCallBack(data);
	},
	cache: false
    });
}

function mailCallBack(data) {
	/*$('#messagefeed').load('home.php', function() {
  		alert('Load was performed.');
  	});*/
	location.reload();
}

/*function searchMail(searchterm) {
	var searchValue = searchterm.value;
	var url = SELECT 	u1.firstname AS from_user, u1.licenseID AS from_license,
													u2.firstname AS to_user, u2.licenseID AS to_license,
													m.subject, l.locationName, m.date, m.message
											FROM 	user AS u1 INNER JOIN message AS m ON u1.licenseID = m.from_user
													INNER JOIN user AS u2 ON m.to_user = u2.licenseID
													INNER JOIN location AS l ON l.locationID = m.locationID
											ORDER BY m.date;";

}*/