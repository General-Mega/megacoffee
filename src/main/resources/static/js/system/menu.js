let menuTree = [];
let selectedMenuData = null;

$(document).ready(function() {
    // 초기 메뉴 로드
    loadMenuTree();

    /**
     * 최상위 메뉴 추가 버튼 클릭 시
     */
    $("#__btnCreate").click(function() {
        selectedMenuData = null;
        openModal(null, null, 0);
    });


    /**
     * modal 저장 버튼 클릭 시
     */
    $("#__modal_btn_save").click(function() {
        let data = toData($("#__modal"));

        if(!data.name || data.name.trim() === "") {
            alert("메뉴명을 입력해주세요.");
            return;
        }

        let isNew = !data.seq || data.seq === "0";
        let confirmMsg = isNew ? "새로운 메뉴를 등록하시겠습니까?" : "메뉴 정보를 수정하시겠습니까?";

        if(confirm(confirmMsg)) {
            let url = isNew ? "/system/menu/append" : "/system/menu/modify";
            doPost(url, data, function(response) {
                let code = response.code;
                if(code === 200) {
                    alert(isNew ? "메뉴가 등록되었습니다." : "메뉴 정보가 수정되었습니다.");
                    closeModal();
                    loadMenuTree();
                } else {
                    alert((isNew ? "메뉴 등록" : "메뉴 수정") + "에 실패하였습니다: " + response.message);
                }
            });
        }
    });

    /**
     * 하위 메뉴 추가 버튼 클릭 시
     */
    $("#__modal_btn_addSub").click(function() {
        let parentMenu = selectedMenuData;
        let newDepth = parentMenu.depth + 1;
        openModal(null, parentMenu, newDepth);
    });

    /**
     * modal 취소 버튼 클릭 시
     */
    $("#__modal_btn_cancel").click(function() {
        closeModal();
    });
});

/**
 * 메뉴 트리 로드
 */
function loadMenuTree() {
    doPost("/system/menu/list", {}, function(response) {
        if(response.code === 200) {
            menuTree = response.data;
            renderMenuTree(menuTree);
        } else {
            alert("메뉴 로드에 실패했습니다.");
        }
    });
}

/**
 * 메뉴 트리 렌더링
 */
function renderMenuTree(menus) {
    let treeHtml = "";
    
    if(!menus || menus.length === 0) {
        $("#__tree_list").html('<div class="tree-empty">메뉴 데이터가 없습니다.</div>');
        return;
    }

    menus.forEach(function(menu) {
        treeHtml += renderTreeNode(menu, 0);
    });

    $("#__tree_list").html(treeHtml);
    attachTreeEventHandlers();
}

/**
 * 트리 노드 렌더링
 */
function renderTreeNode(menu, level) {
    let html = '<div class="tree-node" data-seq="' + menu.seq + '">';
    
    html += '<div class="tree-item">';
    html += '  <span class="tree-label" style="cursor: pointer;">' + escapeHtml(menu.name) + '</span>';
    html += '  <div class="tree-actions">';
    html += '    <button class="btn-action btn-edit" title="수정">✎</button>';
    html += '    <button class="btn-action btn-add-sub" title="하위 메뉴 추가">+</button>';
    html += '    <button class="btn-action btn-delete" title="삭제">✕</button>';
    html += '  </div>';
    html += '</div>';

    if(menu.children && menu.children.length > 0) {
        html += '<div class="tree-children">';
        menu.children.forEach(function(child) {
            html += renderTreeNode(child, level + 1);
        });
        html += '</div>';
    }

    html += '</div>';
    return html;
}

/**
 * 트리 이벤트 핸들러 연결
 */
