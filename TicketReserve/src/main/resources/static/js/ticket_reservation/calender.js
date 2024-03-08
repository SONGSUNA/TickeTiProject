window.onload = function() {
	
	
	
    var date = new Date(),
        month = date.getMonth(),
        year = date.getFullYear(),
        calendar = document.getElementById('calendar'),
        selectedDate;

    function drawCalendar(month, year) {
        var firstDay = (new Date(year, month)).getDay(),
            monthLength = 32 - new Date(year, month, 32).getDate(),
            html = '<table>';

        // 월 표시 추가
        html += '<tr><th><button id="prevMonth"><img src="../img/ticket_reservation/back_btn.png" alt="이전 달"></button></th><th colspan="5">' + year + '. ' + (month + 1) + '</th><th><button id="nextMonth"><img src="../img/ticket_reservation/next_btn.png" alt="다음 달"></button></th></tr>';
        html += '<tr>';

        for (var i = 0; i < 7; i++)
            html += '<td>' + ['일', '월', '화', '수', '목', '금', '토'][i] + '</td>';
        html += '</tr><tr>';

        for (var i = 0; i < 7; i++) {
            if (i < firstDay) {
                html += '<td></td>';
            } else {
                html += '<td class="day">' + (i - firstDay + 1) + '</td>';
            }
        }

        var day = 8 - firstDay;
        while (day <= monthLength) {
            html += '</tr><tr>';
            for (var i = 0; i < 7; i++) {
                if (day <= monthLength) {
                    html += '<td class="day">' + day + '</td>';
                    day++;
                } else {
                    html += '<td></td>';
                }
            }
        }

        html += '</tr></table>';
        calendar.innerHTML = html;

        var days = document.getElementsByClassName('day');
        for (var i = 0; i < days.length; i++) {
            days[i].addEventListener('click', function(e) {
                if (selectedDate) {
                    selectedDate.style.backgroundColor = ""; 
                    selectedDate.style.fontFamily = "";
                }
                selectedDate = e.target;
                selectedDate.style.backgroundColor = "#F1FADA"; 
                selectedDate.style.fontFamily = "'LINESeedKR-Bd'"; 
            });
        }

        document.getElementById('prevMonth').addEventListener('click', function() {
            if (month > 0) {
                month--;
            } else {
                month = 11;
                year--;
            }
            drawCalendar(month, year);
        });

        document.getElementById('nextMonth').addEventListener('click', function() {
            if (month < 11) {
                month++;
            } else {
                month = 0;
                year++;
            }
            drawCalendar(month, year);
        });
    }

    drawCalendar(month, year);
};
