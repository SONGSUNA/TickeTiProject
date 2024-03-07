function findPassword() {
	console.log("findPassword() clicked!!");
	
	let form = document.user_password_find;
	let u_id = form.u_id.value;
	let u_mail = form.u_mail_pre.value + "@" + form.u_mail_suf.value;
	
	if(form.u_id.value == null) {
		 alert('아이디를 입력해주세요.');
    	 form.u_id.focus();
    	 
	} else if(form.u_mail.value == null){
		 alert('아이디를 입력해주세요.');
    	 form.u_mail.focus();
    	 
	} else {
		form.submit();
	}
	 
}