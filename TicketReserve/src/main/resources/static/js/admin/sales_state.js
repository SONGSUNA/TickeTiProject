$(document).ready(function () {
    $('#sales_state_search_btn').click(function search_btn_click() {
        let stDate = $('input[name="stDate"]').val();
        let edDate = $('input[name="edDate"]').val();
        
        let concert = $('input[value="concert"]').prop('checked') ? 'concert' : '';
        let musical = $('input[value="musical"]').prop('checked') ? 'musical' : '';
        let theater = $('input[value="theater"]').prop('checked') ? 'theater' : '';
        let classic = $('input[value="classic"]').prop('checked') ? 'classic' : '';
        let koreanMusic = $('input[value="koreanMusic"]').prop('checked') ? 'koreanMusic' : '';
	
		if(stDate == '' || edDate == '') {
			alert("시작일, 종료일을 입력 해주세요");
			return;
		}
		
		$.ajax({
            url: '/admin/sales_state_search',
            type: 'GET',
            data: {
                "stDate": stDate,
                "edDate": edDate
            },
            success: function (data) {
                 if (data) {
		            let keys = Object.keys(data);
		            let labels = [];
		            let daySalesValues = [];
		            
		            keys.forEach(function(key) {
		                let value = data[key];
		                labels.push(key); // 날짜를 라벨로 사용
		                daySalesValues.push(value.daySales); // 각 날짜의 daySales 값을 배열에 추가
		            });
		            
		            drawChart(labels, daySalesValues);
		        }
            },
            error: function (xhr, status, error) {
			        console.log("AJAX 요청 실패: " + status);
			        console.log("HTTP 상태 코드: " + xhr.status);
			        console.log("오류 내용: " + error);
		    }
        });
    });
});

function drawChart(labels, daySalesValues) {
    var ctx = document.getElementById('myChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels, // X 축 라벨
            datasets: [{
                label: 'Day Sales',
                data: daySalesValues, // Y 축 데이터
                backgroundColor: [
					'rgba(255, 99, 132, 0.2)',
					'rgba(54, 162, 235, 0.2)',
					'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)'], 
                borderColor: [
					'rgba(255, 99, 132, 1)',
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)',
					'rgba(75, 192, 192, 1)'], // 차트 선 색상
                borderWidth: 0.5
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}