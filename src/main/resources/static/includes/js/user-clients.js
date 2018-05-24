$(document).ready(
    function() {
        $.getJSON(
            "/cabinet/users/json-get-clients/",
            {},
            function(users) {
                var usersArray = $.map(
                    users,
                    function(user) {
                        return { value: user.name + ' ' + user.sirname, data: user.id };
                    }
                );

                $('#input-user-selection').autocomplete({
                    source: usersArray,
                    select: function(event, ui) {
                        $('#userId').val(ui.item.data);
                    }
                });
            }
        );
    }
);