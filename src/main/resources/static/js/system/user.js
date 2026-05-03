$(document).ready(function() {
    /**
     * 검색 버튼 클릭 시
     */
    $("#__btn_search").click(function() {
        let data = toData($("div.search-section"));
        doPost("/system/user/list", data, function(response) {
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
            doPost("/system/user/delete", seqs, function(response) {
                let code = response.code;
                if(code === 200) {
                    alert("선택한 항목이 삭제되었습니다.");
                    $("#__btn_search").click();
                } else {
                    alert("삭제에 실패하였습니다: " + response.message);
                }
            });
        }
    });

    /**
     * modal 저장 버튼 클릭 시
     */
    $("#__modal_btn_save").click(function(event) {
        let data = toData($("#__modal"));

        let isNew = false;
        if(data.seq === null || data.seq === undefined || data.seq === "0" || data.seq === "") {
            isNew = true;
        }

        if(isNew) {
            if(confirm("새로운 사용자를 등록하시겠습니까?")) {
                doPost("/system/user/append", data, function(response) {
                    if(response.code === 200) {
                        alert("사용자가 등록되었습니다.");
                        $("#__btn_search").click();
                        closeModal();
                    } else {
                        alert("등록에 실패하였습니다: " + response.message);
                    }
                });
            }
        } else {
            if(confirm("사용자 정보를 수정하시겠습니까?")) {
                doPost("/system/user/modify", data, function(response) {
                    if(response.code === 200) {
                        alert("사용자 정보가 수정되었습니다.");
                        $("#__btn_search").click();
                        closeModal();
                    } else {
                        alert("수정에 실패하였습니다: " + response.message);
                    }
                });
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
     * 전체 선택 체크박스
     */
    $("#__select_all").change(function() {
        let isChecked = $(this).is(":checked");
        $(".table-container table tbody input[type='checkbox']").prop("checked", isChecked);
    });

    // 초기 목록 조회
    $("#__btn_search").click();
});

function openModal(data) {
    $("#__modal").show();

    if(data != null && data != undefined) {
        $("#__modal_title").text("사용자 수정");
        $("#__modal").find("input[name='seq']").val(data.seq);
        $("#__modal_userId").val(data.userId);
        $("#__modal_name").val(data.name);
        $("#__modal_authSeq").val(data.authSeq);
        $("#__modal_password").val("");
        $("#__modal_passwordReset").val(data.passwordReset ? "1" : "0");
        $("#__modal_btn_save").text("수정");
    } else {
        $("#__modal_title").text("사용자 등록");
        $("#__modal").find("input[name='seq']").val("0");
        $("#__modal_userId").val("");
        $("#__modal_name").val("");
        $("#__modal_authSeq").val("");
        $("#__modal_password").val("");
        $("#__modal_passwordReset").val("0");
        $("#__modal_btn_save").text("등록");
    }
}

function closeModal() {
    $("#__modal").hide();
    $("#__modal").find("input[name='seq']").val("0");
    $("#__modal_userId").val("");
    $("#__modal_name").val("");
    $("#__modal_authSeq").val("");
    $("#__modal_password").val("");
    $("#__modal_passwordReset").val("0");
}

function createList(result) {
    let list = result.data;
    let paging = result.paging;

    if(list == null || list.length === 0) {
        $(".table-container table tbody").html("<tr><td colspan='11'>데이터가 없습니다.</td></tr>");
        return;
    }

    $(".table-container table tbody").empty();
    let count = paging.totalDataCount - ((paging.page - 1) * paging.dataCount);
    list.forEach(function(item) {
        let tr = $("<tr></tr>");
        $(tr).append("<td><input type='checkbox' value='" + item.seq + "'></td>");
        $(tr).append("<td>" + count + "</td>");
        $(tr).append("<td>" + nvl(item.userId) + "</td>");
        $(tr).append("<td>" + nvl(item.name) + "</td>");
        $(tr).append("<td>" + nvl(item.authName) + "</td>");
        $(tr).append("<td>" + (item.passwordReset ? "초기화 필요" : "정상") + "</td>");
        $(tr).append("<td>" + nvl(item.lastLoginDatetime) + "</td>");
        $(tr).append("<td>" + nvl(item.createDatetime) + "</td>");
        $(tr).append("<td>" + nvl(item.createUserName) + "</td>");
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

    createPagination(paging, function(page) {
        $(".search-section input[name='page']").val(page);
        $("#__btn_search").click();
    });
}
