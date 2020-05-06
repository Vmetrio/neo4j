//echarts-力导向

var myChart = echarts.init(document.getElementById('main'));
var categories = [{name:"数据库"},{name:"分类单元"},{name:"亚种"},{name:"描述"},{name:"别名"}];
option = {
    // 图的标题
    title: {
        text: '生物图谱'
    },
    // 提示框的配置
    tooltip: {
        formatter: function (x) {
            return x.data.des;
        }
    },
    // 工具箱
    toolbox: {
        // 显示工具箱
        show: true,
        feature: {
            mark: {
                show: true
            },
            mySearch: {
                show : true,
                title : '搜索',
                //icon : 'image://',
                icon : 'image://data:image/png;base64,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',
                onclick: function() {
                    SearchSpecies();
                }
            },
            // 还原
            restore: {
                show: true
            },
            // 保存为图片
            saveAsImage: {
                show: true
            }
        }
    },
    legend: [{
        // selectedMode: 'single',
        data: categories.map(function (a) {
            return a.name;
        })
    }],
    series: [{
        type: 'graph', // 类型:关系图
        layout: 'force', //图的布局，类型为力导图
        symbolSize: 40, // 调整节点的大小
        roam: true, // 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移,可以设置成 'scale' 或者 'move'。设置成 true 为都开启
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [2, 10],
        edgeLabel: {
            normal: {
                textStyle: {
                    fontSize: 20
                }
            }
        },
        force: {
            repulsion: 2500,
            edgeLength: [10, 50]
        },
        draggable: true,
        lineStyle: {
            normal: {
                width: 2,
                color: '#4b565b',
            }
        },
        edgeLabel: {
            normal: {
                show: true,
                formatter: function (x) {
                    return x.data.name;
                }
            }
        },
        label: {
            normal: {
                show: true,
                textStyle: {}
            }
        },

        // 数据
        data: null,
        links: null,
        categories: categories,
    }]
};

$.ajax({
    url:'/data/list',
    type:'POST',
    data:"{}",
    dataType:'json',
    beforeSend: function () {
        load = showLoad();
    },
    success:function (data) {
        option.series[0].nodes=data.nodes;
        option.series[0].links=data.links;
        myChart.setOption(option);
        closeLoad(load);
    },
    error: function (errorMsg) {
        closeLoad(load);
        layer.msg("数据获取失败!",
            {
                anim: 6,// 加上anim，渐显
                time: 1500, // 1.5s后自动关闭
            });
    }
});

myChart.on('dblclick', function (param){
    console.log('param---->', param);  // 打印出param, 可以看到里边有很多参数可以使用
    // 获取节点点击的数组序号
    var arrayIndex = param.dataIndex;
    console.log('arrayIndex---->', arrayIndex);
    console.log('name---->', param.name);
    // console.log('url---->', param.data.dataId);
    if (param.dataType == 'node') {
        SeeInfo(param.name);
    }
});

var load;
function showLoad() {
    return layer.msg('数据加载中...', {
        icon: 16,
        shade: [0.5, '#f5f5f5'],
        scrollbar: false,
        offset: 'auto',
        time:100000
    });
}
function closeLoad(index) {
    layer.close(index);
}