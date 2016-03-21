var userName ='';

function login() {
	var log = document.getElementById("logtext");
	userName = log.value;
	log.value = '';
	var her = document.getElementById("welcome").textContent = userName + " лох!"; 
}	

function logout() {
	userName = '';
	var her = document.getElementById("welcome").textContent = "Войдите в систему"; 
}	