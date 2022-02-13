function updateMultiplication() {
    $.ajax({
        url: "http://localhost:8080/multiplications/randoms"
    }).then(function(data) {
        // Cleans the form
        $("#attempt-form").find( "input[name='result-attempt']" )
            .val("");
        $("#attempt-form").find( "input[name='user-alias']" )
            .val("");
        // Gets a random challenge from API and loads the datain the HTML
        $('.multiplication-a').empty().append(data.factorA);
        $('.multiplication-b').empty().append(data.factorB);
    });
}
$(document).ready(function() {
    updateMultiplication();
    $("#attempt-form").submit(function( event ) {
        // Don't submit the form normally
        event.preventDefault();
        // Get some values from elements on the page
        var a = $('.multiplication-a').text();
        var b = $('.multiplication-b').text();
        var $form = $( this ),
            attempt = $form.find( "input[name='resultattempt']" ).val(),
            userAlias = $form.find( "input[name='user-alias']" ).val();
        // Compose the data in the format that the API isexpecting
        var data = { user: { alias: userAlias}, multiplication:
                {factorA: a, factorB: b}, resultAttempt: attempt};
        // Send the data using post
        $.ajax({
            url: 'http://localhost:8080/results',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async:false,
            success: function(result){
                if(result.correct) {
                    $('.result-message').empty().append("The result is correct! Congratulations!");
                } else {
                    $('.result-message').empty().append("Oopsthat's not correct! But keep trying!");
                }
            }
        });
        updateMultiplication();

        function updateStats(alias) {
            $.ajax({
                url: "http://localhost:8080/results?alias=" + alias,
            }).then(function(data) {
                $('#stats-body').empty();
                data.forEach(function(row) {
                    $('#stats-body').append('<tr><td>' + row.id +
                        '</td>' +
                        '<td>' + row.multiplication.factorA + ' x ' +
                        row.multiplication.factorB + '</td>' +
                        '<td>' + row.resultAttempt + '</td>' +
                        '<td>' + (row.correct === true ? 'YES' : 'NO')
                        + '</td></tr>');
                });
            });
        }
        $(document).ready(function() {
            updateMultiplication();
            $("#attempt-form").submit(function( event ) {
                // [...]
                updateStats(userAlias);
            });

        });
});});