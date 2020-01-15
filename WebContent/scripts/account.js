window.onload = function() {
	let sessionXHR = new XMLHttpRequest;
	sessionXHR.open( "GET", 'http://localhost:5678/SeagullBankWebApp/account', true );
	sessionXHR.onload = function () {
	  if (this.readyState == 4) {
	    if (this.status == 200) {
	      getAccountData();
	    } else {
		  window.location.href = ( 'http://localhost:5678/SeagullBankWebApp/login.html' );
		  }
		}
	}
	sessionXHR.send(); 
};

let logoutButton = document.getElementById( 'logoutButton' );
logoutButton.addEventListener( 'click',  function () {
	let logoutXhr = new XMLHttpRequest;
	logoutXhr.open( 'GET', 'http://localhost:5678/SeagullBankWebApp/logout', true );
	logoutXhr.onload = function () {
		if ( this.readyState == 4 ) {
			if ( this.status === 200 ) {
				window.location.href = logoutXhr.response + '.html';
			} else {
				window.location.href = 'login.html';
			}
		}
	}
	logoutXhr.send(); 
} );

function getAccountData() {
	let accountXhr = new XMLHttpRequest;
	accountXhr.open( 'POST', 'http://localhost:5678/SeagullBankWebApp/account_number', true );
	accountXhr.onload = function () {
		if ( this.readyState == 4 ) {
			if ( this.status === 200 ) {
				let response = accountXhr.response;
				let arr = parseResponseToJsonArray( response );
				document.getElementById( 'selected-account' ).innerHTML = arr[ 0 ].accountNumber;
								
				updateLineGraph();
				let accountList = document.getElementById( 'nav-account-list' );
				
				arr.forEach( element => { 
					accountList.innerHTML += `<li class='nav-item'><button id="selectableAccount" class="btn nav-link"><div class="card"><div class="card-body">
						<h5 class="card-title">${element.type} Account : ${ element.accountNumber }</h5><p class="card-text">
						Balance : $${element.balance}</p> </div> </div></button></li>`;
				
				} );
				
				let container = document.querySelectorAll("#selectableAccount");
				container.forEach( element => {
					element.addEventListener( 'click', function () {
										
						let selectedAccountXhr = new XMLHttpRequest;
						let accountCardValue = this.childNodes[ 0 ].childNodes[ 0 ].childNodes[ 1 ].innerHTML;
						accountCardValue = accountCardValue.slice( accountCardValue.length - 8 );
					
						selectedAccountXhr.open( 'POST', 'http://localhost:5678/SeagullBankWebApp/select_account', true );
						let selectedAccountNumber = {'accountNumber' : parseInt( accountCardValue ) };
						selectedAccountNumber = JSON.stringify( selectedAccountNumber );
						console.log( selectedAccountNumber );
						
						selectedAccountXhr.onload = function () {
							if ( this.readyState == 4 ) {
								if ( this.status === 200 ) {
									let response = selectedAccountXhr.response;
									console.log( response );
									response = response.slice( 1, response.length-1 );
									let arr = response.split( '},' );
									let tempString = arr.join( '}!' );
									arr = tempString.split( '!' );
									makeBalanceArray( arr );
									// make balance array for line chart
								}
							} else {
								console.log( 'couldn\'t select an account' );
							}
							
						}
						
						selectedAccountXhr.send( selectedAccountNumber );
					} );
				} );
			} else {
				console.log( 'couldn\'nt load account list into nav bar' );
			}
		}
	}
	accountXhr.send();

}



