function calculateCredit() {
    const amour = parseFloat(document.getElementById('amour').value);
    const term = parseInt(document.getElementById('term').value);
    const rate = parseFloat(document.getElementById('rate').value);
    const calcType = document.querySelector(
        'input[name="calcType"]:checked').value;

    fetch('/calculateCredit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            amour,
            term,
            rate,
            calcType
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            if (calcType === 'annuity') {
                document.getElementById('monthlyPayment').value =
                    data.monthlyPayment;
            } else if (calcType === 'differentiated') {
                const minMonthlyPayment = data.minMonthlyPayment;
                const maxMonthlyPayment = data.maxMonthlyPayment;
                if (minMonthlyPayment !== undefined &&
                    maxMonthlyPayment !== undefined) {
                    document.getElementById('monthlyPayment')
                        .value = minMonthlyPayment + '-' +
                        maxMonthlyPayment;
                } else {
                    document.getElementById('monthlyPayment')
                        .value = 'Error';
                }
            }
            document.getElementById('totalInterest').value = data
                .overPayment;
            document.getElementById('totalPayment').value = data
                .totalPayment;
        })
        .catch((error) => {
            console.error('Error calculating credit:', error);
            document.getElementById('monthlyPayment').value =
                'Error';
            document.getElementById('totalInterest').value =
                'Error';
            document.getElementById('totalPayment').value =
                'Error';
        });
}