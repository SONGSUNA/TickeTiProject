let reservationNumber;

function ticket_cancel(button) {
  const row = button.closest('tr');
  const reservationNumberCell = row.querySelector('td[id^="reservation-number-"]');
  reservationNumber = reservationNumberCell.textContent;

  console.log('예약 번호:', reservationNumber);
  document.querySelector('.modal_wrap').style.display = 'block';
}

function cancel_confirm() {
	console.log("cancel_confirm()");
	
	location.href="/reservation/cancel_confirm?r_no=" + reservationNumber;
}

function cancel_modal_close() {
	document.querySelector('.modal_wrap').style.display = 'none';
}