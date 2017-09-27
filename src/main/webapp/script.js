var currentView = null;
function gotoView(view) {
    if (currentView) {
        $("#" + currentView).css("display", "none");
    }
    currentView = view;
    $("#" + currentView).css("display", "block");
}

function joinQuiz(id) {
    // Velg nick
    // Quiz
}

function spectateQuiz(id) {
    // Live scoreboard
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
            "<td><input type=\"text\" class=\"form-control\" data-attribute=\"img\" placeholder=\"Bilde-URL\"></td>" +
        "</tr>"
    );
}

function saveNewQuiz() {
    var newQuiz = {
        id: -1, // Serveren genererer id
        name: $("#newQuizName").val(),
        startTime: $("#newQuizStartTime").val(),
        questions: []
    };

    $("#newQuizQuestions").find("tbody").find("tr").find("td").each(function () {
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
            id: -1, // Serveren genererer id
            text: text,
            alternatives: alternatives,
            correct: i[0],
            time: time
        });
    });

    $.ajax({
        url: "/rest/quiz",
        type: "POST",
        data: newQuiz,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (result) {
            console.log(result)
        }
    });

    gotoView("quizSelectionView");
}

/**
 * Funnet på StackOverflow: https://stackoverflow.com/a/6274381
 * All kreds til forfatteren.
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
    $("#quizes").find("tbody").append(
        "<tr id='quiz_230293'>" +
        "<td>Hvaerdette</td>" +
        "<td>Snart</td>" +
        "<td class='col-md-3'>" +
        "<button class='btn btn-primary' onclick='joinQuiz(12332)'>Delta</button>&nbsp;" +
        "<button class='btn btn-secondary' onclick='spectateQuiz(12332)'>Se scoreboard</button>" +
        "</td>" +
        "</tr>"
    );

    gotoView("#createQuizView");
});
