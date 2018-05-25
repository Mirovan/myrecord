$(document).ready(
    function() {
        $.getJSON(
            "/cabinet/users/json-get-clients/",
            {},
            function (users) {
                var dataArray = $.map(
                    users,
                    function (user) {
                        var phone = "";
                        if (user.phone != null) phone = user.phone.replace(/\-/g, "").replace(/\+/g, "").replace(/\(/g, "").replace(/\)/g, "");
                        return {value: user.name + ' ' + user.sirname + ' [' + phone + ']', data: user.id};
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