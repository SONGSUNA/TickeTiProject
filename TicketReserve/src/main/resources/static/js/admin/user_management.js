// User Search --------------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	$('#user_search_btn').click(function search_btn_click () {
		let u_id = $('input[name="search_u_id"]').val();
		let u_name = $('input[name="search_u_name"]').val();
		let u_mail = $('input[name="search_u_mail"]').val();
		
		if (u_id == '' && u_name == '' && u_mail == '') {
			alert("하나 이상의 유저 정보가 필요합니다.");
			$('input[name="search_u_id"]').focus();
			return;
		}
		
		$.ajax({
			url: '/admin/user_search',
			type: 'POST',
			data: {"u_id": u_id,
				   "u_name": u_name,
				   "u_mail": u_mail},
			success: function (data) {
		        if (data) {
		            let html = '';
		            
		            $.each(data, function(index, user) {
		                html += '<tr>';
		                html += '<td>' + user.u_no + '</td>';
		                html += '<td>' + user.u_id + '</td>';
		                html += '<td>' + user.u_name + '</td>';
		                html += '<td>' + user.u_mail + '</td>';
		                html += '<td>' + user.u_phone + '</td>';
		                html += '<td>' + user.u_reg_date + '</td>';
		                html += '<td>' + user.u_mod_date + '</td>';
		                html += '<td>';
		                html += '<button onclick="UserModify(' + user.u_no + ')">수정</button>';
						html += '<button onclick="UserDelete(' + user.u_no + ',\'' + user.u_id + '\')">삭제</button>';
		                html += '</td>';
		                html += '</tr>';
		            });
		            
		            $('table tbody').html(html);
				}
				else {
					let html = '유저 정보를 불러오지 못했습니다.';
					$('table tbody').html(html);
				}
			},
			    error: function (xhr, status, error) {
			        console.log("AJAX 요청 실패: " + status);
			        console.log("HTTP 상태 코드: " + xhr.status);
			        console.log("오류 내용: " + error);
			    }
		})
	})
});


// User Modify --------------------------------------------------------------------------------------------------------------------
function UserModify (u_id) {
	console.log("UserModify()");
	
	$.ajax({
	url: '/admin/select_user_for_modify',
	cache: false,
	type: 'POST',
	data: {"u_id": u_id},
	success: function (data) {
        $('input[name="u_no"]').prop('defaultValue', data.u_no);
		$('input[name="u_id"]').prop('defaultValue', data.u_id);
		$('input[name="u_name"]').prop('defaultValue', data.u_name);
		
		let u_sc_num = data.u_sc_num;
		$('input[name="u_sc_num_pre"]').prop('defaultValue', u_sc_num.substring(0, 6));
		$('input[name="u_sc_num"]').prop('defaultValue', u_sc_num);
		
		let u_mail = data.u_mail;
		let atIndex = u_mail.indexOf("@");
		$('input[name="u_mail_pre"]').prop('defaultValue', u_mail.substring(0, atIndex));
		$('input[name="u_mail_suf"]').prop('defaultValue', u_mail.substring(atIndex + 1));
		$('input[name="u_mail"]').prop('defaultValue', u_mail);
		
		let u_phone = data.u_phone;
		let u_phone_pre = u_phone.substring(4, 8);
		let u_phone_suf = u_phone.substring(9);
		$('input[name="u_phone_pre"]').prop('defaultValue', u_phone_pre);
		$('input[name="u_phone_suf"]').prop('defaultValue', u_phone_suf);
		$('input[name="u_phone"]').prop('defaultValue', u_phone);
		
		let u_address = data.u_address;
		let addressParts = u_address.split("/");
		let postcode = addressParts[0];
		let address = addressParts[1];
		let detailAddress = addressParts[2];
		let extraAddress = addressParts[3];
		$('input[name="postcode"]').prop('defaultValue', postcode);
		$('input[name="address"]').prop('defaultValue', address);
		$('input[name="detailAddress"]').prop('defaultValue', detailAddress);
		$('input[name="extraAddress"]').prop('defaultValue', extraAddress);
		$('input[name="u_address"]').prop('defaultValue', u_address);
	},
	    error: function (xhr, status, error) {
	        console.log("AJAX 요청 실패: " + status);
	        console.log("HTTP 상태 코드: " + xhr.status);
	        console.log("오류 내용: " + error);
	    	}
	})
	
	$('.user_modify').css('display', 'block');
	
}

