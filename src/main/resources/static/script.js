const toggleButtons = document.querySelectorAll('.toggle-button');
toggleButtons.forEach((button, index) => {
    button.addEventListener('click', () => {
        const detailsDiv = button.parentElement.querySelector('.paper-details');
        if (detailsDiv.style.display === 'none') {
            detailsDiv.style.display = 'block';
            button.textContent = '收起';
        } else {
            detailsDiv.style.display = 'none';
            button.textContent = '展开';
        }
    });
});