function findPassword() {
	console.log("findPassword() clicked!!");
	
	let form = document.user_password_find;
	let u_mail = form.u_mail_pre.value + "@" + form.u_mail_suf.value;

	form.u_mail.value = u_mail;
	
	if(form.u_id.value == '') {
		 alert('아이디를 입력해주세요.');
    	 form.u_id.focus();
    	 
	} else if(form.u_mail_pre.value == ''){
		 alert('메일을 입력해주세요.');
    	 form.u_mail_pre.focus();
    	 
	} else if(form.u_mail_suf.value == ''){
		 alert('메일주소를 확인해주세요.');
    	 form.u_mail_suf.focus();
    	 
	} else {
		form.submit();
	}
	 
}

function findIdBtn() {
	console.log("findIdBtn() clicked!!");
	
	let form = document.user_password_find;
	let u_mail = form.u_mail_pre.value + "@" + form.u_mail_suf.value;

	form.u_mail.value = u_mail;
	
	if(form.u_name.value == '') {
		 alert('아이디를 입력해주세요.');
    	 form.u_id.focus();
    	 
	} else if(form.u_mail_pre.value == ''){
		 alert('메일을 입력해주세요.');
    	 form.u_mail_pre.focus();
    	 
	} else if(form.u_mail_suf.value == ''){
		 alert('메일주소를 확인해주세요.');
    	 form.u_mail_suf.focus();
    	 
	} else {
		form.submit();
	}
	 
}