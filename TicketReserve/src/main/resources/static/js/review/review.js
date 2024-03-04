document.addEventListener('DOMContentLoaded', function() {
    var ratings = document.getElementsByName('rv_score');
    var displayRating = document.getElementById('display-rating');
    var reviewInput = document.getElementById('review-input');
    var reviewBtn = document.querySelector('.reviewBtn');

    for (var i = 0; i < ratings.length; i++) {
        ratings[i].addEventListener('change', function() {
            displayRating.innerText = this.value + '점';
        });
    }

    reviewInput.addEventListener('input', function() {
        var length = this.value.length;
        document.getElementById('current-length').innerText = length;
       
    });

    reviewBtn.addEventListener('click', function() {
        var length = reviewInput.value.length;
        
    });
   
    
});