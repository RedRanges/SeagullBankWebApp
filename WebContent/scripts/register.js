let emailInput = document.getElementById( 'inputEmail' );
emailInput.addEventListener( 'change', validateEmail );

let usernameInput = document.getElementById( 'inputUsername' );
usernameInput.addEventListener( 'focus', usernameRequirements );
usernameInput.addEventListener( 'blur', eraseUsernameRequirements );
usernameInput.addEventListener( 'keyup', validateUsername );

let passwordInput = document.getElementById( 'inputPassword' );
passwordInput.addEventListener( 'focus', passwordRequirements );
passwordInput.addEventListener( 'blur', erasePasswordRequirements );
passwordInput.addEventListener( 'keyup', validatePassword );

let passwordVerifyInput = document.getElementById( 'inputVerifyPassword' );
passwordVerifyInput.addEventListener( 'keyup', validateVerifyPassword );


//let registerForm = document.getElementById( 'registerForm' );
//registerForm.addEventListener( 'change',  validateVerifyPassword );
//registerForm.addEventListener( 'keyup',  validateVerifyPassword );

let registerButton = document.getElementById( 'registerButton' );

registerButton.addEventListener( 'click', function() {
	if ( validateEmail() && validateUsername() && validatePassword() && validateVerifyPassword() ) {
		let registerXhr = new XMLHttpRequest;
		let newUserInfo = { 'username' : document.getElementById( 'inputUsername' ).value,
							'password' : document.getElementById( 'inputPassword' ).value,
							'email' : document.getElementById( 'inputEmail' ).value					
							};
		newUserInfo = JSON.stringify( newUserInfo );
		registerXhr.open( "POST", 'http://localhost:5678/SeagullBankWebApp/register_user', true );
		registerXhr.onload = function () {
		  if (this.readyState == 4) {
		    if (this.status == 200) {
		      console.log( 'sick' );
		      window.location.href = ( 'http://localhost:5678/SeagullBankWebApp/register_success.html' );
		    } else {
//			  window.location.href = ( 'http://localhost:5678/SeagullBankWebApp/login.html' );
		    	console.log( 'ruh-oh' );
			  }
			}
		}
		registerXhr.send( newUserInfo ); 
		
	} else {
		// don't register
	}
} );











function validateEmail() {
    let email = document.getElementById( 'inputEmail' ).value;
    if ( email != '' ) {
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test( email ) ) {
            document.getElementById( 'emailFeedback' ).innerHTML = '';
            return true;
        } else {
            document.getElementById( 'emailFeedback' ).innerHTML = "*not a valid email";
            document.getElementById( 'emailFeedback' ).style.color = 'red';
            return false;
        }
    }   
}

function validateUsername() {
    let username = document.getElementById( 'inputUsername' ).value;
    if ( username != '' ) {
        if ( /^(?=.*[a-zA-z])[a-zA-Z0-9]{5,20}$/.test( username ) ){
            document.getElementById( 'usernameFeedback' ).innerHTML = '';
            return true;
        } else {
            document.getElementById( 'usernameFeedback' ).innerHTML = "*not a valid username";
            document.getElementById( 'usernameFeedback' ).style.color = 'red';
            return false;
        }
    }   
}

function usernameRequirements() {
    document.getElementById( 'usernameRequirements' ).innerHTML = '<ul><li>at least 1 letter</li><li>at least 5 characters</li><li>no special characters</li><li>No more than 20 characters</li></ul>'
}

function eraseUsernameRequirements() { 
    document.getElementById( 'usernameRequirements' ).innerHTML = '';
}

function passwordRequirements () {
        document.getElementById( 'passwordRequirements' ).innerHTML = "<ul><li>1 uppercase letter</li><li>1 lowercase letter</li><li>1 number</li><li>8-15 characters in length</li></ul>"
}

function erasePasswordRequirements () {
    document.getElementById( 'passwordRequirements' ).innerHTML = "";
}

function validatePassword() {
    let password = document.getElementById( 'inputPassword' ).value;
    console.log( 'help' );
    if ( password !== '' ) {
        if (/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,15}$/.test( password ) ) {
            document.getElementById( 'passwordFeedback' ).innerHTML = '';
            return true;
        } else {
            document.getElementById( 'passwordFeedback' ).innerHTML = "*not a valid password";
            document.getElementById( 'passwordFeedback' ).style.color = 'red';
            return false;
        }   
    }
}

function validateVerifyPassword() {
    let password = document.getElementById( 'inputPassword' ).value;
    let verifyPassword = document.getElementById( 'inputVerifyPassword' ).value;
    if ( verifyPassword !== '' ) {
        if ( password === verifyPassword ) {
            document.getElementById( 'verifyPasswordFeedback' ).innerHTML = '';
            return true
        } else {
            document.getElementById( 'verifyPasswordFeedback' ).innerHTML = 'passwords do not match';
            document.getElementById( 'verifyPasswordFeedback' ).style.color = 'red';
            return false;
        }
    }
}
