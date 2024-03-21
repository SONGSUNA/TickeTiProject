let idCheck = false;

function createAccountForm() {
   console.log('createAccountForm()  clicked!!!');
   
   let form = document.create_account_form;
   let idRegex = /^[a-zA-Z0-9]{6,20}$/;
   let u_mail = form.u_mail_pre.value + "@" + form.u_mail_suf.value;
   let u_sc_num = form.u_sc_num_pre.value + "-" + form.u_sc_num_suf.value;
   let u_address = form.postcode.value +"/"+ form.address.value + "/" 
   + form.detailAddress.value + "/" + form.extraAddress.value;
   let u_phone = form.u_phone_bas.value+"-"+form.u_phone_pre.value+"-"+
   form.u_phone_suf.value;
   let pwRegex = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;
   
   form.u_phone.value= u_phone;
   form.u_mail.value = u_mail;
   form.u_sc_num.value = u_sc_num;
   form.u_address.value = u_address;
   flag = false;
   
   if (form.u_id.value === '') {
      alert('아이디를 입력해주세요.');
      form.u_id.focus();
      
    } else if (!idRegex.test(form.u_id.value)) {
      alert('아이디는 6-20자로 영문자 또는 숫자를 포함해야 합니다.');
      form.u_id.focus();
         
   } else if (form.u_pw.value === '') {
      alert('비밀번호를 입력해주세요.');
      form.u_pw.focus();
      
   } else if (form.u_pw.value !== form.u_pw_confirm.value) {
      alert('등록한 비밀번호가 일치하지않습니다.');
      form.u_pw.focus();
      
   } else if (!pwRegex.test(form.u_pw.value)) {
       alert('비밀번호는 6-20자로 알파벳 대 소문자, 숫자 또는 특수 문자를 포함해야 합니다.');
       form.u_pw.focus();
      
   } else if (form.u_name.value === '') {
      alert('이름을 입력해주세요.');
      form.u_mail.focus();
      
   } else if (form.u_sc_num_pre.value === '') {
      alert('주민번호를 입력해주세요.');
      form.u_reg_num_pre.focus();
      
   }else if (form.u_sc_num_suf.value === '') {
      alert('주민번호를 입력해주세요.');
      form.u_reg_num_suf.focus();
      
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
      
   } else if (!idCheck) {
	   alert("아이디 중복체크를 먼저 성공해주세요.")
      
   }else {
      
      form.submit();
      
   }
   
}


$(document).ready(function () {
	
    $('#checkId').click(function () {
        let userId = $('input[name="u_id"]').val();
        let idRegex = /^[a-zA-Z0-9]{6,20}$/;
        
        if(userId ===''){
         alert('ID를 입력하세요');
         document.create_account_form.u_id.focus();
       	  return;
         
     	 } else if (!idRegex.test(userId)) {
	      alert('아이디는 6-20자로 영문자 또는 숫자를 포함해야 합니다.');
	        document.create_account_form.u_id.focus();
          	return;
  		 }
        
        $.ajax({
            url: '/user/checkId',
            type: 'POST',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", $('#csrfToken').val());
            },
            data: {"u_id_check": userId},
            success: function (data) {
                if (!data) {
					idCheck = true; 
                    alert('사용 가능한 아이디입니다.');
                    $('#check_id_msg').text('사용 가능한 아이디입니다.');
                    $('#check_id_msg').css('color', 'green')
                    				  .css('font-size','0.6em');
                } else {
					 idCheck = false;
	               alert('이미 사용중인 아이디입니다.');
                    $('#check_id_msg').text('이미 사용 중인 아이디입니다.');
                    $('#check_id_msg').css('color', 'red')
                    				  .css('font-size','0.6em');
                }
            },
             error: function () {
                alert('서버 오류가 발생했습니다.');
             }
        });
    });
    
    $('.u_pw').keyup(function(){
      let pw = $("#u_pw").val();
      let pwcheck = $("#u_pw_check").val();
     let pwRegex = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;

   
   if(pw === pwcheck){
      $('#pw_matching').html('일치');
      $('#pw_matching').css('color', 'green')
      					.css('font-size','0.6em');
   
   } else {
      $('#pw_matching').html('불일치');
      $('#pw_matching').css('color', 'red')
                        .css('font-size','0.6em');
   } 
   
   if(pwRegex.test(pw)){
            $('#regexp').html('비밀번호는 6-20자로 알파벳 대 소문자, 숫자 또는 특수 문자를 포함해야 합니다.')
            .css('color','green')
             .css('font-size','0.6em');
             
        } else{
            $('#regexp').html('비밀번호는 6-20자로 알파벳 대 소문자, 숫자 또는 특수 문자를 포함해야 합니다.')
            .css('color','red')
            .css('font-size','0.6em');
        }
});

  
    $('.modify_pw').keyup(function(){
      let m_pw = $("#modify_pw").val();
      let m_pwcheck = $("#modify_pw_check").val();
      let pwRegex = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;

   
   if(m_pw === m_pwcheck){
      $('#pw_matching').html('일치');
      $('#pw_matching').css('color', 'green');
   
   } else {
      $('#pw_matching').html('불일치');
      $('#pw_matching').css('color', 'red')
                        .css('font-size','0.6em');
   } 
   
   if(pwRegex.test(m_pw)){
            $('#m_regexp').html('비밀번호는 6-20자로 알파벳 대 소문자, 숫자 또는 특수 문자를 포함해야 합니다.')
            .css('color','green')
             .css('font-size','0.6em');
             
        } else{
            $('#m_regexp').html('비밀번호는 6-20자로 알파벳 대 소문자, 숫자 또는 특수 문자를 포함해야 합니다.')
            .css('color','red')
            .css('font-size','0.6em');
        }
});


});
    
