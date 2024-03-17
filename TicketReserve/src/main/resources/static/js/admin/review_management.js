$(document).ready(function() {
  // 검색 기능
 $("input[name='review_u_id'], input[name='review_p_name']").on("input", function() {
        var userId = $("input[name='review_u_id']").val();
        var performanceName = $("input[name='review_p_name']").val();

        $.ajax({
            type: "GET",
            url: "/admin/review_search",
            data: { review_u_id: userId, review_p_name: performanceName },
            success: function(response) {
			  // 검색 결과를 받아와서 리뷰 목록을 업데이트
			  if (response) {
			    var html = "";
			    response.forEach(function(review) {
			      html += "<tr data-review-no='" + review.rv_no + "'>";
			      html += "<td>" + review.rv_no + "</td>";
			      html += "<td>" + review.u_id + "</td>";
			      html += "<td>" + review.p_name + "</td>";
			      html += "<td>" + review.rv_score + "</td>";
			      html += "<td>" + review.rv_txt + "</td>";
			      html += "<td>" + review.rv_reg_date + "</td>";
			      html += "<td>" + review.rv_mod_date + "</td>";
			      html += "<td><button class='delete_btn' data-review-no='" + review.rv_no + "'>삭제</button></td>";
			      html += "</tr>";
			    });
			    $("tbody").html(html);
			  } else {
			    $("tbody").html("");
			  }
			},
            error: function() {
                alert("검색 중 오류가 발생했습니다.");
            }
        });
    });

  // 삭제 기능
  $(document).on("click", ".delete_btn", function() {
    var rv_no = $(this).data("review-no");

    if (confirm("정말로 리뷰를 삭제하시겠습니까?")) {
      console.log(rv_no);

      $.ajax({
        type: "POST",
        url: "/admin/review_delete",
        data: { rv_no: rv_no },
        success: function(response) {
          if (response === 1) {
            alert("리뷰 삭제에 성공했습니다.")
            $("tr[data-review-no='" + rv_no + "']").remove();
          } else {
            alert("리뷰 삭제에 실패했습니다.");
          }
        },
        error: function() {
          alert("서버와의 통신에 실패했습니다.");
        }
      });
    }
  });
});