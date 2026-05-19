function show(elementId, data) {
    document.getElementById(elementId).textContent = JSON.stringify(data, null, 2);
}

async function saveUsername() {
    const username = document.getElementById("usernameInput").value;

    const response = await fetch(`/api/demo/username?username=${encodeURIComponent(username)}`, {
        method: "POST"
    });

    show("usernameResult", await response.json());
}

async function getUsername() {
    const response = await fetch("/api/demo/username");
    show("usernameResult", await response.json());
}

async function deleteUsername() {
    const response = await fetch("/api/demo/username", {
        method: "DELETE"
    });

    show("usernameResult", await response.json());
}

async function saveCode() {
    const code = document.getElementById("codeInput").value;
    const seconds = document.getElementById("ttlInput").value;

    const response = await fetch(`/api/demo/code?code=${encodeURIComponent(code)}&seconds=${seconds}`, {
        method: "POST"
    });

    show("codeResult", await response.json());
}

async function getCode() {
    const response = await fetch("/api/demo/code");
    show("codeResult", await response.json());
}

async function incrementCounter() {
    const response = await fetch("/api/demo/counter/increment", {
        method: "POST"
    });

    const data = await response.json();

    document.getElementById("counterValue").textContent = data.value;
    show("counterResult", data);
}

async function resetCounter() {
    const response = await fetch("/api/demo/counter/reset", {
        method: "POST"
    });

    const data = await response.json();

    document.getElementById("counterValue").textContent = 0;
    show("counterResult", data);
}

async function updateCourier() {
    const status = document.getElementById("courierStatus").value;

    const response = await fetch(`/api/demo/courier/status?status=${status}`, {
        method: "POST"
    });

    show("courierResult", await response.json());
}

async function getCourier() {
    const response = await fetch("/api/demo/courier");
    show("courierResult", await response.json());
}