
window.lib = {};
lib.url = "http://localhost:8080/ExpenseManagement/";
//------------------------------------------------------all-page----------------------------------------------------------------

lib.open = function (file) {
	window.open(file,"_parent");
};

lib.messageToUser = function (id, mes, color = "") {
    document.getElementById(id).innerHTML = mes;
    document.getElementById(id).style.color = color;
}

lib.focusColor = function (id) {
    document.getElementById(id).style.backgroundColor = "gold";
    document.getElementById(id).style.color = "blue";
}

lib.focusOff = function (id) {
    document.getElementById(id).style.backgroundColor = "";
    document.getElementById(id).style.color = "";
}

//------------------------------------------------------session----------------------------------------------------------------


lib.checkSessionUser = function (id) {
   fetch(lib.url + "session", {
        method: 'POST',
        headers: {  'Content-Type': 'application/json',},
    })
        .then((response) => response.json())
        .then((object) => {
		   		if(object.ok == 0){
		    		lib.open("login.html")
		    	}
		    	if(object.ok == 1 ){
		    		document.getElementById(id).innerHTML = "Hello " + object.user + " ,";
		    	}
        });
}


lib.logOut = function () {
   fetch(lib.url + "logout", {
        method: 'POST',
        headers: {  'Content-Type': 'application/json',},
    })
        //.then((response) => response.json())
        .then((data) => {
            lib.open("login.html");
        });
}

//------------------------------------------------------register----------------------------------------------------------------
lib.register = function () {
	lib.messageToUser('message-register',"...","red");
    let username = document.getElementById('username-register').value.trim();
    let password = document.getElementById('password-register').value.trim();
		
    // check value
    let check = checkValue(username, password);
    if (check == 0)
        return;

    const data = {
           userName : username,
           password : password,
         };
   fetch(lib.url + "registration", {
        method: 'POST',
        headers: {'Content-Type': 'application/json',},
        body: JSON.stringify(data),
    })
        .then((response) => response.json())
        .then((data) => {
            lib.messageToUser('message-register',data.message,"red");
        });

    function checkValue(username, password) {
        let check;
        if (username != "" && password != "") {
            check = 1;
        } else {
            lib.messageToUser('message-register',"Error- Missing or incorrect value","red");
            check = 0;
        }
        return check;
    }
}

//------------------------------------------------------login----------------------------------------------------------------

lib.loginToApp = function () {
	lib.messageToUser('message-login',".","red");
	
    let username = document.getElementById('username-login').value.trim();
    let password = document.getElementById('password-login').value;

	const data = {
               userName : username,
               password : password,
             };
    fetch(lib.url + "login", {
            method: 'POST',
            headers: {'Content-Type': 'application/json',},
            body: JSON.stringify(data),
        })
            .then((response) => response.json())
            .then((object) => {
	            if(object.ok == 1)
                    lib.open("home.html");
                else
                	lib.messageToUser('message-login',object.message,"red");

            });
}

//------------------------------------------------------add-----------------------------------------------------------------

lib.showText = function (text, color) {
	lib.messageToUser('message-add',text,color);
}

lib.restart = function () {
    document.getElementById("sum").value = "";
    document.getElementById("description").value = "";
    document.getElementById("date").value = "";
    //document.getElementById('message-add').innerHTML = "";
}

lib.add = function () {
    // get data from user
    let sum = document.getElementById("sum").value;
    let category = document.getElementById("category").value;
    let description = document.getElementById("description").value.trim();
    let date = document.getElementById("date").value;

    // check input
    let message = "";
    if (sum == "" || sum <= 0)
        message += "sum is empty or incorrect\n";
    if (category.trim() == "")
        message += "category is empty or incorrect\n";
    if (date.trim() == "")
        message += "date is empty or incorrect\n";

    // add or send message error
    if (message == "") {
        lib.addItemToDB(new CostItem(0, sum, category, description, date, ""), lib.showText);
    } else {
        lib.showText(message, 'red');
    }
}


lib.addItemToDB = function (item, showText) {
	showText("...", "green")

    const data = {
                id : 0,
             	sum : item.sum,
             	category : item.category,
             	description : item.description,
             	date : item.date,
             	nameUser : item.username
    };

        // add item new
        fetch(lib.url + "add", {
        method: 'POST',
        headers: {  'Content-Type': 'application/json',},
        body: JSON.stringify(data),
        })
        .then((response) => response.json())
        .then((data) => {
                  console.log(data.ok);
                  console.log(data.message);
 		   	if(data.ok == 1){
 		   		showText("Added successfully", "green")
 		   		lib.restart();
 		   }else
 				showText(data.message, "red")
        });
    }


