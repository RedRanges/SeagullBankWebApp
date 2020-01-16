// ####### HELPER FUNCTIONS #######
  Array.prototype.scaleBetween = function(scaledMin, scaledMax) {
    var max = Math.max.apply(Math, this);
    var min = Math.min.apply(Math, this);
    return this.map(num => (scaledMax-scaledMin)*(num-min)/(max-min)+scaledMin);
  }
  
 

//TODO
//views for 30 days 6 months 1 year all time
// add draw line function
function makeBalanceArray ( arr ) {
	let dateSortedArray = arr.sort( ( a, b ) => { 
	    return new Date ( a.dateTime ) - new Date ( b.dateTime  ) } );

	document.getElementById('selected-account').innerHTML = dateSortedArray[ 0 ].accountNumber;
	let balanceHistory = dateSortedArray.map( e => {  return e.balance } );
	let balanceMin = Math.min( ...balanceHistory );
	let balanceMax = Math.max( ...balanceHistory );
	let scaledArray = balanceHistory.scaleBetween( 400, 0 );
	let svg = document.getElementById("my-svg");
	
	destroyNodeChildren( svg );
	let polyline = document.createElementNS("http://www.w3.org/2000/svg", "polyline");
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

function parseResponseToJsonArray ( res ) {
		let response = res.slice( 1, res.length-1 );
		let arr = response.split( '},' );
		let tempString = arr.join( '}!' );
		arr = tempString.split( '!' );
		let jsonArr = arr.map( e => JSON.parse( e ) );
		return jsonArr;	
}
 

 
 function destroyNodeChildren( node ) {
		if ( node.hasChildNodes() ) {
	        let child = node.lastElementChild;  
	        while (child) { 
	        	node.removeChild(child); 
	            child = node.lastElementChild; 
	        } 
		}
 }
 
 // ####### END HELPER FUNCTIONS #######
  
  
// VERIFY LOGIN
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

// ####### LOGOUT #######
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
				if ( arr.length > 0 ){
					updateLineGraph();
				}		

				let accountList = document.getElementById( 'nav-account-list' );
				
				arr.forEach( element => { 
					accountList.innerHTML += `<li class='nav-item'><button id="selectableAccount" class="btn nav-link"><div class="card"><div class="card-body">
						<h5 id="nav-account-info" class="card-title">${element.type} Account : ${ element.accountNumber }</h5><p class="card-text">
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
						document.getElementById('selected-account').innerHTML = selectedAccountNumber.accountNumber
						selectedAccountNumber = JSON.stringify( selectedAccountNumber );
						
						selectedAccountXhr.onload = function () {
							if ( this.readyState == 4 ) {
								if ( this.status === 200 ) {
									let response = selectedAccountXhr.response;

									if ( response.length > 2 ) {
										let arr = parseResponseToJsonArray( response );
										makeBalanceArray( arr );		
									} else {
										displayNoHistory();
									}
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
					console.log( formContainer );
					destroyNodeChildren( formContainer );

				}
			} else {
				console.log( 'couldn\'t make transaction' );
			}
			
  		  }
  		submitTransactionXhr.send( transactionSubmission );
  	  } );

  	} 
  	
  );
  
//####### WITHDRAWS #######
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
  
  // ####### MAKE TRANSFER #######
  let makeTransferButton = document.getElementById( 'make-transfer-btn' );
  makeTransferButton.addEventListener( 'click', function() {
	  let formContainer = document.getElementById( 'form-container' );
	  destroyNodeChildren( formContainer );
	  // make regex to test for format
	  formContainer.innerHTML += `<div id="dynamic-form" width="100%" height="20vh">
						  		  <div mt-3 id="form-header"> Make transfer from account : ${document.getElementById('selected-account').innerHTML}</div>
						  		  <table border="0" style="width: 100%;">
								  <tr class="mt-3">
								  <td class="transfer-input-row mt-3 mr-3"><span>to account:</span></td>
								  <td class="transfer-input-row mt-3"><input id="transfer-input-account" class="form-control pt-2" placeholder="Enter Account Number"></td>
								  </tr>
  								  <tr class="mt-3">
								  <td class="transfer-input-row mt-3 mr-3"><span>amount:</span></td>
								  <td class="transfer-input-row mt-3"><input id="transfer-input-amount" class="form-control pt-2" placeholder="Enter Amount"></td>
								  </tr>
								  </table>
								  <div id="transfer-feedback"><br></div>
								  <button id="submit-btn" class="btn transaction-btn">Send Transfer</button>	
								  </div>`
	
		  
	
	  let submitButton = document.getElementById( 'submit-btn' );
  	  submitButton.addEventListener( 'click', function () {
  		  let submitTransferXhr = new XMLHttpRequest;
  		  
  		  let transferSubmission = { 'accountFrom' : document.getElementById( 'selected-account' ).innerHTML, 
  				  					  'accountTo' : document.getElementById( 'transfer-input-account' ).value,
  				  					 'amount' : document.getElementById( 'transfer-input-amount' ).value,
  				  					 'dateTime' : new Date() };
//  		  console.log( transferSubmission );
  		  transferSubmission = JSON.stringify( transferSubmission );
  		  submitTransferXhr.open( 'POST', 'http://localhost:5678/SeagullBankWebApp/make_transfer', true );
  		  submitTransferXhr.onload = function () {
			if ( this.readyState == 4 ) {
				if ( this.status === 200 ) {
					let response = submitTransferXhr.response;
					console.log( 'response : ', response );
					updateLineGraph();
					destroyNodeChildren( formContainer );
				}
			} else {
				console.log( 'couldn\'t make transfer' );
			}
			
  		  }
  		submitTransferXhr.send( transferSubmission );
  	  } );
  } );
  
  // ####### VIEW INCOMING TRANSFERS #######
  let viewTransferButton = document.getElementById( 'view-transfer-btn' );
  viewTransferButton.addEventListener( 'click', function() {
	  let formContainer = document.getElementById( 'form-container' );
	  destroyNodeChildren( formContainer );

    formContainer.innerHTML += `<div id="dynamic-form" width="100%" height="20vh">
						  		<div mt-3 id="form-header"> Incoming Pending Transfers : ${document.getElementById('selected-account').innerHTML}</div>
    							<table id="pending-transfer-table" class="table scrollbar overflow-auto"></table>
    							<div id="pending-transfer-feedback" class="text-center mt-5"></div>
								</div>`

  		let getAllAccountNumberTransfersXhr = new XMLHttpRequest;
		  
	    let accountSubmission = { 'accountNumber' : document.getElementById( 'selected-account' ).innerHTML };
			  		
//		console.log( accountSubmission );
		accountSubmission = JSON.stringify( accountSubmission );
		getAllAccountNumberTransfersXhr.open( 'POST', 'http://localhost:5678/SeagullBankWebApp/all_account_transfers', true );
		getAllAccountNumberTransfersXhr.onload = function () {
			if ( this.readyState == 4 ) {
				if ( this.status === 200 ) {
					let response = getAllAccountNumberTransfersXhr.response;
					if ( response.length  > 2 ) {
//						console.log( response );
						let pendingTransferArray = parseResponseToJsonArray( response );
						let pendingTransferTable = document.getElementById( 'pending-transfer-table' );
						pendingTransferTable.innerHTML += 
						`<thead class="mt-3">
						<tr>
						<th>ID</th><th>From</th><th>Date</th><th>Amount</th><th>Status</th>
						</tr>
						</thead>`;
						pendingTransferArray.forEach( ( element ) => {
							console.log( element );
							pendingTransferTable.innerHTML += 
							`
							<tr>
							    <td>${element.id}</td><td>${element.accountFrom}</td>
							    <td>${element.dateTime}</td><td>$${element.amount}</td>
							    <td>${element.status}</td>
							 </tr>
							`;
						} );
						
					} else {
						let pendingTransferFeedback = document.getElementById( 'pending-transfer-feedback' );
						pendingTransferFeedback.innerHTML = "No pending transfers";
					}
					

				}
			} else {
				console.log( 'couldn\'t make transfer' );
			}
		
		}
		getAllAccountNumberTransfersXhr.send( accountSubmission );
	
  } );
  
  
  
  // ####### DOM UPDATE #######
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
  
 function displayNoHistory() {
	let svg = document.getElementById( 'my-svg' );
	console.log( svg.width.baseVal.value );
	destroyNodeChildren( svg );
	
	let noHistoryMessage = document.createElementNS( 'http://www.w3.org/2000/svg', 'text');
	noHistoryMessage.setAttributeNS(null,"x", svg.width.baseVal.value/2 );     
	noHistoryMessage.setAttributeNS(null,"y", svg.height.baseVal.value/2 ); 
	noHistoryMessage.setAttributeNS(null,"font-size","15");
	noHistoryMessage.innerHTML = 'no history to display';
	svg.appendChild( noHistoryMessage );
	  
 }
  
  
  
  
  
  
  
  

  
  
  
	  	  
