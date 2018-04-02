$(document).ready(
    function() {
        var day = parseInt($('#dayInput').val()) + '';
        if (day.length < 2) day = '0' + day;
        var month = parseInt($('#monthInput').val()) + '';
        if (month.length < 2) month = '0' + month;
        var year = parseInt($('#yearInput').val()) + '';
        var now = '';
        now = now.concat(year, '-', month, '-', day);

        //get all data - masters and records
        var allData = [];

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
                            defaultView: 'timelineDay',
                            now: now,
                            height: 300,
                            resources: workers,
                            events: records
                        });
                        //get resource - masters
                        // var masters = [];
                        // $.each(data, function(key, item) {
                        //     var master = new Object();
                        //     master["id"] = item["master"]["id"] + '';
                        //     master["title"] = item["master"]["name"];
                        //     masters.push(master);
                        // });
                        //
                        // //get events - user records
                        // var records = [];
                        // $.each(data, function(key, item) {
                        //     var record = Object.assign({}, item);
                        //     record["resourceId"] = item["master"]["id"] + '';
                        //     records.push(record);
                        // });
                    }
                );

            }
        );
    }
);