//------------------------------------------------------table----------------------------------------------------------------

//lib.getItems = function (success, error) {
//	let items = [];
//
//    fetch(lib.url + "items", {
//        method: 'POST',
//        headers: {'Content-Type': 'application/json',},
//
//        })
//        .then((response) => response.json())
//        .then((data) => {
//            let list = data.items;
//            list.forEach(
//                function (ob) {
//                        items.push(new CostItem(ob.id, ob.sum, ob.category, ob.description, ob.date, ob.username));
//                     });
//                 if(items.length == 0)
//                 	{error("no data");}
//                 else
//             		{success(items);}
//        });
//}

//lib.successCostsItems = function (items) {
//    // reset message
//    document.getElementById('message-table').innerHTML = "";
//    let tbody = document.getElementById('tbody-table');
//
//    // remove table
//    while (tbody.firstChild) {
//        tbody.removeChild(tbody.firstChild);
//    }
//    // update table
//    items.forEach(
//        function (ob) {
//            // add data to table
//            let tr = document.createElement("tr");
//            let td;
//            td = document.createElement("td");
//            td.appendChild(document.createTextNode(ob.sum));
//            tr.appendChild(td);
//
//            td = document.createElement("td");
//            td.appendChild(document.createTextNode(ob.category));
//            tr.appendChild(td);
//
//            td = document.createElement("td");
//            td.appendChild(document.createTextNode(ob.date));
//            tr.appendChild(td);
//
//            td = document.createElement("td");
//            td.appendChild(document.createTextNode(ob.description));
//            tr.appendChild(td);
//
//  			let button = document.createElement("a");
//			button.setAttribute("onclick", "lib.deleteItem(" +ob.id+ ");");
//  			button.appendChild(document.createTextNode("delete"));
//         	tr.appendChild(button);
//
//            tbody.appendChild(tr);
//        });
//}

lib.errorCostsItems = function (text) {
    document.getElementById('message-table').innerHTML = text;
    document.getElementById('message-table').style.color = "red";
}

 
//-----------------------------------------------------report------------------------------------------------------------------



lib.getReport = function (dateFrom, dateTo, success, error) {
    const data = {
   			 start : dateFrom,
    		 end : dateTo
    };
    fetch(lib.url + "report", {
        method: 'POST',
        headers: {'Content-Type': 'application/json',},
        body: JSON.stringify(data),
        })
        .then((response) => response.json())
        .then((object) => {

		let list = object.items;
		let sumTotal = object.sum;
		let items = [];

        list.forEach(
            function (ob) {
                items.push(new CostItem(ob.id, ob.sum, ob.category, ob.description, ob.date, ob.username));
            });

          	let map = new Map();
         	let number = 0;
            for (let i = 0; i < items.length; i++) {
                if (!map.has(items[i].category)) {
                    map.set(items[i].category, Number(items[i].sum));
                } else {
                    number = Number(map.get(items[i].category));
                    map.set(items[i].category, Number(items[i].sum) + number);
                }
            }
            success(items, map,sumTotal);
        });
 }



lib.getItemsReport = function () {
    let dateFrom = document.getElementById("date-from").value;
    let dateTo = document.getElementById("date-to").value;
    
    if (dateFrom != "" && dateTo  != "" ) {
        lib.getReport(dateFrom, dateTo, lib.successReport, lib.errorReport);
        document.getElementById('message-report').innerHTML = "";
    } else {
        document.getElementById('message-report').innerHTML = "Error in input";
        document.getElementById('message-report').style.color = "red";
    }
}

