let isUserIdChecked = false;
let originalUserId = "";
let checkedUserId = "";

$(document).ready(function() {
    /**
     * 검색 버튼 클릭 시
     */
    $("#__btn_search").click(function() {
        let data = toData($("div.search-section"));
        doPost("/admin/system/user/list", data, function(response) {
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
            doPost("/admin/system/user/delete", seqs, function(response) {
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

        if(!validateUserForm(data, isNew)) {
            return;
        }

        if(isNew) {
            if(!isUserIdChecked || checkedUserId !== (data.userId || "").trim()) {
                alert("아이디 중복 확인을 먼저 진행해 주세요.");
                return;
            }

            if(confirm("새로운 사용자를 등록하시겠습니까?")) {
                submitAppend(data);
            }
        } else {
            if(confirm("사용자 정보를 수정하시겠습니까?")) {
                submitModify(data);
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
     * 아이디 중복 확인 버튼 클릭 시
     */
    $("#__modal_btn_idCheck").click(function() {
        const userId = $("#__modal_userId").val().trim();
        if(userId === "") {
            setMessage("__modal_userId_message", "아이디를 입력해주세요.", true);
            return;
        }

        checkUserIdDuplicate(userId, function(isDuplicate) {
            if(isDuplicate) {
                setMessage("__modal_userId_message", "이미 사용 중인 아이디입니다.", true);
                isUserIdChecked = false;
                checkedUserId = "";
                updateSaveButtonState();
            } else {
                setMessage("__modal_userId_message", "사용 가능한 아이디입니다.", false);
                isUserIdChecked = true;
                checkedUserId = userId;
                updateSaveButtonState();
            }
        });
    });

    $("#__modal_userId").on("input", function() {
        const currentUserId = $(this).val().trim();
        if(currentUserId !== checkedUserId) {
            isUserIdChecked = false;
            setMessage("__modal_userId_message", "", false);
            updateSaveButtonState();
        }
    });

    $("#__modal_password").on("input", function() {
        validatePasswordMessage();
    });

    $("#__modal_passwordConfirm").on("input", function() {
        validatePasswordConfirmMessage();
    });

    /**
     * 전체 선택 체크박스
     */
    $("#__select_all").change(function() {
        let isChecked = $(this).is(":checked");
        $(".table-container table tbody input[type='checkbox']").prop("checked", isChecked);
    });

    // 페이지 로드 시 모달 닫기
    $("#__modal_btn_cancel").click(); 

    // 초기 목록 조회
    $("#__btn_search").click();
});

function openModal(data) {
    $("#__modal").show();

    if(data != null && data != undefined) {
        originalUserId = data.userId || "";
        isUserIdChecked = true;
        checkedUserId = originalUserId;

        $("#__modal_title").text("사용자 수정");
        $("#__modal").find("input[name='seq']").val(data.seq);
        $("#__modal_userId").val(data.userId);
        $("#__modal_name").val(data.name);
        $("#__modal_authSeq").val(data.authSeq);
        $("#__modal_password").val("");
        $("#__modal_passwordConfirm").val("");
        $("#__modal_btn_save").text("수정");
        setMessage("__modal_userId_message", "", false);
        setMessage("__modal_password_message", "", false);
        setMessage("__modal_passwordConfirm_message", "", false);
        updateSaveButtonState();
        $("#__modal_userId").attr("disabled", "disabled");
        $("#__modal_btn_idCheck").hide();
    } else {
        originalUserId = "";
        isUserIdChecked = false;
        checkedUserId = "";

        $("#__modal_title").text("사용자 등록");
        $("#__modal").find("input[name='seq']").val("0");
        $("#__modal_userId").val("");
        $("#__modal_name").val("");
        $("#__modal_authSeq").val("");
        $("#__modal_password").val("");
        $("#__modal_passwordConfirm").val("");
        $("#__modal_passwordReset").val("0");
        $("#__modal_btn_save").text("등록");
        setMessage("__modal_userId_message", "", false);
        setMessage("__modal_password_message", "", false);
        setMessage("__modal_passwordConfirm_message", "", false);
        $("#__modal_userId").removeAttr("disabled");
        $("#__modal_btn_idCheck").show();
        updateSaveButtonState();
    }
}

function closeModal() {
    $("#__modal").hide();
    $("#__modal").find("input[name='seq']").val("0");
    $("#__modal_userId").val("");
    $("#__modal_name").val("");
    $("#__modal_authSeq").val("");
    $("#__modal_password").val("");
    $("#__modal_passwordConfirm").val("");
    $("#__modal_passwordReset").val("0");
}

function setMessage(elementId, message, isError) {
    const messageElement = $("#" + elementId);
    messageElement.text(message);
    if(message === "") {
        messageElement.css("color", "");
    } else if(isError) {
        messageElement.css("color", "#d32f2f");
    } else {
        messageElement.css("color", "#2e7d32");
    }
}

function updateSaveButtonState() {
    const saveButton = $("#__modal_btn_save");
    const isNew = $("#__modal").find("input[name='seq']").val() === "0";
    if(isNew) {
        saveButton.prop("disabled", !isUserIdChecked);
    } else {
        const currentUserId = $("#__modal_userId").val().trim();
        saveButton.prop("disabled", currentUserId !== originalUserId && !isUserIdChecked);
    }
}

function validatePasswordMessage() {
    const password = $("#__modal_password").val();
    const passwordPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,20}$/;
    if(password === "") {
        setMessage("__modal_password_message", "", false);
        return;
    }
    if(passwordPattern.test(password)) {
        setMessage("__modal_password_message", "사용 가능한 비밀번호 형식입니다.", false);
    } else {
        setMessage("__modal_password_message", "비밀번호는 대문자, 소문자, 숫자, 특수문자를 모두 포함한 8자 이상 20자 이하이어야 합니다.", true);
    }
}

function validatePasswordConfirmMessage() {
    const password = $("#__modal_password").val();
    const passwordConfirm = $("#__modal_passwordConfirm").val();
    if(passwordConfirm === "") {
        setMessage("__modal_passwordConfirm_message", "", false);
        return;
    }
    if(password === passwordConfirm) {
        setMessage("__modal_passwordConfirm_message", "비밀번호가 일치합니다.", false);
    } else {
        setMessage("__modal_passwordConfirm_message", "비밀번호 확인이 일치하지 않습니다.", true);
    }
}

function validateUserForm(data, isNew) {
    const userId = (data.userId || "").trim();
    const password = data.password || "";
    const passwordConfirm = data.passwordConfirm || "";
    const passwordPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,20}$/;

    if(userId === "") {
        alert("아이디를 입력해주세요.");
        return false;
    }

    if(isNew && password === "") {
        setMessage("__modal_password_message", "비밀번호를 입력해주세요.", true);
        alert("비밀번호를 입력해주세요.");
        return false;
    }

    if(password !== "") {
        if(!passwordPattern.test(password)) {
            setMessage("__modal_password_message", "비밀번호는 대문자, 소문자, 숫자, 특수문자를 모두 포함한 8자 이상 20자 이하이어야 합니다.", true);
            alert("비밀번호는 대문자, 소문자, 숫자, 특수문자를 모두 포함한 8자 이상 20자 이하로 입력해야 합니다.");
            return false;
        }

        setMessage("__modal_password_message", "사용 가능한 비밀번호 형식입니다.", false);

        if(password !== passwordConfirm) {
            setMessage("__modal_passwordConfirm_message", "비밀번호 확인이 일치하지 않습니다.", true);
            alert("비밀번호 확인이 일치하지 않습니다.");
            return false;
        }
        setMessage("__modal_passwordConfirm_message", "비밀번호가 일치합니다.", false);
    }

    return true;
}

function checkUserIdDuplicate(userId, callback) {
    if(!userId || userId.trim() === "") {
        callback(false);
        return;
    }

    doPost("/admin/system/user/idCheck", { userId: userId.trim() }, function(response) {
        if(response.code === 200) {
            callback(response.data === true);
        } else {
            alert("아이디 중복 확인 중 오류가 발생했습니다: " + response.message);
            callback(true);
        }
    });
}

function submitAppend(data) {
    doPost("/admin/system/user/append", data, function(response) {
        if(response.code === 200) {
            alert("사용자가 등록되었습니다.");
            $("#__btn_search").click();
            closeModal();
        } else {
            alert("등록에 실패하였습니다: " + response.message);
        }
    });
}

function submitModify(data) {
    doPost("/admin/system/user/modify", data, function(response) {
        if(response.code === 200) {
            alert("사용자 정보가 수정되었습니다.");
            $("#__btn_search").click();
            closeModal();
        } else {
            alert("수정에 실패하였습니다: " + response.message);
        }
    });
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
        $(tr).append("<td for='__checkbox_" + item.seq + "'><input id='__checkbox_" + item.seq + "' type='checkbox' value='" + item.seq + "'></td>");
        $(tr).append("<td>" + count + "</td>");
        $(tr).append("<td class='left'>" + item.userId + "</td>");
        $(tr).append("<td class='left'>" + item.name + "</td>");
        $(tr).append("<td>" + nvl(item.authName) + "</td>");
        $(tr).append("<td>" + nvl(item.mobile) + "</td>");
        $(tr).append("<td>" + nvl(item.email) + "</td>");
        $(tr).append("<td>" + nvl(item.lastLoginDate) + "</td>");
        $(tr).append("<td>" + item.createDatetime + "</td>");
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
