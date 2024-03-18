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