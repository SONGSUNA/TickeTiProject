$(document).ready(function(){
	$(".owl-carousel").owlCarousel({
		loop:true, // 무한 루프
        margin:0, // 아이템 간격
        nav:true, // 이전/다음 버튼 사용
        autoplay:true, // 자동 재생 활성화
        autoplayTimeout:3000, // 자동 재생 간격 (2초)
		responsive:{
			1700:{
				items:1 // 화면 크기가 0px 이상일 때 보일 아이템 수
			}
		}
	});
});