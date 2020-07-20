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
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'matchTime', {}]" :disabled="dateDisabled"/>
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

        disabled:false,
        dateDisabled:false,

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

        if(this.model.gameStatus == 1){
          this.disabled = true
          this.dateDisabled = true
        }

        console.log("statue==",this.disabled)

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
            //时间格式化
            formData.matchTime = formData.matchTime?formData.matchTime.format('YYYY-MM-DD HH:mm:ss'):null;

            console.log(formData)
            /*httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })*/



          }
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>

</style>