document.addEventListener('DOMContentLoaded', function() {
    var ratings = document.getElementsByName('rv_score');
    var displayRating = document.getElementById('display-rating');
    var reviewInput = document.getElementById('review-input');
    var reviewBtn = document.querySelector('.reviewBtn');
	var reviewExitBtn = document.querySelector('.reviewExitBtn');
	
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
	
	reviewExitBtn.addEventListener('click', function() {
       window.close();
    });
   
    
});