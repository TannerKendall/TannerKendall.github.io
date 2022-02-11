$(document).ready(function () {
    
    loadDVDs();
    addDVD();
    updateDVD();
    displayDVD();
});

function loadDVDs(){
    clearDVDTable();
    var contentRows = $('#contentRows');

    $.ajax({
        type: 'GET',
        url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvds',
        success: function(dvdArray) {
            $.each(dvdArray, function(index, dvd){
                var title = dvd.title;
                var releaseYear = dvd.releaseYear;
                var director = dvd.director;
                var rating = dvd.rating;
                var dvdId = dvd.id;

                var row = '<tr>';
                    //row += '<td><a href=displayDVD() id="clickTitle" onclick="displayDVD(' + dvdId + ')">' + title + '</a></td>';
                    row += '<td><button class="link" id="clickTitle" onclick="displayDVD(' + dvdId + ')">' + title + '</button></td>';
                    row += '<td>' + releaseYear + '</td>';
                    row += '<td>' + director + '</td>';
                    row += '<td>' + rating + '</td>';
                    row += '<td><button type="button" class="btn btn-info" onclick="showEditForm(' + dvdId + ')">Edit</button></td>';
                    row += '<td><button type="button" class="btn btn-danger" onclick="deleteDVD(' + dvdId + ')">Delete</button></td>';
                    row += '</tr>';
                
                contentRows.append(row);
            })
        },
        error: function() {
            $('#errorMessages')
            .append($('<li>')
            .attr({class: 'list-group-item list-group-item-danger'})
            .text('Error calling web service. Please try again later.'));
        }
    })
}

function addDVD(){
    showCreateForm();

    $('#addButton').click(function(event){
        var haveValidationErrors = checkAndDisplayValidationErrors($('#addForm').find('input'));
        
        if(haveValidationErrors) {
            return false;
        }
        $.ajax({
            type: 'POST',
            url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd',
            data: JSON.stringify({
                title: $('#addTitle').val(),
                releaseYear: $('#addReleaseYear').val(),
                director: $('#addDirector').val(),
                rating: $('#addRating').val(),
                notes: $('#addNotes').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json'
            },
            'dataType': 'json',
            success: function(){
                $('#errorMessages').empty(),
                $('#addTitle').val(''),
                $('#addReleaseYear').val(''),
                $('#addDirector').val(''),
                $('#addRating').val(''),
                $('#addNotes').val('')
                loadDVDs();
                $('#addButton').click(showDVDTable());
            },
            error: function() {
                $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later.'));
            }
        })
    });

}

function updateDVD(dvdId){
    $('#updateButton').click(function(event){
        var haveValidationErrors = checkAndDisplayValidationErrors($('#addForm').find('input'));
        
        if(haveValidationErrors) {
            return false;
        }
        $.ajax({
            type: 'PUT',
            url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd/' + $('#editDVDId').val(),
            data: JSON.stringify({
                id: $('#editDVDId').val(),
                title: $('#editTitle').val(),
                releaseYear: $('#editReleaseYear').val(),
                director: $('#editDirector').val(),
                rating: $('#editRating').val(),
                notes: $('#editNotes').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            'complete': function(){
                $('#errorMessage').empty();
                hideEditForm();
                loadDVDs();
            },
            'error': function() {
                $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later. 000'));
            }
        })
    })
}

function deleteDVD(dvdId){
    
    $.ajax({
        type: 'DELETE',
        url: 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd/' + dvdId,
        success: function(){
            loadDVDs();
        }
    });
}

function showCreateForm(){
    $('#errorMessages').empty();

    $('#createButton').click(function(event){
        $('#dvdTable').hide();
        $('#editFormDiv').hide();
        $('#addFormDiv').show();
    });
}

function showEditForm(dvdId){
    $('#errorMessages').empty();

    $.ajax({
        type: 'GET',
        url : 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd/' + dvdId,
        success: function(data, status){
            $('#editTitle').val(data.title);
            $('#editReleaseYear').val(data.releaseYear);
            $('#editDirector').val(data.director);
            $('#editRating').val(data.rating);
            $('#editNotes').val(data.notes);
            $('#editDVDId').val(data.id);
        },
        error: function() {
            $('#errorMessages')
            .append($('<li>')
            .attr({class: 'list-group-item list-group-item-danger'})
            .text('Error calling web service. Please try again later.'));
        }
    })

    $('#dvdTable').hide();
    $('#editFormDiv').show();
}

function showDVDTable(){
    $('#errorMessages').empty();

    $('#dvdTable').show();
    $('#addFormDiv').hide();
    $('#editFormDiv').hide();
}

function clearDVDTable(){
    $('#contentRows').empty();
}

function hideEditForm(){
    $('#errorMessages').empty();

    $('#editTitle').val('');
    $('#editReleaseYear').val('');
    $('#editDirector').val('');
    $('#editRating').val('');
    $('#editNotes').val('');

    showDVDTable();
}

function hideAddForm(){
    $('#errorMessages').empty();

    $('#editTitle').val('');
    $('#editReleaseYear').val('');
    $('#editDirector').val('');
    $('#editRating').val('');
    $('#editNotes').val('');

    showDVDTable();
}

function showDVDTable(){
    $('#errorMessages').empty();

    $('#dvdTable').hide();
    $('#addFormDiv').hide();
    $('#editFormDiv').hide();
    $('#singleDVDTable').show();
}

function checkAndDisplayValidationErrors(input) {
    $('#errorMessages').empty();
    
    var errorMessages = [];
    
    input.each(function() {
        if (!this.validity.valid) {
            var errorField = $('label[for=' + this.id + ']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);
        }  
    });
    
    if (errorMessages.length > 0){
        $.each(errorMessages,function(index,message) {
            $('#errorMessages').append($('<li>').attr({class: 'list-group-item list-group-item-danger'}).text(message));
        });
        // return true, indicating that there were errors
        return true;
    } else {
        // return false, indicating that there were no errors
        return false;
    }
}

function displayDVD(dvdId){
    $('#clickTitle').click(function(){
        $.ajax({
            type: 'GET',
            url : 'http://dvd-library.us-east-1.elasticbeanstalk.com/dvd/' + dvdId,
            success: function(data, status){
                showDVDTable();
            },
            error: function() {
                $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later.'));
            }
        })
    })
}