// TODO incorporate date on x axis and sort by date if not sorted already
// views for 30 days 6 months 1 year all time
function makeBalanceArray ( arr ) {
	console.log( arr );
//	let jsonArr = arr.map( e => JSON.parse( e ) );
	dateSortedArray = arr.sort( ( a, b ) => { 
	    return new Date ( a.dateTime ) - new Date ( b.dateTime  ) } );
//	console.log( jsonArr );
	document.getElementById('selected-account').innerHTML = dateSortedArray[ 0 ].accountNumber;
	let balanceHistory = dateSortedArray.map( e => {  return e.balance } );
	let balanceMin = Math.min( ...balanceHistory );
	let balanceMax = Math.max( ...balanceHistory );
	console.log( balanceHistory );
	let scaledArray = balanceHistory.scaleBetween( 400, 0 );
	console.log( scaledArray );

	let svg = document.getElementById("my-svg");
	let polyline = document.createElementNS("http://www.w3.org/2000/svg", "polyline");

	destroyNodeChildren( svg );

	
	polyline.style.fill = "none";
	polyline.style.stroke = "seagreen";
	polyline.style.strokeWidth = "2px";
	svg.appendChild(polyline);
	let points = '';
	
	let maxNumber = document.createElementNS( 'http://www.w3.org/2000/svg', 'text');
	maxNumber.setAttributeNS(null,"x", 0 );     
	maxNumber.setAttributeNS(null,"y", 10 ); 
	maxNumber.setAttributeNS(null,"font-size","12");
	maxNumber.innerHTML = balanceMax;
	svg.appendChild( maxNumber );


	let minNumber = document.createElementNS( 'http://www.w3.org/2000/svg', 'text');
	minNumber.setAttributeNS(null,"x", 0 );     
	minNumber.setAttributeNS(null,"y", 400 ); 
	minNumber.setAttributeNS(null,"font-size","12");
	minNumber.innerHTML = balanceMin;
	svg.appendChild( minNumber );
	let x  = 50;

	scaledArray.forEach( ( element ) => {
	   points += `${ x }, ${ element + 10 } `;
	   x += 20;
	} );

	polyline.setAttributeNS( null, "points", points );
	
	
};



