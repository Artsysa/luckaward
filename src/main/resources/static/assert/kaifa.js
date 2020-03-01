
// window.location.href = 'http://49.235.43.59/wx/wxlogin';

window.onload = function () {
    (function (doc, win, designWidth) {
        var html = doc.documentElement;
        function refreshRem() {
            var clientWidth = html.getBoundingClientRect().Width;
            html.style.fontSize = 100 * (clientWidth / designWidth) + 'px';
        }
        refreshRem()
        //dom加载完的一个事件
        doc.addEventListener('DOMContentLoaded', refreshRem);
    })(document, window, 750);
    $.ajax({
        url: 'http://49.235.43.59/wx/alltest',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            data.obj.forEach(function (element, index) {
                if (index == 1) {
                    $('#chou').val('抽奖(剩余' + element.remain + '次)')
                    var t1 = element.t1,
                        t2 = element.t2,
                        t3 = element.t3,
                        t4 = element.t4,
                        t5 = element.t5,
                        t6 = element.t6,
                        t7 = element.t7,
                        t8 = element.t8,
                        t9 = element.t9,
                        t10 = element.t10;
                    panduan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
                }

                if (index == 2) {
                    let kucun = element.rewardcount;
                    let shen = 120 - parseInt(kucun);
                    let liulannum = element.scancount;
                    zhong(shen);
                    shengy(kucun);
                    liulan(liulannum);
                }
                if (index == 3) {
                    element.forEach(function (ele) {
                        var newName = ele.nickname;
                        var newImg = ele.headimgurl;
                        let strhtml = '';
                        strhtml += `<div class="none">
                        <div class="tou">
                            <div class="im" style="background-image: url('${newImg}')"></div>
                            <span id="name">${newName}</span>
                        </div>
                        <div class="success">集卡成功</div>
                    </div>`
                        grjl(strhtml, newImg);
                    })
                }
            });
        },
        error: function (data) {

        },
    });

    //倒计时函数
    clock();
}


//倒计时函数
function clock() {
    var today = new Date(),//当前时间
        h = today.getHours(),
        m = today.getMinutes(),
        s = today.getSeconds();
    var stopTime = new Date("Mar 29 2020 00:00:00"),//结束时间
        stopH = stopTime.getHours(),
        stopM = stopTime.getMinutes(),
        stopS = stopTime.getSeconds();
    var shenyu = stopTime.getTime() - today.getTime(),//倒计时毫秒数
        shengyuD = parseInt(shenyu / (60 * 60 * 24 * 1000)),//转换为天
        D = parseInt(shenyu) - parseInt(shengyuD * 60 * 60 * 24 * 1000),//除去天的毫秒数
        shengyuH = parseInt(D / (60 * 60 * 1000)),//除去天的毫秒数转换成小时
        H = D - shengyuH * 60 * 60 * 1000,//除去天、小时的毫秒数
        shengyuM = parseInt(H / (60 * 1000)),//除去天的毫秒数转换成分钟
        M = H - shengyuM * 60 * 1000;//除去天、小时、分的毫秒数
    S = parseInt((shenyu - shengyuD * 60 * 60 * 24 * 1000 - shengyuH * 60 * 60 * 1000 - shengyuM * 60 * 1000) / 1000)//除去天、小时、分的毫秒数转化为秒
    document.getElementById("count").innerHTML = '剩余时间：' + (shengyuD + "天" + shengyuH + "小时" + shengyuM + "分" + S + "秒" + "<br>");
    // setTimeout("clock()",500);
    setTimeout(clock, 500);
}


//中奖人数
function zhong(shengyu) {
    $('#zj').html('已中奖：' + shengyu);
}
//剩余人数
function shengy(num) {
    $('#num').html('剩余：' + num);
}
//浏览人数
function liulan(Lnum) {
    $('#liulan').html('- - -' + Lnum + '浏览- - -');
}

