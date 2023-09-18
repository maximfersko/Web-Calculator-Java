let chartInstance = null;

plotButton.addEventListener("click", async function () {
    if (chartInstance) {
        chartInstance.destroy();
    }

    const expression = document.getElementById("graphExpression").value;
    const xStart = parseFloat(document.getElementById("xStart").value);
    const xEnd = parseFloat(document.getElementById("xEnd").value);

    const url = `/calculateGraph?expression=${expression}&xStart=${xStart}&xEnd=${xEnd}`;

    try {
        console.log("Before fetch");

        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        console.log("After fetch");

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();

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
                },
                plugins: {
                    zoom: {
                        pan: {
                            enabled: true,
                            mode: "xy"
                        },
                        zoom: {
                            wheel: {
                                enabled: true,
                            },
                            pinch: {
                                enabled: true
                            },
                            mode: "xy"
                        }
                    }
                }
            }
        };

        chartInstance = new Chart(ctx, graphConfig);
        chartInstance.update();
    } catch (error) {
        console.error("Error:", error);
    }
});
