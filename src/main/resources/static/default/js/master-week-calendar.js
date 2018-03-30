$(document).ready(
    function() {

        $('#calendar').fullCalendar({
            eventSources: [
                {
                    url: '/cabinet/clients/json-users-by-date/',
                    data: {
                        day: parseInt($('#dayInput').val()),
                        month: parseInt($('#monthInput').val()),
                        year: parseInt($('#yearInput').val())
                    }
                }
            ]
        });

    }
);