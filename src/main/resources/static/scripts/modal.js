const modals = {
}

function openModal(name) {
	const modal = document.getElementById(name)
	modal.classList.remove("hidden")
}

function closeModal(event, name) {
	event.preventDefault()
	const modal = document.getElementById(name)
	modal.classList.add("hidden")
}
