const socket = new WebSocket("ws://localhost:8080/CacaNiquel/server");

const emojis = ['😀', '😃', '😄', '😁', '😆', '😅', '😂', '🤣', '😊', '😇', '😉', '😌', '😍', '😘', '😗', '😙', '👺', '😋'];

socket.onmessage = (event) => {
    if (event.data.startsWith("Players")) {
        modifyPlayerCounter(event.data.split(":")[1]);
        return;
    }

    const results = event.data.split(",");

    toggleButton(true);
    modifyPlayerCounter(results[4]);
    spin(emojis[results[0]], emojis[results[1]], emojis[results[2]], results[3], results[5]);
}

function bet (){
    let amount = document.getElementById("amount").innerHTML;
    let betValue = document.getElementById("betValue").innerHTML;

    if (amount === "0.0") {
        alert("Saldo insuficiente para apostar, recarregue a página para continuar!");
        return;
    }

    socket.send(betValue);
}

function spin (icon1, icon2, icon3, amount, status) {
    const wrapperSpin = document.querySelector(".content-spin");

    for (let i = 0; i < emojis.length; i++){
        appendIcon(emojis[i]);
    }

    appendIcon(icon1);
    appendIcon(icon2);
    appendIcon(icon3);

    wrapperSpin.classList.add("spin");

    setTimeout(() => {
        isWin(status);
        toggleButton(false);
        resetWrapperSpin(icon1, icon2, icon3);
        document.getElementById("amount").innerHTML = amount;
    }, 2000);
}

function appendIcon(icon) {
    const wrapperSpin = document.querySelector(".content-spin");
    const iconElem = document.createElement("div");

    iconElem.classList.add("content");
    iconElem.innerHTML = icon;

    wrapperSpin.append(iconElem);
}

function resetWrapperSpin(icon1, icon2, icon3){
    const wrapperSpin = document.querySelector(".content-spin");
    wrapperSpin.classList.remove("spin");
    wrapperSpin.innerHTML = "";
    appendIcon(icon1);
    appendIcon(icon2);
    appendIcon(icon3);
}

function toggleButton(state) {
    const button = document.querySelector(".button");
    button.disabled = state;
}

function modifyPlayerCounter (qtdPlayers) {
    document.getElementById("players-online").innerHTML = qtdPlayers;
}

function isWin (status) {
    if (status === "Ganhou") {
        const machine = document.querySelector(".machine");
        machine.classList.add("twinkle-twinkle-good");

        setTimeout(() => {
            machine.classList.remove("twinkle-twinkle-good");
        }, 1000);
    } else {
        const machine = document.querySelector(".machine");
        machine.classList.add("twinkle-twinkle-bad");

        setTimeout(() => {
            machine.classList.remove("twinkle-twinkle-bad");
        }, 1000);
    }
}