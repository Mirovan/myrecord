//adding new form elements - product for payment
var dataSelect = $('#payment-products').html();
$('#addProduct').click(
    function() {
        $('#payment-products').append(dataSelect);
    }
);