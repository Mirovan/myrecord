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

        showCalendar(month, year);
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

        showCalendar(month, year);
    }
);

$(document).ready(
    function () {
        var month = parseInt($('#monthInput').val()) + '';
        if (month.length < 2) month = '0' + month;
        var year = parseInt($('#yearInput').val()) + '';

        showCalendar(month, year);
    }
);

function showCalendar(month, year) {
    $.getJSON(
        "/cabinet/clients/json-worker-month-schedule/",
        {
            month: month,
            year: year
        },
        function(eventData) {
            $('#calendar').fullCalendar('removeEvents');

            $('#calendar').fullCalendar({
                schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
                header: {
                    left: 'prev,next',
                    center: 'title',
                    right: 'today'
                },
                firstDay: 1,
                //events: eventData,
                dayClick: function(dateData) {
                    var d = new Date(dateData.format());
                    var day = d.getDate();
                    var month = d.getMonth() + 1;
                    var year = d.getFullYear();
                    var url = "/cabinet/clients/record-day/" + day + "/" + month + "/" + year + "/";
                    $(location).attr('href', url);
                }
            });

            $('#calendar').fullCalendar('addEventSource', eventData);
            $('#calendar').fullCalendar('rerenderEvents');
        }
    );
}