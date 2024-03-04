document.addEventListener('DOMContentLoaded', function() {
    var likeButton = document.getElementById('likeButton');
    var likeCount = document.getElementById('likeCount');
    var likes = 0; // 초기 좋아요 수

    likeButton.addEventListener('click', function() {
        likes++; // 좋아요 수 증가
        likeCount.textContent = likes; // 화면에 표시
    });
});