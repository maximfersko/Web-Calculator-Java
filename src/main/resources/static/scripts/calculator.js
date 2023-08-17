shareBtn.addEventListener("click", () => {
    shareBtn.classList.toggle("open");
});

this.classList.add("active");

function handleButtonClick(value) {
    const expressionLabel = document.getElementById(
        'expressionLabel');
    const currentExpression = expressionLabel.innerText;

    if (value === 'x') {
        expressionLabel.innerText += value;
    } else if (value.length === 1 && /^[a-zA-Z]+$/.test(value) &&
        currentExpression.indexOf('x') === -1) {
        expressionLabel.innerText += value + '(';
    } else if (currentExpression.includes("Error")) {
        clearInput();
    } else {
        expressionLabel.innerText += value;
    }
}

function calculateWithX() {
    let expressionLabel = document.getElementById(
        'expressionLabel');
    let expression = expressionLabel.innerText.trim();

    if (expression.includes('x')) {
        showModal();
    } else {
        evaluateExpression();
    }
}


function setXValue() {
    let xInput = document.getElementById("xInput");
    let expressionLabel = document.getElementById(
        "expressionLabel");
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
    let expression = expressionLabel.innerText.trim();

    if (expression === '') {
        expressionLabel.innerText = 'Error';
        return;
    }

    fetch('/calculate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                expression
            }),
        })
        .then((response) => response.json())
        .then((data) => {
            let result;
            if (data.hasOwnProperty('result')) {
                result = data.result;
                expressionLabel.innerText = result;
            } else {
                result = 'Error';
                expressionLabel.innerText = result;
            }
        })
        .catch((error) => {
            expressionLabel.innerText = 'Error';
        });
}