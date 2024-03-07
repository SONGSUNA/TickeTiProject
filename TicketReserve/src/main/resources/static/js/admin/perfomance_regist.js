var isIdChecked = false;

let form = document.perfomance_regist_form;

let p_place = form.postcode.value +"/"+ form.address.value + "/" 
   + form.detailAddress.value + "/" + form.extraAddress.value;
   
   form.p_place.value = p_place;

$(document).ready(function () {
    $('#checkId').click(function () {
        let p_id = $('input[name="p_id"]').val();
        
        if(p_id ===''){
            alert('ID를 입력하세요');
            document.perfomance_regist_form.p_id.focus();
            return;
        }
        
        $.ajax({
            url: '/admin/isPfId',
            type: 'POST',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", $('#csrfToken').val());
            },
            data: {"p_id_check": p_id},
            success: function (data) {
                if (data == false) {
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
});
    
function perfomanceRegist() {
    console.log('perfomanceRegist()');

    if (!isIdChecked) {
        // 아이디 중복 검사가 이루어지지 않았다면
        alert('아이디 중복 검사를 먼저 성공해주세요.');
        return;
    }
}
