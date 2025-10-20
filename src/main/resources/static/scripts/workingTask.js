const MS_TO_SECONDS = 1000;
const MS_TO_MINUTES = 1000 * 60;
const MS_TO_HOURS = 1000 * 60 * 60;

let isTracking = false;
let begin = 0, end = 0;
let seconds = 0, minutes = 0, hours = 0;

function extractInterval(now, then) {
	let interval = Math.abs(now - then);

	seconds = Math.round(interval / MS_TO_SECONDS) % 60;
	minutes = Math.floor(interval / MS_TO_MINUTES) % 60;
	hours = Math.floor(interval / MS_TO_HOURS) % 60;
}

function startTimer() {
	isTracking = true;
	begin = Date.now();

	const timerDisplay = document.getElementById("timer-display");
	setInterval(() => {
		if (isTracking) {
			end = Date.now();
			extractInterval(begin, end)
			timerDisplay.innerText = formatTime();
		}
	}, 1_000);
}

function stopTimer() {
	isTracking = false;
	alert(`Tempo registrado: ${formatTime()}`);
	alert(`Está cronometrando? ${isTracking}`);
	alert(`Tempo final: ${end}`);
	confirm(`Deseja Registrar o tempo entre ${new Date(begin)} e ${new Date(end)}?`)
}

function formatTime() {
	return `${String(hours).padStart(2, "0")}:${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`
}
