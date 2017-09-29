var currentView = null;
function gotoView(view) {
    if (currentView) {
        $("#" + currentView).css("display", "none");
    }
    currentView = view;
    $("#" + view).css("display", "block");
}


var currentQuiz = null;
var currentQuestion = -1;
var quizStarted = false;
var playerName = null;
var questionTime = -1;
var questionIsAnswered = false;
var tickQuizInterval;

function joinQuiz(id) {
    $("#selectNick").css("display", "block");
    $("#quizContent").css("display", "none");

    // Nullstill tilstand fra forrige quiz
    currentQuiz = null;
    currentQuestion = -1;
    questionTime = -1;

    $.getJSON("rest/quiz/" + id, function (data) {
        currentQuiz = data;
        $("#quizNameTitle").html(currentQuiz.name);
        gotoView("playQuizView");
    });
}

function playQuiz() {
    $("#selectNick").css("display", "none");
    $("#waitForQuizStart").css("display", "block");
    quizStarted = false;

    playerName = $("#playerName").val();
    tickQuizInterval = setInterval(tickQuiz, 1000);
}

function tickQuiz() {
    if (!quizStarted) {
        // Nedtelling til quizstart

        var now = Math.floor(Date.now() / 1000);
        if (currentQuiz.startTime <= now) {
            $("#waitForQuizStart").css("display", "none");
            $("#quizContent").css("display", "block");
            quizStarted = true;
        }
        else {
            $("#quizStartCountdown").html((currentQuiz.startTime - now) + " sek");
        }
    }
    else {
        if (currentQuestion < 0) {
            // Første spørsmål
            currentQuestion = 0;
            questionTime = currentQuiz.questions[currentQuestion].time;
            showCurrentQuestion();
        }

        if (questionTime-- <= 0) {
            currentQuestion++;
            questionIsAnswered = false;
            $(".answer-btn").prop("disabled", false);

            if (currentQuiz.questions[currentQuestion] === undefined) {
                // Quizen er ferdig
                clearInterval(tickQuizInterval);

                $("#openScoreboardBtn").click(function () {
                    spectateQuiz(currentQuiz.id);
                });

                gotoView("quizFinishedView");
            }
            else {
                // Vis neste spørsmål
                questionTime = currentQuiz.questions[currentQuestion].time;
                showCurrentQuestion();
            }
        }

        $("#questionTime").html(questionTime + " sek");
    }
}

function showCurrentQuestion() {
    var question = currentQuiz.questions[currentQuestion];
    $("#questionNum").html(currentQuestion + 1);
    $("#questionText").html(question.text);
    $("#answerOption0").html(question.alternatives[0]);
    $("#answerOption1").html(question.alternatives[1]);
    $("#answerOption2").html(question.alternatives[2]);
    $("#answerOption3").html(question.alternatives[3]);
    if (question.img === null) {
        $("#questionImg").css("display", "none");
    }
    else {
        $("#questionImg").css("display", "inline-block").attr("src", question.img);
    }
}

function selectAnswer(num) {
    if (!questionIsAnswered) {
        $(".answer-btn").prop("disabled", true);
        $.ajax({
            url: "rest/quiz/" + currentQuiz.id + "/question/" + currentQuestion + "/answer",
            type: "POST",
            data: JSON.stringify({player: playerName, answer: num}),
            contentType: "application/json",
            dataType: "json",
            success: function (result) {
                console.log(result);
                questionIsAnswered = true;
            }
        });
    }
}

var spectateQuizInterval;
var spectateQuizId;

function spectateQuiz(id) {
    gotoView("spectateQuizView");
    spectateQuizId = id;
    spectateQuizInterval = setInterval(refreshQuizScoreboard, 3000);
    refreshQuizScoreboard();
}

function refreshQuizScoreboard() {
    $.getJSON("rest/quiz/" + spectateQuizId, function (quiz) {
        $("#spectateQuizNameTitle").html(quiz.name);
        $("#spectateQuizScoreboard").find("tbody").empty();
        $.each(quiz.scores, function (i, score) {
            $("#spectateQuizScoreboard").find("tbody").append(
                "<tr>" +
                "<td>" + score.player + "</td>" +
                "<td>" + score.score + "</td>" +
                "</tr>"
            );
        });
    });
}

function exitSpectateQuiz() {
    clearInterval(spectateQuizInterval);
    gotoView("quizSelectionView");
}