//function getRandomInt(max) {
//    return Math.floor(
//        Math.random() * Math.floor(max));
//  };

  Array.prototype.scaleBetween = function(scaledMin, scaledMax) {
    var max = Math.max.apply(Math, this);
    var min = Math.min.apply(Math, this);
    return this.map(num => (scaledMax-scaledMin)*(num-min)/(max-min)+scaledMin);
  }
  // ####### DEPOSITS #######
  let depositButton = document.getElementById( 'deposit-btn' );
  depositButton.addEventListener( 'click', function() {
	  let formContainer = document.getElementById( 'form-container' );
	  	destroyNodeChildren( formContainer );
	  
		// make regex to test for format
	  formContainer.innerHTML += `<div id="dynamic-form" width="100%" height="20vh">
		  <div mt-3 id="form-header"> Deposit into account : ${document.getElementById('selected-account').innerHTML}</div>
		  <hr>
		  <label class="pt-2">Amount : </label>
		  <input  class="form-control pt-2" id="deposit"  placeholder="Enter Amount">
		  <div id="deposit-feedback"><br></div>
		  <button id="submit-btn" class="btn">Submit Deposit</button>
		  </div>`
		  
	  let submitButton = document.getElementById( 'submit-btn' );
  	  submitButton.addEventListener( 'click', function () {
  		  let submitTransactionXhr = new XMLHttpRequest;
  		  
  		  let transactionSubmission = { 'type' : 'deposit', 
  				  						'amount' : document.getElementById( 'deposit' ).value,
  				  						'dateTime' : new Date() };
  		  
  		  transactionSubmission = JSON.stringify( transactionSubmission );
  		  submitTransactionXhr.open( 'POST', 'http://localhost:5678/SeagullBankWebApp/add_transaction', true );
  		  submitTransactionXhr.onload = function () {
			if ( this.readyState == 4 ) {
				if ( this.status === 200 ) {
					let response = submitTransactionXhr.response;
					console.log( 'response : ', response );
					updateLineGraph();
					destroyNodeChild( formContainer );

				}
			} else {
				console.log( 'couldn\'t make transaction' );
			}
			
  		  }
  		submitTransactionXhr.send( transactionSubmission );
  	  } );

  	} 
  	
  );
  
  function updateLineGraph() {
	  let selectedAccountXhr = new XMLHttpRequest;
		let accountNumber = document.getElementById('selected-account').innerHTML;
		
		selectedAccountXhr.open( 'POST', 'http://localhost:5678/SeagullBankWebApp/select_account', true );
		let selectedAccountNumber = {'accountNumber' : parseInt( accountNumber ) };
		selectedAccountNumber = JSON.stringify( selectedAccountNumber );
		console.log( selectedAccountNumber );
		selectedAccountXhr.onload = function () {
			if ( this.readyState == 4 ) {
				if ( this.status === 200 ) {
					let response = selectedAccountXhr.response;
					console.log( response );
//					response = response.slice( 1, response.length-1 );
//					let arr = response.split( '},' );
//					let tempString = arr.join( '}!' );
//					arr = tempString.split( '!' );
					let arr = parseResponseToJsonArray( response );
					makeBalanceArray( arr );
					// make balance array for line chart
				}
			} else {
				console.log( 'couldn\'t select an account' );
			}
			
		}
		
		selectedAccountXhr.send( selectedAccountNumber );
	  
  };
  
  
  let withdrawButton = document.getElementById( 'withdraw-btn' );
  withdrawButton.addEventListener( 'click', function() {
	  let formContainer = document.getElementById( 'form-container' );
	  	destroyNodeChildren( formContainer );
	  
		// make regex to test for format
	  formContainer.innerHTML += `<div id="dynamic-form" width="100%" height="20vh">
		  <div mt-3 id="form-header"> Withdraw from account : ${document.getElementById('selected-account').innerHTML}</div>
		  <hr>
		  <label class="pt-2">Amount : </label>
		  <input  class="form-control pt-2" id="withdraw"  placeholder="Enter Amount">
		  <div id="withdraw-feedback"><br></div>
		  <button id="submit-btn" class="btn">Submit Withdraw</button>
		  </div>`
		  
	  let submitButton = document.getElementById( 'submit-btn' );
  	  submitButton.addEventListener( 'click', function () {
  		  let submitTransactionXhr = new XMLHttpRequest;
  		  
  		  let transactionSubmission = { 'type' : 'withdraw', 
  				  						'amount' : document.getElementById( 'withdraw' ).value,
  				  						'dateTime' : new Date() };
  		  
  		  transactionSubmission = JSON.stringify( transactionSubmission );
  		  submitTransactionXhr.open( 'POST', 'http://localhost:5678/SeagullBankWebApp/add_transaction', true );
  		  submitTransactionXhr.onload = function () {
			if ( this.readyState == 4 ) {
				if ( this.status === 200 ) {
					let response = submitTransactionXhr.response;
					console.log( 'response : ', response );
					updateLineGraph();
					destroyNodeChild( formContainer );

				}
			} else {
				console.log( 'couldn\'t make transaction' );
			}
			
  		  }
  		submitTransactionXhr.send( transactionSubmission );
  	  } );

  	} 
  	
  );
  
  
  
  
  
 function parseResponseToJsonArray ( res ) {
		let response = res.slice( 1, res.length-1 );
		let arr = response.split( '},' );
		let tempString = arr.join( '}!' );
		arr = tempString.split( '!' );
		console.log( arr );
		let jsonArr = arr.map( e => JSON.parse( e ) );
		console.log( jsonArr );
		return jsonArr;
	
 }
  
 
  
  function destroyNodeChildren( node ) {
//	  let formContainer = document.getElementById( 'form-container' );
		if ( node.hasChildNodes() ) {
	        let child = node.lastElementChild;  
	        while (child) { 
	        	node.removeChild(child); 
	            child = node.lastElementChild; 
	        } 
		}
  }
  
  
  
	  	  
