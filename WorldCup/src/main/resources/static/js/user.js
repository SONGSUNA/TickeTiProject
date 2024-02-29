function createAccountForm() {
	console.log('createAccountForm()');
	
	let form = document.create_account_form;
	if (form.u_id.value === '') {
		alert('INPUT ID!!');
		form.u_id.focus();
		
	} else if (form.u_pw.value === '') {
		alert('INPUT PW!!');
		form.u_pw.focus();
		
	} else if (form.u_mail.value === '') {
		alert('INPUT MAIL!!');
		form.u_mail.focus();
		
	} else if (form.u_phone.value === '') {
		alert('INPUT PHONE!!');
		form.u_phone.focus();
		
	} else {
		form.submit();
		
	}
	
}