function addNewQuestion() {
    $("#newQuizQuestions").find("tbody").append(
        "<tr>" +
            "<td><input type=\"text\" class=\"form-control\" data-attribute=\"text\" placeholder=\"Spørsmål\"></td>" +
            "<td>" +
                "<input type=\"text\" class=\"form-control\" data-attribute=\"correct\" placeholder=\"Riktig svar\">" +
                "<input type=\"text\" class=\"form-control\" data-attribute=\"alt2\" placeholder=\"Alternativ 2\">" +
                "<input type=\"text\" class=\"form-control\" data-attribute=\"alt3\" placeholder=\"Alternativ 3\">" +
                "<input type=\"text\" class=\"form-control\" data-attribute=\"alt4\" placeholder=\"Alternativ 4\">" +
            "</td>" +
            "<td><input type=\"number\" min=\"1\" step=\"1\" class=\"form-control\" data-attribute=\"time\" placeholder=\"Tid i sekunder\"></td>" +
            "<td><input type=\"text\" class=\"form-control\" data-attribute=\"img\" placeholder=\"Valgfri bilde-URL\"></td>" +
        "</tr>"
    );
}

function saveNewQuiz() {
    var datetimeparts = $("#newQuizStartTime").val().split(" ");
    var dateparts = datetimeparts[0].split("/");
    var timeparts = datetimeparts[1].split(":");
    var time = new Date(dateparts[2], parseInt(dateparts[1]) - 1, dateparts[0], timeparts[0], timeparts[1], timeparts[2]);

    var newQuiz = {
        id: -1, // Serveren genererer id
        name: $("#newQuizName").val(),
        startTime: Math.floor(time.getTime() / 1000),
        questions: [],
        scores: []
    };

    $("#newQuizQuestions").find("tbody tr").each(function () {
        var row = $(this);
        var text = row.find("input[data-attribute='text']").val();
        var correct = row.find("input[data-attribute='correct']").val();
        var alt2 = row.find("input[data-attribute='alt2']").val();
        var alt3 = row.find("input[data-attribute='alt3']").val();
        var alt4 = row.find("input[data-attribute='alt4']").val();
        var time = row.find("input[data-attribute='time']").val();
        var img = row.find("input[data-attribute='img']").val();

        if (text === "") return;

        // Stokk om på svaralternativene og husk hvilken som er rett
        var i = [0, 1, 2, 3];
        shuffle(i);
        var alternatives = [];
        alternatives[i[0]] = correct;
        alternatives[i[1]] = alt2;
        alternatives[i[2]] = alt3;
        alternatives[i[3]] = alt4;

        newQuiz.questions.push({
            text: text,
            alternatives: alternatives,
            correct: i[0],
            time: parseInt(time),
            img: img
        });
    });

    $.ajax({
        url: "rest/quiz",
        type: "POST",
        data: JSON.stringify(newQuiz),
        contentType: "application/json",
        dataType: "json",
        success: function (result) {
            console.log(result);
        }
    });

    refreshQuizSelectionList();
    gotoView("quizSelectionView");
}

function refreshQuizSelectionList() {
    $.getJSON("rest/quiz", function (data) {
        $("#quizes").find("tbody").empty();
        $.each(data, function (i, quiz) {
            var now = Math.floor(Date.now() / 1000);
            var joinBtn = "";
            if (quiz.startTime > now) {
                joinBtn = "<button class='btn btn-success' onclick='joinQuiz(" + quiz.id + ")'>Delta</button>&nbsp;";
            }

            var startTime = new Date(quiz.startTime * 1000);
            var startTimeStr = startTime.getDate() + "/" + (startTime.getMonth() + 1) + "/" + startTime.getFullYear()
                + " " + ("0" + startTime.getHours()).substr(-2)
                + ":" + ("0" + startTime.getMinutes()).substr(-2)
                + ":" + ("0" + startTime.getSeconds()).substr(-2);

            $("#quizes").find("tbody").append(
                "<tr id='quiz_" + quiz.id + "'>" +
                "<td>" + quiz.name + "</td>" +
                "<td>" + startTimeStr + "</td>" +
                "<td class='col-md-3'>" +
                joinBtn +
                "<button class='btn btn-secondary' onclick='spectateQuiz(" + quiz.id +")'>Se scoreboard</button>" +
                "</td>" +
                "</tr>"
            );
        });
    });
}

/**
 * Funnet på StackOverflow: https://stackoverflow.com/a/6274381
 * @param a
 */
function shuffle(a) {
    var j, x, i;
    for (i = a.length; i; i--) {
        j = Math.floor(Math.random() * i);
        x = a[i - 1];
        a[i - 1] = a[j];
        a[j] = x;
    }
}

$(function () {
    refreshQuizSelectionList();
    setInterval(refreshQuizSelectionList, 5000);
    gotoView("quizSelectionView");
});
