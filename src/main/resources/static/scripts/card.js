const items = ["testitu"]

function addItem(event) {
	event.preventDefault()

	const item = document.getElementById("item")
	items.push(item.value)
	item.value = ""

	refreshList(event)
}

function removeItem(event, index) {
	event.preventDefault()
	const itemIndex = items.indexOf(index)

	items.splice(itemIndex, 1)

	refreshList(event)
}

function refreshList(event) {
	event.preventDefault()
	const tag = document.getElementById("allItems")
	tag.classList.add("bg-blue-500", "w-full")
	tag.replaceChildren()

	for(const item of items) {
		const elem = document.createElement("span")
		elem.innerHTML = `
			<p class="flex justify-between align-center w-2/10 bg-red-700">
				${item}
			</p>
			<button onclick="removeItem(event, '${item}')">
				<img th:src="@{/svgs/close.svg}" alt="pequeno ícone vermelho de botão de fechar">
			</button>
		`

		tag.appendChild(elem)
	}
}