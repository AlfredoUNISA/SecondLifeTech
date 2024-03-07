$(document).ready(function () {
    let finalizeButton = $('#finalizeButton');
    let shippingAddress = $('#shippingAddressSelect');
    let paymentMethod = $('#paymentMethodSelect');

    function checkFields() {
        let shippingAddressVal = shippingAddress.val();
        let paymentMethodVal = paymentMethod.val();

        if ((!shippingAddressVal || !paymentMethodVal) || (shippingAddressVal === "" || paymentMethodVal === "")) {
            finalizeButton.prop('disabled', true);
        } else {
            finalizeButton.prop('disabled', false);
        }

    }

    checkFields();

    shippingAddress.change(function () {
        checkFields();
    });

    paymentMethod.change(function () {
        checkFields();
    });
});