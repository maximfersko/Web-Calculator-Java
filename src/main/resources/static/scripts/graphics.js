document.addEventListener("DOMContentLoaded", function() {
  const expressionInput = document.getElementById("graphExpression");
  const xStartInput = document.getElementById("xStart");
  const xEndInput = document.getElementById("xEnd");
  const plotButton = document.getElementById("plotButton");
  const graphContainer = document.getElementById("graphContainer");

  let currentChart = null;

  plotButton.addEventListener("click", function () {
    const expression = expressionInput.value;
    const xStart = parseFloat(xStartInput.value);
    const xEnd = parseFloat(xEndInput.value);

    fetch("/calculateGraph", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        expression: expression,
        xStart: xStart,
        xEnd: xEnd,
      }),
    })
    .then((response) => response.json())
    .then((graphData) => {
      const xValues = graphData.xValues;
      const yValues = graphData.yValues;
      plotGraphUsingChartJs(xValues, yValues, graphContainer);
    })
    .catch((error) => console.error("Error:", error));
  });

  function plotGraphUsingChartJs(xValues, yValues, container) {
    const ctx = container.getContext("2d");

    if (currentChart) {
      currentChart.destroy();
    }

    currentChart = new Chart(ctx, {
      type: "line",
      data: {
        labels: xValues,
        datasets: [
          {
            label: "Graph",
            borderColor: "rgba(75, 192, 192, 1)",
            backgroundColor: "rgba(75, 192, 192, 0.2)",
            data: yValues,
            fill: false,
            borderColor: "rgb(75, 192, 192)",
            tension: 0.1,
          },
        ],
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
});
