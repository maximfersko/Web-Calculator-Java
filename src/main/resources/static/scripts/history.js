document.addEventListener("DOMContentLoaded", function() {
    const historyList = document.getElementById(
    "historyList");

    historyList.addEventListener("click", function(event) {
        const clickedItem = event.target;

        if (clickedItem.tagName === "LI") {
            const expression = clickedItem
            .textContent;
            setExpressionInput(expression);
        }
    });
});

function setExpressionInput(expression) {
    const expressionInput = document.getElementById(
    "expressionLabel");
    const parts = expression.split("=");
    if (parts.length > 0) {
        expressionInput.innerText = parts[0];
    }
}

function fetchHistory() {
    fetch('/history')
        .then((response) => response.json())
        .then((data) => {
            const historyList = document.getElementById(
                "historyList");
            historyList.innerHTML = "";
            data.forEach((item) => {
                const historyItem = document
                    .createElement("li");
                historyItem.textContent = item;
                historyList.appendChild(historyItem);
            });
        })
        .catch((error) => {
            console.error("Error fetching history:", error);
        });
}


function toggleHistory() {
    let historySidebar = document.getElementById("historySidebar");
    historySidebar.classList.toggle("open");
    if (historySidebar.classList.contains("open")) {
        fetchHistory();
    }
}

function clearHistory() {
    const historyList = document.getElementById("historyList");
    historyList.innerHTML = "";

    fetch('/clearHistory', {
            method: 'POST',
        })
        .then((response) => response.text())
        .then((data) => {
            console.log(data);
        })
        .catch((error) => {
            console.error('Error clearing history:', error);
        });
}

function handleHistoryItemClick(event) {
    const historyItems = document.querySelectorAll("#historyList li");
    historyItems.forEach(item => item.classList.remove("active"));
    event.target.classList.add("active");

    const expressionLabel = document.getElementById(
    "expressionLabel");
    expressionLabel.innerText = event.target.getAttribute(
        "data-expression");
}