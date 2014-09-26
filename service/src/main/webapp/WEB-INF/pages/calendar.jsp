<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset='utf-8' />
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/fullcalendar.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/fullcalendar.print.css" />" rel="stylesheet" media="print" />
    <script src="http://maps.google.com/maps/api/js?sensor=true"></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/jquery-1.8.2.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/moment.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/fullcalendar.min.js" />"></script>
    <script>

        $(document).ready(function() {

            $('#calendar').fullCalendar({
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay'
                },
                defaultDate: '2014-09-12',
                editable: true,
                eventLimit: true, // allow "more" link when too many events
                events: {
                    url: "${pageContext.request.contextPath}/getEvents",
                    error: function() {
                        $('#script-warning').show();
                    }
                },
                loading: function(bool) {
                    $('#loading').toggle(bool);
                },
                eventDrop: function(event, delta, revertFunc) {

                    alert(event.title + " was dropped on " + event.start.format());

                    if (!confirm("Are you sure about this change?")) {
                        revertFunc();
                    } else {
                        //update in db
                        $.getJSON("${pageContext.request.contextPath}/updateEventOnDrop",
                                {
                                    id: event.id,
                                    start: event.start.format()
                                },
                                function (data) {

                                    var data = JSON.stringify(data);
                                    var json = JSON.parse(data);

                                    //TODO: check result?
                                })
                                .done(function () {

                                })
                                .fail(function () {
                                })
                                .complete(function () {
                                });
                    }

                }
            });

        });

    </script>
    <style>

        body {
            margin: 0;
            padding: 0;
            font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
            font-size: 14px;
        }

        #script-warning {
            display: none;
            background: #eee;
            border-bottom: 1px solid #ddd;
            padding: 0 10px;
            line-height: 40px;
            text-align: center;
            font-weight: bold;
            font-size: 12px;
            color: red;
        }

        #loading {
            display: none;
            position: absolute;
            top: 10px;
            right: 10px;
        }

        #calendar {
            max-width: 900px;
            margin: 40px auto;
            padding: 0 10px;
        }

    </style>
</head>
<body>

<div class="container" style="padding-top: 10px">
    <h1>Calendar Service</h1>
</div>

<div id='script-warning'>
    <code>/getEvents</code> must be running.
</div>

<div id='loading'>loading...</div>

<div id='calendar'></div>

</body>
</html>
