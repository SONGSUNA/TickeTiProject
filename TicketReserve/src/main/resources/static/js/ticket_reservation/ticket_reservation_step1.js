let p_id;
let selectedDate;
let selectedTimeInfo;

window.onload = function() {

    let start_date, end_date, dayAndTime, ticket;

    getDateTiem();
    
    // p_id, 선택 날짜 시간 불러오기 ==============================================================================================================
    function getDateTiem() {
        $.ajax({
            url: '/reservation/getDateTime',
            type: 'GET',
            success: function(data) {
                p_id = data.p_id;
                selectedDate = data.selectedDate;
                selectedTimeInfo = data.selectedTime;

                console.log(p_id)
                console.log(selectedDate)
                console.log(selectedTimeInfo)

                getInfoForReservation(p_id);
            },
            error: function(xhr, status, error) {
                console.log("AJAX 요청 실패: " + status);
                console.log("HTTP 상태 코드: " + xhr.status);
                console.log("오류 내용: " + error);
            }
        });
    }

    // 공연 정보 불러오기 ==============================================================================================================
    function getInfoForReservation(productId) {
        $.ajax({
            url: '/reservation/getInfoForReservation',
            type: 'GET',
            data: { "p_id": productId,
            		"selectedDate": selectedDate },
            success: function(data) {
                start_date = new Date(data.p_start_date.replace(/\./g, '-'));
                end_date = new Date(data.p_end_date.replace(/\./g, '-'));
                dayAndTime = data.ticketDto.t_p_date;
                max_seat = data.perfomanceDto.p_max_reserve;
                ticket = data.ticketDto;
                
                $('.performance img').attr("src", data.perfomanceDto.p_thum);
                
                let html = "<p class='name'>" + data.perfomanceDto.p_name + "</p>";
                html += "<p class='date1'>" + data.perfomanceDto.p_start_date + " ~ </p>"
                html += "<p class='date2'>" + data.perfomanceDto.p_end_date + "</p>"
                html += "<p class='location'>" + data.perfomanceDto.p_theater + "</p>";
                $('.performance_info').html(html);

                const selectedDateObj = new Date(selectedDate);
                const selectedMonth = selectedDateObj.getMonth();
                const selectedYear = selectedDateObj.getFullYear();

                drawCalendar(selectedMonth, selectedYear);

                let tiket_html = "<ul> <li>" + ticket.t_seattype_1 + "</li>";
                tiket_html += "<li>" + numberWithCommas(ticket.t_price_1) + "원 </li> </ul>";
                
                if (ticket.t_seattype_2 != "null") {
                    tiket_html += "<ul> <li>" + ticket.t_seattype_2 + "</li>";
                    tiket_html += "<li>" + numberWithCommas(ticket.t_price_2) + "원 </li> </ul>";
                }
                if (ticket.t_seattype_3 != "null") {
                    tiket_html += "<ul> <li>" + ticket.t_seattype_3 + "</li>";
                    tiket_html += "<li>" + numberWithCommas(ticket.t_price_3) + "원 </li> </ul>";
                }
                if (ticket.t_seattype_4 != "null") {
                    tiket_html += "<ul> <li>" + ticket.t_seattype_4 + "</li>";
                    tiket_html += "<li>" + numberWithCommas(ticket.t_price_4) + "원 </li> </ul>";
                }
                if (ticket.t_seattype_5 != "null") {
                    tiket_html += "<ul> <li>" + ticket.t_seattype_5 + "</li>";
                    tiket_html += "<li>" + numberWithCommas(ticket.t_price_5) + "원 </li> </ul>";
                }
                if (ticket.t_seattype_6 != "null") {
                    tiket_html += "<ul> <li>" + ticket.t_seattype_6 + "</li>";
                    tiket_html += "<li>" + numberWithCommas(ticket.t_price_6) + "원 </li> </ul>";
                }
                
                $('.tiket_price').html(tiket_html);
                
                if (dayAndTime) {
                    const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
                    const selectedDayOfWeek = daysOfWeek[selectedDateObj.getDay()];

                    const timeSlots = getTimeSlots(dayAndTime, selectedDayOfWeek);
                    const rightDataDiv = document.querySelector('.time_select');

                    rightDataDiv.innerHTML = '';

                    timeSlots.forEach((time, index) => {
                        const listItem = document.createElement('li');
                        listItem.textContent = `[${index + 1}회] ${time}`;
                        if (selectedTimeInfo === listItem.textContent) {
                            listItem.classList.add('selected');
                            listItem.style.backgroundColor = "#265073";
                            listItem.style.color = "#fff";
                        }
                        rightDataDiv.appendChild(listItem);

                        // 여기서 클릭 이벤트 핸들러를 추가합니다.
                        listItem.addEventListener('click', function() {
                            document.querySelectorAll('.time_select li').forEach(li => {
                                li.classList.remove('selected');
                                li.style.backgroundColor = "";
                                li.style.color = "#242424";
                            });
                            this.classList.add('selected');
                            this.style.backgroundColor = "#265073";
                            this.style.color = "#fff";
                            selectedTimeInfo = this.textContent;

                            const selectedTimeMatch = this.textContent.match(/\d{2}:\d{2}/);
                            if (selectedTimeMatch) {
                                const selectedTime = selectedTimeMatch[0];
                                updateSelectedInfo(selectedDate, selectedTime);
                            }
                        });
                    });

                    if (selectedTimeInfo) {
                        const selectedTimeMatch = selectedTimeInfo.match(/\d{2}:\d{2}/);
                        if (selectedTimeMatch) {
                            const selectedTime = selectedTimeMatch[0];
                            updateSelectedInfo(selectedDate, selectedTime);
                        }
                    }
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
                let className = "within-range";
                if (fullDate === selectedDate) {
                    className += " selected";
                    html += `<td class="${className}" data-date="${fullDate}" data-day="${['일', '월', '화', '수', '목', '금', '토'][dayOfWeek]}요일" style="background-color: #F1FADA; font-family: 'LINESeedKR-Bd';">${day}</td>`;
                } else {
                    html += `<td class="${className}" data-date="${fullDate}" data-day="${['일', '월', '화', '수', '목', '금', '토'][dayOfWeek]}요일">${day}</td>`;
                }
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
        // 이전에 선택된 날짜의 스타일 초기화
        const prevSelectedDate = calendar.querySelector('.selected');
        if (prevSelectedDate) {
            prevSelectedDate.classList.remove('selected');
            prevSelectedDate.style.backgroundColor = "";
            prevSelectedDate.style.fontFamily = "";
        }

        // 'within-range' 클래스를 가진 요소에만 이벤트 적용
        if (e.target.classList.contains('within-range')) {
            selectedDate = e.target;
            selectedDate.classList.add('selected');
            selectedDate.style.backgroundColor = "#F1FADA";
            selectedDate.style.fontFamily = "'LINESeedKR-Bd'";

            console.log(`선택된 날짜: ${selectedDate.dataset.date}, 요일: ${selectedDate.dataset.day}`);

            const selected_date = new Date(selectedDate.dataset.date);
            const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
            const selectedDayOfWeek = daysOfWeek[selected_date.getDay()];

            if (dayAndTime) {
                const timeSlots = getTimeSlots(dayAndTime, selectedDayOfWeek);
                const rightDataDiv = document.querySelector('.time_select');

                rightDataDiv.innerHTML = '';

                timeSlots.forEach((time, index) => {
                    const listItem = document.createElement('li');
                    listItem.textContent = `[${index + 1}회] ${time}`;
                    rightDataDiv.appendChild(listItem);

                    listItem.onclick = function() {
                        document.querySelectorAll('.time_select li').forEach(li => {
                            li.classList.remove('selected');
                            li.style.backgroundColor = "";
                            li.style.color = "#242424";
                        });
                        this.classList.add('selected');
                        this.style.backgroundColor = "#265073";
                        this.style.color = "#fff";
                        selectedTimeInfo = this.textContent;

                        const selectedTimeMatch = this.textContent.match(/\d{2}:\d{2}/);
                        if (selectedTimeMatch) {
                            const selectedTime = selectedTimeMatch[0];
                            updateSelectedInfo(selectedDate.dataset.date, selectedTime);
                        }
                    };
                });
            }
        }
    };

    function updateSelectedInfo(date, time) {
        document.getElementById('selected-date').textContent = date;
        document.getElementById('selected-time').textContent = time;
    }

    // 선택 가능 시간 추가 ==============================================================================================================
    function getTimeSlots(dayAndTime, selectedDayOfWeek) {
	    console.log(">>>>>>>>>>>>>" + dayAndTime);
	    
	    const regex = new RegExp(`${selectedDayOfWeek}요일\\d{2}:\\d{2}`, 'g');
	    
	    const matches = dayAndTime.replace(/[\[\]]/g, '').match(regex); // 대괄호를 제거한 후 매칭 시도
	    
	    // matches 배열 내의 각 시간 문자열을 hh:mm 형식으로 조정
	    return matches ? matches.map(match => {
	        let time = match.slice(3); // 기존 코드에서 시간 부분만 추출
	        
	        console.log(time);
	        
	        let [hours, minutes] = time.split(':'); // 시간과 분을 분리
	
	        // 시간(hours)이 한 자리 숫자일 경우 앞에 0을 붙여줌
	        if (hours.length === 1) {
	            hours = '0' + hours;
	        }
	        
	        // 분(minutes)이 한 자리 숫자일 경우 (이 경우는 없겠지만) 앞에 0을 붙여줌
	        if (minutes.length === 1) {
	            minutes = '0' + minutes;
	        }
	        
	        console.log("********************" + `${hours}:${minutes}`);
	        
	        return `${hours}:${minutes}`; // 조정된 시간 포맷 반환
	       
	    }) : [];
	}
};

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	
}