var selectedTdClass = "red-cell";

$(document).ready(
    function() {
        //action cell click
        $('#scheduleContainer').on("click", "td", function() {
            if ($(this).text() != "") {
                $(this).toggleClass(selectedTdClass);
                showSelectForm();
            }
        } );

        //load all Schedule when page loaded
        showSchedule();

        //action - prev month click
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

        //action - next month click
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
    var userSheduleArr = [];

    //get user schedule dates
    $.getJSON(
        "/cabinet/users/user-schedule/",
        {
            userId: $('#userInput').val(),
            year: $('#yearInput').val(),
            month: $('#monthInput').val()
        },
        function(data) {
            $('#userScheduleContainer').html('user sched: <br />');
            $.each(data, function(key, dateValue) {
                if (dateValue.sdate != null) {
                    var day = dateValue.sdate.dayOfMonth;
                    var month = dateValue.sdate.monthValue - 1; // Month is 0-indexed
                    var year = dateValue.sdate.year;
                    date = new Date(year, month, day);
                    userSheduleArr.push( $.format.date(date, 'dd-MM-yyyy') );
                    $('#userScheduleContainer').append($.format.date(date, 'dd-MM-yyyy') + '<br />');
                }
            });
        }
    );


    //get month dates and show user schedule dates
    $.getJSON(
        "/cabinet/users/month-schedule/",
        {
            userId: $('#userInput').val(),
            year: $('#yearInput').val(),
            month: $('#monthInput').val()
        },
        function(data) {
            $('#scheduleContainer').html('<tr><th>Пн</th><th>Вт</th><th>Ср</th><th>Чт</th><th>Пт</th><th>Сб</th><th>Вс</th></tr>');
            $.each(data, function(key, valueList) {

                //fill table
                $('#scheduleContainer').append('<tr>');
                $.each(valueList, function(key2, scheduleDay) {
                    var date = new Date();
                    if (scheduleDay.sdate != null) {
                        var day = scheduleDay.sdate.dayOfMonth;
                        var month = scheduleDay.sdate.monthValue - 1; // Month is 0-indexed
                        var year = scheduleDay.sdate.year;
                        date = new Date(year, month, day);
                        var formatedDate = $.format.date(date, 'dd-MM-yyyy');
                        if ( userSheduleArr.indexOf(formatedDate) >= 0 ) {
                            $('#scheduleContainer tr:last').append('<td class="'+selectedTdClass+'">' + formatedDate + '</td>');
                        } else {
                            $('#scheduleContainer tr:last').append('<td>' + formatedDate + '</td>');
                        }
                    } else {
                        $('#scheduleContainer tr:last').append('<td></td>');
                    }
                });
                $('#scheduleContainer').append('</tr>');
            });
            showSelectForm();
        }
    );
}


function showSelectForm() {
    $('#divSelectContainer').html('');
    var allTds = $("#scheduleContainer td.red-cell").map(function() {
        return this.innerHTML;
    }).get();

    for (var item in allTds) {
        $('#divSelectContainer').append('<input type="checkbox" value="'+ allTds[item] +'" name="dates[]" checked="checked" />'+ allTds[item] +'<br/>');
    }
}

