function handleButtonClick(value) {
    const expressionInput = document.getElementById('expressionInput');

    if (value.length === 1 && /^[a-zA-Z]+$/.test(value)) {
        expressionInput.value += value + '(';
    } else {
        expressionInput.value += value;
    }
}

