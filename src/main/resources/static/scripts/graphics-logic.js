function calculateGraph() {
    console.log("calculateGraph() function is executing...");
    const expression = document.getElementById("graphExpression").value;
    const xStart = parseFloat(document.getElementById("xStart").value);
    const xEnd = parseFloat(document.getElementById("xEnd").value);
    const step = parseFloat(document.getElementById("step").value);
    console.log("Expression:", expression);
    console.log("X Start:", xStart);
    console.log("X End:", xEnd);
    console.log("Step:", step);
    fetch("/calculateGraph", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            expression: expression,
            xStart: xStart,
            xEnd: xEnd,
            step: step,
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            drawGraph(data);
        })
        .catch((error) => console.error("Error:", error));
}
  function plotGraphUsingChartJs(xValues, yValues, graphContainer) {
    const ctx = document.createElement("canvas").getContext("2d");
    graphContainer.appendChild(ctx);
  
    new Chart(ctx, {
      type: "line",
      data: {
        labels: xValues,
        datasets: [{
          label: "Graph",
          borderColor: "rgba(75, 192, 192, 1)",
          data: yValues,
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            type: "linear",
            position: "bottom",
          },
          y: {
            type: "linear",
            position: "left",
          }
        }
      }
    });
  }
  