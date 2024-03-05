function userSearch () {
	console.log("userSearch()");
	
	let form = document.user_search_form;
	
	if (form.u_id.value == '' &&
		form.u_name.value == '' &&
		form.u_mail.value == '') {
			alert("유저 정보가 하나 이상 필요합니다.");
			
			if (form.u_id.value == '') {
				form.u_id.focus();
				return;
			}
			if (form.u_name.value == '') {
				form.u_name.focus();
				return;
			}
			if (form.mail.value == '') {
				form.mail.focus();
				return;
			}
		}
	else
		submit();
}

function UserModify (u_no) {
	console.log("UserModify()");
}

function UserDelete (u_no) {
	console.log("UserDelete()");
}