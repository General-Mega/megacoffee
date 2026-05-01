function doPost(url, data, callback) {
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