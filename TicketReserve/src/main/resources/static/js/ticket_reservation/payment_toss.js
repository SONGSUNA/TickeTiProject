
    var IMP = window.IMP; 
    IMP.init("imp16540835"); 
  
    var today = new Date();   
    var hours = today.getHours(); // 시
    var minutes = today.getMinutes();  // 분
    var seconds = today.getSeconds();  // 초
    var milliseconds = today.getMilliseconds();
    var makeMerchantUid = hours +  minutes + seconds + milliseconds;
    

    function tossPay() {
        IMP.request_pay({
            pg : 'tosspay',
            pay_method : 'card',
            merchant_uid: makeMerchantUid, //상점에서 생성한 고유 주문번호
            name : '주문명:' + makeMerchantUid,   //필수 파라미터 입니다.
            amount : payPrice,
            buyer_email : `${user_email}`,
            buyer_name : `${username}`,
        }, function (rsp) { // callback
            if (rsp.success) {
                console.log(rsp);
                
                
                insertReserve();

                alert('결제가 완료되었습니다.');
                window.close();
            } else {
                console.log(rsp);
            }
        });
    }
 