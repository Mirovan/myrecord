$(document).ready(
    function() {
        var day = parseInt($('#dayInput').val()) + '';
        if (day.length < 2) day = '0' + day;
        var month = parseInt($('#monthInput').val()) + '';
        if (month.length < 2) month = '0' + month;
        var year = parseInt($('#yearInput').val()) + '';
        var now = '';
        now = now.concat(year, '-', month, '-', day);

        $('#calendar').fullCalendar({
            schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
            defaultView: 'timelineDay',
            now: now,
            height: 300,
            eventSources: [
                {
                    url: '/cabinet/clients/json-users-by-date/',
                    data: {
                        day: day,
                        month: month,
                        year: year
                    }
                }
            ]
        });

    }
);