//抽福袋的函数
function de(e) {
    let timer;
    return function () {
        if (timer) clearTimeout(timer);//如果500毫秒内又一次触发，则会重新计算时间
        timer = setTimeout(function (e) {
            $.ajax({
                url: 'http://49.235.43.59/lk/testzi',
                type: 'post',
                dataType: 'json',
                data: {
                    openid: 'oTZhLxH80vLXI15BluOE2UZlqVCE'
                },
                success: function (data) {
                    console.log(data.obj);
                    data.obj.forEach(function (ele, index) {
                        if (index == 0) {
                            layer.open({
                                title: [
                                    '奖励',
                                    'background-color: #FF4351; color:#fff;'
                                ],
                                content: '恭喜你获得：福袋' + ele.msg,
                                btn: '确定'
                            });
                        }

                        if (index == 1) {
                            $('#chou').val('抽奖(剩余' + ele.remain + '次)');
                            var t1 = ele.t1,
                                t2 = ele.t2,
                                t3 = ele.t3,
                                t4 = ele.t4,
                                t5 = ele.t5,
                                t6 = ele.t6,
                                t7 = ele.t7,
                                t8 = ele.t8,
                                t9 = ele.t9,
                                t10 = ele.t10;
                            panduan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
                            if (t1 > 0 && t2 > 0 && t3 > 0 && t4 > 0 && t5 > 0 && t6 > 0 && t7 > 0 && t8 > 0 && t9 > 0 && t10 > 0) {
                                layer.open({
                                    title: [
                                        '奖励',
                                        'background-color: #FF4351; color:#fff;'
                                    ],
                                    content: '恭喜您，已经集得所有福袋，请前往抽将处抽奖',
                                    btn: '确定'
                                });
                            }
                            // if (ele.remain < 0) {
                            //     layer.open({
                            //         title: [
                            //             '抱歉',
                            //             'background-color: #FF4351; color:#fff;'
                            //         ],
                            //         content: '您今天的抽奖次数已经用完，请分享好友获得抽奖字数吧',
                            //         btn: '确定'
                            //     });
                            // }

                        }

                    })
                },
                error: function (data) {
                    layer.open({
                        title: [
                            '错误',
                            'background-color: #FF4351; color:#fff;'
                        ],
                        content: '网络故障',
                    });
                },
            });
        }, 1000)
    }
}
var chou = document.getElementById('chou');
chou.addEventListener('touchstart', de(event))

//个人奖励
var gj = document.getElementById('Gj');
gj.addEventListener('touchstart', function (e) {
    $.ajax({
        url: 'http://49.235.43.59/lk/testreward',
        type: 'post',
        dataType: 'json',
        data: {
            token: 'oTZhLxMeEUgLtlPyxaeTC4djpDAk'
        },
        success: function (data) {
            console.log(data.obj)
            layer.open({
                title: [
                    '个人奖励',
                    'background-color: #FF4351; color:#fff;'
                ],
                content: '恭喜你获得：' + data.obj.name
            });
        },
        error: function (data) {

        },
    });
    e.preventDefault()
})


// 马上抽奖
var mashang = document.getElementById('mashang');
function deMa() {
    let timer;
    return function () {
        if (timer) clearTimeout(timer);//如果500毫秒内又一次触发，则会重新计算时间
        timer = setTimeout(() => {
            $.ajax({
                url: 'http://49.235.43.59/lk/testdraw',
                type: 'post',
                dataType: 'json',
                data: {
                    id: 'oTZhLxH80vLXI15BluOE2UZlqVCE'
                },
                success: function (data) {
                    console.log(data);
                    layer.open({
                        title: [
                            '个人奖励',
                            'background-color: #FF4351; color:#fff;'
                        ],
                        content: '恭喜你获得：' + data.obj + '，请到驾校兑奖处兑奖'
                    });
                },
                error: function () {

                }
            })
        }, 1000)
    }
}
mashang.addEventListener('touchstart', deMa())


//奖励信息
function grjl(str) {
    $('.ren').append(str);
}


//判断福袋
function panduan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) {
    if (t1 > 0) {
        $('#xx1').attr("src", "/assert/images/x1.png")
    }
    if (t2 > 0) {
        $('#xx2').attr("src", "/assert/images/x2.png")
    }
    if (t3 > 0) {
        $('#xx3').attr("src", "/assert/images/x3.png")
    }
    if (t4 > 0) {
        $('#xx4').attr("src", "/assert/images/x4.png")
    }
    if (t5 > 0) {
        $('#xx5').attr("src", "/assert/images/x5.png")
    }
    if (t6 > 0) {
        $('#xx6').attr("src", "/assert/images/x6.png")
    }
    if (t7 > 0) {
        $('#xx7').attr("src", "/assert/images/x7.png")
    }
    if (t8 > 0) {
        $('#xx8').attr("src", "/assert/images/x8.png")
    }
    if (t8 > 0) {
        $('#xx9').attr("src", "/assert/images/x9.png")
    }
    if (t10 > 0) {
        $('#xx10').attr("src", "/assert/images/x10.png")
    }

}