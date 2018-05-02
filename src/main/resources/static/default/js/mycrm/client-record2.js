$(document).ready(
    function() {
        var day = parseInt($('#dayInput').val()) + '';
        if (day.length < 2) day = '0' + day;
        var month = parseInt($('#monthInput').val()) + '';
        if (month.length < 2) month = '0' + month;
        var year = parseInt($('#yearInput').val()) + '';
        var now = '';
        now = now.concat(year, '-', month, '-', day);

        $.getJSON(
            "/cabinet/clients/json-month-schedule/",
            {
                day: day,
                month: month,
                year: year
            },
            function(eventData) {
                $('#calendar').fullCalendar({
                    schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
                    header: {
                        left: 'today prev,next',
                        center: 'title',
                        right: 'month'
                        //right: 'timelineDay,timelineThreeDays,agendaWeek,month,listWeek'
                    },
                    //defaultView: 'timelineDay',
                    now: now,
                    firstDay: 1,
                    //resources: workers,
                    events: eventData
                });
            }
        );
    }
);