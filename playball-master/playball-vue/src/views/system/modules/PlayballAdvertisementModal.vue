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
          label="广告描述">
          <a-input placeholder="请输入广告描述" v-decorator="['posDescribe', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="广告轮播图">
          <j-image-upload class="avatar-uploader" text="上传" v-model="model.posRotation" ></j-image-upload>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="链接类型">
          <j-search-select-tag placeholder="请选择链接类型" v-model="model.posType" :dictOptions="labelOptions" @change="onChange">
          </j-search-select-tag>
        </a-form-item>

        <template v-if="model.posType==1">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="跳转链接">
            <a-input placeholder="请输入跳转链接" v-model="model.posJumpUrl" />
          </a-form-item>
        </template>
        <template v-if="model.posType==2">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="APP跳转页面">
            <a-select placeholder="请选择APP跳转页面"  v-model="model.posJumpUrl">
              <a-select-option :value="item.appUrl"  v-for="item in appRouterList"  >
                {{ item.appName }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </template>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "PlayballAdvertisementModal",
    components: {
      JImageUpload,
      JSearchSelectTag
    },
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

        labelOptions:[{
          text:"跳转页面",
          value:"1"
        },{
          text:"APP内跳转",
          value:"2"
        }],

        appRouterList:{},

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        posId:{rules: [{ required: true, message: '请输入广告表主键id!' }]},
        },
        url: {
          add: "/playball/playballAdvertisement/add",
          edit: "/playball/playballAdvertisement/edit",
          getAppRouteList:"/playball/playballAdvertisement/getAdvertisementAppRoute",
        },
      }
    },
    created () {
      this.getAppRouteList();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.model.posRotation='';

        if(this.model.posId){
          //this.jumpUrl = this.model.posJumpUrl
          setTimeout(() => {
            this.model.posRotation = record.posRotation
          }, 5)
        }

        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'posDescribe'))
		  //时间格式化
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
            if(!this.model.posId){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //formData.posJumpUrl = this.jumpUrl
            //时间格式化

            console.log(formData)
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
      getAppRouteList() {
        getAction(this.url.getAppRouteList, {}).then((res) => {
          if (res.success) {
            this.appRouterList = res.result;
          }
        });
      },
      onChange(){
        delete this.model.posJumpUrl
      }
    }
  }
</script>

<style lang="less" scoped>

</style>