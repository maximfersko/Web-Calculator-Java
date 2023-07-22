function handleButtonClick(value) {
    const expressionLabel = document.getElementById('expressionLabel');
    const currentExpression = expressionLabel.innerText;

    if (value === 'x') {
        expressionLabel.innerText += value;
    } else if (value.length === 1 && /^[a-zA-Z]+$/.test(value) && currentExpression.indexOf('x') === -1) {
        expressionLabel.innerText += value + '(';
    } else {
        expressionLabel.innerText += value;
    }
}

function calculateWithX() {
    let expressionLabel = document.getElementById('expressionLabel');
    let expression = expressionLabel.innerText.trim();

    if (expression.includes('x')) {
        showModal();
    } else {
        evaluateExpression();
    }
}

function setXValue() {
    let xInput = document.getElementById("xInput");
    let expressionLabel = document.getElementById("expressionLabel");
    let expression = expressionLabel.innerText;
    let newValue = xInput.value;

    if (newValue === "" || isNaN(newValue)) {
        newValue = "0";
    }

    expression = expression.replace(/x/g, newValue);
    expressionLabel.innerText = expression;
    closeModal();

    evaluateExpression();
}

function evaluateExpression() {
    let expressionLabel = document.getElementById("expressionLabel");
    let expression = expressionLabel.innerText;

    try {
        let result = eval(expression);
        expressionLabel.innerText = result;
    } catch (error) {
        expressionLabel.innerText = "Error";
    }
}

function showModal() {
    let modal = document.getElementById("modal");
    modal.style.display = "block";
}

function closeModal() {
    let modal = document.getElementById("modal");
    modal.style.display = "none";
}

function clearInput() {
    document.getElementById('expressionLabel').innerText = "";
    document.getElementById('resultLabel').innerText = "";
}