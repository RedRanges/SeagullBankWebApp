let applySubmission = document.getElementById( 'apply-submission' );
applySubmission.addEventListener( 'click', function() {
	let applicationMessage = document.getElementById( 'application-message' );
	applicationMessage.innerHTML = '<br>';
	if ( document.getElementById( 'account-selection' ).value == 'checking' || 
			document.getElementById( 'account-selection' ).value == 'savings') {
	let accountApplicationSubmissionXhr = new XMLHttpRequest;
	
	  
	  let accountApplicationSubmission = { 'type' : document.getElementById( 'account-selection' ).value };
//	  console.log( transferSubmission );
	  accountApplicationSubmission = JSON.stringify( accountApplicationSubmission );
	  accountApplicationSubmissionXhr.open( 'POST', 'http://localhost:5678/SeagullBankWebApp/create_account', true );
	  accountApplicationSubmissionXhr.onload = function () {
		if ( this.readyState == 4 ) {
			if ( this.status === 200 ) {
				let response = accountApplicationSubmissionXhr.response;
				console.log( 'response : ', response );
				applicationMessage.innerHTML = 'Your application is pending. Please wait for one of our qualified employees to update the status of your account';
				applicationMessage.style.color = 'green';
				
			}
		} else {
			console.log( 'can\'t' );
		}
		
	  }
	  accountApplicationSubmissionXhr.send( accountApplicationSubmission );
	} else {
	
		applicationMessage.innerHTML = '*Please select an account type';
		applicationMessage.style.color = 'red';
		
	}
	
} );