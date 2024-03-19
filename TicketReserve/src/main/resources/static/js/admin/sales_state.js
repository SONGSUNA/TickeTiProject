let myChart;

$(document).ready(function () {
    $('#sales_state_search_btn').click(function search_btn_click() {
        let stDate = $('input[name="stDate"]').val();
        let edDate = $('input[name="edDate"]').val();
        
        let concert = $('input[value="concert"]').prop('checked') ? 'concert' : '';
        let musical = $('input[value="musical"]').prop('checked') ? 'musical' : '';
        let theater = $('input[value="theater"]').prop('checked') ? 'theater' : '';
        let classic = $('input[value="classic"]').prop('checked') ? 'classic' : '';
        let koreanMusic = $('input[value="koreanMusic"]').prop('checked') ? 'koreanMusic' : '';
		
		if(concert === "")
			console.log("concert" + concert);
		
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
		            let daySalesConcert = [];
		            let daySalesMusical = [];
		            let daySalesTheater = [];
		            let daySalesClassic = [];
		            let daySalesKoreanMusic = [];
		            
		            keys.forEach(function(key) {
		                let value = data[key];
		                labels.push(key.substring(0, key.indexOf(' ')));
		                daySalesValues.push(value.daySales);
		                if(concert !=="")
    		                daySalesConcert.push(value.concertSales);
		                if(musical !=="")
		                	daySalesMusical.push(value.musicalSales);
		                if(theater !=="")
		                	daySalesTheater.push(value.theaterSales);
		                if(classic !=="")
		                	daySalesClassic.push(value.classicSales);
	                	if(koreanMusic !=="")
		               		daySalesKoreanMusic.push(value.koreanMusicSales);
		                
		            });
		            
		            drawChart(labels, daySalesValues, daySalesConcert, daySalesMusical, daySalesTheater, daySalesClassic, daySalesKoreanMusic);
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

function drawChart(labels, daySalesValues, daySalesConcert, daySalesMusical, daySalesTheater, daySalesClassic, daySalesKoreanMusic) {
    var ctx = document.getElementById('myChart').getContext('2d');
    
    if(myChart)
    	myChart.destroy();

    var backgroundColor = [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)'
    ];

    var borderColor = [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)'
    ];
    
    var datasets = [];

    // 전체 매출에서 각 장르별 매출 데이터를 뺀 값을 계산
    let totalMinusGenres = daySalesValues.slice();
    if (daySalesConcert.length > 0) {
        totalMinusGenres = totalMinusGenres.map((value, index) => value - daySalesConcert[index]);
    }
    if (daySalesMusical.length > 0) {
        totalMinusGenres = totalMinusGenres.map((value, index) => value - daySalesMusical[index]);
    }
    if (daySalesTheater.length > 0) {
        totalMinusGenres = totalMinusGenres.map((value, index) => value - daySalesTheater[index]);
    }
    if (daySalesClassic.length > 0) {
        totalMinusGenres = totalMinusGenres.map((value, index) => value - daySalesClassic[index]);
    }
    if (daySalesKoreanMusic.length > 0) {
        totalMinusGenres = totalMinusGenres.map((value, index) => value - daySalesKoreanMusic[index]);
    }


    // 각 장르별 매출 데이터셋 추가
    if (daySalesConcert.length > 0) {
        datasets.push({
            label: '콘서트',
            data: daySalesConcert,
            backgroundColor: backgroundColor[0],
            borderColor: borderColor[0],
            borderWidth: 0.5,
            stack: 'combined'
        });
    }

    if (daySalesMusical.length > 0) {
        datasets.push({
            label: '뮤지컬',
            data: daySalesMusical,
            backgroundColor: backgroundColor[1],
            borderColor: borderColor[1],
            borderWidth: 0.5,
            stack: 'combined'
        });
    }
    
    if (daySalesTheater.length > 0) {
        datasets.push({
            label: '연극',
            data: daySalesTheater,
            backgroundColor: backgroundColor[2],
            borderColor: borderColor[2],
            borderWidth: 0.5,
            stack: 'combined'
        });
    }
    
    if (daySalesClassic.length > 0) {
        datasets.push({
            label: '클래식',
            data: daySalesClassic,
            backgroundColor: backgroundColor[3],
            borderColor: borderColor[3],
            borderWidth: 0.5,
            stack: 'combined'
        });
    }
    
    if (daySalesKoreanMusic.length > 0) {
        datasets.push({
            label: '국악',
            data: daySalesKoreanMusic,
            backgroundColor: backgroundColor[4],
            borderColor: borderColor[4],
            borderWidth: 0.5 ,
            stack: 'combined'
        });
    }
    
    // 전체 매출에서 각 장르별 매출 데이터를 뺀 값을 데이터셋으로 추가
	datasets.push({
        label: '하루 매출',
        data: totalMinusGenres, // 그래프 값은 totalMinusGenres
        backgroundColor: 'rgba(128, 128, 128, 0.2)',
        borderColor: 'rgba(128, 128, 128, 1)',
        borderWidth: 0.5,
        stack: 'combined',
        datalabels: {
            display: 'auto',
            formatter: function(value, context) {
                return daySalesValues[context.dataIndex].toLocaleString();
            },
            align: 'top',
            anchor: 'end'
        }
    });
    
    myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: datasets
        },
        options: {
            scales: {
                xAxes: [{
                    stacked: true
                }],
                yAxes: [{
                    stacked: true,
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            plugins: {
                datalabels: {
                    color: 'black',
                    anchor: 'center',
                    align: 'center',
                    formatter: Math.round
                }
            }, 
            plugins: {
                datalabels: {
                    formatter: function(value, context) {
                        return value.toLocaleString(); // 세자리 마다 쉼표(,) 추가
                    }
                }
            },
            title: {
                display: true,
                text: '단위: 원', // 그래프 우측 상단에 "단위: 원" 텍스트 추가
                position: 'topRight'
            }
        },
        plugins: [ChartDataLabels]
    });
}