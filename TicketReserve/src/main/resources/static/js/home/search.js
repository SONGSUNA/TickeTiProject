function searchIcon() {
	console.log("searchIcon()");
	let form = document.search_form1;
	let form2 = document.search_form2;
	console.log(form);	
	console.log(form2);
	if (form.search_value.value === "") {
	    alert("검색어를 입력 해주세요");
    }
	else 
		form.submit();
}

function validateSearch() {
  const searchInput = document.getElementById("search_input");
  if (searchInput.value === "") {
    alert("검색어를 입력 해주세요");
    return false; // prevent form submission
  }
  return true; // allow form submission
}