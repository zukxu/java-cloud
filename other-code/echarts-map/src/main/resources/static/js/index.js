/**
 * 实时显示时间
 */
function showTime(){
	var time = new Date();
	var year = time.getFullYear();
	var month = (time.getMonth()+1+'').padStart(2,'0');
	var day = (time.getDate()+'').padStart(2,'0');
	var hour = (time.getHours()+'').padStart(2,'0');
	var minute = (time.getMinutes()+'').padStart(2,'0');
	var second = (time.getSeconds()+'').padStart(2,'0');
	
	var content = `${year}年${month}月${day}日${hour}:${minute}:${second}`;
	$('#title .time').text(content);
}

showTime();
setInterval(showTime,1000);      //每秒运行一次

/**
 * 向腾讯发送请求，获取数据
 */

function getData(){
	$.ajax({
		url:'https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5',
		data:{
			name:'disease_h5'
		},
		dataType:'jsonp',
		success:function(res){
			//console.log(res.data);
			var data = JSON.parse(res.data);
			//console.log(data);
			
			center1(data);
			center2(data);
			right1(data);
			right2(data);
		}
	});

	$.ajax({
		type:'post',
		url:'https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list',
		data:{
			modules: 'chinaDayList,chinaDayAddList,nowConfirmStatis,provinceCompare'
		},
		dataType:'json',
		success:function(res){
			//console.log(res);
			var data = res.data;
			//console.log(res.data);
			left1(data);
			left2(data);
		}
	});
}

getData();
setInterval(getData,5*60*1000);   //每5min查询获取一次数据

function center1(data){
	$('#confirm').text(data.chinaTotal.confirm);
	$('#heal').text(data.chinaTotal.heal);
	$('#dead').text(data.chinaTotal.dead);
	$('#nowConfirm').text(data.chinaTotal.nowConfirm);
	$('#noInfect').text(data.chinaTotal.noInfect);
	$('#import').text(data.chinaTotal.importedCase);
}


function center2(data){
	var myChart=echarts.init($('#center2')[0],'dark');
	var option={
		title:{
			text:'\n\n中国地区累计确诊分布',
			left:'center',
			textStyle: {
				fontSize: 20,
				color: "rgba(255, 0, 0, 1)"
			  },
			  //subtext: "@腾讯新闻",
			  //sublink:'www.baidu.com'
		},
		tooltip:{
			//show:true,
			trigger:'item'
		},
		visualMap:{ //左侧小导航图标
			show:true,
			x:'left',
			y:'bottom',
			textStyle:{
				fontSize:8,
			},
			splitList:[{start:1,end: 9},
				{start:10,end:99},
				{start:100,end:999},
				{start:1000,end:9999},
				{start:10000}],
			color:['#8A3310','#C64918','#E55B25','#F2AD92','#F9DCD1']		
		},
		series:[{
			name:'累计确诊人数',
			type:'map',
			mapType:'china',
			roam:false,		//禁用拖动和缩放 
			
			itemStyle:{		//图形样式
				normal:{
					borderWidth: .3,//区域边框宽度
					borderColor:'#009fe8',//区域边框颜色
					areaColor:'#ffefd5',       //区域颜色
				},
				emphasis:{      //鼠标滑过地图高亮的相关设置
				borderWidth: .5,
				borderColor:'#4b0082',
				areaColor:'#fff',
				}
			},
			label:{ // 图形上的文本标签	
				normal:{ 
					show:true,   //省份名称
					fontSize:10,
					
				},
				emphasis:{
					show:true,
					fontSize:8,
				}
			},
			data:[]     //[{'name':'上海'，'value':318},{'name':'江苏'，'value':308}]
		}]
	};	
	
	var provinces = data.areaTree[0].children;
	for(var province of provinces){
		option.series[0].data.push({
			'name':province.name,
			'value':province.total.confirm
		});
	}
	
	myChart.setOption(option);
}

function right1(data){
	var myChart = echarts.init($('#right1')[0],'dark');
	
	var option={
		title:{
			text:"全国确诊省市TOP10",
			textStyle:{
				color:'white',
			},
			left:'left'
		},
		color:['#3398DB'],
		tooltip:{
			trigger:'axis',
			//指示器
			axisPointer:{
				type:'shadow'  //默认为直线，可选为： ‘line’ | 'shadow'
			}
		},
		xAxis:{
			tpye:'category',
			data:[]  //['湖北','广州','北京']
		},
		yAxis:{
			type:'value',
			//y轴字体设置
			axisLable:{
				show:true,
				color:'white',
				fontSize:12,
				formatter:function(value){
					if(value>=1000){
						value = value/1000 + 'k';
					}
					return value;
				}
			},
		},
	
	series:[{
		data:[],//[582,300,100],
		type:'bar',
		barMaxWidth:"50%"
	}]
	};
	
	var provinces = data.areaTree[0].children;
	var topData = [];
	for(var province of provinces){
		topData.push({
			'name':province.name,
			'value':province.total.confirm
		});
	}
	//降序排列
	topData.sort(function(a,b){
		return b.value-a.value;
	});
	//只保留前10个
	topData.length = 10;
	//console.log(topData);
	//分别取出省份名称和数值
	for(var province of topData){
		option.xAxis.data.push(province.name) ;
		option.series[0].data.push(province.value);
	}	
	myChart.setOption(option);
}

