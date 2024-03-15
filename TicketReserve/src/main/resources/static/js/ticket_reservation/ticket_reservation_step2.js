let p_id;
let selectedDate;
let selectedTimeInfo;

window.onload = function() {
    // p_id, 선택 날짜 시간 불러오기
    function getDateTiem() {
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
                let start_date = new Date(data.p_start_date.replace(/\./g, '-'));
                let end_date = new Date(data.p_end_date.replace(/\./g, '-'));
                let dayAndTime = data.ticketDto.t_p_date;
                let max_seat = data.p_max_reserve;
                let ticket = data.ticketDto;

                $('.performance img').attr("src", data.perfomanceDto.p_thum);

                let html = "<p class='name'>" + data.perfomanceDto.p_name + "</p>";
                html += "<p class='date1'>" + data.perfomanceDto.p_start_date + " ~ </p>";
                html += "<p class='date2'>" + data.perfomanceDto.p_end_date + "</p>";
                html += "<p class='location'>" + data.perfomanceDto.p_theater + "</p>";
                $('.performance_info').html(html);
            }
        });
    }

    // 함수 호출
    getDateTiem();
}
