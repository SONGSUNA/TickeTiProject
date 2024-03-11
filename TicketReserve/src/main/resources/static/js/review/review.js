document.addEventListener('DOMContentLoaded', function() {
    var ratings = document.getElementsByName('rv_score');
    var displayRating = document.getElementById('display-rating');
    var reviewInput = document.getElementById('review-input');
    var reviewBtn = document.querySelector('.reviewBtn');

	
    for (var i = 0; i < ratings.length; i++) {
        ratings[i].addEventListener('change', function() {
            displayRating.innerText = this.value + '점';
        });
    }

    reviewInput.addEventListener('input', function() {
        var length = this.value.length;
        document.getElementById('current-length').innerText = length;
       
    });

    reviewBtn.addEventListener('click', function(event) {
    var length = reviewInput.value.length;
    var selectedRating = false;

    // 별점 체크
    for (var i = 0; i < ratings.length; i++) {
        if (ratings[i].checked) {
            selectedRating = true;
            break;
        }
    }

    if (length <= 0 && !selectedRating) {
        event.preventDefault(); // 기본 동작(폼 제출) 방지
        alert('리뷰와 별점을 작성해주세요.');
    } else if (length <= 0) {
        event.preventDefault(); // 기본 동작(폼 제출) 방지
        alert('리뷰를 작성해주세요.');
    } else if (!selectedRating) {
        event.preventDefault(); // 기본 동작(폼 제출) 방지
        alert('별점을 선택해주세요.');
    }
	});
	

   
// 별점을 표시할 요소 선택
var displayScores = document.getElementsByClassName('display_score');

// 각 별점 요소에 대하여
for (var j = 0; j < displayScores.length; j++) {
    var score = displayScores[j].getAttribute('data-score'); // 데이터 속성에서 별점 값 가져오기
    score = parseInt(score, 10); // 문자열을 숫자로 변환

    // 별점에 따른 별 표시
    for (var i = 1; i <= 5; i++) {
        var star = document.createElement('span');
        if (i <= score) {
            star.className = 'fas fa-star';
            star.style.color = '#f73c32'; // 별점에 따른 색상
        } else {
            star.className = 'far fa-star';
            star.style.color = '#ddd'; // 기본 색상
        }

        star.style.fontFamily = 'Font Awesome 5 Free';
        star.style.fontWeight = '900';
        star.style.fontSize = '1rem';


        displayScores[j].appendChild(star); // 현재 처리 중인 display_score 요소에 별 추가
    }
}
    
});