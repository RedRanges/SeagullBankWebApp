window.onload = function() {
	let sessionXHR = new XMLHttpRequest;
	sessionXHR.open( "GET", 'http://localhost:5678/SeagullBankWebApp/admin_account', true );
	sessionXHR.onload = function () {
	  if (this.readyState == 4) {
	    if (this.status == 200) {
	    	getCustomerData();
	      
	    } else {
		  window.location.href = ( 'http://localhost:5678/SeagullBankWebApp/login.html' );
		  }
		}
	}
	sessionXHR.send(); 
};

function updateAccountStatus() {
	
};


function getCustomerData() {
	// transfers
	let getAllTransfersXhr = new XMLHttpRequest;
	getAllTransfersXhr.open( "POST", 'http://localhost:5678/SeagullBankWebApp/get_transfers', true );
	getAllTransfersXhr.onload = function () {
		if ( this.readyState == 4 ) {
			if ( this.status === 200 ) {
				let response = getAllTransfersXhr.response;
					let arr = parseResponseToJsonArray( response );
					let dateSortedArr = sortArrayByDate( arr );
					console.log( dateSortedArr );
					buildTransferTable( dateSortedArr );
			} else {
				// no transfers
			}
		}
	}
	getAllTransfersXhr.send();
	// transactions
	let getAllTransactionsXhr = new XMLHttpRequest;
	getAllTransactionsXhr.open( "POST", 'http://localhost:5678/SeagullBankWebApp/get_transactions', true );
	getAllTransactionsXhr.onload = function () {
		if ( this.readyState == 4 ) {
			if ( this.status === 200 ) {
				let response = getAllTransactionsXhr.response;
					let arr = parseResponseToJsonArray( response );
					let dateSortedArr = sortArrayByDate( arr );
					console.log( dateSortedArr );
					buildTransactionTable( dateSortedArr );
			} else {
				// no transactions
			}
		}
	}
	getAllTransactionsXhr.send();
	
	
	// pending
	let getPendingAccountsXhr = new XMLHttpRequest;
	getPendingAccountsXhr.open( "POST", 'http://localhost:5678/SeagullBankWebApp/get_pending', true );
	getPendingAccountsXhr.onload = function () {
		if ( this.readyState == 4 ) {
			if ( this.status === 200 ) {
				let response = getPendingAccountsXhr.response;
					let arr = parseResponseToJsonArray( response );
//					let dateSortedArr = sortArrayByDate( arr );
//					console.log( dateSortedArr );
					console.log( arr );
					buildPendingAccountsTable( arr );
			} else {
				// no transactions
			}
		}
	}
	getPendingAccountsXhr.send();
	
	
};

function sortArrayByDate( arr ){ 
	let dateSortedArray = arr.sort( ( a, b ) => { 
    return new Date ( a.dateTime ) - new Date ( b.dateTime  ) } );
	return dateSortedArray;
}


function parseResponseToJsonArray ( res ) {
	let response = res.slice( 1, res.length-1 );
	let arr = response.split( '},' );
	let tempString = arr.join( '}!' );
	arr = tempString.split( '!' );
	let jsonArr = arr.map( e => JSON.parse( e ) );
	return jsonArr;	
}


function buildTransferTable( arr ) {
	let transferTable = document.getElementById('transfer-table');
	transferTable.innerHTML += `<tr><th>Id</th><th>account from</th><th>account to</th><th>date time</th><th>amount</th><th>status</th></tr>`;
	arr.forEach( element => {
		transferTable.innerHTML += `<tr><td>${element.id}</td><td>${element.accountFrom}</td>
									<td>${element.accountTo}</td><td>${element.dateTime}</td>
									<td>$${element.amount}</td><td>${element.status}</td>
									</tr>`
	}
	);
}


function buildTransactionTable( arr ) {
	let transactionTable = document.getElementById('transaction-table');
	transactionTable.innerHTML += `<tr><th>Username</th><th>account number</th><th>date time</th><th>amount</th><th>type</th></tr>`;
	arr.forEach( element => {
		transactionTable.innerHTML += `<tr><td>${element.username}</td><td>${element.accountNumber}</td>
										<td>${element.dateTime}</td>
										<td>$${element.amount}</td><td>${element.type}</td>
									</tr>`
	}
	);
}

function buildPendingAccountsTable( arr ) {
	let pendingAccountsTable = document.getElementById( 'pending-accounts-table' );
	pendingAccountsTable.innerHTML += `<tr><th>Username</th><th>account type</th><th>account number</th><th>status</th><th>accept</th><th>reject</th></tr>`;
	arr.forEach( ( element, index ) => {
		console.log( element );
		pendingAccountsTable.innerHTML +=`<tr id="row${index}"><td>${element.username}</td><td>${element.type}</td>
							<td id="account${index}">${element.accountNumber}</td><td id="status${index}">${element.status}</td>
							<td>
								<button id="approve-button${index}" class="btn optionButton" style="background-color:green">Approve</button>
							</td>
							<td>
							<button id="reject-button${index}" class="btn optionButton" style="background-color:red">Reject</button>
							</td>
							</tr>`
		
		let optionButtons = document.querySelectorAll( '.optionButton' );
		
		optionButtons.forEach( button => {
		
			
			button.addEventListener( 'click', function() {
			
			
				if ( button.innerHTML === 'Approve' ) {
					let status = "approved";
					updateStatus( { 'accountNumber' : document.getElementById( `account${button.id.slice(-1)}`).innerHTML,
									'status' : status } );
				} else {
					let status = "rejected";
					updateStatus( { 'accountNumber' : document.getElementById( `account${button.id.slice(-1)}`).innerHTML,
						'status' : status } );
				}
				
				let row = document.getElementById( `row${button.id.slice(-1)}`);
				row.outerHTML = "";
			} );
		} );

		
			
	} );
}

function updateStatus( account ) {
	let updatePendingXhr = new XMLHttpRequest;
	updatePendingXhr.open( "PUT", 'http://localhost:5678/SeagullBankWebApp/get_pending', true );
	let stringAccount = JSON.stringify( account );
	updatePendingXhr.onload = function () {
		if ( this.readyState == 4 ) {
			if ( this.status === 200 ) {
				let response = updatePendingXhr.response;
					console.log( response );
					
			} else {
				// no transactions
			}
		}
	}
	updatePendingXhr.send( stringAccount );
	
};
