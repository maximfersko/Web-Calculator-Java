plotButton.addEventListener("click", async function () {
    const expression = document.getElementById("graphExpression").value;
    const xStart = parseFloat(document.getElementById("xStart").value);
    const xEnd = parseFloat(document.getElementById("xEnd").value);

    const requestData = {
        expression: expression,
        xStart: xStart,
        xEnd: xEnd
    };

    try {
        const response = await fetch("/calculateGraph", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();

        console.log("Received data:", data);

        const xValues = data.xValues;
        const yValues = data.yValues;

        const ctx = graphContainer.getContext("2d");

        const graphConfig = {
            type: "line",
            data: {
                labels: xValues,
                datasets: [{
                    label: "Graph",
                    data: yValues,
                    borderColor: "rgba(75, 192, 192, 1)",
                    fill: false
                }]
            },
            options: {
                scales: {
                    x: {
                        type: "linear",
                        position: "bottom"
                    },
                    y: {
                        beginAtZero: true
                    }
                }
            }
        };

        new Chart(ctx, graphConfig);
    } catch (error) {
        console.error("Error:", error);
    }
});
