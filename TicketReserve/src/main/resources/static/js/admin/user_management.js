// User Search --------------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	$('#user_search_btn').click(function search_btn_click () {
		let u_id = $('input[name="u_id"]').val();
		let u_name = $('input[name="u_name"]').val();
		let u_mail = $('input[name="u_mail"]').val();
		
		if (u_id == '' && u_name == '' && u_mail == '') {
			alert("하나 이상의 유저 정보가 필요합니다.");
			$('input[name="u_id"]').focus();
		}
		
		$.ajax({
			url: '/admin/user_search',
			type: 'POST',
			data: {"u_id": u_id,
				   "u_name": u_name,
				   "u_mail": u_mail},
		   	beforeSend: function(xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", $('#csrfToken').val());
            },
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
function UserModify (u_no) {
	console.log("UserModify()");
	
	let url = '/admin/user_modify_form?u_no=' + u_no;
	
	window.open(url, '_blank', windowFeatures);
}


// User Delete --------------------------------------------------------------------------------------------------------------------
function UserDelete (u_no, u_id) {
	console.log("UserDelete()");
	
	let confirm = window.confirm(u_id + ' 유저를 탈퇴처리 하시겠습니까?');
	
	if (confirm) location.href = "/admin/user_delete?u_no=" + u_no; 
}