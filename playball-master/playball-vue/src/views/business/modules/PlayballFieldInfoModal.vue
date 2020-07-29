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
          label="场地名">
          <a-input placeholder="请输入场地名" v-decorator="['fieldName', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="图片链接地址">
          <j-image-upload class="avatar-uploader" text="上传" v-model="model.imageUrl" ></j-image-upload>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="运动类型">
          <a-select placeholder="请输入运动类型"  v-model="model.sportsId">
            <a-select-option :value="sports.id"  v-for="sports in sportsTypeList" >
              {{ sports.sportsName }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="场地价格">
          <a-input-number v-decorator="[ 'fieldPrice', {}]" />
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
  import JImageUpload from '@/components/jeecg/JImageUpload'

  export default {
    name: "PlayballFieldInfoModal",
    components: {
      JImageUpload
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

        sportsTypeList:{},

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/business/playballFieldInfo/add",
          edit: "/business/playballFieldInfo/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        /*直接赋值不行*/
        this.model.imageUrl = "";
        if(record.imageUrl){
          setTimeout(() => {
            this.model.imageUrl = record.imageUrl;
          }, 5)
        }
        this.visible = true;

        getSportsTypeList('').then((res)=>{
          if(res.success){
            this.sportsTypeList = res.result.records;
          }
        })

        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'fieldName','fieldPrice'))
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
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
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


    }
  }
</script>

<style lang="less" scoped>

</style>