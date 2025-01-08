let value_X = 0;
let value_Y = 0;
let value_R = 2;
const CANVAS = document.getElementById("myCanvas");
const CTX = CANVAS.getContext("2d");
CANVAS.addEventListener("click", (event) => handleImageClick(CANVAS, event))
draw();

function setValueX(value) {
    value_X = value;
}

function setValueY(value) {
    value_Y = value;
}

function setValueR(value) {
    value_R = value;
    draw();
}

function draw() {
    CTX.clearRect(0, 0, CANVAS.width, CANVAS.height);
    CTX.scale(1, 1);
    CTX.beginPath();
    CTX.fillStyle = "blue";
    CTX.moveTo(CANVAS.width / 2, CANVAS.height / 2);
    CTX.arc(CANVAS.width / 2, CANVAS.height / 2, 30 * value_R, 0.5 * Math.PI, Math.PI);
    CTX.fill();
    CTX.closePath();

    CTX.fillRect(CANVAS.width / 2, CANVAS.height / 2, -15 * value_R, -30 * value_R);

    CTX.moveTo(CANVAS.width / 2, CANVAS.height / 2);
    CTX.lineTo(CANVAS.width / 2, CANVAS.height / 2 + 30 * value_R);
    CTX.lineTo(CANVAS.width / 2 + 30 * value_R, CANVAS.height / 2);
    CTX.fill();

    CTX.fillStyle = "black";
    CTX.beginPath();
    CTX.moveTo(0, CANVAS.height / 2);
    CTX.lineTo(CANVAS.width, CANVAS.height / 2);
    CTX.closePath();
    CTX.stroke();

    CTX.beginPath();
    CTX.moveTo(CANVAS.width, CANVAS.height / 2);
    CTX.lineTo(CANVAS.width - 10, CANVAS.height / 2 - 5);
    CTX.lineTo(CANVAS.width - 10, CANVAS.height / 2 + 5);
    CTX.fillText('x', CANVAS.width - 15, CANVAS.height / 2 + 15);
    CTX.closePath();
    CTX.fill();

    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2 - 15 * value_R, CANVAS.height / 2 + 5);
    CTX.lineTo(CANVAS.width / 2 - 15 * value_R, CANVAS.height / 2 - 5);
    CTX.closePath();
    CTX.stroke();
    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2 - 30 * value_R, CANVAS.height / 2 + 5);
    CTX.lineTo(CANVAS.width / 2 - 30 * value_R, CANVAS.height / 2 - 5);
    CTX.closePath();
    CTX.stroke();
    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2 + 15 * value_R, CANVAS.height / 2 + 5);
    CTX.lineTo(CANVAS.width / 2 + 15 * value_R, CANVAS.height / 2 - 5);
    CTX.closePath();
    CTX.stroke();
    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2 + 30 * value_R, CANVAS.height / 2 + 5);
    CTX.lineTo(CANVAS.width / 2 + 30 * value_R, CANVAS.height / 2 - 5);
    CTX.closePath();
    CTX.stroke();

    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2, 0);
    CTX.lineTo(CANVAS.width / 2, CANVAS.height);
    CTX.closePath();
    CTX.stroke();

    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2, 0);
    CTX.lineTo(CANVAS.width / 2 - 5, 10);
    CTX.lineTo(CANVAS.width / 2 + 5, 10);
    CTX.fillText('y', CANVAS.width / 2 + 15, 10);
    CTX.closePath();
    CTX.fill();

    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2 + 5, CANVAS.height / 2 - 30 * value_R);
    CTX.lineTo(CANVAS.width / 2 - 5, CANVAS.height / 2 - 30 * value_R);
    CTX.closePath();
    CTX.stroke();
    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2 + 5, CANVAS.height / 2 - 15 * value_R);
    CTX.lineTo(CANVAS.width / 2 - 5, CANVAS.height / 2 - 15 * value_R);
    CTX.closePath();
    CTX.stroke();
    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2 + 5, CANVAS.height / 2 + 15 * value_R);
    CTX.lineTo(CANVAS.width / 2 - 5, CANVAS.height / 2 + 15 * value_R);
    CTX.closePath();
    CTX.stroke();
    CTX.beginPath();
    CTX.moveTo(CANVAS.width / 2 + 5, CANVAS.height / 2 + 30 * value_R);
    CTX.lineTo(CANVAS.width / 2 - 5, CANVAS.height / 2 + 30 * value_R);
    CTX.closePath();
    CTX.stroke();
    if (value_R > 2) {
        CTX.fillText("-R/2", CANVAS.width / 2 - 15 * value_R, CANVAS.height / 2 + 10);
        CTX.fillText("-R", CANVAS.width / 2 - 30 * value_R, CANVAS.height / 2 + 10);
        CTX.fillText("R/2", CANVAS.width / 2 + 15 * value_R, CANVAS.height / 2 + 10);
        CTX.fillText("R", CANVAS.width / 2 + 30 * value_R, CANVAS.height / 2 + 10);
        CTX.fillText("-R", CANVAS.width / 2 - 25, CANVAS.height / 2 + 30 * value_R);
        CTX.fillText("-R/2", CANVAS.width / 2 - 25, CANVAS.height / 2 + 15 * value_R);
        CTX.fillText("R", CANVAS.width / 2 - 25, CANVAS.height / 2 - 30 * value_R);
        CTX.fillText("R/2", CANVAS.width / 2 - 25, CANVAS.height / 2 - 15 * value_R);
    }
}

