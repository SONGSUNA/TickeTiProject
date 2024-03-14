
//리뷰 등록================================================

   document.addEventListener('DOMContentLoaded', function() {
    var ratings = document.getElementsByName('rv_score');
    var displayRating = document.getElementById('display-rating');
    var reviewInput = document.getElementById('review-input');
    var reviewBtn = document.querySelector('.reviewBtn');
    var reviewModiInput = document.querySelector('.review-input_modi');




    reviewInput.addEventListener('input', function() {
        var length = this.value.length;
        document.getElementById('current-length').innerText = length;

    });
   reviewModiInput.addEventListener('input', function() {
        var length = this.value.length;
        document.querySelector('.current-length').innerText = length;
    });



   //리뷰 등록
   reviewBtn.addEventListener('click', function(event) {
    event.preventDefault(); // 기본 동작(폼 제출) 방지

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
        alert('리뷰와 별점을 작성해주세요.');
    } else if (length <= 0) {
        alert('리뷰를 작성해주세요.');
    } else if (!selectedRating) {
        alert('별점을 선택해주세요.');
    } else {
        // Ajax 요청 보내기
        var form = event.target.closest('form');
        var rv_score = form.querySelector('input[name="rv_score"]:checked').value;
        var rv_txt = form.querySelector('textarea[name="rv_txt"]').value;
        var p_id = form.querySelector('input[name="p_id"]').value;
      console.log(rv_score);
      console.log(rv_txt);
      console.log(p_id);

      $.ajax({
        url: '/review/review_write',
        type: 'POST',
        data: {
            "rv_score": rv_score,
            "rv_txt": rv_txt,
            "p_id" : p_id
        },
        success: function(response) {

            console.log('리뷰 등록 확인 성공:', response);

            // 수정 완료 후 모달 창 닫기
            $('.review_mod_wrap').hide();
            alert('리뷰등록이 성공하였습니다');
            // 상세 페이지로 리다이렉트
            window.location.href = '/product/' + p_id;
        },
        error: function(xhr, status, error) {
            console.error('리뷰 등록 확인 실패:', error);

            alert('리뷰등록이 실패하였습니다');

        }
     });
    }
});


// 리뷰 리스트=================================================================   

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

        displayScores[j].appendChild(별); // 현재 처리 중인 display_score 요소에 별 추가
    }
}      
// 별 수정할때===================
$('input[name="rv_score"]').click(function() {
    console.log('별클릭하기');
    var selectedRating = $(this).val();
    $(this).closest('form').find('input[name="rv_score"][value="' + selectedRating + '"]').prop('checked', true);
});

// 리뷰 수정====================================================

       $('.reviewModifyBtn').click(function () {
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
            "p_id" : p_id
        },
        success: function(response) {
            console.log('리뷰 수정 확인 성공:', response);
            // 수정 완료 후 모달 창 닫기
            $('.review_mod_wrap').hide();
            alert('리뷰수정이 성공하였습니다');
            // 상세 페이지로 리다이렉트
            window.location.href = '/product/' + p_id;
        },
        error: function(xhr, status, error) {
            console.error('리뷰 수정 확인 실패:', error);
            alert('리뷰수정이 실패하였습니다');
        }
    });
});


//리뷰 삭제=================================
      $('.reviewDeleteBtn').click(function () {
           let rv_no = $(this).siblings('input[name="rv_no"]').val();
           let confirm = window.confirm('리뷰를 삭제하시겠습니까?')
          let url = document.URL;
            let p_id = url.split("product/")[1];
          if(!confirm)return

           $.ajax({
               url: '/review/review_delete_confirm',
               type: 'POST',
               data: {"rv_no": rv_no,
                     "p_id" : p_id
                     },
               success: function(response) {
            alert('리뷰가 삭제되었습니다.')
            window.location.href = '/product/' + p_id;
            },
               error: function(xhr, status, error) {
                  console.log(response);    
                   console.error('리뷰 삭제실패', error);
               }
           });

   });
});