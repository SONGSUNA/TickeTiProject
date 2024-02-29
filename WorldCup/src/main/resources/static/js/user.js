function createAccountForm() {
	console.log('createAccountForm()');
	
	let form = document.create_account_form;
	let u_mail = form.u_mail_pre.value + "@" + form.u_mail_suf.value;
	let u_sc_num = form.u_sc_num_pre.value + "-" + form.u_sc_num_suf.value;
	let u_address = form.postcode.value +"/"+ form.address.value + "/" 
	+ form.detailAddress.value + "/" + form.extraAddress.value;
	let u_phone = form.u_phone_bas.value+"-"+form.u_phone_pre.value+"-"+
	form.u_phone_suf.value;
	
	form.u_phone.value= u_phone;
	form.u_mail.value = u_mail;
	form.u_sc_num.value = u_sc_num;
	form.u_address.value = u_address;
	
	if (form.u_id.value === '') {
		alert('아이디를 입력해주세요.');
		form.u_id.focus();
		
	} else if (form.u_pw.value === '') {
		alert('비밀번호를 입력해주세요.');
		form.u_pw.focus();
		
	} else if (form.u_pw.value !== form.u_pw_confirm.value) {
		alert('등록한 비밀번호가 일치하지않습니다.');
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
		
	}else if (form.extraAddress.value === '') {
		alert('주소 참고항목을 입력해주세요.');
		form.extraAddress.focus();
		
	} else {
		
		form.submit();
		
	}
	
}
$(document).ready(function () {
    $('#checkId').click(function () {
        var userId = $('input[name="u_id"]').val();
        
        if(userId ===''){
			alert('ID를 입력하세요');
			document.create_account_form.u_id.focus();
			return;
		}
        
        $.ajax({
            url: '/user/checkId',
            type: 'POST',
            data: {"u_id_check": userId},
            success: function (data) {
                if (data) {
                    alert('중복된 ID');
                    $('#check_id_msg').text('사용 가능한 아이디입니다.');
                    $('#check_id_msg').css('color', 'blue');
                } else {
					alert('중복안된 ID');
                    $('#check_id_msg').text('이미 사용 중인 아이디입니다.');
                    $('#check_id_msg').css('color', 'red');
                }
            }
        });
    });
});


function loginForm() {
	console.log('createAccountForm()');
	
	let form = document.login_form;
	if (form.u_id.value === '') {
		alert('아이디 입력칸이 비었습니다. 아이디를 입력해주세요');
		form.u_id.focus();
		
	} else if (form.u_pw.value === '') {
		alert('비밀번호 입력칸이 비었습니다. 비밀번호를 입력해주세요');
		form.u_pw.focus();
		
	}else {
		form.submit();
		
	}
}