function modifyConfirm() {
    console.log("modifyConfirm()");
    ``
    let result = confirm("회원정보를 수정하시겠습니까?");
    if (!result) return;
    
    let $form = $("form[name='modify_form']");
    
    // 이메일 조합
    let u_mail = $form.find("input[name='u_mail_pre']").val() + "@" + $form.find("input[name='u_mail_suf']").val();
    // 주소 조합
    let u_address = $form.find("input[name='postcode']").val() + "/" + $form.find("input[name='address']").val() + "/" +
                    $form.find("input[name='detailAddress']").val() + "/" + $form.find("input[name='extraAddress']").val();
    // 전화번호 조합
    let u_phone = $form.find("input[name='u_phone_bas']").val() + "-" + $form.find("input[name='u_phone_pre']").val() + "-" + $form.find("input[name='u_phone_suf']").val();
    
    if ($form.find("input[name='u_name']").val() === $form.find("input[name='u_name']")[0].defaultValue &&
        $form.find("input[name='u_mail_pre']").val() === $form.find("input[name='u_mail_pre']")[0].defaultValue &&
        $form.find("input[name='u_mail_suf']").val() === $form.find("input[name='u_mail_suf']")[0].defaultValue &&
        $form.find("input[name='postcode']").val() === $form.find("input[name='postcode']")[0].defaultValue &&
        $form.find("input[name='address']").val() === $form.find("input[name='address']")[0].defaultValue &&
        $form.find("input[name='detailAddress']").val() === $form.find("input[name='detailAddress']")[0].defaultValue &&
        $form.find("input[name='extraAddress']").val() === $form.find("input[name='extraAddress']")[0].defaultValue &&
        $form.find("input[name='u_phone_bas']").val() === $form.find("input[name='u_phone_bas']")[0].defaultValue &&
        $form.find("input[name='u_phone_pre']").val() === $form.find("input[name='u_phone_pre']")[0].defaultValue &&
        $form.find("input[name='u_phone_suf']").val() === $form.find("input[name='u_phone_suf']")[0].defaultValue) {
        alert("수정된 정보가 없습니다.");
        return;
    }
    
    if ($form.find("input[name='u_name']").val() === '') {
        alert('이름을 입력해주세요.');
        $form.find("input[name='u_name']").focus();
        
    } else if ($form.find("input[name='u_mail_pre']").val() === '') {
        alert('이메일을 입력해주세요.');
        $form.find("input[name='u_mail_pre']").focus();
        
    } else if ($form.find("input[name='u_mail_suf']").val() === '') {
        alert('이메일을 입력해주세요.');
        $form.find("input[name='u_mail_suf']").focus();
        
    } else if ($form.find("input[name='postcode']").val() === '') {
        alert('우편번호를 입력해주세요.');
        $form.find("input[name='postcode']").focus();
        
    } else if ($form.find("input[name='address']").val() === '') {
        alert('주소를 입력해주세요');
        $form.find("input[name='address']").focus();
    }
    
    else {
        let formData = new FormData();

	    formData.append('u_id', $form.find("input[name='u_id']").val());
	    formData.append('u_name', $form.find("input[name='u_name']").val());
	    formData.append('u_mail', u_mail);
	    formData.append('u_phone', u_phone);
	    formData.append('u_address', u_address);


	    $.ajax({
	        url: '/admin/user_modify_confirm',
	        type: 'POST',
	        data: formData,
	        processData: false, // FormData를 사용할 때는 필수
	        contentType: false, // FormData를 사용할 때는 필수
	        success: function(response) {
	            console.log(response);
	            alert(response + " 회원의 정보가 성공적으로 수정되었습니다.");
	            
	            location.href="/admin/user_management";
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            console.error("Error: " + textStatus + ", " + errorThrown);
	            alert("회원 정보 수정에 실패하였습니다.");
	        }
	    });
    }
}

function modify_close () {
	console.log("modify_close()");
	
	$('.user_modify').css('display', 'none');
}


// User Delete --------------------------------------------------------------------------------------------------------------------
function UserDelete (u_no, u_id) {
	console.log("UserDelete()");
	
	let confirm = window.confirm(u_id + ' 유저를 탈퇴처리 하시겠습니까?');
	
	if (confirm) location.href = "/admin/user_delete?u_no=" + u_no; 
}