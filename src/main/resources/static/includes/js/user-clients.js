$(document).ready(
    function() {
        $.getJSON(
            "/cabinet/users/json-get-clients/",
            {},
            function (users) {
                var dataArray = $.map(
                    users,
                    function (user) {
                        return {value: user.name + ' ' + user.sirname + ' [' + user.phone + ']', data: user.id};
                    }
                );

                $('#client').autocomplete({
                    lookup: dataArray,
                    onSelect: function (suggestion) {
                        $('#clientId').val(suggestion.data);
                    }
                });
            }
        );
    }
);