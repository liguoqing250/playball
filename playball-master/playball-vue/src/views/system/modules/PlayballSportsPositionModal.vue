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
          label="运动类型">
          <a-select placeholder="请选择运动类别" style="width:240px;" v-model="model.stId" @change ="changeSportsList($event)">
            <a-select-option :value="item.id"  v-for="item in sportsTypeList" >
              {{ item.sportsName }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="球员位置名">
          <a-input placeholder="请输入球员位置名" style="width:240px;" v-decorator="['text', {}]" />
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import { getSportsTypeList } from '@/api/api'

  export default {
    name: "PlayballSportsPositionModal",
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

        typeName:'',
        sportsTypeList:{},
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/playball/playballSportsPosition/add",
          edit: "/playball/playballSportsPosition/edit",
        },
      }
    },
    created () {
      this.getSportsList()
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'text'))
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
            formData.creatTime = formData.creatTime?formData.creatTime.format('YYYY-MM-DD HH:mm:ss'):null;

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
      getSportsList(){
        if(this.model.sportsName!=null){
          typeName = this.model.sportsName;
        }
        getSportsTypeList('').then((res)=>{
          if(res.success){
            console.log(res.result.records)
            this.sportsTypeList = res.result.records;
          }
        })
      },
      changeSportsList(id){
        //this.model.stId = id
      },

    }
  }
</script>

<style lang="less" scoped>

</style>