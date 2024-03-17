let p_id;
let selectedDate;
let selectedTimeInfo;

let totalSeats;
let seatGrades = [
   ['S석', 'S', 30000],
   ['A석', 'A', 20000],
   ['B석', 'B', 15000]
];
let reservedSeats = [1, 2, 5, 6, 7,10, 20, 30, 40, 50, 51, 55, 56, 90, 91];

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
               totalSeats = data.perfomanceDto.p_max_reserve;
               
               let ticket = data.ticketDto;
               let ticketList = [];
               
               let ticket1 = [ticket.t_seattype_1, ticket.t_seattype_1.replace("석", ""), ticket.t_price_1];
               ticketList.push(ticket1)
               if (ticket.t_seattype_2 != 'null') {
				   let ticket2 = [ticket.t_seattype_2, ticket.t_seattype_2.replace("석", ""), ticket.t_price_2];
				   ticketList.push(ticket2)
			   }
               if (ticket.t_seattype_3 != 'null') {
				   let ticket3 = [ticket.t_seattype_3, ticket.t_seattype_3.replace("석", ""), ticket.t_price_3];
				   ticketList.push(ticket3)
			   }
               if (ticket.t_seattype_4 != 'null') {
				   let ticket4 = [ticket.t_seattype_4, ticket.t_seattype_4.replace("석", ""), ticket.t_price_4];
				   ticketList.push(ticket4)
			   }
               if (ticket.t_seattype_5 != 'null') {
				   let ticket5 = [ticket.t_seattype_5, ticket.t_seattype_5.replace("석", ""), ticket.t_price_5];
				   ticketList.push(ticket5)
			   }
               if (ticket.t_seattype_6 != 'null') {
				   let ticket6 = [ticket.t_seattype_6, ticket.t_seattype_6.replace("석", ""), ticket.t_price_6];
				   ticketList.push(ticket6)
			   }
			   
			   seatGrades = ticketList;
           
               $('.performance img').attr("src", data.perfomanceDto.p_thum);

               let html = "<p class='name'>" + data.perfomanceDto.p_name + "</p>";
               html += "<p class='date1'>" + data.perfomanceDto.p_start_date + " ~ </p>";
               html += "<p class='date2'>" + data.perfomanceDto.p_end_date + "</p>";
               html += "<p class='location'>" + data.perfomanceDto.p_theater + "</p>";
               $('.performance_info').html(html);

               createSeat(totalSeats, seatGrades, reservedSeats);
           }
       });
   }

   // getDateTime 함수 호출
   getDateTime();
   
   function createSeat(totalSeats, seatGrades, reservedSeats) {
     const seatingArea = document.querySelector('.seating-area');
     const selectedSeatsContainer = document.querySelector('.select_show');
     seatingArea.innerHTML = '';
     selectedSeatsContainer.innerHTML = '<p>선택한 좌석</p>';
   
     const isGeneralAdmission = seatGrades.some(grade => grade[0] === '전석' || grade[0] === '전석무료');
     if (totalSeats > 10000 || (isGeneralAdmission && totalSeats > 0)) {
       seatingArea.textContent = "지정 좌석 예매를 지원하지 않는 공연입니다.";
       const messageElement = document.createElement('div');
       messageElement.textContent = "지정 좌석 예매를 지원하지 않는 공연입니다.";
       selectedSeatsContainer.appendChild(messageElement);
       return;
     }
   
     let seatNumber = 1;
   
     const createSeatElement = (seatPrice, seatName) => {
       const seat = document.createElement('div');
       seat.classList.add('seat');
       seat.dataset.price = seatPrice;
       seat.dataset.name = seatName;
       seat.dataset.number = seatNumber;
   
       const seatNumberElement = document.createElement('span');
       seatNumberElement.classList.add('seat-number');
       seatNumberElement.textContent = seatNumber;
       seat.appendChild(seatNumberElement);
   
       if (reservedSeats.includes(seatNumber)) {
         seat.classList.add('reserved');
         seat.style.backgroundColor = '#dadada';
         seat.addEventListener('click', () => {
           alert('이미 예매 완료된 좌석입니다.');
         });
       } else {
         seat.addEventListener('click', () => {
           if (!seat.classList.contains('reserved') && !seat.classList.contains('occupied')) {
             if (selectedSeatsCount < maxSelectedSeats) {
               seat.classList.toggle('selected');
               const seatInfo = `${seat.dataset.name} ${seat.dataset.number}번 좌석`;
               if (seat.classList.contains('selected')) {
                 addSelectedSeat(seatInfo, seatPrice);
               } else {
                 removeSelectedSeat(seatInfo, seatPrice);
               }
               updatePaymentInfo();
             } else if (seat.classList.contains('selected')) {
               seat.classList.remove('selected');
               const seatInfo = `${seat.dataset.name} ${seat.dataset.number}번 좌석`;
               removeSelectedSeat(seatInfo, seatPrice);
               updatePaymentInfo();
             } else {
               alert('최대 2개의 좌석만 선택할 수 있습니다.');
             }
           }
         });
       }
   
       seatNumber++;
       return seat;
     };
   
     const createSectionTitle = (seatName) => {
       const sectionTitle = document.createElement('div');
       sectionTitle.classList.add('section-title');
       sectionTitle.textContent = seatName;
       return sectionTitle;
     };
   
     const createSeatsContainer = () => {
       const seatsContainer = document.createElement('div');
       seatsContainer.classList.add('seats');
       return seatsContainer;
     };
   
     const totalGrades = seatGrades.length;
     const seatsPerGrade = Math.floor(totalSeats / totalGrades);
     const remainingSeats = totalSeats % totalGrades;
   
     seatGrades.forEach(([seatName, seatCode, seatPrice], index) => {
       const sectionTitle = createSectionTitle(seatName);
       seatingArea.appendChild(sectionTitle);
   
       const seatsContainer = createSeatsContainer();
       seatingArea.appendChild(seatsContainer);
   
       let seatsCount = seatsPerGrade;
       if (index < remainingSeats) {
         seatsCount++;
       }
   
       for (let i = 0; i < seatsCount && seatNumber <= totalSeats; i++) {
         const seat = createSeatElement(seatPrice, seatName);
         seatsContainer.appendChild(seat);
       }
     });
   
     let selectedSeatsCount = 0;
     const maxSelectedSeats = 2;
     let totalPrice = 0;
   
     function addSelectedSeat(seatInfo, seatPrice) {
       if (selectedSeatsCount < maxSelectedSeats) {
         const seatItem = document.createElement('div');
         seatItem.textContent = seatInfo;
         selectedSeatsContainer.appendChild(seatItem);
         selectedSeatsCount++;
         totalPrice += seatPrice;
       } else {
         alert('최대 2개의 좌석만 선택할 수 있습니다.');
       }
     }
   
     function removeSelectedSeat(seatInfo, seatPrice) {
       const seatItems = selectedSeatsContainer.querySelectorAll('div');
       seatItems.forEach((item) => {
         if (item.textContent === seatInfo) {
           selectedSeatsContainer.removeChild(item);
           selectedSeatsCount--;
           totalPrice -= seatPrice;
         }
       });
     }
   
     function updatePaymentInfo() {
       const ticketPriceElement = document.querySelector('.money .value:nth-child(2)');
       const totalPriceElement = document.querySelector('.money .value:last-child');
   
       // 금액에 ,를 삽입하는 함수
       function formatCurrency(amount) {
         return amount.toLocaleString('ko-KR');
       }
   
       ticketPriceElement.textContent = formatCurrency(totalPrice) + ' 원';
       totalPriceElement.textContent = formatCurrency(totalPrice) + ' 원';
     }
   }
}