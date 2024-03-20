function ticket_cancel(button) {
    const row = button.closest('tr');
    const ticketNumberCell = row.querySelector('td[id^="ticket-number-"]');
    const ticketNumber = ticketNumberCell.textContent;
    // ticketNumber 값을 사용하여 원하는 로직 수행
    console.log('티켓 번호:', ticketNumber);
}