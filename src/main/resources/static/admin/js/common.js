function doPost(url, data, callback, errorCallback) {
    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(response) {
            callback(response);
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            if (errorCallback && typeof errorCallback === 'function') {
                errorCallback(xhr);
            } else {
                alert('An error occurred while processing your request.');
            }
        }
    });
}

function doPostForm(url, data, callback){
    const isFormData = data instanceof FormData;

    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        processData: !isFormData,
        contentType: isFormData ? false : 'application/x-www-form-urlencoded; charset=UTF-8',
        success: function(response) {
            callback(response);
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('An error occurred while processing your request.');
        }
    });
}

function doGet(url, callback) {
    $.ajax({
        url: url,
        type: 'GET',
        success: function(response) {
            callback(response);
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('An error occurred while processing your request.');
        }
    });
}

function toData(target){
    let data = {};
    $(target).find("input, select, textarea").each(function() {
        const name = $(this).attr("name");
        const value = $(this).val();
        if(name) {
            data[name] = value;
        }
    });   
    
    return data;
}

function createPagination(paging, onPageClick) {
    let page = paging.page;
    let dataCount = paging.dataCount;
    let pageCount = paging.pageCount;
    let totalDataCount = paging.totalDataCount;
    let pageList = paging.pageList;
    let firstPageNumber = paging.firstPageNumber;
    let lastPageNumber = paging.lastPageNumber;
    let startPageNumber = paging.startPageNumber;
    let endPageNumber = paging.endPageNumber;
    let previousPageNumber = paging.previousPageNumber;
    let nextPageNumber = paging.nextPageNumber;

    let pagination = $(".pagination");
    pagination.empty();

    $(pagination).append(`<span data="`+firstPageNumber+`">« 처음</span>`);
    $(pagination).append(`<span data="`+previousPageNumber+`">‹ 이전</span>`);
    pageList.forEach(function(pageNo) {
        $(pagination).append(`<span class="`+(pageNo === page ? 'current' : '')+`" data="`+pageNo+`">`+pageNo+`</span>`);
    });
    $(pagination).append(`<span data="`+nextPageNumber+`">다음 ›</span>`);
    $(pagination).append(`<span data="`+lastPageNumber+`">마지막 »</span>`);
    
    $(pagination).find("span").click(function() {
        let pageNo = $(this).attr("data");
        if(pageNo) {
            onPageClick(pageNo);
        }
    });
}

function nvl(value, defaultValue){
    if(defaultValue == null || defaultValue == undefined){
        defaultValue = "";
    }
    if(value == null || value == undefined || value == ""){
        return defaultValue;
    }
    else{
        return value;
    }
}

function showAlert(title, message, callbackOK) {
    let _container = $("<div class='alert'></div>");
    let _content = $("<div class='alert-content'></div>");
    let _title = $("<div class='alert-title'><h2>" + title + "</h2></div>");
    let _message = $("<div class='alert-message'><p>" + message + "</p></div>");
    let _buttons = $("<div class='alert-buttons'></div>");
    let _okButton = $("<button type='button' id='__alert-ok'>확인</button>");

    _buttons.append(_okButton);
    _content.append(_title);
    _content.append(_message);
    _content.append(_buttons);
    _container.append(_content);

    $(document.body).append(_container);

    _okButton.click(function() {
        if (callbackOK && typeof callbackOK === 'function') {
            callbackOK();
        }
        _container.remove();
    });
}

function showConfirm(title, message, callbackOK, callbackCancel) {
    let _container = $("<div class='alert'></div>");
    let _content = $("<div class='alert-content'></div>");
    let _title = $("<div class='alert-title'><h2>" + title + "</h2></div>");
    let _message = $("<div class='alert-message'><p>" + message + "</p></div>");
    let _buttons = $("<div class='alert-buttons'></div>");
    let _okButton = $("<button type='button' id='__alert-ok'>확인</button>");
    let _cancelButton = $("<button class='cancel' type='button' id='__alert-cancel'>취소</button>");

    _buttons.append(_okButton);
    _buttons.append(_cancelButton);
    _content.append(_title);
    _content.append(_message);
    _content.append(_buttons);
    _container.append(_content);

    $(document.body).append(_container);

    _okButton.click(function() {
        if (callbackOK && typeof callbackOK === 'function') {
            callbackOK();
        }
        _container.remove();
    });

    _cancelButton.click(function() {
        if (callbackCancel && typeof callbackCancel === 'function') {
            callbackCancel();
        }
        _container.remove();
    });
}