lib.successReport = function (items, map,sumTotal) {

	let tbodyAllItem = document.getElementById('tbody-all-item');
	let tbodyCategory = document.getElementById('tbody-category');
	
    // reset message
    document.getElementById('message-report').innerHTML = "";
    lib.addPieChart(map);

    // delete table
    while (tbodyAllItem.firstChild) {
        tbodyAllItem.removeChild(tbodyAllItem.firstChild);
    }    
    while (tbodyCategory.firstChild) {
        tbodyCategory.removeChild(tbodyCategory.firstChild);
    }

    // tbody AllItem add table
    let tr, td;
    items.forEach(
        function (ob) {
            tr = document.createElement("tr");

            td = document.createElement("td");
            td.appendChild(document.createTextNode(ob.sum));
            tr.appendChild(td);

            td = document.createElement("td");
            td.appendChild(document.createTextNode(ob.category));
            tr.appendChild(td);

            td = document.createElement("td");
            td.appendChild(document.createTextNode(ob.date));
            tr.appendChild(td);

            td = document.createElement("td");
            td.appendChild(document.createTextNode(ob.description));
            tr.appendChild(td);
            
            tbodyAllItem.appendChild(tr);
        });

    // ------------------------tbodyCategory add table -----------------
  
    let key = map.keys();
    let val = map.values();

    for (let i = 0; i < map.size; i++) {
        tr = document.createElement("tr");

        td = document.createElement("td");
        td.appendChild(document.createTextNode(val.next().value));
        tr.appendChild(td);

        td = document.createElement("td");
        td.appendChild(document.createTextNode(key.next().value));
        tr.appendChild(td);

        tbodyCategory.appendChild(tr);
    }
    
    if(sumTotal != 0)
		lib.messageToUser('sum-total',"sum total = " +sumTotal,"red");
	else
		lib.messageToUser('sum-total',"not data","red");
	
}

lib.errorReport = function (text) {
    document.getElementById('message-report').innerHTML = text;
    document.getElementById('message-report').style.color = "red";
}

lib.addPieChart = function (map) {

    google.charts.load('current', {'packages': ['corechart']});
    // array of category and sum
    let key = map.keys();
    let val = map.values();
    let vec = [['Type of Cost', 'Shekels']];
    // add data to array vec
    for (let i = 0; i < map.size; i++) {
        vec.push([key.next().value, val.next().value]);
    }
    
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        let data = google.visualization.arrayToDataTable(vec);
        let options = {
        title: 'My costs' , is3D : true ,legend :{position: 'top', textStyle: {color: 'blue', fontSize: 20}} };
        let chart = new google.visualization.PieChart(document.getElementById('piechart'));
        chart.draw(data, options);
    }
}


lib.searchTable = function (){
    $(document).ready(function(){
        $("#Search").on("keyup", function() {
            var value = $(this).val().toLowerCase();
            $("#tbody-table tr").filter(function() {
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
            });
        });
    });
}


//-----------------------------------------------------delete------------------------------------------------------------------

lib.deleteItem = function (id){
    fetch(lib.url + "delete/" + id, {
        method: 'GET',
        headers: {  'Content-Type': 'application/json',},
        })
      //  .then((response) => response.json())
        .then(() => {
             lib.open("table.html");
        });
}



//-----------------------------------------------------home------------------------------------------------------------------

lib.home = function (){
  
  	let date = new Date();
  	let year = date.getFullYear();
  	let month = date.getMonth();

//  	const data = {
//    	year : year ,
//    	month : month
//     };

    fetch(lib.url + "home", {
          method: 'POST',
          headers: {  'Content-Type': 'application/json',},
       //   body: JSON.stringify(data),
          })
          .then((response) => response.json())
          .then((object) => {

          		let list = object.items;
          		let sum = [object.current];

                list.forEach(function (ob) {
                          sum.push(ob.sum);
                      });
                let months = ["Current month"],m;
                for(let i=0;i<6;i++){
                  	m = month - i ;
                  	if (m > 0)
                  		months.push(m);
                  	else
                  		months.push(m + 12);
                  }
          		lib.addHalfYear(sum,months);
            });
}


lib.addHalfYear = function (sum,months){

	let barColors = ["gold" ,"red","green","blue","orange","brown","red"];

	new Chart("chart-home", {
  		type: "bar",
  		data: { labels: months,    	
    	datasets: [{backgroundColor: barColors, data: sum }]
  		},
  	options: {
    	legend: {display: false},
    	title: { display: true,  text: "Expenditure in the last half year"}   
  		}
	});
}


//------------------------------------------------------class-CostItem-------------------------------------------------------------

class CostItem {
    id;
    sum;
    category;
    description;
    date;
    username;

    constructor(id, sum, category, description, date, username) {
        this.id = id;
        this.sum = sum;
        this.category = category;
        this.description =description;
        this.date = date;
        this.username = username;
    }
}
