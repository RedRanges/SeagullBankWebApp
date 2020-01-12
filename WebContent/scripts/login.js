let usernameInput = document.getElementById( 'inputUsername' );
usernameInput.addEventListener( 'blur', validateUsername );

function validateUsername() {
    let username = document.getElementById( 'inputUsername' ).value;
    if ( username != '' ) {
        if ( /^(?=.*[a-zA-z])[a-zA-Z0-9]{5,20}$/.test( username ) ){
            return true;
        } else {
            return false;
        }
    }   
}


let passwordInput = document.getElementById( 'inputPassword' );
passwordInput.addEventListener( 'blur', validatePassword );

function validatePassword() {
    let password = document.getElementById( 'inputPassword' ).value;
    if ( password !== '' ) {
        if (/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,15}$/.test( password ) ) {
            return true;
        } else {
        	return false;
        }   
    }
}


let loginButton = document.getElementById( 'loginButton' );

loginButton.addEventListener( 'click', submitLogin );

function submitLogin() {
	let loginSubmission = {
			'username' : document.getElementById( 'inputUsername' ).value,
			'password' : document.getElementById( 'inputPassword' ).value
		};
		loginSubmission = JSON.stringify( loginSubmission );
		let loginXhr = new XMLHttpRequest;
		loginXhr.open( "POST", "http://localhost:5678/SeagullBankWebApp/login", true );
		loginXhr.onload = function () {
			  if (this.readyState == 4) {
			    if (this.status == 200) {
			      console.log( loginXhr.response );
			      console.log( document.cookie );
			      window.location.href = ( 'account.html' )
			  } else {
				  console.log( 'not a sucess' );
			  }
			}
		}

		
    loginXhr.send( loginSubmission ); 
}

