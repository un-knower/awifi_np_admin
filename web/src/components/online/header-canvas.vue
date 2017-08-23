/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-04 16:58
 * 创建作者: haoxu
 * 文件名称: header-canvas.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
        <canvas id="myCanvas"  ref='div'>your browser does not support the canvas tag </canvas>
</template>
<script type="text/javascript">
    export default{
        name: 'HeadCanvas',
      	props: {
      	    title:{
      	        type:Array,
                default:[]
      	    },
			num: {
				type: Number,
				default: '1'
			}
		},
        data(){
            return{
            }
        },
        mounted: function () {
            var canvas =  this.$refs.div;
            canvas.width=800;
             canvas.height=100;
            var ctx =canvas.getContext("2d");
            var select = false;
            var num = 0;
            var x = 0;
            var y= 0;

           for( var i=0;i<this.title.length;i++){
                num++;
                if(i+1 == this.num){
                   select = true;
                }else{
                    select = false;
                }
               if(i == 0){
                   num = 1;
               }else{
                   x = x+112;
               }
               this.drawCanvas(ctx,x,y,this.title[i],select,num);

           }
        },
        methods: {
            drawCanvas: function(ctx,x,y,title,select,num){
                ctx.beginPath();
                ctx.moveTo(x,y);
                ctx.lineTo(x+105, y);
                ctx.lineTo(x+120, y+17);
                ctx.lineTo(x+105, y+34);
                ctx.lineTo(x, y+34);
                if(num != 1){
                    ctx.lineTo(x+17, y+17);
                }
                if(select){
                    ctx.fillStyle='#147FE3';
                }else{
                    ctx.fillStyle='#E7E7E7';
                }
                ctx.fill();
                this.drawText(ctx,title,x ,y,select);
                ctx.closePath();
            },
            drawText:function(ctx,title,x,y,select){
                ctx.font = "14px serif";
                if(select){
                    ctx.fillStyle='#fff';
                }else{
                    ctx.fillStyle='#888';
                }
                ctx.fillText(title, x+30, y+22);
            }
        },
        components:{
        }
    }
</script>