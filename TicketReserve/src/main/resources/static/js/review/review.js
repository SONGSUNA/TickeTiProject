document.addEventListener('DOMContentLoaded', function() {
  var ratings = document.getElementsByName('rv_score');
  var displayRating = document.getElementById('display-rating');
  var reviewInput = document.getElementById('review-input');
  var reviewBtn = document.querySelector('.reviewBtn');
  var reviewModiInput = document.querySelector('.review-input_modi');
  displayReviewRatings();

  reviewInput.addEventListener('input', function() {
    var length = this.value.length;
    document.getElementById('current-length').innerText = length;
  });

  reviewModiInput.addEventListener('input', function() {
    var length = this.value.length;
    document.querySelector('.current-length').innerText = length;
  });

  $('.open_review_modal').click(function(event){
    let url = document.URL;
    let p_id = url.split("product/")[1]; 
    let u_id = this.getAttribute('data-u_id');
    
    $.ajax({
      url: '/review/check_user_review',
      type: 'GET',
      data: {
        "p_id": p_id,
        "u_id": u_id
      },
      success: function(response) {
        if (response) {
          alert('이미 리뷰를 작성하셨습니다.');
          return;
        } else {
          $('.review_modal_wrap').show();
        }
      }
    });
  });

  //리뷰 등록
  $('.reviewBtn').click(function(event) {
    event.preventDefault(); // 기본 동작(폼 제출) 방지

    var form = $(this).closest('form');
    var rv_score = form.find('input[name="rv_score"]:checked').val();
    var rv_txt = form.find('textarea[name="rv_txt"]').val();
    var p_id = form.find('input[name="p_id"]').val();

    if (!rv_score || rv_txt.trim() === '') {
      alert('리뷰와 별점을 작성해주세요.');
      return;
    }

    $.ajax({
      url: '/review/review_write',
      type: 'POST',
      data: {
        "rv_score": rv_score,
        "rv_txt": rv_txt,
        "p_id": p_id
      },
      success: function(response) {
        console.log('리뷰 등록 확인 성공:', response);

        // 새로운 리뷰 아이템 생성
        var newReviewItem = $('<div>').attr('id', 'user_review').attr('data-review-no', response.rv_no);

        var reviewList = $('<ul>').addClass('review_list');
        reviewList.append($('<li>').addClass('review_li1').text(response.u_id));
        reviewList.append($('<li>').addClass('display_score review_li2').attr('data-score', response.rv_score));
        reviewList.append($('<li>').append($('<span>').addClass('review_date').text(response.rv_reg_date.substring(0, 10))));
        reviewList.append($('<li>').append($('<input>').attr('type', 'hidden').attr('name', 'rv_no').val(response.rv_no))
          .append($('<input>').attr('type', 'button').val('수정').addClass('reviewModifyBtn'))
          .append($('<input>').attr('type', 'button').val('삭제').addClass('reviewDeleteBtn')));
        newReviewItem.append(reviewList);

        var reviewText = $('<ul>').addClass('review_text');
        reviewText.append($('<li>').addClass('review_txt').text(response.rv_txt));
        newReviewItem.append(reviewText);

        // 리뷰 목록에 새로운 리뷰 추가
        $('#user_review_null').hide();
        $('#review_page').prepend(newReviewItem);

        // 폼 초기화
        form.find('input[name="rv_score"]').prop('checked', false);
        form.find('textarea[name="rv_txt"]').val('');

        displayReviewRatings();

        // 리뷰 등록 모달 닫기
        $('.review_modal_wrap').hide();
        alert('리뷰등록이 성공하였습니다');
      },
      error: function(xhr, status, error) {
        console.error('리뷰 등록 확인 실패:', error);
        alert('리뷰등록이 실패하였습니다');
      }
    });
  });

  // 리뷰 리스트 ================================================================= 
  function displayReviewRatings() {
    var displayScores = document.getElementsByClassName('display_score');

    for (var j = 0; j < displayScores.length; j++) {
      var displayScore = displayScores[j];
      var score = displayScore.getAttribute('data-score');
      score = parseInt(score, 10);

      // 기존 별점 요소 제거
      while (displayScore.firstChild) {
        displayScore.removeChild(displayScore.firstChild);
      }

      // 별점에 따른 별 표시
      for (var i = 1; i <= 5; i++) {
        var star = document.createElement('span');
        if (i <= score) {
          star.className = 'fas fa-star';
          star.style.color = '#f73c32';
        } else {
          star.className = 'far fa-star';
          star.style.color = '#ddd';
        }
        star.style.fontFamily = 'Font Awesome 5 Free';
        star.style.fontWeight = '900';
        star.style.fontSize = '1rem';

        displayScore.appendChild(star);
      }
    }
  }

  // 별 수정할때===================
  $('input[name="rv_score"]').click(function() {
    console.log('별클릭하기');
    var selectedRating = $(this).val();
    $(this).closest('form').find('input[name="rv_score"][value="' + selectedRating + '"]').prop('checked', true);
  });

  // 리뷰 수정====================================================
  $('#review_page').on('click', '.reviewModifyBtn', function() {
    let rv_no = $(this).siblings('input[name="rv_no"]').val();
    $('.review_mod_wrap').hide();
    $('form').each(function() {
      this.reset();
    });

    $.ajax({
      url: '/review/review_modify',
      type: 'POST',
      data: {"rv_no": rv_no},
      success: function(response) {
        $('.review-input_modi').text(response.rv_txt);

        $('.review_mod_wrap').show();
        console.log(rv_no);
        // 별점 설정
        $('input[name="rv_score"][value="' + response.rv_score + '"]').prop('checked', true);

        // 리뷰 내용 설정
        $('.close').click(function() {
          $('.review_mod_wrap').hide();
        });

        $('#rv_no').val(response.rv_no);

        console.log('리뷰 수정 페이지보기:', response);
      },
      error: function(xhr, status, error) {
        // 오류가 발생했을 때의 로직을 작성합니다.
        // 예: 사용자에게 오류 메시지를 표시합니다.
        console.error('리뷰 수정 페이지 실패:', error);
      }
    });
  });

  //리뷰수정컨펌===================================
  $('.reviewModifyConfirmBtn').click(function(event) {
    event.preventDefault();

    var form = $(this).closest('form');
    var rv_no = form.find('input[name="rv_no"]').val();
    var rv_score = form.find('input[name="rv_score"]:checked').val();
    var rv_txt = form.find('.review-input_modi').val();
    var p_id = form.find('input[name="p_id"]').val();

    $.ajax({
      url: '/review/review_modify_confirm',
      type: 'POST',
      data: {
        "rv_no": rv_no,
        "rv_score": rv_score,
        "rv_txt": rv_txt,
        "p_id": p_id
      },
      success: function(response) {
        console.log('리뷰 수정 확인 성공:', response);

        // 수정된 리뷰 정보로 해당 리뷰 항목 업데이트
        var reviewItem = $("div#user_review[data-review-no='" + rv_no + "']");

        var displayScore = reviewItem.find(".display_score");
        displayScore.attr("data-score", rv_score);
        displayScore.empty(); // 기존 별점 요소 제거
        for (var i = 0; i < rv_score; i++) {
          var star = document.createElement('span');
          star.className = 'fas fa-star';
          star.style.color = '#f73c32';
          displayScore.append(star);
        }
        for (var i = rv_score; i < 5; i++) {
          var star = document.createElement('span');
          star.className = 'far fa-star';
          star.style.color = '#ddd';
          displayScore.append(star);
        }

        reviewItem.find(".review_txt").text(response.rv_txt);

        var modDate = new Date(response.rv_mod_date);
        var formattedDate = modDate.getFullYear() + '-' +
                            ('0' + (modDate.getMonth() + 1)).slice(-2) + '-' +
                            ('0' + modDate.getDate()).slice(-2);

        if (response.rv_mod_date !== response.rv_reg_date) {
          reviewItem.find("span.review_date").text(formattedDate+"(수정됨)");
        } else {
          reviewItem.find("span.review_date").text(formattedDate)
        }

        // 리뷰 수정 폼 닫기
        $(".review_mod_wrap").hide();

        alert('리뷰수정이 성공하였습니다');
      },
      error: function(xhr, status, error) {
        console.error('리뷰 수정 확인 실패:', error);
        console.log(response);
        alert('리뷰수정이 실패하였습니다');
      }
    });
  });

  //리뷰 삭제=================================
  $('#review_page').on('click', '.reviewDeleteBtn', function() {
    let rv_no = $(this).siblings('input[name="rv_no"]').val();
    let confirm = window.confirm('리뷰를 삭제하시겠습니까?');
    let url = document.URL;
    let p_id = url.split("product/")[1];

    if (!confirm) return;

    $.ajax({
      url: '/review/review_delete_confirm',
      type: 'POST',
      data: {
        "rv_no": rv_no,
        "p_id": p_id
      },
      success: function(response) {
        alert('리뷰가 삭제되었습니다.');
        $("div[data-review-no='" + rv_no + "']").remove();
      },
      error: function(xhr, status, error) {
        console.log(response);    
        console.error('리뷰 삭제실패', error);
      }
    });
  });
});