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
				
				response = response.slice( 1, response.length-1 );
				
				let arr = response.split( '},' );
				
				let tempString = arr.join( '}!' );
				arr = tempString.split( '!' );
				

				let accountList = document.getElementById( 'nav-account-list' );
				
				arr.forEach( element => { 
					let account = JSON.parse( element );
					console.log( account );
					accountList.innerHTML += `<li class='nav-item'><a class="nav-link" href="#"><div class="card"><div class="card-body">
					<h5 class="card-title">${account.type} Account : ${ account.accountNumber }</h5><p class="card-text">
					Balance : $${account.balance}</p> </div> </div></a></li>`;
				} );

			} else {
				console.log( 'oopsie' );
			}
		}
	}
	accountXhr.send();
	
}

function getRandomInt(max) {
    return Math.floor(
        Math.random() * Math.floor(max));
  };

  Array.prototype.scaleBetween = function(scaledMin, scaledMax) {
    var max = Math.max.apply(Math, this);
    var min = Math.min.apply(Math, this);
    return this.map(num => (scaledMax-scaledMin)*(num-min)/(max-min)+scaledMin);
  }

let balance = 5000.23;
let checkedBalance = 5000.23;

const accountHistory = [];
const balanceHistory = [];


class Transaction {
    constructor () {
        let d = new Date( Date.now() - getRandomInt( 9999999999 ) );
        let month = d.getMonth() + 1;
        let day = d.getDate();
        let year = d.getFullYear();
        let hour = d.getHours();
        let minutes = d.getMinutes();
        let seconds = d.getSeconds();
        this.date = month + "/" + "/" + day + "/" + year + ' '+ hour+':'+minutes+':'+seconds + ' CST';
        if ( d % 2 === 0 ) {
            this.type = "withdraw";
        } else {
            this.type = "deposit"
        }
        this.amount = `$${getRandomInt(3000)}.${getRandomInt(99)}`;
    }
};

for ( let i = 0; i < 50; i++ ) {
    accountHistory.push( new Transaction() );
};

let sortedAccountHisory = accountHistory.sort( ( a, b ) => { 
    return new Date ( a.date ) - new Date ( b.date  ) } );

sortedAccountHisory.forEach( ( element ) => {
    // console.log( checkedBalance );
    if ( element.type === 'withdraw' ) {

        if ( checkedBalance - Number( element.amount.slice(1) ) < 0 ) {
            // console.log( 'not allowed' );
            sortedAccountHisory.splice( sortedAccountHisory.indexOf( element ), 1 );
        } else {
            checkedBalance -= Number( element.amount.slice(1) );
        }
    } else {
        checkedBalance +=  Number( element.amount.slice(1) );
    }
} );


sortedAccountHisory.forEach( element => {
    if ( element.type === 'withdraw' ) {
        balance -= Number( element.amount.slice( 1 ) ); 
        element.balance = balance;
       
    } else {
        balance += Number( element.amount.slice( 1 ) );
        element.balance = balance;
    }
    balanceHistory.push( balance );
});

balanceHistory.unshift( 5000.23 );

let balanceMin = Math.min( ...balanceHistory );
let balanceMax = Math.max( ...balanceHistory );

let scaledArray = balanceHistory.scaleBetween( 0, 400 );


let svg = document.getElementById("my-svg");
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



