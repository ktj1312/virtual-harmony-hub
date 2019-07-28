$(document).ready(function () {
    var counter = 0;

    $("#addrow").on("click", function () {
        var newRow = $("<tr>");
        var cols = "";

        cols += '<td><input type="hidden" class="form-control" name="id"' + counter + '"/></td>';
        cols += '<td><input type="text" class="form-control" required name="name"' + counter + '"/></td>';
        cols += '<td><input type="text" class="form-control" required name="slug"' + counter + '"/></td>';
        cols += '<td><input type="text" class="form-control" required name="label"' + counter + '"/></td>';
        cols += '<td><input type="text" class="form-control" required name="type"' + counter + '"/></td>';
        cols += '<td><input type="text" class="form-control" required name="data"' + counter + '"/></td>';
        cols += '<td><input type="text" class="form-control" name="length"' + counter + '"/></td>';
        cols += '<td><input type="text" class="form-control" name="khz"' + counter + '"/></td>';
        cols += '<td><button type="button" class="ibtnDel btn btn-md btn-danger "' + counter + '">Delete</button></td>';

        newRow.append(cols);
        $("table.table-dark").append(newRow);
        counter++;
    });

    $("table.table-dark").on("click", ".ibtnDel", function (event) {
        $(this).closest("tr").remove();
        counter -= 1
    });

    $( "#commandsForm" ).submit(function( event ) {
        event.preventDefault();
        var url = $(this).attr( "action" );
        var data = $('#command_table').tableToJSON();
        console.log(data);

        var newFormData=[];
        jQuery('#command_table tr:not(:first)').each(function(i){
            var tb=jQuery(this);
            var obj={};
            tb.find('input').each(function(){
               obj[this.name]=this.value;
            });
            //obj['row']=i;
            newFormData.push(obj);
        });
        console.log(newFormData);

        $.ajax({
            url: url,
            method: "post",
            type: "json",
            contentType: "application/json",
            data: JSON.stringify(newFormData),
            success: function(data) {
                alert(data.result);
                location.reload();
            }
        });
    });
});

function calculateRow(row) {
    var price = +row.find('input[name^="price"]').val();
}

function calculateGrandTotal() {
    var grandTotal = 0;
    $("table.table-dark").find('input[name^="price"]').each(function () {
        grandTotal += +$(this).val();
    });
    $("#grandtotal").text(grandTotal.toFixed(2));
}

