let p_id;
let t_no;
let selectedDate;
let selectedTimeInfo;

let u_id;
let totalPrice;
let selectedSeats;
let href;


// payment용
let user_email;
let username;
let p_name;
let payPrice;


window.onload = function() {
   // p_id, 선택 날짜 시간 불러오기
   function getDateTime() {
       $.ajax({
           url: '/reservation/getDateTime',
           type: 'GET',
           success: function(data) {
               p_id = data.p_id;
               selectedDate = data.selectedDate;
               selectedTimeInfo = data.selectedTime;

               console.log(p_id);
               console.log(selectedDate);
               console.log(selectedTimeInfo);

               getInfoForReservation(p_id);
           },
           error: function(xhr, status, error) {
               console.log("AJAX 요청 실패: " + status);
               console.log("HTTP 상태 코드: " + xhr.status);
               console.log("오류 내용: " + error);
           }
       });
   }

   // 공연 정보 불러오기
   function getInfoForReservation(productId) {
       $.ajax({
           url: '/reservation/getInfoForReservation',
           type: 'GET',
           data: { "p_id": productId },
           success: function(data) {
            
               $('.performance img').attr("src", data.perfomanceDto.p_thum);

               let html = "<p class='name'>" + data.perfomanceDto.p_name + "</p>";
               html += "<p class='date1'>" + data.perfomanceDto.p_start_date + " ~ </p>";
               html += "<p class='date2'>" + data.perfomanceDto.p_end_date + "</p>";
               html += "<p class='location'>" + data.perfomanceDto.p_theater + "</p>";
               $('.performance_info').html(html);
               
               p_name = data.perfomanceDto.p_name;
               
               getInfoForStep4();
           },
           error: function(xhr, status, error) {
               console.log("AJAX 요청 실패: " + status);
               console.log("HTTP 상태 코드: " + xhr.status);
               console.log("오류 내용: " + error);
           }
       });
   }
   
   function getInfoForStep4() {
	   $.ajax({
           url: '/reservation/getInfoForStep4',
           type: 'GET',
           success: function(data) {
			  	totalPrice = formatNumber(data.totalPrice);
			  	selectedSeats = data.selectedSeats;
			  	u_id = data.u_id;
			  	href = data.step3;
			  	
			  	user_email = data.u_mail;
	            username = data.u_name;
			  	
			  	const selectShow = document.querySelector('.select_show');
			  	const seatItem = document.createElement('div');
			  	
			  	let seteItemString = selectedSeats[0].replace("/", " ");
			  	seteItemString += " 좌석";
			  	
			  	seatItem.textContent = seteItemString;
			  	selectShow.appendChild(seatItem);
			  	
			  	if (selectedSeats[1] != null) {
					  const seatItem2 = document.createElement('div');
					  
					  let seteItem2String = selectedSeats[1].replace("/", " ");
			  		  seteItem2String += " 좌석";
					  
					  seatItem2.textContent = seteItem2String;
					  selectShow.appendChild(seatItem2);
				  }
				  
			  const ticketPrice = document.querySelector('input[name="ticket_price"]');
			  ticketPrice.value = totalPrice + "원";
			  const discountPrice = document.querySelector('input[name="discount_price"]');
			  discountPrice.value = data.discount + "원";
			  countingMoney();
		   },
           error: function(xhr, status, error) {
               console.log("AJAX 요청 실패: " + status);
               console.log("HTTP 상태 코드: " + xhr.status);
               console.log("오류 내용: " + error);
           }
       })
   };

   // getDateTime 함수 호출
   getDateTime();
   
   // input 태그 금액 변경 =======================================================================================
   const ticket_price 	= document.querySelector('input[name="ticket_price"]');
   const charge_price 	= document.querySelector('input[name="charge_price"]');
   const delivery_value = document.querySelector('input[name="delivery_value"]');
   const plus_total 	= document.querySelector('input[name="plus_total"]');
   
   const discount_price = document.querySelector('input[name="discount_price"]');
   const couporn_price 	= document.querySelector('input[name="couporn_price"]');
   const minus_total 	= document.querySelector('input[name="minus_total"]');
   
   const total_value 	= document.querySelector('input[name="total_value"]');
   
	function countingMoney() {
	  let tp = parseInt(ticket_price.value.replace("원", "").replace(",", ""));
	  let cp = parseInt(charge_price.value.replace("원", "").replace(",", ""));
	  let dv = parseInt(delivery_value.value.replace("원", "").replace(",", ""));
	
	  plus_total.value = formatNumber(tp + cp + dv) + "원";
	
	  let dp = parseInt(discount_price.value.replace("원", "").replace(",", ""));
	  let cp2 = parseInt(couporn_price.value.replace("원", "").replace(",", ""));
	
	  minus_total.value = formatNumber(dp + cp2) + "원";
	
	  let pt = parseInt(plus_total.value.replace("원", "").replace(",", ""));
	  let mt = parseInt(minus_total.value.replace("원", "").replace(",", ""));
	
	  total_value.value = formatNumber(pt - mt) + "원";
	  
	  payPrice = formatNumber(pt - mt);
	}
   
   // page btn =======================================================================================================
	const preBtn = document.querySelector('.pre_button');
	preBtn.addEventListener('click', () => {
		console.log(href);
		location.href = href;
	});

	
	// 금액에 ,를 삽입하는 함수
	function formatNumber(number) {
	  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
};