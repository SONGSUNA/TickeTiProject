// User Search --------------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	$('#user_search_btn').click(function () {
		let u_id = $('input[name="u_id"]').val();
		let u_name = $('input[name="u_name"]').val();
		let u_mail = $('input[name="u_mail"]').val();
		
		if (u_id == '' && u_name == '' && u_mail == '') {
			alert("하나 이상의 유저 정보가 필요합니다.");
			$('input[name="u_id"]').focus();
		}
		
		$.ajax({
			url: '/admin/user_search',
			type: 'GET',
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
		                html += '<button onclick="UserModifyInSearch(' + user.u_no + ',' + data + ')">수정</button>';
		                html += '<button onclick="UserDeleteInSearch(' + user.u_no + ',' + data + ')">삭제</button>';
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
	
	
}

function UserModifyInSearch (u_no) {
	console.log("UserModifyInSearch()");
}

// User Delete --------------------------------------------------------------------------------------------------------------------
function UserDelete (u_no) {
	console.log("UserDelete()");
}

function UserDeleteInSearch(u_no) {
	console.log("UserDeleteInSearch()");
}