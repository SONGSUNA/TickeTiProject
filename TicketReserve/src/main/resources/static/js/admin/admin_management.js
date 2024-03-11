// admin Search --------------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	$('#admin_search_btn').click(function search_btn_click () {
		let a_id = $('input[name="search_a_id"]').val();
		let a_name = $('input[name="search_a_name"]').val();
		let a_mail = $('input[name="search_a_mail"]').val();
		
		if (a_id == '' && a_name == '' && a_mail == '') {
			alert("하나 이상의 관리자 정보가 필요합니다.");
			$('input[name="search_a_id"]').focus();
			return;
		}
		
		$.ajax({
			url: '/admin/admin_search',
			type: 'POST',
			data: {"a_id": a_id,
				   "a_name": a_name,
				   "a_mail": a_mail},
			success: function (data) {
		        if (data) {
		            let html = '';
		            
		            $.each(data, function(index, admin) {
		                html += '<tr>';
		                html += '<td>' + admin.a_no + '</td>';
		                html += '<td>' + admin.a_id + '</td>';
		                html += '<td>' + admin.a_name + '</td>';
		                html += '<td>' + admin.a_mail + '</td>';
		                html += '<td>' + admin.a_phone + '</td>';
		                html += '<td>' + admin.a_reg_date + '</td>';
		                html += '<td>' + admin.a_mod_date + '</td>';
		                html += '<td>';
		                html += '<button onclick="adminModify(' + admin.a_id + ')">수정</button>';
						html += '<button onclick="adminDelete(' + admin.a_no + ',\'' + admin.a_id + '\')">삭제</button>';
		                html += '</td>';
		                html += '</tr>';
		            });
		            
		            $('table tbody').html(html);
				}
				else {
					let html = '관리자 정보를 불러오지 못했습니다.';
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


// admin Modify --------------------------------------------------------------------------------------------------------------------
function adminModify (a_id) {
	console.log("adminModify()");
	
	$.ajax({
	url: '/admin/select_admin_for_modify',
	cache: false,
	type: 'POST',
	data: {"a_id": a_id},
	success: function (data) {
        $('input[name="a_no"]').prop('defaultValue', data.a_no);
		$('input[name="a_id"]').prop('defaultValue', data.a_id);
		$('input[name="a_name"]').prop('defaultValue', data.a_name);
		
		let a_mail = data.a_mail;
		let atIndex = a_mail.indexOf("@");
		$('input[name="a_mail_pre"]').prop('defaultValue', a_mail.substring(0, atIndex));
		$('input[name="a_mail_suf"]').prop('defaultValue', a_mail.substring(atIndex + 1));
		$('input[name="a_mail"]').prop('defaultValue', a_mail);
		
		let a_phone = data.a_phone;
		let a_phone_pre = a_phone.substring(4, 8);
		let a_phone_suf = a_phone.substring(9);
		$('input[name="a_phone_pre"]').prop('defaultValue', a_phone_pre);
		$('input[name="a_phone_suf"]').prop('defaultValue', a_phone_suf);
		$('input[name="a_phone"]').prop('defaultValue', a_phone);
		
	},
	    error: function (xhr, status, error) {
	        console.log("AJAX 요청 실패: " + status);
	        console.log("HTTP 상태 코드: " + xhr.status);
	        console.log("오류 내용: " + error);
	    	}
	})
	
	$('.admin_modify').css('display', 'block');
	
}

function modifyConfirm() {
    console.log("modifyConfirm()");
    
    let result = confirm("회원정보를 수정하시겠습니까?");
    if (!result) return;
    
    let $form = $("form[name='modify_form']");
    
    // 이메일 조합
    let a_mail = $form.find("input[name='a_mail_pre']").val() + "@" + $form.find("input[name='a_mail_suf']").val();
    // 전화번호 조합
    let a_phone = $form.find("input[name='a_phone_bas']").val() + "-" + $form.find("input[name='a_phone_pre']").val() + "-" + $form.find("input[name='a_phone_suf']").val();
    
    if ($form.find("input[name='a_name']").val() === $form.find("input[name='a_name']")[0].defaultValue &&
        $form.find("input[name='a_mail_pre']").val() === $form.find("input[name='a_mail_pre']")[0].defaultValue &&
        $form.find("input[name='a_mail_suf']").val() === $form.find("input[name='a_mail_suf']")[0].defaultValue &&
        $form.find("input[name='a_phone_bas']").val() === $form.find("input[name='a_phone_bas']")[0].defaultValue &&
        $form.find("input[name='a_phone_pre']").val() === $form.find("input[name='a_phone_pre']")[0].defaultValue &&
        $form.find("input[name='a_phone_suf']").val() === $form.find("input[name='a_phone_suf']")[0].defaultValue) {
        alert("수정된 정보가 없습니다.");
        return;
    }
    
    if ($form.find("input[name='a_name']").val() === '') {
        alert('이름을 입력해주세요.');
        $form.find("input[name='a_name']").focus();
        
    } else if ($form.find("input[name='a_mail_pre']").val() === '') {
        alert('이메일을 입력해주세요.');
        $form.find("input[name='a_mail_pre']").focus();
        
    } else if ($form.find("input[name='a_mail_suf']").val() === '') {
        alert('이메일을 입력해주세요.');
        $form.find("input[name='a_mail_suf']").focus();
    }
    
    else {
        // 새로운 FormData 인스턴스 생성
    	let formData = new FormData();

	    formData.append('a_id', $form.find("input[name='a_id']").val());
	    formData.append('a_name', $form.find("input[name='a_name']").val());
	    formData.append('a_mail', a_mail);
	    formData.append('a_phone', a_phone);


	    $.ajax({
	        url: '/admin/admin_modify_confirm',
	        type: 'POST',
	        data: formData,
	        processData: false, // FormData를 사용할 때는 필수
	        contentType: false, // FormData를 사용할 때는 필수
	        success: function(response) {
	            console.log(response);
	            alert(response + " 회원의 정보가 성공적으로 수정되었습니다.");
	            
	            location.href="/admin/admin_management";
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
	
	$('.admin_modify').css('display', 'none');
}


// admin Delete --------------------------------------------------------------------------------------------------------------------
function adminDelete (a_no, a_id) {
	console.log("adminDelete()");
	
	let confirm = window.confirm(a_id + ' 관리자를 탈퇴처리 하시겠습니까?');
	
	if (confirm) location.href = "/admin/admin_delete?a_no=" + a_no; 
}