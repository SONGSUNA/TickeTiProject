function clickLike(button) {
  const performanceId = button.getAttribute('data-performance-id');
  const userId = button.getAttribute('data-user-id');
  const isLiked = button.getAttribute('data-is-liked') === 'true';
  const likeIcon = button.querySelector('#likeIcon');
  const likeCount = button.querySelector('#likeCount');

  if (!userId) {
    alert('로그인이 필요합니다.');
    window.location.href = '/user/login';
    return;
  }
	
  $.ajax({
    url: '/product/like',
    type: 'POST',
    data: {
      p_id: performanceId,
      u_id: userId
    },
    success: function(response) {
      if (response > 0) {
        if (isLiked) {
          likeIcon.classList.remove('fas');
          likeIcon.classList.add('far');
          likeIcon.classList.remove('bounce');
          likeCount.textContent = parseInt(likeCount.textContent) - 1;
          button.setAttribute('data-is-liked', 'false');
        } else {
          likeIcon.classList.remove('far');
          likeIcon.classList.add('fas');
          likeIcon.classList.add('bounce');
          likeCount.textContent = parseInt(likeCount.textContent) + 1;
          button.setAttribute('data-is-liked', 'true');
        }
      } else {
        alert('좋아요 처리에 실패했습니다.');
      }
    },
    error: function() {
      alert('서버 오류가 발생했습니다.');
    }
  });
  
  
}