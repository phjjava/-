// 最大化时保存弹窗的位置大小
var preDialogWidth = 0; 
var preDialogHeight = 0;
var preDialogLeft = "0px";
var preDialogTop = "0px";
// 页面初始化
$(function () {
    // 常用功能
    $("#btn_show_dialog").bind("click", showDialog);
    $("#btn_show_dialog1").bind("click", showDialog1);
    $("#btn_close").bind("click", hideDialog);
    $("#hide").bind("click", hideDialog);
    $("#btn_close1").bind("click", hideDialog1); 
    $("#btn_close2").bind("click", hideDialog2); 
    $("#hide1").bind("click", hideDialog1);
    $("#hide2").bind("click", hideDialog2);
   /* $("#dlg_submit").bind("click", submitHandler);
*/
    // 移动
    $("#dialog").bind("mousedown", moveHandler);
    $("#usershow").bind("mousedown", moveHandler);

    // 最大化 || 还原
    $("#maxShow").bind("click", maxDialog);
    $(".dlg_btn_reback_top").bind("click", rebackDialog);
    $("#maxShow1").bind("click", maxDialog1);
    $(".dlg_btn_reback_top1").bind("click", rebackDialog1);
    $("#maxShow2").bind("click", maxDialog2);
    $(".dlg_btn_reback_top2").bind("click", rebackDialog2);
    $("#maxShow3").bind("click", maxDialog3);
    $(".dlg_btn_reback_top3").bind("click", rebackDialog3);
});

