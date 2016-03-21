userName ="";

function $(id){
    return docoment.getElementById(id);
}

function login() {
    userName = document.getElementById('ulogin').value;
    document.getElementById('ulogin').value = "";
    document.getElementById('welcome').textContent = "Welcome, " + userName;
}

 function logout() {
    userName = "";
    document.getElementById('welcome').textContent = "Welcome to chat!";
}

function togglePassword() {
    var upass = document.getElementById('upass');            
    if(upass.type == "password"){
    upass.type = "text";
    } else {
        upass.type = "password";
    }
}

function toggleLogin() {
    var ulogin = document.getElementById('ulogin');
    if(ulogin.type == "password"){
        ulogin.type = "text";
    } else {
        ulogin.type = "password";
    }
}

function guid() {
  function s4() {
    return Math.floor((1 + Math.random()) * 0x10000)
      .toString(16)
      .substring(1);
  }
  return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
    s4() + '-' + s4() + s4() + s4();
}

function server(){
        alert("Not found 404!");
}

function send(){
    if (userName != "") {
        var message = document.getElementById('usermsg').value;
        document.getElementById('usermsg').value = "";

        var mycheckbox = document.createElement('input');
        mycheckbox.type = "checkbox";
        mycheckbox.id = "mycheckbox";
        var now = new Date();
        var temp = now.getMonth() + 1;

        var table = document.getElementById('messageTable');
        var tr = document.createElement('tr'); 
        var td0 = document.createElement('td'); td0 = mycheckbox;
        var td1 = document.createElement('td'); td1.innerHTML = userName;
        var td2 = document.createElement('td'); td2.innerHTML = message;
        var td3 = document.createElement('td'); td3.innerHTML = now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
        var td4 = document.createElement('td'); td4.innerHTML = now.getDate() + "." + temp + "." + now.getFullYear();
        //var td4 = document.createElement('td'); td4.innerHTML = guid();
 
        tr.appendChild(td0);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3); 
        tr.appendChild(td4);
        table.appendChild(tr); 
    }else{
        alert("You must login!!");
        document.getElementById('usermsg').value = "";
    }
}

function rename(){
    if (userName!="") {

        do{
            var newName = prompt("Write new name:", userName);
        }while(newName == null || newName == "")
        
        var table = document.getElementById('messageTable');
        var length = table.querySelectorAll('tr').length;
        
        for (var i=1; i < length; i++) {
            if(table.rows[i].cells[0].textContent == userName){
                table.rows[i].cells[0].textContent = newName;
            }
        };
        
        userName = newName;
        document.getElementById('welcome').textContent = "Welcome, " + userName;
    }else{
        alert("You must login!!");
    }
}

function deleteMessage(){
    if (userName!="") {
        var table = document.getElementById('messageTable');
        var length = table.querySelectorAll('tr').length;
        var galki = table.querySelectorAll('input');
        count = 0;

        for (var i=0; i < length - 1; i++) {
            if(galki[count].checked){
                if (userName == table.rows[i+1].cells[0].textContent) {
                    table.deleteRow(i+1);
                    i--;
                }else{
                    alert("You want delete another's message!! Access closed!!");
                }   
            }
            count++;
        };
    
    }else{
        alert("You must login!!");
    }
}

function overwriteMessage(){
    if (userName!="") {

        var table = document.getElementById('messageTable');
        var length = table.querySelectorAll('tr').length;
        var galki = table.querySelectorAll('input');
        
        for (var i=0; i < length - 1; i++) {
            if(galki[i].checked){
                if (userName == table.rows[i+1].cells[0].textContent) {
                    table.rows[i+1].cells[1].textContent = prompt("Overwrite your message", table.rows[i+1].cells[1].textContent);
                    galki[i].checked = false;
                }else{
                    alert("You want overwrite another's message!! Access closed!!");
                }   
            }
        }
    
    }else{
        alert("You must login!!");
        document.getElementById('usermsg').value = "";
    }
}