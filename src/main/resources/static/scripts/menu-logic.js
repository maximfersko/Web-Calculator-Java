function showCalculator(calculatorName) {
    const calculatorContainers = document.querySelectorAll('.calculator');
    const calculatorWrapper = document.querySelector('.calculator-wrapper');

    calculatorContainers.forEach(container => {
        if (container.id === calculatorName + 'Calculator') {
            container.style.display = 'block';
        } else {
            container.style.display = 'none';
        }
    });

    setTimeout(() => {
        calculatorWrapper.classList.add('animate-slide-up');
    }, 50);

    calculatorWrapper.addEventListener('animationend', () => {
        calculatorWrapper.classList.remove('animate-slide-up');
    });
}