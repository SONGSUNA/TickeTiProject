const url = document.URL;
const p_id = url.split("product/")[1];

let selectedDate;
let selectedTimeInfo;

window.onload = function() {

    let start_date, end_date, dayAndTime, max_seat, ticket;

    getInfoForReservation(p_id);

    // 공연 정보 불러오기 ==============================================================================================================
	function getInfoForReservation(productId) {
	    $.ajax({
	        url: '/reservation/getInfoForReservation',
	        type: 'GET',
	        data: { "p_id": productId },
	        success: function(data) {
	            start_date = new Date(data.p_start_date.replace(/\./g, '-'));
	            end_date = new Date(data.p_end_date.replace(/\./g, '-'));
	            max_seat = data.p_max_reserve;
	            ticket = data.ticketDto;
	
	            if (data.ticketDto && data.ticketDto.t_p_date) {
	                dayAndTime = data.ticketDto.t_p_date.replace(/요일/g, ' ');
	                drawCalendar(new Date().getMonth(), new Date().getFullYear());
	            } else {
	                calendar.innerHTML = "<div class='no-reservation-info'>예약 가능 정보가 곧 추가될 예정입니다.</div>";
	                $('.right_data').html("<div class='no-reservation-info'>예약 가능 정보가 곧 추가될 예정입니다.</div>");
	            }
	        },
	        error: function(xhr, status, error) {
	            console.log("AJAX 요청 실패: " + status);
	            console.log("HTTP 상태 코드: " + xhr.status);
	            console.log("오류 내용: " + error);
	        }
	    });
	}

    const calendar = document.getElementById('calendar');

    // 달력 생성 ==============================================================================================================
	function drawCalendar(month, year) {
		const firstDay = (new Date(year, month)).getDay();
		const monthLength = 32 - new Date(year, month, 32).getDate();
		let html = '<table>';

		html += `<tr><th><button id="prevMonth"><img src="../img/ticket_reservation/back_btn.png" alt="이전 달"></button></th><th colspan="5">${year}. ${month + 1}</th><th><button id="nextMonth"><img src="../img/ticket_reservation/next_btn.png" alt="다음 달"></button></th></tr>`;
		html += '<tr>' + ['일', '월', '화', '수', '목', '금', '토'].map(day => `<td>${day}</td>`).join('') + '</tr><tr>';

		for (let i = 0; i < firstDay; i++) {
			html += '<td></td>';
		}

		for (let day = 1; day <= monthLength; day++) {
			const dayOfWeek = (day + firstDay - 1) % 7;
			if ((day + firstDay - 1) % 7 === 0 && day > 1) html += '</tr><tr>';
			const fullDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
			const currentDate = new Date(fullDate);

			if (currentDate >= start_date && currentDate <= end_date) {
				html += `<td class="within-range" data-date="${fullDate}" data-day="${['일', '월', '화', '수', '목', '금', '토'][dayOfWeek]}요일">${day}</td>`;
			} else {
				html += `<td class="outside-range" data-date="${fullDate}" data-day="${['일', '월', '화', '수', '목', '금', '토'][dayOfWeek]}요일">${day}</td>`;
			}
		}

		const remainingDays = (7 - (monthLength + firstDay) % 7) % 7;
		for (let i = 0; i < remainingDays; i++) {
			html += '<td></td>';
		}

		html += '</tr></table>';
		calendar.innerHTML = html;

		document.getElementById('prevMonth').onclick = () => updateCalendar(month - 1, year);
		document.getElementById('nextMonth').onclick = () => updateCalendar(month + 1, year);
	}

	function updateCalendar(month, year) {
		if (month < 0) {
			month = 11;
			year--;
		} else if (month > 11) {
			month = 0;
			year++;
		}
		drawCalendar(month, year);
	}

    // 날짜 클릭 함수 ==============================================================================================================
    calendar.onclick = function(e) {
        if (e.target.classList.contains('within-range')) {
            if (selectedDate) {
                selectedDate.style.backgroundColor = "";
                selectedDate.style.fontFamily = "";
            }

            selectedDate = e.target;
            selectedDate.style.backgroundColor = "#F1FADA";
            selectedDate.style.fontFamily = "'LINESeedKR-Bd'";

            console.log(`선택된 날짜: ${selectedDate.dataset.date}, 요일: ${selectedDate.dataset.day}`);

            const selected_date = new Date(selectedDate.dataset.date);
            const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
            const selectedDayOfWeek = daysOfWeek[selected_date.getDay()];


            if (dayAndTime) {
                const timeSlots = getTimeSlots(dayAndTime, selectedDayOfWeek);
                const rightDataDiv = document.querySelector('.right_data');

                rightDataDiv.innerHTML = '';

                timeSlots.forEach((time, index) => {
                    const listItem = document.createElement('li');
                    listItem.textContent = `[${index + 1}회] ${time}`;
                    rightDataDiv.appendChild(listItem);

                    listItem.onclick = function() { // 클릭 이벤트 리스너 추가
                        // 모든 li 태그의 배경색을 초기화
                        document.querySelectorAll('.right_data li').forEach(li => {
                            li.style.backgroundColor = ""; // 배경색을 초기 상태로
                        });
                        // 클릭된 li 태그의 배경색 변경
                        this.style.backgroundColor = "#F1FADA";
                        selectedTimeInfo = this.textContent; // 선택된 시간 정보 저장
                    };
                });
            }
        }
    };

    // 선택 가능 시간 추가 ==============================================================================================================
	function getTimeSlots(dayAndTime, selectedDayOfWeek) {
	    const regex = new RegExp(`${selectedDayOfWeek}\\s\\d{2}:\\d{2}`, 'g');
	    const matches = dayAndTime.match(regex);
	    
	    return matches ? matches.map((match) => {
	        const time = match.split(' ')[1]; // 공백을 기준으로 분리하여 시간 부분만 추출
	        return time;
	    }) : [];
	}
};

// 예매하기 버튼 클릭 =========================================================================================================================
function doReserve() {
    if (!selectedDate || !selectedTimeInfo) {
        alert("날짜와 시간을 선택해주세요.");
        return;
    }

    $.ajax({
        url: '/reservation/saveDateTime',
        type: 'POST',
        data: {
            date: selectedDate.dataset.date,
            time: selectedTimeInfo,
            "p_id": p_id
        },
        success: function(response) {
            if(response == "logout") {
				alert("예매를 위해 로그인이 필요합니다.");
				return;
			}
            window.open("/reservation/step1", "_blank", "width=855, height=565");
        }
    });
}