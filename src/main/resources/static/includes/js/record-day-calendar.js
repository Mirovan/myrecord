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
            "/cabinet/clients/json-workers-by-date/",
            {
                day: day,
                month: month,
                year: year
            },
            function(workers) {
                $.getJSON(
                    "/cabinet/clients/json-records-by-date/",
                    {
                        day: day,
                        month: month,
                        year: year
                    },
                    function(records) {
                        $('#calendar').fullCalendar({
                            schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
                            header: {
                                left: 'title',
                                right: ''
                            },
                            locale: 'ru',
                            defaultView: 'timelineDay',
                            now: now,
                            height: 300,
                            resources: workers,
                            events: records
                        });
                    }
                );

            }
        );
    }
);