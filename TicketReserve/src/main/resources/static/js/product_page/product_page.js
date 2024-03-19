var modal = document.querySelector('.review_modal_wrap');
/*var btn = document.querySelector('.open_review_modal');

// 버튼을 클릭하면 모달 창을 열도록 설정
btn.onclick = function() {
    modal.style.display = "block";
}*/

// 사용자가 모달 창 외부를 클릭하면 모달 창을 닫도록 설정

var span = document.getElementsByClassName("close")[0];

	span.onclick = function() {
	  modal.style.display = "none";
	  $('form').each(function() {
                this.reset();
            });
	}
	
document.addEventListener('DOMContentLoaded', function() {
    // URL에서 앵커 태그 추출
    var hash = window.location.hash;

    if (hash === '#review') {
        // 후기 부분으로 스크롤
        var reviewSection = document.getElementById('review_list_title');
        if (reviewSection) {
            reviewSection.scrollIntoView({ behavior: 'smooth' });
        }
    }
});