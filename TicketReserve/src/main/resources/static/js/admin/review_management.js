function reviewDelete(rv_no) {
  if (confirm("정말로 리뷰를 삭제하시겠습니까?")) {
    console.log(
		rv_no
	);
    $.ajax({
      type: "POST",
      url: "/admin/review_delete",
      data: { 
		  rv_no: rv_no
		  },
      success: function(response) {
        if (response === 1) {
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
}

