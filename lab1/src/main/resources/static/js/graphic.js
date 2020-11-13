let labels = [];
let values = [];

$("#graphicButtonId").on("click", function () {
    getStatistics();
    renderChart();
});

function getStatistics() {
    $.get({
        url: "/vocabulary/statistic",
        async: false,
        success: function (data) {
            getDataForChart(data);
        }
    });
}

function getDataForChart(stat) {
    labels = [];
    values = [];
    for (let i = 0; i < stat.length; i++) {
        labels.push(stat[i].frequency);
        values.push(stat[i].rank);
    }
}

function renderChart() {
    new Chart(document.getElementById("graphic"), {
        type: 'line',
        data: {
            labels: values,
            datasets: [{
                label: "Zipfs Law",
                borderColor: 'rgb(255, 99, 132)',
                data: labels
            }]
        }
    });
}