
document.getElementById('payment_kakao').addEventListener('click', kakaoPay);

var IMP = window.IMP;

var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = `${hours}` + `${minutes}` + `${seconds}` + `${milliseconds}`;

function kakaoPay() {
    if (confirm("카카오페이 결제로 예매하시겠습니까?")) { // 구매 클릭시 한번 더 확인하기
		
        IMP.init("imp63560567"); // 가맹점 식별코드
        IMP.request_pay({
            pg: 'kakaopay.TC0ONETIME', // PG사 코드표에서 선택
            pay_method: 'card', // 결제 방식
            merchant_uid: "IMP" + makeMerchantUid, // 결제 고유 번호
            name: p_name, // 제품명
            amount: payPrice, // 가격
            //구매자 정보 ↓
            buyer_email: `${user_email}`,
            buyer_name: `${username}`,
        }, async function (rsp) { // callback
            if (rsp.success) { //결제 성공시
                console.log(rsp);
                
                
                insertReserve();

                alert('결제가 완료되었습니다.');
                window.close();
            } else if (rsp.success == false) { // 결제 실패시
                alert(rsp.error_msg)
            }
        });
    } else { // 구매 확인 알림창 취소 클릭시 돌아가기
        return false;
    }
} 
       
function insertReserve() {
	console.log("insertReserve()");
	
	$.ajax({
       url: '/reservation/reserveConfirm',
       type: 'GET',
       success: function(data) {
           if (data > 0) {
			   console.log("reserve success");
		   }
        },
       error: function(xhr, status, error) {
           console.log("AJAX 요청 실패: " + status);
           console.log("HTTP 상태 코드: " + xhr.status);
           console.log("오류 내용: " + error);
       }
   });
   
}
