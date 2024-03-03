$(document).ready(function () {
	let cartButton = $("#addToCartButton");
	cartButton.click(() => {

		let quantity = $("#quantity");

		console.log(cartButton.val());
		console.log(quantity.text());

		$.ajax({
			type: "post",
			url: "/my-cart/add",
			data: {
				quantity: quantity.text(),
				id: cartButton.val()
			},
			success: function(response) {
				// Redirect to the new page after the AJAX request is completed
				window.location.href = "/my-cart";
			}
		});
	});
});
