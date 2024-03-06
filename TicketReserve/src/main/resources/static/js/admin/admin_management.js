// admin Search --------------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	$('#admin_search_btn').click(function search_btn_click () {
		let a_id = $('input[name="a_id"]').val();
		let a_name = $('input[name="a_name"]').val();
		let a_mail = $('input[name="a_mail"]').val();
		
		if (a_id == '' && a_name == '' && a_mail == '') {
			alert("하나 이상의 관리자 정보가 필요합니다.");
			$('input[name="a_id"]').focus();
		}
		
		$.ajax({
			url: '/admin/admin_search',
			type: 'POST',
			data: {"a_id": a_id,
				   "a_name": a_name,
				   "a_mail": a_mail},
		   	beforeSend: function(xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", $('#csrfToken').val());
            },
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
		                html += '<button onclick="adminModify(' + admin.a_no + ')">수정</button>';
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
function adminModify (a_no) {
	console.log("adminModify()");
	
	let url = '/admin/admin_modify_form?a_no=' + a_no;
	
	window.open(url, '_blank', windowFeatures);
}


// admin Delete --------------------------------------------------------------------------------------------------------------------
function adminDelete (a_no, a_id) {
	console.log("adminDelete()");
	
	let confirm = window.confirm(a_id + ' 관리자를 탈퇴처리 하시겠습니까?');
	
	if (confirm) location.href = "/admin/admin_delete?a_no=" + a_no; 
}