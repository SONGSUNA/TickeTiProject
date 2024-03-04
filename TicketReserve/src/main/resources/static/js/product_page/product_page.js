document.addEventListener('DOMContentLoaded', function() {
    var likeButton = document.getElementById('likeButton');
    var likeCount = document.getElementById('likeCount');
    var likes = 0; // 초기 좋아요 수

    likeButton.addEventListener('click', function() {
        likes++; // 좋아요 수 증가
        likeCount.textContent = likes; // 화면에 표시
    });
});
function openReviewPage() {
    if (isLoggedIn()) {
        window.open('/review', '_blank', 'width=750,height=500');
    } else {
        window.location.href = '/login';
    }
}

function isLoggedIn() {
    // 이 함수는 실제 로그인 상태를 확인하는 로직으로 대체해야 합니다.
    // 예를 들어, 로그인 상태를 나타내는 쿠키나 세션을 확인할 수 있습니다.
    // 여기서는 단순히 false를 반환하도록 하였습니다.
    return true;
}