function deleteConfirm() {
	console.log("deleteConfirm() clicked !! ");
	
	let form = document.delete_form;
	
	if(form.u_pw.value === '') {
		alert("비밀번호를 입력해주세요");
		form.u_pw.focus();
		
	} else if(form.u_pw_check.value === '') {
		alert("비밀번호확인을 입력해주세요");
		form.u_pw_check.focus();
		
	} else if(form.u_pw.value !== form.u_pw_check.value) {
		alert("비밀번호가 일치하지 않습니다.");
		form.u_pw.focus();
		
	} else {
		form.submit();
	}
}