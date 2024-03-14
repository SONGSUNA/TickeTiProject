window.onload = function() {
    let url = document.URL;
    let p_id = url.split("product/")[1];
    
    let start_date;
    let end_date;
    let dayAndTime;
    let ticket;
    
    getInfoForReservation(p_id);
    
   function getInfoForReservation(p_id) {
        $.ajax({
            url: '/reservation/getInfoForReservation',
            type: 'GET',
            data: {"p_id": p_id},
            success: function (data) {
                // 전역 변수에 값 할당
                start_date = new Date(data.p_start_date.replace(/\./g, '-'));
                end_date = new Date(data.p_end_date.replace(/\./g, '-'));
                dayAndTime = data.ticketDto.t_p_date;
                ticket = data.ticketDto;

                // drawCalendar 함수 호출
                drawCalendar(date.getMonth(), date.getFullYear());
            },
            error: function (xhr, status, error) {
                console.log("AJAX 요청 실패: " + status);
                console.log("HTTP 상태 코드: " + xhr.status);
                console.log("오류 내용: " + error);
            }
        });
    }

    let date = new Date();
    let calendar = document.getElementById('calendar');
    let selectedDate;

    function drawCalendar(month, year) {
        let firstDay = (new Date(year, month)).getDay(),
            monthLength = 32 - new Date(year, month, 32).getDate(),
            html = '<table>',
            dayOfWeek;

        // 월 표시 추가
        html += `<tr><th><button id="prevMonth"><img src="../img/ticket_reservation/back_btn.png" alt="이전 달"></button></th><th colspan="5">${year}. ${month + 1}</th><th><button id="nextMonth"><img src="../img/ticket_reservation/next_btn.png" alt="다음 달"></button></th></tr>`;
        html += '<tr>' + ['일', '월', '화', '수', '목', '금', '토'].map(day => `<td>${day}</td>`).join('') + '</tr><tr>';

        for (let i = 0; i < firstDay; i++) {
            html += '<td></td>';
        }

        for (let day = 1; day <= monthLength; day++) {
            dayOfWeek = (day + firstDay - 1) % 7;
            if ((day + firstDay - 1) % 7 === 0 && day > 1) html += '</tr><tr>';
            let fullDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
            let currentDate = new Date(fullDate);
            
            // 시작 날짜와 종료 날짜 사이에 있는지 확인
            if (currentDate >= start_date && currentDate <= end_date) {
                html += `<td class="within-range" style="background-color: #F1FADA;" data-date="${fullDate}" data-day="${['일', '월', '화', '수', '목', '금', '토'][dayOfWeek]}요일">${day}</td>`;
            } else {
                // 범위 밖 날짜의 경우 'outside-range' 클래스 추가
                html += `<td class="outside-range" data-date="${fullDate}" data-day="${['일', '월', '화', '수', '목', '금', '토'][dayOfWeek]}요일">${day}</td>`;
            }
        }

        // 빈 칸으로 남은 주 채우기
        let remainingDays = (7 - (monthLength + firstDay) % 7) % 7;
        for (let i = 0; i < remainingDays; i++) {
            html += '<td></td>';
        }

        html += '</tr></table>';
        calendar.innerHTML = html;

        // 이전/다음 달 버튼 이벤트 리스너
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

    calendar.onclick = function(e) {
        if (e.target.classList.contains('within-range')) {
            if (selectedDate) {
                selectedDate.style.backgroundColor = "";
                selectedDate.style.fontFamily = "";
            }
            selectedDate = e.target;
            selectedDate.style.backgroundColor = "#F1FADA";
            selectedDate.style.fontFamily = "'LINESeedKR-Bd'";
            // 선택된 날짜와 요일을 원하는 형식으로 콘솔에 출력
            console.log(`선택된 날짜: ${selectedDate.dataset.date}, 요일: ${selectedDate.dataset.day}`);
            
            let selected_date = new Date(selectedDate.dataset.date);
            
        }
    };

    drawCalendar(date.getMonth(), date.getFullYear());
};