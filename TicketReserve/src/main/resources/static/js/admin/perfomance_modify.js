// 메뉴 선택 -----------------------------------------------------------------------------------------------------------
function allPerfomance() {
	location.href = "/admin/performance_modify";
}

function searchPerfomance() {
	$('.list_menu .all').css('background-color', '#fff');
	$('.list_menu .all').css('color', '#000');
	$('.all_table').css('display', 'none');
	
	$('.list_menu .yet').css('background-color', '#fff');
	$('.list_menu .yet').css('color', '#000');
	$('.yet_table').css('display', 'none');
	
	$('.list_menu .search').css('background-color', '#265073');
	$('.list_menu .search').css('color', '#fff');
	$('.search_table').css('display', 'block');
}

// 공연 검색 -----------------------------------------------------------------------------------------------------------
function search_pf() {
	console.log("search_pf()");
	
	let p_name = $('input[name="search_p_name"]').val();
	
	$.ajax({
	    url: '/admin/getPerfomanceByName',
	    type: 'GET',
	    data: {"p_name": p_name},
	    success: function (data) {
	        if (data != "") {
	            let html = '';
	            
	            $.each(data, function(index, perfomance) {
	                html += '<tr>';
	                html += '<td>' + perfomance.p_id + '</td>';
	                html += '<td>' + perfomance.p_name + '</td>';
	                html += '<td>' + perfomance.p_start_date + '</td>';
	                html += '<td>' + perfomance.p_end_date + '</td>';
	                html += '<td>' + perfomance.p_theater + '</td>';
	                html += '<td>' + perfomance.p_grade + '</td>';
	                html += '<td>' + perfomance.p_category + '</td>';
	                html += '<td>' + perfomance.p_max_reserve + '</td>';
	                html += '<td>' + perfomance.p_now_reserve + '</td>';
	                html += '<td>' + perfomance.p_host + '</td>';
	                html += '<td><button onclick="modifyForm(\'' + perfomance.p_id + '\')">상세</button></td>';
	                html += '</tr>';
	            });
	            
	            $('.search_table table tbody').html(html);
			}
			else {
				let html = '<tr><td colspan="11">검색 결과가 존재하지 않습니다.</td></tr>';
            	$('.search_table table tbody').html(html);
			}
	    },
	    	error: function (xhr, status, error) {
		        console.log("AJAX 요청 실패: " + status);
		        console.log("HTTP 상태 코드: " + xhr.status);
		        console.log("오류 내용: " + error);
		    }
	});
}

