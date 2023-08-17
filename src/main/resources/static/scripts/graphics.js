function plotGraph() {
    const expression = document.getElementById("graphExpression")
        .value;
    const xStart = parseFloat(document.getElementById("xStart")
    .value);
    const xEnd = parseFloat(document.getElementById("xEnd").value);
    const stepInput = document.getElementById("step");
    const step = parseFloat(stepInput.value);

    if (isNaN(step)) {
        alert("Please enter a valid numeric value for Step.");
        stepInput.focus();
        return;
    }

    const requestData = {
        expression: expression,
        data: [xStart, xEnd, step]
    };

    fetch("/calculateGraph", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(requestData),
        })
        .then((response) => response.json())
        .then((data) => {
            const xValues = data.xValues;
            const yValues = data.yValues;
            draw(xValues, yValues, document.getElementById(
                "graphContainer"));
        })
        .catch((error) => console.error("Error:", error));
}

function draw(xValues, yValues, graphContainer) {
    const ctx = graphContainer.getContext("2d");

    new Chart(ctx, {
        type: "line",
        data: {
            labels: xValues,
            datasets: [{
                label: "Graph",
                borderColor: "rgba(75, 192, 192, 1)",
                backgroundColor: "rgba(75, 192, 192, 0.2)",
                data: yValues,
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }],
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    type: "linear",
                    position: "bottom",
                },
                y: {
                    type: "linear",
                    position: "left",
                    min: -100,
                    max: 100,
                },
            },
            plugins: {
                zoom: {
                    zoom: {
                        wheel: {
                            enabled: true,
                        },
                        pinch: {
                            enabled: true,
                        },
                        mode: "xy",
                    },
                    pan: {
                        enabled: true,
                        mode: "xy",
                    },
                },
            },
        },
    });
}