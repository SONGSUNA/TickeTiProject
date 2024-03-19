let p_id;
let t_no;
let selectedDate;
let selectedTimeInfo;

let u_id;
let totalPrice;
let selectedSeats;

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
               
               getInfoForStep3();
           },
           error: function(xhr, status, error) {
               console.log("AJAX 요청 실패: " + status);
               console.log("HTTP 상태 코드: " + xhr.status);
               console.log("오류 내용: " + error);
           }
       });
   }
   
   function getInfoForStep3() {
	   $.ajax({
           url: '/reservation/getInfoForStep3',
           type: 'GET',
           success: function(data) {
			  	totalPrice = formatNumber(data.totalPrice);
			  	selectedSeats = data.selectedSeats;
			  	u_id = data.u_id;
				t_no = data.t_no;			  	
			  	
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
	}
   
	// 할인 선택 ======================================================================================================
	const pnm = document.querySelector('input[name=pnm]');
	const student = document.querySelector('input[name=student]');
	const disabled = document.querySelector('input[name=disabled]');
	const older = document.querySelector('input[name=older]');
	
	const willCall = document.querySelector('input[value="willCall"]');
	const mail = document.querySelector('input[value="mail"]');
	const phone = document.querySelector('input[value="phone"]');
	
	function disableTakeMethods() {
	  willCall.checked = true;
	  mail.disabled = true;
	  phone.disabled = true;
	}
	
	function enableTakeMethods() {
	  mail.disabled = false;
	  phone.disabled = false;
	}
	
	function updateTakeMethod() {
	  if (pnm.checked || student.checked || disabled.checked || older.checked) {
	    disableTakeMethods();
	  } else {
	    enableTakeMethods();
	  }
	}
	
	function updateDiscountPrice() {
	  let tp = parseInt(ticket_price.value.replace("원", "").replace(",", ""));
	  let dp = parseInt(discount_price.value.replace("원", "").replace(",", ""));
	
	  if (this.checked)
	    discount_price.value = formatNumber(dp + (tp * 0.1)) + "원";
	  else
	    discount_price.value = formatNumber(dp - (tp * 0.1)) + "원";
	
	  countingMoney();
	}
	
	pnm.onchange = function() {
	  updateTakeMethod();
	  updateDiscountPrice.call(this);
	}
	
	student.onchange = function() {
	  updateTakeMethod();
	  updateDiscountPrice.call(this);
	}
	
	disabled.onchange = function() {
	  updateTakeMethod();
	  updateDiscountPrice.call(this);
	}
	
	older.onchange = function() {
	  updateTakeMethod();
	  updateDiscountPrice.call(this);
	}
   
   // page btn =======================================================================================================
	const preBtn = document.querySelector('.pre_button');
	preBtn.addEventListener('click', () => {
		location.href = "/reservation/step2";
	});
	
	const nextBtn = document.querySelector('.next_button');

	nextBtn.addEventListener('click', () => {
	    console.log("next_btn click!");
	
	    const willCall = document.querySelector('input[value="willCall"]');
	    const mail = document.querySelector('input[value="mail"]');
	    const phone = document.querySelector('input[value="phone"]');
	
	    if (!willCall.checked && !mail.checked && !phone.checked) {
	        alert("수령 방법을 선택해주세요.");
	        return;
	    }
	
	    let phone1 = document.querySelector('input[name=phone1]').value;
	    let phone2 = document.querySelector('input[name=phone2]').value;
	    let phone3 = document.querySelector('input[name=phone3]').value;
	
	    if (phone1 == '' || phone2 == '' || phone3 == '') {
	        alert("비상연락망을 입력해주세요.");
	        return;
	    }
	
	    let r_sub_phone = phone1 + "-" + phone2 + "-" + phone3;
	    let r_take_ticket;
	
	    if (willCall.checked) r_take_ticket = 1;
	    if (mail.checked) r_take_ticket = 2;
	    if (phone.checked) r_take_ticket = 3;
	    
	    let r_time = selectedTimeInfo.match(/\d{2}:\d{2}/)[0];
	    r_time += ":00";
	    let r_price = total_value.value.replace("원", "");
	    r_price = r_price.replace(",", "");
	    if (selectedSeats[1] != null) {
			r_price = r_price / 2;
		}
	
	    let tiketInfo = {
	        "u_id": u_id,
	        "r_sub_phone": r_sub_phone,
	        "t_no": t_no,
	        "r_date": selectedDate,
	        "r_time": r_time,
	        "r_take_ticket": r_take_ticket,
	        "r_price": r_price,
	        "t_seat": selectedSeats[0].match(/\d+/)[0],
	        "r_discount": getSelectedDiscounts()
	    };
	
	    if (selectedSeats[1] != null) {
	        tiketInfo = {
		        "u_id": u_id,
		        "r_sub_phone": r_sub_phone,
		        "t_no": t_no,
		        "r_date": selectedDate,
		        "r_time": r_time,
		        "r_take_ticket": r_take_ticket,
		        "r_price": r_price,
		        "t_seat": selectedSeats[0].match(/\d+/)[0],
		        "t_seat2": selectedSeats[1].match(/\d+/)[0],
		        "r_discount": getSelectedDiscounts()
		    };	
	    }
	
	    $.ajax({
	        url: '/reservation/setTikietInfo',
	        type: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify(tiketInfo),
	        success: function(data) {
	            console.log(data);
	            let discount = parseInt(discount_price.value.replace("원", "").replace(",", ""));
	            const step3 = window.location.href;
	            location.href = "/reservation/step4?discount=" + discount + "&step3=" + encodeURIComponent(step3);
	        },
	        error: function(xhr, status, error) {
	            console.log("AJAX 요청 실패: " + status);
	            console.log("HTTP 상태 코드: " + xhr.status);
	            console.log("오류 내용: " + error);
	        }
	    });
	});

			
	function getSelectedDiscounts() {
	  const discounts = [];
	
	  if (pnm.checked) {
	    discounts.push("국가유공자");
	  }
	  if (student.checked) {
	    discounts.push("학생");
	  }
	  if (disabled.checked) {
	    discounts.push("장애인");
	  }
	  if (older.checked) {
	    discounts.push("65세");
	  }
	  
	  if (discounts.length === 0) {
	    discounts.push("우대 할인 없음");
	  }
	
	  const discountString = discounts.join(",");

  	  return discountString;
	}

	
	// 금액에 ,를 삽입하는 함수
	function formatNumber(number) {
	  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
};