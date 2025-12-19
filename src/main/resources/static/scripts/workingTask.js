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

function startTimer(event) {
	event.preventDefault()

	document.getElementById("start-timer-button").disabled = true
	document.getElementById("stop-timer-button").disabled = false

	isTracking = true;
	begin = Date.now();

	setInterval(() => {
		if (isTracking) {
			end = Date.now();
			extractInterval(begin, end)
			document.getElementById("timer-display").innerText = formatTime();
		}
	}, 1_000);
}

function stopTimer(event) {
	event.preventDefault()
	isTracking = false;
	
	document.getElementById("start-timer-button").disabled = false
	document.getElementById("stop-timer-button").disabled = true

	document.getElementById("start-timer-value").value = begin
	document.getElementById("stop-timer-value").value = end

	console.log(document.getElementById("start-timer-value"))
	console.log(document.getElementById("stop-timer-value"))
}

function formatTime() {
	return `${String(hours).padStart(2, "0")}:${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`
}
