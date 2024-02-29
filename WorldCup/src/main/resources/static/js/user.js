function createAccountForm() {
	console.log('createAccountForm()');
	
	let form = document.create_account_form;
	if (form.u_id.value === '') {
		alert('아이디 입력칸이 비었습니다. 아이디를 입력해주세요');
		form.u_id.focus();
		
	} else if (form.u_pw.value === '') {
		alert('비밀번호 입력칸이 비었습니다. 비밀번호를 입력해주세요');
		form.u_pw.focus();
		
	} else if (form.u_mail.value === '') {
		alert('이메일 입력칸이 비었습니다. 이메일을 입력해주세요');
		form.u_mail.focus();
		
	} else if (form.u_phone.value === '') {
		alert('연락처 입력칸이 비었습니다. 연락처를 입력해주세요');
		form.u_phone.focus();
		
	} else if (form.postcode.value === '') {
		alert('우편번호가 비었습니다.');
		form.postcode.focus();
		
	} else if (form.address.value === '') {
		alert('주소가 비었습니다.');
		form.postcode.focus();
		
	} else if (form.detailAddress.value === '') {
		alert('INPUT PHONE!!');
		form.postcode.focus();
		
	}else if (form.extraAddress.value === '') {
		alert('INPUT PHONE!!');
		form.postcode.focus();
		
	}else {
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
