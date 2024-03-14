function validateSearch1() {
	console.log("validateSearch1()");
 	const searchInput = document.getElementById("search_input1");
  	console.log(searchInput.value);
  	return validation(searchInput.value)
}

function validateSearch2() {
	console.log("validateSearch2()");
 	const searchInput = document.getElementById("search_input2");
  	console.log(searchInput.value);
  	return validation(searchInput.value)
}

function validateSearch3() {
	console.log("validateSearch3()");
  	const searchInput = document.getElementById("search_input3");
  	console.log(searchInput.value);
  	return validation(searchInput.value)
}

function validation(value) {
	if (value === "" || value === " ") {
    	alert("검색어를 입력 해주세요");
    	return false; // prevent form submission
  	}
	return true;
}