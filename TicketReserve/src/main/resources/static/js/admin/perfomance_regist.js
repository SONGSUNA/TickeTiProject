var isIdChecked = false;

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

$(document).ready(function () {
    // 썸네일 이미지 미리보기
    $('#thum_img').change(function () {
        let reader = new FileReader();
        reader.onload = function (e) {
            $('#thumbPreview').attr('src', e.target.result).show();
        }
        reader.readAsDataURL(this.files[0]);
    });

    // 상세페이지 이미지 미리보기
    $('#detail_img').change(function () {
        let reader = new FileReader();
        reader.onload = function (e) {
            $('#detailImgPreview').attr('src', e.target.result).show();
        }
        reader.readAsDataURL(this.files[0]);
    });
});
    
function perfomanceRegist() {
    console.log('perfomanceRegist()');
        if (!isIdChecked) {
        // 아이디 중복 검사가 이루어지지 않았다면
        alert('아이디 중복 검사를 먼저 성공해주세요.');
        return;
    }
    
    let form = document.getElementsByName("perfomance_regist_form")[0];
    
    let p_place = form.postcode.value +"/"+ form.address.value + "/" 
   + form.detailAddress.value + "/" + form.extraAddress.value;
    
	form.p_place.value = p_place;
    
    if (form.p_name.value === '') {
	    alert('공연명을 입력해주세요.');
	    form.p_name.focus();
        
    } else if (form.p_start_date.value === '') {
        alert('공연 시작 날짜를 입력해주세요.');
        form.p_start_date.focus();
        
    } else if (form.p_end_date.value === '') {
        alert('공연 종료 날짜를 입력해주세요.');
        form.p_end_date.focus();
        
    } else if (form.p_grade.value === 'none') {
        alert('연령 등급을 선택해주세요.');
        form.p_grade.focus();
        
    } else if (form.p_theater.value === '') {
        alert('공연 시설명을 입력해주세요.');
        form.p_theater.focus();
        
    } else if (form.postcode.value === '') {
      	alert('우편번호를 입력해주세요.');
      	form.postcode.focus();ㅌ
    } else if (form.address.value === '') {
      	alert('주소를 입력해주세요');
      	form.address.focus();
        
    } else if (form.thum_img.value === '') {
        alert('썸네일 이미지를 첨부해주세요.');
        form.thum_img.focus();
        
    } else if (form.p_category.value === 'none') {
        alert('카테고리를 선택해주세요.');
        form.p_category.focus();
        
    } else if (form.p_max_reserve.value === '') {
        alert('좌석 수를 입력해주세요.');
        form.p_max_reserve.focus();
        
    } else if (form.p_running_time.value === '') {
        alert('관람 시간을 입력해주세요.');
        form.p_running_time.focus();
        
    } else if (form.p_characters.value === '') {
        alert('출연진을 입력해주세요.');
        form.p_characters.focus();
        
    } else if (form.p_ticket.value === '') {
        alert('티켓 정보를 입력해주세요.');
        form.p_ticket.focus();
        
    } else if (form.detail_img.value === '') {
        alert('상세페이지 이미지를 첨부해주세요.');
        form.detail_img.focus();
        
    } else if (form.p_agency_info.value === '') {
        alert('기획사 정보를 입력해주세요.');
        form.p_agency_info.focus();
        
    } else if (form.p_host.value === '') {
        alert('주최를 입력해주세요.');
        form.p_host.focus();
        
    } else if (form.p_inquiry.value === '') {
        alert('상담 정보를 입력해주세요.');
        form.p_inquiry.focus();
        
    } else {
        form.submit();
    }
}
