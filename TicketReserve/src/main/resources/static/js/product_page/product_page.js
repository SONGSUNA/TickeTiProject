document.addEventListener('DOMContentLoaded', function ddd() {
    var likeButton = document.getElementById('likeButton');
    var likeCount = document.getElementById('likeCount');
    var likes = 0; // 초기 좋아요 수

    likeButton.addEventListener('click', function() {
        likes++; // 좋아요 수 증가
        likeCount.textContent = likes; // 화면에 표시
        
        
        
        
    });
});


var modal = document.querySelector('.review_modal_wrap');
var btn = document.querySelector('.open_review_modal');
var span = document.getElementsByClassName("close")[0];

span.onclick = function() {
  modal.style.display = "none";
}

btn.onclick = function() {
  isLoggedIn(function(isLoggedIn) {
    if (isLoggedIn) {
      modal.style.display = "block";
    } else {
      alert('로그인 후 이용 가능합니다.');
    }
  });
}

function isLoggedIn(callback) {
  // 서버에 로그인 상태를 확인하는 요청을 보냅니다.
  fetch('/checkSession')
    .then(function(response) {
		console.log(response);
      return response.text();
    })
    .then(function(text) {
      callback(text == "로그인");
    });
}