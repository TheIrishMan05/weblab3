let value_X = 0;
let value_Y = 0;
let value_R = 2;
const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");
let points = JSON.parse(sessionStorage.getItem("points")) || [];
canvas.addEventListener("click", (() => {
    return function handleImageClick() {
        setValueR(document.getElementById("form:r-slider").value);
        const event = arguments[0];
        let area = canvas.getBoundingClientRect();

        let rawX = event.clientX - area.left - canvas.width / 2;
        let rawY = canvas.height / 2 - (event.clientY - area.top);

        value_X = (rawX / (canvas.width / 2) * value_R * 1.75);
        value_Y = (rawY / (canvas.height / 2) * value_R * 1.75);

        const dot_X = canvas.width / 2 + rawX;
        const dot_Y = canvas.height / 2 - rawY;

        if (checkPoint(value_X, value_Y, value_R)) {
            ctx.fillStyle = "green";
        } else {
            ctx.fillStyle = "red";
        }
        ctx.beginPath();
        ctx.arc(dot_X, dot_Y, 3, 0, 2 * Math.PI);
        ctx.fill();
        ctx.closePath();
        drawOldPoints(value_R);
        points.push({
            x: value_X,
            y: value_Y,
            r: value_R,
        });
        document.getElementById("formHidden:hiddenX").value = value_X;
        document.getElementById("formHidden:hiddenY").value = value_Y;
        document.getElementById("formHidden:submitHidden").click();
    };
})());
draw();


function setValueR(value) {
    value_R = value;
    draw();
}

function draw() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.scale(1, 1);
    ctx.beginPath();
    ctx.fillStyle = "blue";
    ctx.moveTo(canvas.width / 2, canvas.height / 2);
    ctx.arc(canvas.width / 2, canvas.height / 2, 30 * value_R, 0.5 * Math.PI, Math.PI);
    ctx.fill();
    ctx.closePath();

    ctx.fillRect(canvas.width / 2, canvas.height / 2, -15 * value_R, -30 * value_R);

    ctx.moveTo(canvas.width / 2, canvas.height / 2);
    ctx.lineTo(canvas.width / 2, canvas.height / 2 + 30 * value_R);
    ctx.lineTo(canvas.width / 2 + 30 * value_R, canvas.height / 2);
    ctx.fill();

    ctx.fillStyle = "black";
    ctx.beginPath();
    ctx.moveTo(0, canvas.height / 2);
    ctx.lineTo(canvas.width, canvas.height / 2);
    ctx.closePath();
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(canvas.width, canvas.height / 2);
    ctx.lineTo(canvas.width - 10, canvas.height / 2 - 5);
    ctx.lineTo(canvas.width - 10, canvas.height / 2 + 5);
    ctx.fillText('x', canvas.width - 15, canvas.height / 2 + 15);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(canvas.width / 2 - 15 * value_R, canvas.height / 2 + 5);
    ctx.lineTo(canvas.width / 2 - 15 * value_R, canvas.height / 2 - 5);
    ctx.closePath();
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(canvas.width / 2 - 30 * value_R, canvas.height / 2 + 5);
    ctx.lineTo(canvas.width / 2 - 30 * value_R, canvas.height / 2 - 5);
    ctx.closePath();
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(canvas.width / 2 + 15 * value_R, canvas.height / 2 + 5);
    ctx.lineTo(canvas.width / 2 + 15 * value_R, canvas.height / 2 - 5);
    ctx.closePath();
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(canvas.width / 2 + 30 * value_R, canvas.height / 2 + 5);
    ctx.lineTo(canvas.width / 2 + 30 * value_R, canvas.height / 2 - 5);
    ctx.closePath();
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(canvas.width / 2, 0);
    ctx.lineTo(canvas.width / 2, canvas.height);
    ctx.closePath();
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(canvas.width / 2, 0);
    ctx.lineTo(canvas.width / 2 - 5, 10);
    ctx.lineTo(canvas.width / 2 + 5, 10);
    ctx.fillText('y', canvas.width / 2 + 15, 10);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(canvas.width / 2 + 5, canvas.height / 2 - 30 * value_R);
    ctx.lineTo(canvas.width / 2 - 5, canvas.height / 2 - 30 * value_R);
    ctx.closePath();
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(canvas.width / 2 + 5, canvas.height / 2 - 15 * value_R);
    ctx.lineTo(canvas.width / 2 - 5, canvas.height / 2 - 15 * value_R);
    ctx.closePath();
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(canvas.width / 2 + 5, canvas.height / 2 + 15 * value_R);
    ctx.lineTo(canvas.width / 2 - 5, canvas.height / 2 + 15 * value_R);
    ctx.closePath();
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(canvas.width / 2 + 5, canvas.height / 2 + 30 * value_R);
    ctx.lineTo(canvas.width / 2 - 5, canvas.height / 2 + 30 * value_R);
    ctx.closePath();
    ctx.stroke();
    if (value_R > 2) {
        ctx.fillText("-R/2", canvas.width / 2 - 15 * value_R, canvas.height / 2 + 10);
        ctx.fillText("-R", canvas.width / 2 - 30 * value_R, canvas.height / 2 + 10);
        ctx.fillText("R/2", canvas.width / 2 + 15 * value_R, canvas.height / 2 + 10);
        ctx.fillText("R", canvas.width / 2 + 30 * value_R, canvas.height / 2 + 10);
        ctx.fillText("-R", canvas.width / 2 - 25, canvas.height / 2 + 30 * value_R);
        ctx.fillText("-R/2", canvas.width / 2 - 25, canvas.height / 2 + 15 * value_R);
        ctx.fillText("R", canvas.width / 2 - 25, canvas.height / 2 - 30 * value_R);
        ctx.fillText("R/2", canvas.width / 2 - 25, canvas.height / 2 - 15 * value_R);
    }
}

