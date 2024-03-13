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
	                html += '<button class="ticket-button" data-p_id="' + perfomance.p_id + '" data-p_name="' + perfomance.p_name + '">등록</button>';
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
					    html += '<button onclick="ticketModifyForm(' + perfomance.p_id + ',\'' + perfomance.p_name + '\')">등록</button>';
					    html += '</td>';
					}
					else {
					    html += '<td>';
					    html += '<button onclick="ticketModifyForm(' + perfomance.p_id + ',\'' + perfomance.p_name + '\')">수정</button>';
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

// 티켓 수정 ----------------------------------------------------------------------------------------------------------------
function ticketModifyForm(p_id, p_name) {
	console.log("ticketModifyForm()");
	
	$.ajax({
	    url: '/admin/getTicketInfo',
	    type: 'GET',
	    data: {"p_id": p_id},
	    success: function (data) {
	        if (data) {
				
				let t1 = data.t_seattype_1;
				let t2 = data.t_seattype_2;
				let t3 = data.t_seattype_3;
				let t4 = data.t_seattype_4;
				let t5 = data.t_seattype_5;
				let t6 = data.t_seattype_6;
				
				let p1 = data.t_price_1;
				let p2 = data.t_price_2;
				let p3 = data.t_price_3;
				let p4 = data.t_price_4;
				let p5 = data.t_price_5;
				let p6 = data.t_price_6;
				
				if (t1 == 'null') t1 = '';
				if (t2 == 'null') t2 = '';
				if (t3 == 'null') t3 = '';
				if (t4 == 'null') t4 = '';
				if (t5 == 'null') t5 = '';
				if (t6 == 'null') t6 = '';
				
				if (p1 == 0) p1 = '';
				if (p2 == 0) p2 = '';
				if (p3 == 0) p3 = '';
				if (p4 == 0) p4 = '';
				if (p5 == 0) p5 = '';
				if (p6 == 0) p6 = '';
				
				let date = data.t_p_date.replace(/[\[\]]/g, "");
				
	            $('input[name="p_id"]').val(data.p_id);
	            $('input[name="p_name"]').val(p_name);
	            $('input[name="t_seattype_1"]').val(t1);
	            $('input[name="t_price_1"]').val(p1);
	            $('input[name="t_seattype_2"]').val(t2);
	            $('input[name="t_price_2"]').val(p2);
	            $('input[name="t_seattype_3"]').val(t3);
	            $('input[name="t_price_3"]').val(p3);
	            $('input[name="t_seattype_4"]').val(t4);
	            $('input[name="t_price_4"]').val(p4);
	            $('input[name="t_seattype_5"]').val(t5);
	            $('input[name="t_price_5"]').val(p5);
	            $('input[name="t_seattype_5"]').val(t6);
	            $('input[name="t_price_5"]').val(p6);
	            $('input[name="t_p_date"]').val(date);
	            
	            $('.modal_wrap').css("display", "block");
			}
	    },
	    	error: function (xhr, status, error) {
		        console.log("AJAX 요청 실패: " + status);
		        console.log("HTTP 상태 코드: " + xhr.status);
		        console.log("오류 내용: " + error);
		    }
	});
}

$(document).on('click', '.ticket-button', function() {
    var p_id = $(this).data('p_id');
    var p_name = $(this).data('p_name');
    
    $('input[name="p_id"]').val(p_id);
	$('input[name="p_name"]').val(p_name);
	$('input[name="t_seattype_1"]').val('');
	$('input[name="t_price_1"]').val('');
	$('input[name="t_seattype_2"]').val('');
	$('input[name="t_price_2"]').val('');
	$('input[name="t_seattype_3"]').val('');
	$('input[name="t_price_3"]').val('');
	$('input[name="t_seattype_4"]').val('');
	$('input[name="t_price_4"]').val('');
	$('input[name="t_seattype_5"]').val('');
	$('input[name="t_price_5"]').val('');
	$('input[name="t_seattype_5"]').val('');
	$('input[name="t_price_5"]').val('');
	$('input[name="t_p_date"]').val('');
	
	$('.modal_wrap').css("display", "block");
});

function modifyConfirm() {
	let form = document.modify_form;
	
	if(form.t_seattype_1.value == '') {
		alert("하나 이상의 티켓 정보는 필수 등록해야 합니다.");
		form.t_seattype_1.focus();
		return;
	}
	
	if (form.t_price_1.value == '') {
		alert("하나 이상의 티켓 정보는 필수 등록해야 합니다.");
		form.t_price_1.focus();
		return;
	}
	
	if (form.t_seattype_2.value == '') form.t_seattype_2.value = 'null';
	if (form.t_seattype_3.value == '') form.t_seattype_3.value = 'null';
	if (form.t_seattype_4.value == '') form.t_seattype_4.value = 'null';
	if (form.t_seattype_5.value == '') form.t_seattype_5.value = 'null';
	if (form.t_seattype_6.value == '') form.t_seattype_6.value = 'null';
	
	if (form.t_price_2.value == '') form.t_price_2.value = 0;
	if (form.t_price_3.value == '') form.t_price_3.value = 0;
	if (form.t_price_4.value == '') form.t_price_4.value = 0;
	if (form.t_price_5.value == '') form.t_price_5.value = 0;
	if (form.t_price_6.value == '') form.t_price_6.value = 0;
	
	form.submit();
}

// 모달 종료 ------------------------------------------------------------------------------------------------------------------------
function modify_close() {
	$('.modal_wrap').css("display", "none");
}

