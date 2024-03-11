
// 비밀번호 변경 유효성
function modifyFwBtn(){
	console.log("modifyFwBtn() CLICKED!! ");
	
	let form = document.modify_fw_form;
	 let pwRegex = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;
	
	if(form.new_pw.value === '') {
		alert('새 비밀번호를 설정해주세요.');
		form.new_pw.value.focus();
		
	} else if(form.new_pw_check.value === ''){
		alert('새 비밀번호 확인을 입력해주세요.');
		form.new_pw_check.value.focus();
		
	} else if(form.new_pw.value  !== form.new_pw_check.value){
		alert('비밀번호가 일치하지 않습니다.');
		form.new_pw.focus();
		
	} else if(!pwRegex.test(form.new_pw.value)){
		alert('비밀번호는 6-20자로 알파벳 대 소문자, 숫자 또는 특수 문자를 포함해야 합니다.');
		form.new_pw.focus();
		
	} else {
		form.submit();
	
	}
}


// 정보수정 유효성
function modifyForm() {
	console.log("modifyForm() clicked!!!")
	let form = document.modify_form;
   let u_mail = form.u_mail_pre.value + "@" + form.u_mail_suf.value;
   let u_address = form.postcode.value +"/"+ form.address.value + "/" 
   + form.detailAddress.value + "/" + form.extraAddress.value;
   let u_phone = form.u_phone_bas.value+"-"+form.u_phone_pre.value+"-"+
   form.u_phone_suf.value;
   let pwRegex = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;
   
   form.u_phone.value= u_phone;
   form.u_mail.value = u_mail;
   form.u_address.value = u_address;
   
   if (form.u_pw.value === '') {
      alert('비밀번호를 입력해주세요.');
      form.u_pw.focus();
      
   } else if (form.u_name.value === '') {
      alert('이름을 입력해주세요.');
      form.u_mail.focus();
      
   } else if (form.u_mail_pre.value === '') {
      alert('이메일을 입력해주세요.');
      form.u_mail_pre.focus();
      
   } else if (form.u_mail_suf.value === '') {
      alert('이메일을 입력해주세요.');
      form.u_mail_suf.focus();
      
   } else if (form.postcode.value === '') {
      alert('우편번호를 입력해주세요.');
      form.postcode.focus();
      
   } else if (form.address.value === '') {
      alert('주소를 입력해주세요');
      form.address.focus();
      
   } else {
      
      form.submit();
      
   }
}