// 메뉴 선택 -----------------------------------------------------------------------------------------------------------
function allPerfomance() {
	location.href = "/admin/ticket_state";
}

function searchPerfomance() {
	$('.list_menu .all').css('background-color', '#fff');
	$('.list_menu .all').css('color', '#000');
	$('.all_table').css('display', 'none');

	$('.list_menu .search').css('background-color', '#265073');
	$('.list_menu .search').css('color', '#fff');
	$('.search_table').css('display', 'block');
}

// 공연 검색 -----------------------------------------------------------------------------------------------------------
$(document).ready(function() {
    $('#search_p_name').on('keyup', function(event) {
        if (event.keyCode === 13) {
            search_pf();
        }
    });
});


function search_pf() {
	console.log("search_pf()");
	
	let p_name = $('input[name="search_p_name"]').val();
	
	$.ajax({
	    url: '/admin/getReservationByName',
	    type: 'GET',
	    data: {"p_name": p_name},
	    success: function (data) {
	        if (data != "") {
	            let html = '';
	            $.each(data, function(index, reservation) {
	                let paymentState;
	                if (reservation.r_payment_state == 0)
	                    paymentState = '결제 전';
	                else if (reservation.r_payment_state == 1)
	                    paymentState = '결제완료';
	                else if (reservation.r_payment_state == -1)
	                    paymentState = '예매취소';
	
	                console.log(reservation.r_discount);
	
	                html += '<tr class="' + reservation.r_no + '">';
	                html += '<td>' + reservation.r_no + '</td>';
	                html += '<td class="p_name">' + reservation.p_name + '</td>';
	                html += '<td>' + reservation.r_date.substring(0, 10) + '</td>'; // r_date 형식 변경
	                html += '<td>' + reservation.r_time.substring(0, 5) + '</td>'; // r_time 형식 변경
	                html += '<td>' + reservation.u_id + '</td>';
	                html += '<td>' + reservation.r_price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '</td>';
	                html += '<td>' + reservation.t_seat + '</td>';
	                html += '<td>' + reservation.r_discount + '</td>';
	                html += '<td class="payment_state">' + paymentState + '</td>';
	                html += '<td>' + reservation.r_reg_date.substring(0, 19) + '</td>';
	                if (paymentState !== '예매취소') {
	                    html += '<td class="cancel"><button onclick="ticket_cancel(\'' + reservation.r_no + '\')">취소</button></td>';
	                }
	                else {
	                    html += '<td></td>';
	                }
	                html += '</tr>';
	            });
            	$('.search_table table tbody').html(html);
			}
			else {
				let html = '<tr><td colspan="11">검색 결과가 존재하지 않습니다.</td></tr>';
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


// 티켓 취소 -----------------------------------------------------------------------------------------------------------
function ticket_cancel(r_no) {
	console.log("ticket_cancel()");
	
	let result = confirm(r_no + "번 티켓을 취소 처리 하시겠습니까?");
	if (!result) return;
	
	$.ajax({
	    url: '/admin/ticket_cancel_confirm',
	    type: 'GET',
	    data: {"r_no": r_no},
	    success: function (data) {
			console.log("ticketCancelConfirm() " + data);
			
			$("tr." + r_no + " td.payment_state").text("예매 취소");
			$("tr." + r_no + " td.cancel").html('');
		},
	    error: function (xhr, status, error) {
		        console.log("AJAX 요청 실패: " + status);
		        console.log("HTTP 상태 코드: " + xhr.status);
		        console.log("오류 내용: " + error);
		}
	});
}