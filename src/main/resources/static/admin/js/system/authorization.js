let selectedAuthSeq = null;
let selectedAuthName = null;
let menuList = [];

$(document).ready(function() {
    $(".authorization-list input[name='seq']").on("change", function() {
        let selected = $(this);
        let seq = selected.val();
        let name = selected.siblings("span").text();

        $("#__selected_authorization_name").text(name);


        doPost("/admin/system/authorization/" + seq + "/menus", {}
            , function(response) {
                // 메뉴 트리를 초기화하고 새로 렌더링
                let tree = $("#__menu_tree");
                tree.empty();

                let code = response.code;
                let message = response.message;
                let data = response.data;

                if(code === 200) {
                    if(!data || data.length === 0) {
                        $(tree).append('<div class="empty">메뉴가 없습니다.</div>');
                        return;
                    }

                    data.forEach(function(menu) {
                        $(tree).append(renderMenuNode(menu, 0));
                    });
                } else {
                    $(tree).append('<div class="empty">권한에 대한 메뉴 정보를 불러오지 못했습니다. 관리자에게 문의하세요.</div>');
                }
            }
        );
    });

    $(".authorization-list input[name='seq']").first().prop("checked", true);
    $(".authorization-list input[name='seq']:checked").trigger("change");

    $("#__btnSave").click(function() {
        let authSeq = $(".authorization-list input[name='seq']:checked").val();
        let menuSeqs = [];
        $("#__menu_tree input[type='checkbox']:checked").each(function() {
            menuSeqs.push($(this).val());
        });
    });
});

function renderMenuNode(menu, level) {
    let container = $("<div class='menu-node'></div>");
    container.css("padding-left", (level * 22) + "px");

    let checkbox = $("<input type='checkbox' class='menu-checkbox'>");
    checkbox.prop("checked", menu.checked === true || menu.checked === 1 || menu.checked === '1');
    checkbox.data("seq", menu.seq);
    checkbox.val(menu.seq);
    checkbox.change(function() {
        menu.checked = $(this).is(":checked");
    });

    let label = $("<span class='menu-label'></span>");
    label.text(menu.name + (menu.url ? ' (' + menu.url + ')' : ''));
    label.click(function() {
        checkbox.prop("checked", !checkbox.prop("checked")).trigger("change");
    });

    container.append(checkbox);
    container.append(label);

    if(menu.children && menu.children.length > 0) {
        let childContainer = $("<div class='menu-children'></div>");
        menu.children.forEach(function(child) {
            childContainer.append(renderMenuNode(child, level + 1));
        });
        container.append(childContainer);
    }

    return container;
}

function saveAuthMenu() {
    if(!selectedAuthSeq) {
        alert("먼저 권한을 선택해주세요.");
        return;
    }

    let checkedSeqs = [];
    $(".menu-checkbox:checked").each(function() {
        checkedSeqs.push($(this).data("seq"));
    });

    if(!confirm("선택한 메뉴 연결 정보를 저장하시겠습니까?\n기존 연결 정보는 모두 삭제되고 새로 등록됩니다.")) {
        return;
    }

    doPost("/admin/system/authorization/save", { authSeq: selectedAuthSeq, seqs: checkedSeqs }, function(response) {
        if(response.code === 200) {
            alert("저장이 완료되었습니다.");
            loadMenuTree(selectedAuthSeq);
        } else {
            alert("저장에 실패했습니다: " + response.message);
        }
    });
}

function escapeHtml(text) {
    return String(text)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');
}
