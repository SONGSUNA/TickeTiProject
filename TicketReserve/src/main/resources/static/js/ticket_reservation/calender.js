window.onload = function() {
    let url = document.URL;
    let p_id = url.split("product/")[1];

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
            // 날짜 데이터 속성에 연, 월, 일을 추가
            let fullDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
            html += `<td class="day" data-date="${fullDate}" data-day="${['일', '월', '화', '수', '목', '금', '토'][dayOfWeek]}요일">${day}</td>`;
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
        if (e.target.classList.contains('day')) {
            if (selectedDate) {
                selectedDate.style.backgroundColor = ""; 
                selectedDate.style.fontFamily = "";
            }
            selectedDate = e.target;
            selectedDate.style.backgroundColor = "#F1FADA"; 
            selectedDate.style.fontFamily = "'LINESeedKR-Bd'"; 
            // 선택된 날짜와 요일을 원하는 형식으로 콘솔에 출력
            console.log(`선택된 날짜: ${selectedDate.dataset.date}, 요일: ${selectedDate.dataset.day}`);
            
            
        }
    };

    drawCalendar(date.getMonth(), date.getFullYear());
};
