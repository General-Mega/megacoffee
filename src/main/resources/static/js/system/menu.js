$(document).ready(function() {
    /**
     * 검색 버튼 클릭 시
     */
    $("#__btn_search").click(function() {
        let data = toData($("div.search-section"));
        console.log("검색 데이터:", data);
        doPost("/system/auth/list", data, function(response) {
            createList(response);
        });
    });

    /**
     * 등록 버튼 클릭 시
     */
    $("#__btnCreate").click(function() {
        openModal(null);
    });

    /**
     * 삭제 버튼 클릭 시
     */
    $("#__btnDelete").click(function() {
        let seqs = [];
        $(".table-container table tbody input[type='checkbox']:checked").each(function() {
            seqs.push($(this).val());
        });

        if(seqs.length === 0) {
            alert("삭제할 항목을 선택해주세요.");
            return;
        }

        if(confirm("선택한 항목을 삭제하시겠습니까?")) {

            doPost("/system/auth/delete", seqs
                , function(response) {
                    let code = response.code;
                    if(code === 200) {
                        alert("선택한 항목이 삭제되었습니다.");

                        // 삭제 결과 처리
                        $("#__btn_search").click(); // 삭제 후 목록 새로고침
                    } else {
                        alert("항목 삭제에 실패하였습니다: " + response.message);
                    }
                }
            );
        }
    });

    /**
     * modal 저장 버튼 클릭 시
     */
    $("#__modal_btn_save").click(function(event) {
        let data = toData($("#__modal"));

        let isNew = false;

        if(data.seq === null || data.seq === undefined || data.seq === "0") {
            isNew = true;
        }

        if(isNew){
            if(confirm("새로운 권한을 등록하시겠습니까?")) {
                doPost("/system/auth/append", data
                    , function(response) {
                        let code = response.code;
                        if(code === 200) {
                            alert("권한이 등록되었습니다.");
    
                            // 저장 결과 처리
                            $("#__btn_search").click(); // 저장 후 목록 새로고침
                            closeModal(); // 모달 닫기
                        } else {
                            alert("권한 등록에 실패하였습니다: " + response.message);
                        }
                    }
                );
            }
        }
        else{
            if(confirm("권한 정보를 수정하시겠습니까?")) {
                doPost("/system/auth/modify", data
                    , function(response) {
                        let code = response.code;
                        if(code === 200) {
                            alert("권한 정보가 수정되었습니다.");
    
                            // 저장 결과 처리
                            $("#__btn_search").click(); // 저장 후 목록 새로고침
                            closeModal(); // 모달 닫기
                        } else {
                            alert("권한 수정에 실패하였습니다: " + response.message);
                        }
                    }
                );
            }
        }

    });

    /**
     * modal 취소 버튼 클릭 시
     */
    $("#__modal_btn_cancel").click(function() {
        closeModal();
    });

    /** 
     * 목록의 전체 선택 체크박스 변경 시 
     * 
    */
    $("#__select_all").change(function() {
        let isChecked = $(this).is(":checked");
        $(".table-container table tbody input[type='checkbox']").prop("checked", isChecked);
    });

    // 페이지 로드 시 모달 닫기
    $("#__modal_btn_cancel").click(); 

    $("#__btn_search").click(); // 페이지 로드 시 목록 불러오기
});

/** 
 * 모달 열기
 */
function openModal(data){
    $("#__modal").show();

    if(data != null && data != undefined){
        $("#__modal").find("#__modal_title").text("권한 수정");
        $("#__modal").find("input[name='seq']").val(data.seq);
        $("#__modal").find("input[name='name']").val(data.name);
        $("#__modal").find("input[name='sorting']").val(data.sorting);
        $("#__modal").find("#__modal_btn_save").text("수정");
    }
    else{
        $("#__modal").find("#__modal_title").text("권한 등록");
        $("#__modal").find("input[name='seq']").val("0");
        $("#__modal").find("input[name='name']").val("");
        $("#__modal").find("input[name='sorting']").val("0");
        $("#__modal").find("#__modal_btn_save").text("등록");
    }
}

/** 
 * 모달 닫기
 */
function closeModal(){
    $("#__modal").hide();

    $("#__modal").find("input[name='seq']").val("0");
    $("#__modal").find("input[name='name']").val("");
}

function createList(result){
    let code = result.code;
    let message = result.message;
    let list = result.data;
    let paging = result.paging;

    // 목록 정보가 없으면
    if(list == null || list.length == 0){
        $(".table-container table tbody").html("<tr><td colspan='5'>데이터가 없습니다.</td></tr>");
        return;
    }

    // 목록 정보가 있으면 테이블에 추가
    $(".table-container table tbody").empty();
    let count = paging.totalDataCount - ((paging.page - 1) * paging.dataCount);
    list.forEach(function(item) {
        let tr = $("<tr></tr>");
        $(tr).append("<td for='__checkbox_" + item.seq + "'><input id='__checkbox_" + item.seq + "' type='checkbox' value='" + item.seq + "'></td>");
        $(tr).append("<td>" + count + "</td>");
        $(tr).append("<td class='left'>" + item.name + "</td>");
        $(tr).append("<td>" + item.sorting + "</td>");
        $(tr).append("<td>" + item.createDatetime + "</td>");
        $(tr).append("<td>" + item.createUserName + "</td>");
        $(tr).append("<td>" + nvl(item.updateDatetime) + "</td>");
        $(tr).append("<td>" + nvl(item.updateUserName) + "</td>");
        $(".table-container table tbody").append(tr);

        $(tr).find("td:not(:first-child)").click(function() {
            openModal(item);
        });

        count--;
    });

    $(".table-container table tbody input[type='checkbox']").click(function() {
        let allChecked = $(".table-container table tbody input[type='checkbox']").length === $(".table-container table tbody input[type='checkbox']:checked").length;
        $("#__select_all").prop("checked", allChecked);
    });

    // 페이징 정보로 페이지네이션 생성
    createPagination(paging, function(page) {

        $(".search-section input[name='page']").val(page); // 페이지 번호 업데이트
        $("#__btn_search").click(); // 페이지 번호 클릭 시 검색 버튼 클릭하여 목록 새로고침
    });
}