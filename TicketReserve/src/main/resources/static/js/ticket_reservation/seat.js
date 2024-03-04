document.addEventListener('DOMContentLoaded', function() {
    const sectionA = document.getElementById('section-A').querySelector('.seats');
    const sectionB = document.getElementById('section-B').querySelector('.seats');
    const sectionC = document.getElementById('section-C').querySelector('.seats');

    createSeats(sectionA, 70);
    createSeats(sectionB, 40);
    createSeats(sectionC, 40);

    // 예매된 좌석을 설정
    setReservedSeats(sectionA, [1, 3, 5, 6, 7, 8, 10, 11, 13, 14, 15, 16, 18, 19, 25, 26, 31, 33, 45, 46, 47, 48, 49, 50, 55, 56, 57, 58, 59, 61]); // A구역의 지정된 좌석을 예매된 상태로 설정
    setReservedSeats(sectionB, [2, 3, 4, 5, 7, 8, 9, 14, 16, 18, 19, 20, 23, 24, 27, 28, 30, 31]); // A구역의 지정된 좌석을 예매된 상태로 설정
    setReservedSeats(sectionC, [3, 4, 5, 6, 7, 8, 9, 10, 11, 16, 17, 18, 20, 21, 22, 26, 29, 30, 33, 35, 38]); // A구역의 지정된 좌석을 예매된 상태로 설정

    // 좌석 선택 이벤트 처리
    document.querySelectorAll('.seats').forEach(function(seats) {
        seats.addEventListener('click', function(event) {
            toggleSelect(event);
        });
    });
});

function createSeats(section, seatCount) {
    for (let i = 1; i <= seatCount; i++) {
        const seat = document.createElement('div');
        seat.classList.add('seat');
        seat.innerText = i;
        seat.dataset.number = i; // 좌석 번호를 'data-number' 속성에 저장
        section.appendChild(seat);
    }
}

function setReservedSeats(section, seatNumbers) {
    seatNumbers.forEach(function(number) {
        const seat = section.querySelector(`div[data-number="${number}"]`);
        seat.classList.add('reserved'); // 예매된 좌석에 'reserved' 클래스를 추가
    });
}

function toggleSelect(e) {
    // 클릭 이벤트가 발생한 요소가 좌석을 나타내는 div 요소이고 예매되지 않은 좌석인지 확인
    if (e.target.classList.contains('seat') && !e.target.classList.contains('reserved')) {
        e.target.classList.toggle('selected');

        // 선택한 좌석의 정보 가져오기
        var section = e.target.closest('.stage_section').querySelector('.section-title').textContent;
        var number = e.target.dataset.number; // 좌석의 번호 정보를 'data-number' 속성에서 가져옴

        // 선택한 좌석의 정보를 'select_show' 구역에 추가 또는 제거
        var selectShow = document.querySelector('.select_show');
        var li = selectShow.querySelector(`li[data-number="${number}"]`); // 해당 좌석 정보를 가진 li 요소 찾기

        if (e.target.classList.contains('selected')) {
            // 좌석이 선택된 경우, 새로운 li 요소를 생성하고 선택한 좌석의 정보를 설정
            if (!li) {
                li = document.createElement('li');
                li.dataset.number = number; // 좌석 번호를 'data-number' 속성에 저장
                selectShow.appendChild(li);
            }
            li.textContent = section + ' ' + number + '번 좌석';
        } else {
            // 좌석 선택이 해제된 경우, 해당 좌석 정보를 'select_show' 구역에서 제거
            if (li) {
                selectShow.removeChild(li);
            }
        }
    }
}
