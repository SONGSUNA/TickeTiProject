$(document).ready(function() {
    // 검색 기능
	$("form[name='review_search_form']").on("submit", function(event) {
	    event.preventDefault();
	    var userId = $("input[name='review_u_id']").val();
	    var performanceName = $("input[name='review_p_name']").val();
	    var page = 1;
	    var size = 10;
	    searchReviews(page, size, userId, performanceName);
        
        $.ajax({
            type: "GET",
            url: "/admin/review_search",
            data: { 
                review_u_id: userId, 
                review_p_name: performanceName,
                page: page,
                size: size
            },
            success: function(response) {
                updateReviewTable(response);
                updatePagination(page, size, userId, performanceName);
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
            $.ajax({
                type: "POST",
                url: "/admin/review_delete",
                data: { rv_no: rv_no },
                success: function(response) {
                    if (response === 1) {
                        alert("리뷰 삭제에 성공했습니다.");
                        var userId = $("input[name='review_u_id']").val();
                        var performanceName = $("input[name='review_p_name']").val();
                        var page = parseInt($(".pagination .active").text());
                        var size = 10;
                        searchReviews(page, size, userId, performanceName);
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
    
    // 페이지네이션 클릭 이벤트
    $(document).on("click", ".pagination a", function(event) {
	    event.preventDefault();
	    var page = $(this).data("page");
	    var size = 10;
	    var userId = $("input[name='review_u_id']").val();
	    var performanceName = $("input[name='review_p_name']").val();
	    searchReviews(page, size, userId, performanceName);
    });
    
    // 검색 함수
    function searchReviews(page, size, userId, performanceName) {
        $.ajax({
            type: "GET",
            url: "/admin/review_search",
            data: { 
                review_u_id: userId, 
                review_p_name: performanceName,
                page: page,
                size: size
            },
            success: function(response) {
                updateReviewTable(response);
                updatePagination(page, size, userId, performanceName);
            },
            error: function() {
                alert("검색 중 오류가 발생했습니다.");
            }
        });
    }
    
    // 리뷰 테이블 업데이트 함수
    function updateReviewTable(reviews) {
        var tableBody = $("tbody");
        tableBody.empty();
        
        if (reviews.length === 0) {
            var emptyRow = $("<tr>");
            var emptyCell = $("<td>").attr("colspan", "8").text("검색 결과가 없습니다.");
            emptyRow.append(emptyCell);
            tableBody.append(emptyRow);
        } else {
            reviews.forEach(function(review) {
                var row = $("<tr>").attr("data-review-no", review.rv_no);
                row.append($("<td>").text(review.rv_no));
                row.append($("<td>").text(review.u_id));
                row.append($("<td>").text(review.p_name));
                row.append($("<td>").text(review.rv_score));
                row.append($("<td>").text(review.rv_txt));
                row.append($("<td>").text(review.rv_reg_date));
                row.append($("<td>").text(review.rv_mod_date));
                row.append($("<td>").html("<button class='delete_btn' data-review-no='" + review.rv_no + "'>삭제</button>"));
                tableBody.append(row);
            });
        }
    }
    
    // 페이지네이션 업데이트 함수
    function updatePagination(page, size, userId, performanceName) {
        $.ajax({
            type: "GET",
            url: "/admin/review_count",
            data: { 
                review_u_id: userId, 
                review_p_name: performanceName
            },
            success: function(totalCount) {
                var totalPages = Math.ceil(totalCount / size);
                var paginationHtml = "";
                
                if (page > 1) {
                    paginationHtml += "<a href='#' data-page='" + (page - 1) + "'>&lt;</a>";
                }
                
                for (var i = 1; i <= totalPages; i++) {
                    var activeClass = (i === page) ? "active" : "";
                    paginationHtml += "<a href='#' class='" + activeClass + "' data-page='" + i + "'>" + i + "</a>";
                }
                
                if (page < totalPages) {
                    paginationHtml += "<a href='#' data-page='" + (page + 1) + "'>&gt;</a>";
                }
                
                $(".pagination").html(paginationHtml);
            },
            error: function() {
                alert("페이지네이션 업데이트 중 오류가 발생했습니다.");
            }
        });
    }
});