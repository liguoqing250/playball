<template>
  <j-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="请设置比赛日期">
          <!--v-decorator="[ 'matchTime', validatorRules.matchTime]"-->
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss'  :disabled="dateDisabled"
                         v-decorator="['matchTime',{rules: [{validator: validateMatchTime,},],},]"/>
        </a-form-item>


          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="更新比赛结果">
            <a-row type="flex" style="margin-bottom:10px" :gutter="16">
              <a-col :span="10">
                <a-form-item>
                  <a-card hoverable style="width: 200px">
                    <a-card-meta
                      :title="model.teamName"
                      description="">
                    </a-card-meta>
                    <a-input-number :disabled="disabled" v-decorator="[ 'enterBall', {}]" />
                  </a-card>
                </a-form-item>
              </a-col>

              <a-col :span="10">
                <a-form-item>
                  <a-card hoverable style="width: 200px">
                    <a-card-meta
                      :title="model.opponentName"
                      description="">
                    </a-card-meta>
                    <a-input-number :disabled="disabled" v-decorator="[ 'lostBall', {}]" />
                  </a-card>
                </a-form-item>
              </a-col>

            </a-row>
          </a-form-item>


      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "PlayballScheduleModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        validatorRules:{
          matchTime:{rules: [{validator: this.validateMatchTime}]},
        },


        disabled:false,
        dateDisabled:false,

        gameStartTime:null,
        gameEndTime:null,
        gameMatchTime:null,

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          edit: "/playball/playballSchedule/edit",
        },
      }
    },
    created () {
    },
    beforeDestroy() {

    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.disabled = false;
        this.dateDisabled = false;
        this.model = Object.assign({}, record);

        this.gameEndTime = new Date(moment(record.endTime))
        this.gameStartTime = new Date(moment(record.startTime))
        this.gameMatchTime = new Date(moment(record.matchTime))
        let now = new Date()

        if(this.model.gameStatus == 1){
          this.disabled = true
          this.dateDisabled = true
        }else{
          //比赛时间肯定要大于开始时间且小于结束时间，在这期间内都可以设置时间
          //当前时间大于比赛结束时间，比赛结束，无需设置任何时间
          if(now.getTime() > this.gameEndTime.getTime()){
            //this.dateDisabled=true
          }

          //大于比赛时间才能设置比分，比将比赛状态设置为结束状态
          if(now.getTime() > this.gameMatchTime.getTime()){
            this.disabled = false
          }else{
            this.disabled = true
          }
        }

        console.log(this.model )
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'enterBall','lostBall'))
		  //时间格式化
          this.form.setFieldsValue({matchTime:this.model.matchTime?moment(this.model.matchTime):null})
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          console.log(err)
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            formData.matchTime = formData.matchTime?formData.matchTime.format('YYYY-MM-DD HH:mm:ss'):null;
            if(this.model.enterBall != null && this.model.lostBall!=null){
              formData.gameStatus = 1
              if(this.model.enterBall > this.model.lostBall){
                formData.gameResult = 1
              }else if(this.model.enterBall < this.model.lostBall){
                formData.gameResult = 3
              }else{
                formData.gameResult = 2
              }
            }

            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel () {
        this.close()
      },
      validateMatchTime(rule, value, callback){
        if(!value){
          callback("请设置比赛时间")
        }else{
          let setTime = new Date(value)
          if(setTime.getTime() >this.gameEndTime.getTime()){
            callback("您输入的比赛时间大于赛事结束时间，请重新输入！");
          }else{
            callback()
          }
        }
      }

    }
  }
</script>

<style lang="less" scoped>

</style>