function validateX() {
    return value_X !== undefined && value_X !== null && !isNaN(value_X) && value_X <= 5 && value_X >= -5;
}

function validateY() {
    const yRegexp = /^-?[0-9](\.\d{0,4})?$/;
    if (yRegexp.test(value_Y)) {
        return value_Y >= -3 && value_Y <= 5;
    } else {
        return false;
    }
}

function validateR() {
    return value_R !== undefined && value_R !== null && !isNaN(value_R) && value_R <= 5 && value_R >= 2;
}

function handleImageClick(canvas, event) {
    let area = canvas.getBoundingClientRect();
    let rawX = event.clientX - area.left - canvas.width / 2;
    let rawY = canvas.height / 2 - (event.clientY - area.top);
    value_X = (rawX / (canvas.width / 2) * value_R * 1.75);
    value_Y = (rawY / (canvas.height / 2) * value_R * 1.75);
    drawPoint(value_X, value_Y, value_R)
}

function checkPoint(x, y, r) {
    if (x <= 0 && y >= 0) {
        return checkRectangle(x, y, r);
    } else if (x >= 0 && y <= 0) {
        return checkTriangle(x, y, r);
    } else if (x <= 0 && y <= 0) {
        return checkCircle(x, y, r);
    } else {
        return false;
    }
}

function checkRectangle(x, y, r) {
    return x >= (-r / 2) && y <= r;
}

function checkCircle(x, y, r) {
    return x ** 2 + y ** 2 <= (-r) ** 2;
}

function checkTriangle(x, y, r) {
    return x <= r && y >= -r && x - r >= y;
}


function drawPoint(x, y, r) {
    value_X = x;
    value_Y = y;
    value_R = r;
    if (validateX() && validateY() && validateR()) {
        const scale = 30 * value_R;
        const center_X = CANVAS.width / 2;
        const center_Y = CANVAS.height / 2;
        const dot_X = center_X + (value_X / (value_R * 1.75)) * scale;
        const dot_Y = center_Y - (value_Y / (value_R * 1.75)) * scale;
        if (checkPoint(value_X, value_Y, value_R)) {
            CTX.fillStyle = "green";
        } else {
            CTX.fillStyle = "red";
        }
        CTX.beginPath();
        CTX.arc(dot_X, dot_Y, 3, 0, 2 * Math.PI);
        CTX.fill();
        CTX.closePath();
    } else {
        document.getElementById("result-text").innerText = "Some of parameters(X, Y, R) are invalid." +
            "\nMake sure that input data is correct and try again.";
        document.getElementById("result-text").classList.add("errorStub");
        document.getElementById("result-text").style.display = "flex";
        setTimeout(() => {
            document.getElementById("result-text").style.display = "none";
            document.getElementById("result-text").classList
                .remove(...document.getElementById("result-text").classList);
        }, 1000);
    }
}