$(document).ready(
    function() {
        $('td').click( function() {
            $(this).toggleClass("red-cell");
        } );

        $('#aPrev').click (
            function () {
                var month = parseInt($('#monthInput').val()) - 1;
                var year = parseInt($('#yearInput').val());
                if (month <= 0) {
                    month = 12;
                    year--;
                    if (year < 0) year = 0;
                }
                $('#monthInput').val(month);
                $('#yearInput').val(year);
                showSchedule()
            }
        );

        $('#aNext').click (
            function () {
                var month = parseInt($('#monthInput').val()) + 1;
                var year = parseInt($('#yearInput').val());
                if (month > 12) {
                    month = 1;
                    year++;
                }
                $('#monthInput').val(month);
                $('#yearInput').val(year);
                showSchedule()
            }
        );
    }
);


function showSchedule() {
    $.getJSON(
        "/cabinet/users/schedule/",
        {
            userId: $('#userInput').val(),
            year: $('#yearInput').val(),
            month: $('#monthInput').val()
        },
        function(data) {
            $('#divScheduleContainer').html('');
            $.each(data, function(key, valueList) {
                $('#divScheduleContainer').append('<tr>');
                $.each(valueList, function(key2, scheduleDay) {
                    var date = new Date();
                    if (scheduleDay.sdate != null) {
                        var day = scheduleDay.sdate.dayOfMonth;
                        var month = scheduleDay.sdate.monthValue - 1; // Month is 0-indexed
                        var year = scheduleDay.sdate.year;
                        date = new Date(year, month, day);
                        $('#divScheduleContainer').append('<td>' + $.format.date(date, 'dd-MM-yyyy') + '</td>');
                    } else {
                        $('#divScheduleContainer').append('<td></td>');
                    }
                });
                $('#divScheduleContainer').append('</tr>');
            });
        }
    );
}