function validateX(x) {
    return x !== undefined && x !== null && !isNaN(x) && x <= 5 && x >= -5;
}

function validateY(y) {
    const yRegexp = /-?\d+[.?\d+]*/i;
    if (yRegexp.test(y)) {
        return y >= -3 && y <= 5;
    } else {
        return false;
    }
}

function validateR(r) {
    return r !== undefined && r !== null && !isNaN(r) && r <= 5 && r >= 2;
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

function drawOldPoints(r){
    points.forEach(point => {
        if(point["r"] === r) {
            checkPoint(point["x"], point["y"], point["r"]) ? ctx.fillStyle = "green" : ctx.fillStyle = "red";
            ctx.beginPath();
            ctx.arc(point["x"], point["y"], 3, 0, 2 * Math.PI);
            ctx.fill();
            ctx.closePath();
        }
    });
}
function checkRectangle(x, y, r) {
    return x >= (-r / 2) && y <= r;
}

function checkCircle(x, y, r) {
    return x ** 2 + y ** 2 <= (-r) ** 2;
}

function checkTriangle(x, y, r) {
    return x <= r && y >= -r && x - r <= y;
}



function drawPoint(x, y, r) {
    setValueR(r);
    console.log(x, y, r)
    if (validateX(x) && validateY(y) && validateR(r)) {
        const scale = 30 * r;
        const center_X = canvas.width / 2;
        const center_Y = canvas.height / 2;
        const dot_X = center_X + (x / (r * 1.75)) * scale;
        const dot_Y = center_Y - (y / (r * 1.75)) * scale;
        console.log(checkPoint(x, y, r));
        checkPoint(x, y, r) ? ctx.fillStyle = "green" : ctx.fillStyle = "red";
        ctx.beginPath();
        ctx.arc(dot_X, dot_Y, 3, 0, 2 * Math.PI);
        ctx.fill();
        ctx.closePath();
        drawOldPoints(r);
        points.push({
            x: value_X,
            y: value_Y,
            r: value_R,
        });
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
        drawOldPoints(r);
    }
}