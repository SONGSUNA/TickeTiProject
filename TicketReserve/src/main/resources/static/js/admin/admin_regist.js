// 아이디 중복 검사 여부를 저장하는 전역 변수
var isIdChecked = false;

function adminRegist() {
    console.log('adminRegist()');
    
    if (!isIdChecked) {
        // 아이디 중복 검사가 이루어지지 않았다면
        alert('아이디 중복 검사를 먼저 성공해주세요.');
        return;
    }
    
    let form = document.regist_form;
    let a_mail = form.a_mail_pre.value + "@" + form.a_mail_suf.value;
    let a_phone = form.a_phone_bas.value+"-"+form.a_phone_pre.value+"-"+form.a_phone_suf.value;
    let pwRegex = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;
    
    form.a_phone.value= a_phone;
    form.a_mail.value = a_mail;
    
    if (form.a_id.value === '') {
        alert('아이디를 입력해주세요.');
        form.a_id.focus();
        
    } else if (form.a_pw.value === '') {
        alert('비밀번호를 입력해주세요.');
        form.a_pw.focus();
        
    } else if (form.a_pw.value !== form.a_pw_confirm.value) {
        alert('입력한 비밀번호가 일치하지않습니다.');
        form.a_pw_confirm.focus();
        
    } else if (!pwRegex.test(form.a_pw.value)) {
        alert('비밀번호는 6-20자로 알파벳 대 소문자, 숫자 또는 특수 문자를 포함해야 합니다.');
        form.a_pw.focus();
        
    } else if (form.a_name.value === '') {
        alert('이름을 입력해주세요.');
        form.a_name.focus();
        
    } else if (form.a_mail_pre.value === '') {
        alert('이메일을 입력해주세요.');
        form.a_mail_pre.focus();
        
    } else if (form.a_mail_suf.value === '') {
        alert('이메일을 입력해주세요.');
        form.a_mail_suf.focus();
        
    } else {
        form.submit();
    }
}

$(document).ready(function () {
    $('#checkId').click(function () {
        let adminId = $('input[name="a_id"]').val();
        
        if(adminId ===''){
            alert('ID를 입력하세요');
            document.regist_form.a_id.focus();
            return;
        }
        
        $.ajax({
            url: '/admin/checkId',
            type: 'POST',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", $('#csrfToken').val());
            },
            data: {"a_id_check": adminId},
            success: function (data) {
                if (data) {
                    isIdChecked = true;  // 아이디 중복 검사 완료
                    alert('중복되지 않은 ID');
                    $('#check_id_msg').text('사용 가능한 아이디입니다.');
                    $('#check_id_msg').css('color', 'blue');
                } else {
                    isIdChecked = false;  // 아이디 중복 검사 완료하지 않음
                    alert('중복된 ID');
                    $('#check_id_msg').text('이미 사용 중인 아이디입니다.');
                    $('#check_id_msg').css('color', 'red');
                }
            },
            	error: function (xhr, status, error) {
			        console.log("AJAX 요청 실패: " + status);
			        console.log("HTTP 상태 코드: " + xhr.status);
			        console.log("오류 내용: " + error);
			    }
        });
    });
    
    $('.a_pw').keyup(function(){
        let pw = $("#a_pw").val();
        let pwcheck = $("#a_pw_check").val();
        let pwRegex = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;

        if(pw === pwcheck){
            $('#pw_matching').html('일치');
            $('#pw_matching').css('color', 'green');
        
        } else {
            $('#pw_matching').html('불일치');
            $('#pw_matching').css('color', 'red')
                        .css('font-size','0.6em');
        } 

        if(pwRegex.test(pw)){
            $('#regexp').html('비밀번호는 6-20자로 알파벳 대 소문자, 숫자 또는 특수 문자를 포함해야 합니다.')
            .css('color','green')
        } else{
            $('#regexp').html('비밀번호는 6-20자로 알파벳 대 소문자, 숫자 또는 특수 문자를 포함해야 합니다.')
            .css('color','red')
            .css('font-size','0.6em');
        }
    });
});
