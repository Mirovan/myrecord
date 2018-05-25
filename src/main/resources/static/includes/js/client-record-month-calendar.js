$(document).on(
    "click",
    ".fc-prev-button",
    function () {
        var month = parseInt($('#monthInput').val()) - 1;
        var year = parseInt($('#yearInput').val());
        if (month <= 0) {
            month = 12;
            year = year - 1;
        }

        $('#monthInput').val(month);
        $('#yearInput').val(year);

        if (month.length < 2) month = '0' + month;
        month = month + '';
        year = year + '';

        showCalendar(month, year, $('#productId').val(), $('#workerId').val());
    }
);


$(document).on(
    "click",
    ".fc-next-button",
    function () {
        var month = parseInt($('#monthInput').val()) + 1;
        var year = parseInt($('#yearInput').val());
        if (month > 12) {
            month = 1;
            year = year + 1;
        }

        $('#monthInput').val(month);
        $('#yearInput').val(year);

        if (month.length < 2) month = '0' + month;
        month = month + '';
        year = year + '';

        showCalendar(month, year, $('#productId').val(), $('#workerId').val());
    }
);


$(document).ready(
    function () {
        var month = parseInt($('#monthInput').val()) + '';
        if (month.length < 2) month = '0' + month;
        var year = parseInt($('#yearInput').val()) + '';

        $('#productId').change (
            function () {
                changeUserSelectList();
            }
        );

        $('#workerId').change (
            function () {
                showCalendar(month, year, $('#productId').val(), $('#workerId').val());
            }
        );

        showCalendar(month, year, $('#productId').val(), $('#workerId').val());
    }
);


function showCalendar(month, year, productId, workerId) {
    $.getJSON(
        "/cabinet/clients/json-worker-month-schedule/",
        {
            month: month,
            year: year,
            productId: productId,
            workerId: workerId
        },
        function(eventBackgroundData) {
            $.getJSON(
                "/cabinet/clients/json-month-records/",
                {
                    month: month,
                    year: year
                },
                function(eventRecordsData) {
                    var eventData = eventBackgroundData.concat(eventRecordsData);
                    drawCalendar(eventData);
                }
            );
        }
    );
}


function drawCalendar(eventData) {
    $('#calendar').fullCalendar('removeEvents');

    $('#calendar').fullCalendar({
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        header: {
            left: 'prev,next',
            center: 'title',
            right: 'today'
        },
        locale: 'ru',
        firstDay: 1,
        //events: eventData,
        dayClick: function(dateData) {
            var d = new Date(dateData.format());
            var day = d.getDate();
            var month = d.getMonth() + 1;
            var year = d.getFullYear();
            var url = "/cabinet/clients/record-day/?";

            if ((day + "").length < 2) day = "0" + day;
            if ((month + "").length < 2) month = "0" + month;
            var date = "date=" + day + "-" + month + "-" + year;

            url += date;
            $(location).attr('href', url);
        }
    });

    $('#calendar').fullCalendar('addEventSource', eventData);
    $('#calendar').fullCalendar('rerenderEvents');
}


function changeUserSelectList() {
    $.getJSON(
        "/cabinet/clients/json-users-by-product/",
        {
            productId: $('#productId').val(),
        },
        function(data) {
            $('#workerId').html('');
            $('#workerId').append('<option value="">-- Все мастера --</option>');
            $.each(data, function(key, item) {
                $('#workerId').append('<option value="' + item.id + '">' + item.name + ' ' + item.sirname + '</option>');
            });

            var month = parseInt($('#monthInput').val()) + '';
            if (month.length < 2) month = '0' + month;
            var year = parseInt($('#yearInput').val()) + '';
            
            showCalendar(month, year, $('#productId').val(), $('#workerId').val());
        }
    );
}