function right2(data){
	var myChart = echarts.init($('#right2')[0],'dark');
	
	var option = {
	    title: {
	        text: '境外输入确诊省市TOP5',
	        left: 'center'
	    },
	    tooltip: {
	        trigger: 'item',
	        formatter: '{a} <br/>{b} : {c} ({d}%)'
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data:[],     		
	    },
	    series: [
	        {
	            name: '省市名称',
	            type: 'pie',
	            radius: '55%',
	            center: ['50%', '60%'],
				data:[],
	            emphasis: {
	                itemStyle: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
	
	var provinces  = data.areaTree[0].children; //境外输入的判断
	var topData =[];
	for(var province of provinces){
		for(var item of province.children){
			if(item.name === '境外输入'){
				topData.push({
					'name':province.name,
					'value':item.total.confirm
			});
				break;
			}
		}
		
	}
	//降序的排列
	topData.sort(function(a,b){
		return b.value -a.value;
	});
	//只保留前5个
	topData.length=5;
	//分别取出省份名称和数据
	for(var province of topData){
		option.legend.data.push(province.name);
		option.series[0].data.push(province);
	}
	
	//console.log(topData);
	
	
	myChart.setOption(option);
}

function left1(data){
	var myChart = echarts.init($('#left1')[0],'dark');
	var option = {
		title:{
			text:"全国累计趋势",
			textStyle:{
				color:'white',
			},
			left:'left',
		},
		tooltip:{
			trigger:'axis',
			//指示器
			axisPointer:{
				type:'line',
				lineStyle:{
					color:'#7171C6'
				}
			},
		},
		//图例
		legend:{
			data:['累计确诊','累计治愈','累计死亡'],
			left:"right"
		},
		//图形位置
		grid:{
			left:'4%',
			right:'6%',
			bottom:'4%',
			top:50,
			containLabel:true
		},
		xAxis:[{
			type:'category',
			data:[]   // ['03.20','03.21','03.22'],
		}],
		yAxis:[{
			type:'value',
			//y轴字体设置
			axisLabel:{
				show:true,
				color:'white',
			
				fontSize:12,
				formatter: function(value){
					if(value>=1000){
						value = value/1000+'k';
					}
					return value;
				},
	
			},
			
			//y轴线设置展示
			axisLine:{
				show:true
			},
			//与x轴平行的线样式
			splitLine:{
				show:true,
				lineStyle:{
					color:'#17273B',
					width:1,
					type:'solid',
				}
			}
		}],
		series:[{
			name:"累计确诊",
			type:'line',
			smooth:true,
			data:[]//[260,406,529],
		},{
			name:"累计治愈",
			type:'line',
			smooth:true,
			data:[]  //[6,150,270],
		},{
			name:"累计死亡",
			type:'line',
			smooth:true ,
			data:[] //[6,9,17],
		}]
	};

	var chinaDayList =data.chinaDayList;
	for(var day of chinaDayList){
		//console.log(day);
		option.xAxis[0].data.push(day.date);
		option.series[0].data.push(day.confirm);
		option.series[1].data.push(day.heal);
		option.series[2].data.push(day.dead)
	}
	

	myChart.setOption(option);
}

function left2(data){
	var myChart = echarts.init($('#left2')[0],'dark');

	var option = {
		title:{
			text:"全国新增趋势",
			textStyle:{
				color:'white',
			},
			left:'left',
		},
		tooltip:{
			trigger:'axis',
			//指示器
			axisPointer:{
				type:'line',
				lineStyle:{
					color:'#7171C6'
				}
			},
		},
		//图例
		legend:{
			data:['新增确诊','新增疑似','新增境外输入'],
			left:"right"
		},
		//图形位置
		grid:{
			left:'4%',
			right:'6%',
			bottom:'4%',
			top:50,
			containLabel:true
		},
		xAxis:[{
			type:'category',
			data:[],
		}],
		yAxis:[{
			type:'value',
			//y轴字体设置
			axisLabel:{
				show:true,
				color:'white',
			
				fontSize:12,
				formatter: function(value){
					if(value>=1000){
						value = value/1000+'k';
					}
					return value;
				}
	
			},
			
			//y轴线设置展示
			axisLine:{
				show:true
			},
			//与x轴平行的线样式
			splitLine:{
				show:true,
				lineStyle:{
					color:'#17273B',
					width:1,
					type:'solid',
				}
			}
		}],
		series:[{
			name:"新增确诊",
			type:'line',
			smooth:true,
			data:[],
		},{
			name:"新增疑似",
			type:'line',
			smooth:true,
			data: [],
		},{
			name:"新增境外输入",
			type:'line',
			smooth:true,
			data: [],
		}]
	};

	var  chinaDayAddList = data.chinaDayAddList;
	for(var day of chinaDayAddList){
		//console.log(day);
		option.xAxis[0].data.push(day.date);
		option.series[0].data.push(day.confirm);
		option.series[1].data.push(day.suspect);
		option.series[2].data.push(day.importedCase);
	}

	myChart.setOption(option);
}