function attachTreeEventHandlers() {
    // 트리 라벨 클릭 (수정)
    $(".tree-label").click(function(e) {
        e.stopPropagation();
        let seq = $(this).closest(".tree-node").data("seq");
        let menuData = findMenuBySeq(menuTree, seq);
        if(menuData) {
            selectedMenuData = menuData;
            openModal(menuData, menuData.parentSeq ? findMenuBySeq(menuTree, menuData.parentSeq) : null, menuData.depth);
        }
    });

    // 수정 버튼 클릭
    $(".btn-edit").click(function(e) {
        e.stopPropagation();
        let seq = $(this).closest(".tree-node").data("seq");
        let menuData = findMenuBySeq(menuTree, seq);
        if(menuData) {
            selectedMenuData = menuData;
            openModal(menuData, menuData.parentSeq ? findMenuBySeq(menuTree, menuData.parentSeq) : null, menuData.depth);
        }
    });

    // 하위 메뉴 추가 버튼 클릭
    $(".btn-add-sub").click(function(e) {
        e.stopPropagation();
        let seq = $(this).closest(".tree-node").data("seq");
        let parentMenu = findMenuBySeq(menuTree, seq);
        if(parentMenu) {
            selectedMenuData = parentMenu;
            let newDepth = parentMenu.depth + 1;
            openModal(null, parentMenu, newDepth);
        }
    });

    // 삭제 버튼 클릭
    $(".btn-delete").click(function(e) {
        e.stopPropagation();
        let seq = $(this).closest(".tree-node").data("seq");
        let menuData = findMenuBySeq(menuTree, seq);
        if(menuData) {
            if(menuData.children && menuData.children.length > 0) {
                alert("하위 메뉴가 있어서 삭제할 수 없습니다.\n먼저 하위 메뉴를 삭제해주세요.");
                return;
            }

            if(confirm("'" + menuData.name + "' 메뉴를 삭제하시겠습니까?")) {
                doPost("/system/menu/delete", [seq], function(response) {
                    if(response.code === 200) {
                        alert("메뉴가 삭제되었습니다.");
                        loadMenuTree();
                    } else {
                        alert("메뉴 삭제에 실패했습니다: " + response.message);
                    }
                });
            }
        }
    });
}

/**
 * seq로 메뉴 찾기 (재귀)
 */
function findMenuBySeq(menus, seq) {
    for(let i = 0; i < menus.length; i++) {
        if(menus[i].seq == seq) {
            return menus[i];
        }
        if(menus[i].children && menus[i].children.length > 0) {
            let found = findMenuBySeq(menus[i].children, seq);
            if(found) {
                return found;
            }
        }
    }
    return null;
}

/**
 * 모달 열기
 */
function openModal(data, parentMenu, depth) {
    $("#__modal").show();

    if(data != null && data != undefined) {
        // 수정 모드
        $("#__modal").find("#__modal_title").text("메뉴 수정");
        $("#__modal").find("input[name='seq']").val(data.seq);
        $("#__modal").find("input[name='parentSeq']").val(data.parentSeq || "");
        $("#__modal").find("input[name='depth']").val(data.depth);
        $("#__modal").find("input[name='name']").val(data.name);
        $("#__modal").find("input[name='url']").val(data.url || "");
        $("#__modal").find("input[name='matchUrl']").val(data.matchUrl || "");
        $("#__modal").find("input[name='sorting']").val(data.sorting || 0);
        $("#__modal").find("#__modal_parentName").val((parentMenu ? parentMenu.name : "최상위 메뉴") || "");
        $("#__modal").find("#__modal_btn_save").text("수정");
        
        // 하위 메뉴 추가 버튼 표시
        if(data.depth < 3) {  // 최대 3단계까지만 허용
            $("#__modal").find("#__modal_btn_addSub").show();
        } else {
            $("#__modal").find("#__modal_btn_addSub").hide();
        }
    } else {
        // 등록 모드
        $("#__modal").find("#__modal_title").text("메뉴 등록");
        $("#__modal").find("input[name='seq']").val("0");
        $("#__modal").find("input[name='parentSeq']").val(parentMenu ? parentMenu.seq : "");
        $("#__modal").find("input[name='depth']").val(depth);
        $("#__modal").find("input[name='name']").val("");
        $("#__modal").find("input[name='url']").val("");
        $("#__modal").find("input[name='matchUrl']").val("");
        $("#__modal").find("input[name='sorting']").val(0);
        $("#__modal").find("#__modal_parentName").val((parentMenu ? parentMenu.name : "최상위 메뉴") || "");
        $("#__modal").find("#__modal_btn_save").text("등록");
        
        // 하위 메뉴 추가 버튼 숨김
        $("#__modal").find("#__modal_btn_addSub").hide();
    }
}

/**
 * 모달 닫기
 */
function closeModal() {
    $("#__modal").hide();
    selectedMenuData = null;
    $("#__modal").find("input[name='seq']").val("0");
    $("#__modal").find("input[name='name']").val("");
}

/**
 * HTML 특수문자 이스케이프
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}