// 공연 상세 / 수정 =====================================================================================================================
function modifyForm (p_id) {
	console.log("modifyForm()");
	
	$.ajax({
	    url: '/admin/getPerformancetInfo',
	    type: 'POST',
	    data: {"p_id": p_id},
	    success: function (data) {
	        if (data) {
				
	            $('input[name="p_id"]').val(data.p_id);
	            $('input[name="p_id_show"]').val(data.p_id);
	            $('input[name="p_name"]').val(data.p_name);
	            $('input[name="p_start_date"]').val(data.p_start_date.replace(/\./g, '-'));
	            $('input[name="p_end_date"]').val(data.p_end_date.replace(/\./g, '-'));
	            $('select[name="p_grade"] option').prop('selected', false);
				$('select[name="p_grade"] option').filter(function() {
				  return $(this).text() === data.p_grade}).prop('selected', true);
	            $('input[name="p_theater"]').val(data.p_theater);
				$('input[name="current_p_place"]').val(data.p_place.replace(/\//g, ''));
				$('input[name="p_latitude"]').val(data.p_latitude);
				$('input[name="p_lognitude"]').val(data.p_lognitude);
				
				$('#thumbPreview').attr('src', data.p_thum).show();
	            
	            $('select[name="p_category"] option').prop('selected', false);
				$('select[name="p_category"] option').filter(function() {
				  return $(this).text() === data.p_category}).prop('selected', true);
	            $('input[name="p_max_reserve"]').val(data.p_max_reserve);
	            $('input[name="p_running_time"]').val(data.p_running_time);
	            $('input[name="p_characters"]').val(data.p_characters);
	            
	            let url = detailImg(data.p_detail_img);
	            console.log(url);
				$('#detailImgPreview').attr('src', url).show();
	            
	            $('textarea[name="p_agency_info"]').val(data.p_agency_info);
	            $('textarea[name="p_host"]').val(data.p_host);
	            $('textarea[name="p_inquiry"]').val(data.p_inquiry);
	            $('textarea[name="p_detail_cautions"]').val(data.p_detail_cautions);
	            
	            $('.modal_wrap').css("display", "block");
			}
	    },
	    	error: function (xhr, status, error) {
		        console.log("AJAX 요청 실패: " + status);
		        console.log("HTTP 상태 코드: " + xhr.status);
		        console.log("오류 내용: " + error);
		    }
	});
}

function perfomanceModify() {
	console.log("perfomanceModify()")
	
	let form = document.getElementsByName("perfomance_modify_form")[0];
	
	if (form.postcode.value === '' ||
    			form.address.value === '') {
					
      	form.p_place.value = form.current_p_place.value;
      	
      	console.log(form.p_place.value);
    }
	
	if (form.p_name.value === '') {
	    alert('공연명을 입력해주세요.');
	    form.p_name.focus();
	    
    } else if (form.p_start_date.value === '') {
        alert('공연 시작 날짜를 입력해주세요.');
        form.p_start_date.focus();
        
    } else if (form.p_end_date.value === '') {
        alert('공연 종료 날짜를 입력해주세요.');
        form.p_end_date.focus();
        
    } else if (form.p_grade.value === 'none') {
        alert('연령 등급을 선택해주세요.');
        form.p_grade.focus();
        
    } else if (form.p_theater.value === '') {
        alert('공연 시설명을 입력해주세요.');
        form.p_theater.focus();
        
    }  else if (form.p_category.value === 'none') {
        alert('카테고리를 선택해주세요.');
        form.p_category.focus();
        
    } else if (form.p_max_reserve.value === '') {
        alert('좌석 수를 입력해주세요.');
        form.p_max_reserve.focus();
        
    } else if (form.p_running_time.value === '') {
        alert('관람 시간을 입력해주세요.');
        form.p_running_time.focus();
        
    } else if (form.p_characters.value === '') {
        alert('출연진을 입력해주세요.');
        form.p_characters.focus();
        
    } else if (form.p_agency_info.value === '') {
        alert('기획사 정보를 입력해주세요.');
        form.p_agency_info.focus();
        
    } else if (form.p_host.value === '') {
        alert('주최를 입력해주세요.');
        form.p_host.focus();
        
    } else if (form.p_inquiry.value === '') {
        alert('상담 정보를 입력해주세요.');
        form.p_inquiry.focus();
    
    } else {
        form.submit();
    }
}

// 모달 종료 ------------------------------------------------------------------------------------------------------------------------
function modify_close() {
	$('.modal_wrap').css("display", "none");
}


// 디테일 이미지 주소 수정 -----------------------------------------------------------------------------------------------------------
function detailImg(img) {
	// "StyUrl{url='["와 "]'}" 부분을 제거하고, ", "를 기준으로 URL을 나눕니다.
	let urls = img.substring(13, img.length -2).split(', ');
	let url = urls[0].replace('[', '').replace("]'", "").replace(']', '');
	
	return url;
}

// 이미지 미리보기 ------------------------------------------------------------------------------------------------------------------------

$(document).ready(function () {
    // 썸네일 이미지 미리보기
    $('#thum_img').change(function () {
        let reader = new FileReader();
        reader.onload = function (e) {
            $('#thumbPreview').attr('src', e.target.result).show();
        }
        reader.readAsDataURL(this.files[0]);
    });

    // 상세페이지 이미지 미리보기
    $('#detail_img').change(function () {
        let reader = new FileReader();
        reader.onload = function (e) {
            $('#detailImgPreview').attr('src', e.target.result).show();
        }
        reader.readAsDataURL(this.files[0]);
    });
});


