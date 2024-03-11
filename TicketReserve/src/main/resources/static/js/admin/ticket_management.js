// 메뉴 선택 -----------------------------------------------------------------------------------------------------------
function allPerfomance() {
	location.href = "/admin/ticket_management";
}

function yetPerfomance() {
	$('.list_menu .all').css('background-color', '#fff');
	$('.list_menu .all').css('color', '#000');
	$('.all_table').css('display', 'none');
	
	$('.list_menu .yet').css('background-color', '#265073');
	$('.list_menu .yet').css('color', '#fff');
	$('.yet_table').css('display', 'block');
	
	$('.list_menu .search').css('background-color', '#fff');
	$('.list_menu .search').css('color', '#000');
	$('.search_table').css('display', 'none');
	
    $.ajax({
	    url: '/admin/getNoTicketPfs',
	    type: 'GET',
	    success: function (data) {
	        if (data) {
	            let html = '';
	            
	            $.each(data, function(index, perfomance) {
	                html += '<tr>';
	                html += '<td>' + perfomance.p_name + '</td>';
			        html += '<td>' + (perfomance.p_time ? perfomance.p_time : '정보없음') + '</td>';
			        html += '<td>' + (perfomance.p_ticket ? perfomance.p_ticket : '정보없음') + '</td>';
	                html += '<td>';
	                html += '<button onclick="ticketRegist(' + performance.u_no + ')">등록</button>';
	                html += '</td>';
	                html += '</tr>';
	            });
	            
	            $('.yet_table table tbody').html(html);
			}
			else {
				let html = '<tr><td colspan="4">미등록 공연이 존재하지 않습니다.</td></tr>';
            	$('.search_table table tbody').html(html);
			}
	    },
	    	error: function (xhr, status, error) {
		        console.log("AJAX 요청 실패: " + status);
		        console.log("HTTP 상태 코드: " + xhr.status);
		        console.log("오류 내용: " + error);
		    }
	});
}

function searchPerfomance() {
	$('.list_menu .all').css('background-color', '#fff');
	$('.list_menu .all').css('color', '#000');
	$('.all_table').css('display', 'none');
	
	$('.list_menu .yet').css('background-color', '#fff');
	$('.list_menu .yet').css('color', '#000');
	$('.yet_table').css('display', 'none');
	
	$('.list_menu .search').css('background-color', '#265073');
	$('.list_menu .search').css('color', '#fff');
	$('.search_table').css('display', 'block');
}

// 공연 검색 -----------------------------------------------------------------------------------------------------------
function search_pf() {
	console.log("search_pf()");
	
	let p_name = $('input[name="search_p_name"]').val();
	
	$.ajax({
	    url: '/admin/getPerfomanceByName',
	    type: 'GET',
	    data: {"p_name": p_name},
	    success: function (data) {
	        if (data != "") {
	            let html = '';
	            
	            $.each(data, function(index, perfomance) {
	                html += '<tr>';
	                html += '<td>' + perfomance.p_name + '</td>';
			        html += '<td>' + (perfomance.p_time ? perfomance.p_time : '정보없음') + '</td>';
			        html += '<td>' + (perfomance.p_ticket ? perfomance.p_ticket : '정보없음') + '</td>';
			        
			        if (perfomance.p_time == null || perfomance.p_ticket == null) {
						html += '<td>';
	                	html += '<button onclick="ticketRegist(' + performance.u_no + ')">등록</button>';
	                	html += '</td>';
					}
	                else {
						html += '<td>';
	                	html += '<button onclick="ticketModify(' + performance.u_no + ')">수정</button>';
	                	html += '</td>';
					}
	                
	                html += '</tr>';
	            });
	            
	            $('.search_table table tbody').html(html);
			}
			else {
				let html = '<tr><td colspan="4">공연명이 존재하지 않습니다.</td></tr>';
            	$('.search_table table tbody').html(html);
			}
	    },
	    	error: function (xhr, status, error) {
		        console.log("AJAX 요청 실패: " + status);
		        console.log("HTTP 상태 코드: " + xhr.status);
		        console.log("오류 내용: " + error);
		    }
	});
}
