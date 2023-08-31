//let chartInstance = null;
//plotButton.addEventListener("click", async function () {
//    if (chartInstance) {
//        chartInstance.destroy();
//    }
//
//    const expression = document.getElementById("graphExpression").value;
//    const xStart = parseFloat(document.getElementById("xStart").value);
//    const xEnd = parseFloat(document.getElementById("xEnd").value);
//
//    const requestData = {
//        expression: expression,
//        xStart: xStart,
//        xEnd: xEnd
//    };
//
//    try {
//        console.log("Before fetch");
//
//            const response = await fetch("/calculateGraph", {
//                method: "POST",
//                headers: {
//                    "Content-Type": "application/json"
//                },
//                body: JSON.stringify(requestData)
//            });
//
//            console.log("After fetch");
//
//            if (!response.ok) {
//                throw new Error(`HTTP error! Status: ${response.status}`);
//            }
//
//        const data = await response.json();
//
//        const xValues = data.xValues;
//        const yValues = data.yValues;
//
//        const ctx = graphContainer.getContext("2d");
//
//        const graphConfig = {
//            type: "line",
//            data: {
//                labels: xValues,
//                datasets: [{
//                    label: "Graph",
//                    data: yValues,
//                    borderColor: "rgba(75, 192, 192, 1)",
//                    fill: false
//                }]
//            },
//            options: {
//                scales: {
//                    x: {
//                        type: "linear",
//                        position: "bottom"
//                    },
//                    y: {
//                        beginAtZero: true
//                    }
//                },
//                plugins: {
//                    zoom: {
//                        pan: {
//                            enabled: true,
//                            mode: "xy"
//                        },
//                        zoom: {
//                            wheel: {
//                                enabled: true,
//                            },
//                            pinch: {
//                                enabled: true
//                            },
//                            mode: "xy"
//                        }
//                    }
//                }
//            }
//        };
//
//        chartInstance = new Chart(ctx, graphConfig);
//        chartInstance.update();
//    } catch (error) {
//        console.error("Error:", error);
//    }
//});



// Получаем ссылки на HTML-элементы
var graphForm = document.getElementById("graphForm");
var plotButton = document.getElementById("plotButton");
var graphContainer = document.getElementById("graphContainer");

// Обработчик события нажатия на кнопку "Plot Graph"
plotButton.addEventListener("click", function() {
    // Получаем значения из полей формы
    var expression = document.getElementById("graphExpression").value;
    var xStart = parseFloat(document.getElementById("xStart").value);
    var xEnd = parseFloat(document.getElementById("xEnd").value);

    // Создаем объект с данными для отправки на сервер
    var requestBody = {
        expression: expression,
        xStart: xStart,
        xEnd: xEnd
    };

    // Отправляем AJAX-запрос на сервер
    fetch("/calculateGraph", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestBody)
    })
    .then(function(response) {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Error: " + response.status);
        }
    })
    .then(function(graphData) {
        // Получаем контекст для рисования на холсте
        var context = graphContainer.getContext("2d");

        // Очищаем холст
        context.clearRect(0, 0, graphContainer.width, graphContainer.height);

        // Рисуем точки графика
        context.beginPath();
        context.strokeStyle = "blue";
        context.lineWidth = 2;
        for (var i = 0; i < graphData.x.length; i++) {
            var x = graphData.x[i];
            var y = graphData.y[i];

            // Преобразуем координаты на оси X в пиксели относительно размеров холста
            var xPixel = (x - xStart) / (xEnd - xStart) * graphContainer.width;
            // Изменяем направление оси Y, чтобы положительное направление было вверх
            var yPixel = graphContainer.height - (y * graphContainer.height);

            if (i === 0) {
                context.moveTo(xPixel, yPixel);
            } else {
                context.lineTo(xPixel, yPixel);
            }
        }
        context.stroke();
    })
    .catch(function(error) {
        console.log(error);
    });
});
