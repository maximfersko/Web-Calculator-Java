function showModal() {
    let modal = document.getElementById("modal");
    modal.style.display = "block";
}

function closeModal() {
    let modal = document.getElementById("modal");
    modal.style.display = "none";
}

function clearInput() {
    document.getElementById('expressionLabel').innerText = "";
    document.getElementById('resultLabel').innerText = "";
}