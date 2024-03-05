$(document).ready(function () {
	const name = $("#name").text();

	$.ajax({
		type: "get",
		url: `/variations/${name}`,
		dataType: "json",
		success: function (response) {
			console.log(response);
			processData(response);
		},
		error: function (error) {
			console.log(error);
		}
	});

	function processData(products) {
		// Put all the ram, display, storage, color, and state values into arrays without duplicates
		const rams = [];
		const displays = [];
		const storages = [];
		const colors = [];
		const states = [];

		products.forEach(function (product) {
			if (!rams.includes(product.ram)) {
				rams.push(product.ram);
			}

			if (!displays.includes(product.display)) {
				displays.push(product.display);
			}

			if (!storages.includes(product.storage)) {
				storages.push(product.storage);
			}

			if (!colors.includes(product.color)) {
				colors.push(product.color);
			}

			if (!states.includes(product.state)) {
				states.push(product.state);
			}
		});


		// Set the radio button values to the first variation
		$("input[name='ram-radio'][value='" + rams[0] + "']").prop("checked", true);
		$("input[name='display-radio'][value='" + displays[0] + "']").prop("checked", true);
		$("input[name='size-radio'][value='" + storages[0] + "']").prop("checked", true);
		$("input[name='color-radio'][value='" + colors[0] + "']").prop("checked", true);
		$("input[name='state-radio'][value='" + states[0] + "']").prop("checked", true);

		let selectedRam = $("input[name='ram-radio']:checked").val();
		let selectedDisplay = $("input[name='display-radio']:checked").val();
		let selectedStorage = $("input[name='size-radio']:checked").val();
		let selectedColor = $("input[name='color-radio']:checked").val();
		let selectedState = $("input[name='state-radio']:checked").val();

		// Initialize with the first product variation
		updateOptions();

		// Inizializza la quantità corrente
		var currentQuantity = 1;

		// Imposta il valore iniziale di stock (puoi cambiarlo secondo le tue esigenze)
		var stock = $('#stock').text();

		// Mostra la quantità corrente e il valore di stock
		$('#quantity').text(currentQuantity);
		$('#stock').text(stock);

		// Gestisci il clic sul pulsante di rimozione della quantità
		$('#removeOneQuantity').click(function () {
			if (currentQuantity > 1) {
				currentQuantity--;
				$('#quantity').text(currentQuantity);
				// Riattiva il pulsante di aggiunta se la quantità corrente è inferiore a stock
				if (currentQuantity < stock) {
					$('#addOneQuantity').parent().removeAttr('style');
				}
			}
			// Disabilita il pulsante di rimozione se la quantità corrente è 1
			if (currentQuantity === 1) {
				$(this).parent().attr('style', 'pointer-events: none;');
			}
		});

		// Gestisci il clic sul pulsante di aggiunta della quantità
		$('#addOneQuantity').click(function () {
			if (currentQuantity < stock) {
				currentQuantity++;
				$('#quantity').text(currentQuantity);
				// Riattiva il pulsante di rimozione se la quantità corrente è > 1
				$('#removeOneQuantity').parent().removeAttr('style');
			}
			// Disabilita il pulsante di aggiunta se la quantità corrente è uguale a stock
			if (currentQuantity === stock) {
				$(this).parent().attr('style', 'pointer-events: none;');
			}
		});

		// Function to disable options based on selected variation
		function updateOptions() {
			// Disable options that are not available

			let available = [];
			displays.forEach(function (display) {
				//display = display.toFixed(1);

				// Check if the variation exists whith the previous values
				const exists = existsVariationByRamAndDisplay(selectedRam, display);

				// Disable the radio button if the variation does not exist, enable it otherwise
				$("input[name='display-radio'][value='" + display + "']").prop("disabled", !exists);

				// If the variation does exist, add it to the available array
				if (exists)
					available.push(display);
			});

			// If the selected value is not available, select the first available value
			if (!available.includes(selectedDisplay)) {
				$("input[name='display-radio'][value='" + available[0] + "']").prop("checked", true);
				selectedDisplay = available[0];
			}


			available = [];
			storages.forEach(function (storage) {
				// Check if the variation exists whith the previous values
				const exists = existsVariationByRamAndDisplayAndStorage(selectedRam, selectedDisplay, storage);

				// Disable the radio button if the variation does not exist, enable it otherwise
				$("input[name='size-radio'][value='" + storage + "']").prop("disabled", !exists);

				// If the variation does exist, add it to the available array
				if (exists)
					available.push(storage);
			});

			// If the selected value is not available, select the first available value
			if (!available.includes(selectedStorage)) {
				$("input[name='size-radio'][value='" + available[0] + "']").prop("checked", true);
				selectedStorage = available[0];
			}

			available = [];
			colors.forEach(function (color) {
				// Check if the variation exists whith the previous values
				const exists = existsVariationByRamAndDisplayAndStorageAndColor(selectedRam, selectedDisplay, selectedStorage, color);

				// Disable the radio button if the variation does not exist, enable it otherwise
				$("input[name='color-radio'][value='" + color + "']").prop("disabled", !exists);

				// If the variation does exist, add it to the available array
				if (exists)
					available.push(color);
			});

			// If the selected value is not available, select the first available value
			if (!available.includes(selectedColor)) {
				$("input[name='color-radio'][value='" + available[0] + "']").prop("checked", true);
				selectedColor = available[0];
			}

			available = [];
			states.forEach(function (state) {
				// Check if the variation exists whith the previous values
				const exists = existsVariationByRamAndDisplayAndStorageAndColorAndState(selectedRam, selectedDisplay, selectedStorage, selectedColor, state);

				// Disable the radio button if the variation does not exist, enable it otherwise
				$("input[name='state-radio'][value='" + state + "']").prop("disabled", !exists);

				// If the variation does exist, add it to the available array
				if (exists)
					available.push(state);
			});

			// If the selected value is not available, select the first available value
			if (!available.includes(selectedState)) {
				$("input[name='state-radio'][value='" + available[0] + "']").prop("checked", true);
				selectedState = available[0];
			}

			// Find the selected variation
			const variation = findVariation(selectedRam, selectedDisplay, selectedStorage, selectedColor, selectedState);

			$("#price").text(`€ ${variation.price}`);

			if (variation.inStock == 0) {
				let container = $("#stockContainer");
				container.text("Out of stock");
				container.removeClass("text-bg-warning");
				container.addClass("text-bg-danger");
				$("#stock").text(0);
				$("#addToCartButton").prop("disabled", true);
			} else {
				let container = $("#stockContainer");
				container.html("Ancora <span id='stock'></span> in magazzino!");
				container.removeClass("text-bg-danger");
				container.addClass("text-bg-warning");
				$("#stock").text(variation.inStock);
				$("#stock").val(variation.inStock);
				$("#addToCartButton").prop("disabled", false);
			}

			$("#addToCartButton").val(variation.id);
		}

		// Event listener for radio button change
		$("input[type='radio']").change(function () {
			selectedRam = $("input[name='ram-radio']:checked").val();
			selectedDisplay = $("input[name='display-radio']:checked").val();
			selectedStorage = $("input[name='size-radio']:checked").val();
			selectedColor = $("input[name='color-radio']:checked").val();
			selectedState = $("input[name='state-radio']:checked").val();

			updateOptions();
		});

		function existsVariationByRamAndDisplay(ram, display) {
			return products.some(function (product) {
				return product.ram === ram
					&& product.display === display;
			});
		}

		function existsVariationByRamAndDisplayAndStorage(ram, display, storage) {
			return products.some(function (product) {
				return product.ram === ram
					&& product.display === display
					&& product.storage === storage;
			});
		}

		function existsVariationByRamAndDisplayAndStorageAndColor(ram, display, storage, color) {
			return products.some(function (product) {
				return product.ram === ram
					&& product.display === display
					&& product.storage === storage
					&& product.color === color;
			});
		}

		function existsVariationByRamAndDisplayAndStorageAndColorAndState(ram, display, storage, color, state) {
			return products.some(function (product) {
				return product.ram === ram
					&& product.display === display
					&& product.storage === storage
					&& product.color === color
					&& product.state === state;
			});
		}

		function findVariation(ram, display, storage, color, state) {
			return products.find(function (product) {
				return product.ram === ram
					&& product.display === display
					&& product.storage === storage
					&& product.color === color
					&& product.state === state;
			});
		}
	}


});
