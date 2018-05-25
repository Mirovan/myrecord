$(document).ready(
    function() {
        $.getJSON(
            "/cabinet/users/json-get-workers/",
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

                $('#worker').autocomplete({
                    lookup: dataArray,
                    onSelect: function (suggestion) {
                        $('#workerId').val(suggestion.data);
                    }
                });
            }
        );
    }
);