// 还原
function rebackDialog() {
	$("#half").show();
    el_dialog = $("#dialog")[0];
    el_dialog.style.left = preDialogLeft;
    el_dialog.style.top = preDialogTop;
    el_dialog.style.width = preDialogWidth + "px";
    el_dialog.style.height = preDialogHeight + "px";

    $(this).blur();
    $(this).removeClass("dlg_btn_reback_top").addClass("dlg_btn_max_top");
    $(".dlg_btn_max_top").unbind("click").bind("click", maxDialog);
}
function rebackDialog1() {
	$("#half").show();
    el_dialog = $("#usershow")[0];
    el_dialog.style.left = preDialogLeft;
    el_dialog.style.top = preDialogTop;
    el_dialog.style.width = preDialogWidth + "px";
    el_dialog.style.height = preDialogHeight + "px";

    $(this).blur();
    $(this).removeClass("dlg_btn_reback_top1").addClass("dlg_btn_max_top1");
    $(".dlg_btn_max_top1").unbind("click").bind("click", maxDialog1);
}
function rebackDialog2() {
	$("#half").show();
    el_dialog = $("#dialog1")[0];
    el_dialog.style.left = preDialogLeft;
    el_dialog.style.top = preDialogTop;
    el_dialog.style.width = preDialogWidth + "px";
    el_dialog.style.height = preDialogHeight + "px";

    $(this).blur();
    $(this).removeClass("dlg_btn_reback_top2").addClass("dlg_btn_max_top2");
    $(".dlg_btn_max_top2").unbind("click").bind("click", maxDialog2);
}
function rebackDialog3() {
	$("#half").show();
    el_dialog = $("#usershow1")[0];
    el_dialog.style.left = preDialogLeft;
    el_dialog.style.top = preDialogTop;
    el_dialog.style.width = preDialogWidth + "px";
    el_dialog.style.height = preDialogHeight + "px";

    $(this).blur();
    $(this).removeClass("dlg_btn_reback_top3").addClass("dlg_btn_max_top3");
    $(".dlg_btn_max_top3").unbind("click").bind("click", maxDialog3);
}
// 最大化
function maxDialog() {
	$("#half").show();
    el_dialog = $("#dialog")[0];
    preDialogWidth = el_dialog.offsetWidth;
    preDialogHeight = el_dialog.offsetHeight;
    preDialogLeft = el_dialog.style.left;
    preDialogTop = el_dialog.style.top;
    el_dialog.style.left = 0 + "px";
    el_dialog.style.top = 0 + "px";
    el_dialog.style.width = window.innerWidth - 205 + "px";
    el_dialog.style.height = window.innerHeight - 5 + "px";
    $(this).blur();
    $(this).removeClass("dlg_btn_max_top").addClass("layui-layer-maxmin"); 
    $(".layui-layer-maxmin").unbind("click").bind("click", rebackDialog);  
}
function maxDialog1() {
	$("#half").show();
    el_dialog = $("#usershow")[0];
    preDialogWidth = el_dialog.offsetWidth;
    preDialogHeight = el_dialog.offsetHeight;
    preDialogLeft = el_dialog.style.left;
    preDialogTop = el_dialog.style.top;
    el_dialog.style.left = 0 + "px";
    el_dialog.style.top = 0 + "px";
    el_dialog.style.width = window.innerWidth - 205 + "px";
    el_dialog.style.height = window.innerHeight - 5 + "px";
    $(this).blur();
    $(this).removeClass("dlg_btn_max_top1").addClass("layui-layer-maxmin1"); 
    $(".layui-layer-maxmin1").unbind("click").bind("click", rebackDialog1);  
}
function maxDialog2() {
	$("#half").show();
    el_dialog = $("#dialog1")[0];
    preDialogWidth = el_dialog.offsetWidth;
    preDialogHeight = el_dialog.offsetHeight;
    preDialogLeft = el_dialog.style.left;
    preDialogTop = el_dialog.style.top;
    el_dialog.style.left = 0 + "px";
    el_dialog.style.top = 0 + "px";
    el_dialog.style.width = window.innerWidth - 205 + "px";
    el_dialog.style.height = window.innerHeight - 5 + "px";
    $(this).blur();
    $(this).removeClass("dlg_btn_max_top2").addClass("layui-layer-maxmin2"); 
    $(".layui-layer-maxmin2").unbind("click").bind("click", rebackDialog2);  
}
function maxDialog3() {
	$("#half").show();
    el_dialog = $("#usershow1")[0];
    preDialogWidth = el_dialog.offsetWidth;
    preDialogHeight = el_dialog.offsetHeight;
    preDialogLeft = el_dialog.style.left;
    preDialogTop = el_dialog.style.top;
    el_dialog.style.left = 0 + "px";
    el_dialog.style.top = 0 + "px";
    el_dialog.style.width = window.innerWidth - 205 + "px";
    el_dialog.style.height = window.innerHeight - 5 + "px";
    $(this).blur();
    $(this).removeClass("dlg_btn_max_top3").addClass("layui-layer-maxmin3"); 
    $(".layui-layer-maxmin3").unbind("click").bind("click", rebackDialog3);  
}
// 移动
function moveHandler(evt) {
    var $trgt = $(event.target);
    if (!$trgt.hasClass("dlg_top")) return;

    var $this = $(this);
    var el = $this[0];
    var oevent = evt || event;
    var distanceX = oevent.clientX - el.offsetLeft;
    var distanceY = oevent.clientY - el.offsetTop;
    $(document).bind("mousemove", function (evt) {
        var oevent = evt || event;
        el.style.left = oevent.clientX - distanceX + 'px';
        el.style.top = oevent.clientY - distanceY + 'px';
    });
    $(document).bind("mouseup", function () {
        $(document).unbind("mousemove");
        $(document).unbind("mouseup");
    });
}
// 显示弹窗
function showDialog() {
    $("#dialog").show();
    $("#half").show();
}
function showDialog1() {
    $("#dialog1").show();
    $("#half").show();
}
// 隐藏弹窗
function hideDialog() {
    $("#dialog").hide();
    $("#half").hide();
}
function hideDialog1() {
    $("#usershow").hide();
    $("#half").hide();
}
function hideDialog2() {
    $("#dialog1").hide();
    $("#half").hide();
}
// 提交事件
function submitHandler() {
    alert("submit success!");
}
// 拖拽缩放：支持右拉 || 下拉 || 右下拉
window.onload = function () {
    var el_dlg_right_bottom = document.getElementById("dlg_right_bottom");
    var el_dialog = document.getElementById("dialog");
    var minHeight = $(el_dialog).attr("minheight");
    var minWidth = $(el_dialog).attr("minwidth");
    var right = document.getElementById("dlg_right");
    var bottom = document.getElementById("dlg_bottom");
    var mouseStart = {};
    var divStart = {};
    var rightStart = {};
    var bottomStart = {};
    // drag from right
    right.onmousedown = function (ev) {
        var oEvent = ev || event;
        mouseStart.x = oEvent.clientX;
        mouseStart.y = oEvent.clientY;
        rightStart.x = right.offsetLeft;
        if (right.setCapture) {
            right.onmousemove = doDragToRightBottomToRight;
            right.onmouseup = stopDragToRightBottomToRight;
            right.setCapture();
        }
        else {
            document.addEventListener("mousemove", doDragToRightBottomToRight, true);
            document.addEventListener("mouseup", stopDragToRightBottomToRight, true);
        }
    };
    function doDragToRightBottomToRight(ev) {
        var oEvent = ev || event;
        var l = oEvent.clientX - mouseStart.x + rightStart.x;
        var w = l + el_dlg_right_bottom.offsetWidth;
        if (w < el_dlg_right_bottom.offsetWidth) {
            w = el_dlg_right_bottom.offsetWidth;
        }
        else if (w > document.documentElement.clientWidth - el_dialog.offsetLeft) {
            w = document.documentElement.clientWidth - el_dialog.offsetLeft - 2;
        }
        if (w < minWidth) return;
        el_dialog.style.width = w + "px";
    };
    function stopDragToRightBottomToRight() {
        if (right.releaseCapture) {
            right.onmousemove = null;
            right.onmouseup = null;
            right.releaseCapture();
        }
        else {
            document.removeEventListener("mousemove", doDragToRightBottomToRight, true);
            document.removeEventListener("mouseup", stopDragToRightBottomToRight, true);
        }
    };
    // drag from bottom
    bottom.onmousedown = function (ev) {
        var oEvent = ev || event;
        mouseStart.x = oEvent.clientX;
        mouseStart.y = oEvent.clientY;
        bottomStart.y = bottom.offsetTop;
        if (bottom.setCapture) {
            bottom.onmousemove = doDragToRightBottomToBottom;
            bottom.onmouseup = stopDragToRightBottomToBottom;
            bottom.setCapture();
        }
        else {
            document.addEventListener("mousemove", doDragToRightBottomToBottom, true);
            document.addEventListener("mouseup", stopDragToRightBottomToBottom, true);
        }
    };
    function doDragToRightBottomToBottom(ev) {
        var oEvent = ev || event;
        var t = oEvent.clientY - mouseStart.y + bottomStart.y;
        var h = t + el_dlg_right_bottom.offsetHeight;
        if (h < el_dlg_right_bottom.offsetHeight) {
            h = el_dlg_right_bottom.offsetHeight;
        }
        else if (h > document.documentElement.clientHeight - el_dialog.offsetTop) {
            h = document.documentElement.clientHeight - el_dialog.offsetTop - 2;
        }
        if (h < minHeight) return;
        el_dialog.style.height = h + "px";
    };
    function stopDragToRightBottomToBottom() {
        if (bottom.releaseCapture) {
            bottom.onmousemove = null;
            bottom.onmouseup = null;
            bottom.releaseCapture();
        }
        else {
            document.removeEventListener("mousemove", doDragToRightBottomToBottom, true);
            document.removeEventListener("mouseup", stopDragToRightBottomToBottom, true);
        }
    };
    // drag from right bottom
    el_dlg_right_bottom.onmousedown = function (ev) {
        var oEvent = ev || event;
        mouseStart.x = oEvent.clientX;
        mouseStart.y = oEvent.clientY;
        divStart.x = el_dlg_right_bottom.offsetLeft;
        divStart.y = el_dlg_right_bottom.offsetTop;
        if (el_dlg_right_bottom.setCapture) {
            el_dlg_right_bottom.onmousemove = doDragToRightBottom;
            el_dlg_right_bottom.onmouseup = stopDragToRightBottom;
            el_dlg_right_bottom.setCapture();
        }
        else {
            document.addEventListener("mousemove", doDragToRightBottom, true);
            document.addEventListener("mouseup", stopDragToRightBottom, true);
        }
    };
    function doDragToRightBottom(ev) {
        var oEvent = ev || event;
        var l = oEvent.clientX - mouseStart.x + divStart.x;
        var t = oEvent.clientY - mouseStart.y + divStart.y;
        var w = l + el_dlg_right_bottom.offsetWidth;
        var h = t + el_dlg_right_bottom.offsetHeight;
        if (w < el_dlg_right_bottom.offsetWidth) {
            w = el_dlg_right_bottom.offsetWidth;
        }
        else if (w > document.documentElement.clientWidth - el_dialog.offsetLeft) {
            w = document.documentElement.clientWidth - el_dialog.offsetLeft - 2;
        }
        if (h < el_dlg_right_bottom.offsetHeight) {
            h = el_dlg_right_bottom.offsetHeight;
        }
        else if (h > document.documentElement.clientHeight - el_dialog.offsetTop) {
            h = document.documentElement.clientHeight - el_dialog.offsetTop - 2;
        }
        if (w < minWidth) return;
        el_dialog.style.width = w + "px";
        if (h < minHeight) return;
        el_dialog.style.height = h + "px";
    };
    function stopDragToRightBottom() {
        if (el_dlg_right_bottom.releaseCapture) {
            el_dlg_right_bottom.onmousemove = null;
            el_dlg_right_bottom.onmouseup = null;
            el_dlg_right_bottom.releaseCapture();
        }
        else {
            document.removeEventListener("mousemove", doDragToRightBottom, true);
            document.removeEventListener("mouseup", stopDragToRightBottom, true);
        }
    };    
};