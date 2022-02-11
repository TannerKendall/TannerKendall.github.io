
$(document).ready(function(){
    
    loadItems();
    resetMoney();
    addMoney();
    makePurchase();
    returnChange();

})

function loadItems(){
    $("#itemsDiv").empty();
    var itemDivRef = $('#itemsDiv');

    $.ajax({
        type: 'GET',
        url: 'http://vending.us-east-1.elasticbeanstalk.com/items',
        success: function(itemArray){
            $.each(itemArray, function(index, item){
                var id = item.id;
                var name = item.name;
                var price = item.price.toFixed(2);
                var quantity = item.quantity;

                    var itemDiv = '<div class="itemDiv col-md-4" style="padding: 2px; border: 1px solid #021a40;" id="item' + id + '" onclick="itemIdHelper(\'' + name + '\', ' + id + '); resetMessages()"><p>';
                        itemDiv += id + '</span>';
                        itemDiv += '<br><div style="text-align: center;">'
                        itemDiv += name;
                        itemDiv += '<br><br>';
                        itemDiv += '$' + price;
                        itemDiv += '<br><br>';
                        itemDiv += 'Quantity Left: ' + quantity;
                        itemDiv += '</div></p></div>';
                    itemDivRef.append(itemDiv);
    

            });
        },
        error: function(){
            $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later.'));
        }
    });
}

function addMoney(){

    $('#dollar-button').click(function(event){
        
        var moneyString = $('#money-amount-field').val();
        var money = parseFloat(moneyString);
        var total = money + 1;
        $('#money-amount-field').val(total.toFixed(2));
        
    });

    $('#quarter-button').click(function(event){
        
        var moneyString = $('#money-amount-field').val();
        var money = parseFloat(moneyString);
        var total = money + .25;
        $('#money-amount-field').val(total.toFixed(2));

    });

    $('#dime-button').click(function(event){
        var moneyString = $('#money-amount-field').val();
        var money = parseFloat(moneyString);
        var total = money + .10;
        $('#money-amount-field').val(total.toFixed(2));
    });

    $('#nickel-button').click(function(event){
        var moneyString = $('#money-amount-field').val();
        var money = parseFloat(moneyString);
        var total = money + .05;
        $('#money-amount-field').val(total.toFixed(2));
    });

}

function addMoney2(coinTypeValue){

    var moneyString = $('#money-amount-field').val();
    var money = parseFloat(moneyString);
    var total = money + coinTypeValue;
    $('#money-amount-field').val(total.toFixed(2));

}

function resetMoney(){
    $('#money-amount-field').val('0.00');
}
function resetMessages(){
    $('#message-field').val('')
}

function returnChange(){

    $('#return-change-button').click(function(event){
        var userTotal = $('#money-amount-field').val();
        var quartersReturned = 0;
        var dimesReturned = 0;
        var nickelsReturned = 0;
        var penniesReturned = 0;

        if($('#change-field').val() != 'undefined'){
            loadItems();
            $('#item-field').val('');
            resetMessages();
        }

        if(userTotal > 0.00){
            var userTotal = $('#money-amount-field').val();
            var temp = userTotal * 100;
            var totalPennies = temp.toFixed(2);

            while(totalPennies >= 25){
                quartersReturned++;
                totalPennies -= 25;
            }

            while(totalPennies >= 10){
                dimesReturned++;
                totalPennies -= 10;
            }

            while(totalPennies >= 5){
                nickelsReturned++;
                totalPennies -= 5;
            }

            while(totalPennies >= 1){
                penniesReturned++;
                totalPennies -= 1;
            }

            resetMoney();
        }

        formatChangeReturned(quartersReturned, dimesReturned, nickelsReturned, penniesReturned);
    });

}

function formatChangeReturned(quarters, dimes, nickels, pennies){

    var changeMessage = '';
    var coinsLeft = quarters + dimes + nickels + pennies;

    coinsLeft -= quarters;
    if(quarters == 1 & coinsLeft > 0){
        changeMessage += quarters + ' Quarter, ';
    } else if(quarters == 1 & coinsLeft == 0){
        changeMessage += quarters + ' Quarter';
    }  else if(quarters > 1 & coinsLeft > 0){
        changeMessage += quarters + ' Quarters, ';
    } else if(quarters > 1 & coinsLeft == 0){
        changeMessage += quarters + ' Quarters';
    }

    coinsLeft -= dimes;
    if(dimes == 1 & coinsLeft > 0){
        changeMessage += dimes + ' Dime, ';
    } else if(dimes == 1 & coinsLeft == 0){
        changeMessage += dimes + ' Dime';
    }  else if(dimes > 1 & coinsLeft > 0){
        changeMessage += dimes + ' Dimes, ';
    } else if(dimes > 1 & coinsLeft == 0){
        changeMessage += dimes + ' Dimes';
    }

    coinsLeft -= nickels;
    if(nickels == 1 & coinsLeft > 0){
        changeMessage += nickels + ' Nickel, '
    } else if(nickels == 1 & coinsLeft == 0){
        changeMessage += nickels + ' Nickel'
    }

    if(pennies == 1){
        changeMessage += pennies + ' Penny';
    } else if(pennies > 1){
        changeMessage += pennies + ' Pennies';
    }

    $('#change-field').val(changeMessage);

}

function itemIdHelper(itemName, itemId){

    $('#item-field').val('(' + itemId + ') ' + itemName);
    $('#change-field').val('');
    $('#hiddenId').val(itemId);

}

function makePurchase(){

    $('#make-purchase-button').click(function (event) {

        var moneyAmount = $('#money-amount-field').val();
        var itemId = $('#hiddenId').val();

        $.ajax({
            type: 'POST',
            url: 'http://vending.us-east-1.elasticbeanstalk.com/money/' + moneyAmount + '/item/' + itemId,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function(data) {
                var quarters = data.quarters;
                var dimes = data.dimes;
                var nickels = data.nickels;
                var pennies = data.pennies;

                formatChangeReturned(quarters, dimes, nickels, pennies);
                $('#message-field').val('THANK YOU!!!');
                $('#money-amount-field').val('0.00');
                loadItems();
                
            },
            error: function(xhr, status, error) {
                var responseText = $.parseJSON(xhr.responseText);
                $('#message-field').val(responseText.message);

            }
        });

    });

}