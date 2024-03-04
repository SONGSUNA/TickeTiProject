window.addEventListener('scroll', function() {
    var topBar = document.querySelector('.top_bar');
    if (window.scrollY > 500) { // 스크롤이 100px 이상 되면
        topBar.style.display = 'block'; // top_bar를 표시
    } else {
        topBar.style.display = 'none'; // 그렇지 않으면 숨김
    }
});