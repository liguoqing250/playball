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
          label="教程标题">
          <a-input placeholder="请输入教程标题" v-decorator="['cTitle', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="教程详情">
          <a-textarea placeholder="请输入教程详情" v-decorator="['cInfo', {}]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="封面图片">
          <j-image-upload class="avatar-uploader" text="上传" v-model="imageList" ></j-image-upload>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="教程视频">
          <j-upload v-model="fileList"></j-upload>
          <!--<a-input placeholder="请输入教程视频" v-decorator="['cvideo', {}]" />-->
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="运动类型">
          <a-select placeholder="请输入视频类型"  v-model="model.sId" @change ="changeSportsList($event)">
            <a-select-option :value="sports.id"  v-for="sports in sportsTypeList"  >
              {{ sports.sportsName }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="适龄范围1">
          <a-input placeholder="请输入适龄范围1" v-decorator="['cAgerange', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否付费">
          <!--<a-input placeholder="请输入是否付费" v-decorator="['cisFree', {}]" />-->
          <a-switch v-model="bFree"/>

        </a-form-item>

        <template v-if="bFree">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="价格">
            <a-input-number v-decorator="[ 'cPrice', {}]" />
          </a-form-item>
        </template>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="优先级">
          <a-input-number v-decorator="[ 'cFirst', {}]" />
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import { getSportsTypeList } from '@/api/api'
  import pick from 'lodash.pick'
  import moment from "moment"
  import JUpload from '@/components/jeecg/JUpload'
  import JImageUpload from '@/components/jeecg/JImageUpload'

  export default {
    name: "PlayballCourseModal",
    components: {
      JUpload,
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

        typeName:'',
        bFree:false,
        sportsTypeList:{},
        fileList:'',
        imageList:'',

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        ctitle:{rules: [{ required: true, message: '请输入课程标题' }]},
        },
        url: {
          add: "/playball/playballCourse/add",
          edit: "/playball/playballCourse/edit",
        },
      }
    },
    created () {
      getSportsTypeList('').then((res)=>{
        if(res.success){
          this.sportsTypeList = res.result.records;
        }
      })
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);

        if(this.model.cId){
          setTimeout(() => {
            this.imageList = record.cCover;
          }, 5)
        }

        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'cTitle','cInfo','cAgerange','cPrice','cFirst'))
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
            if(!this.model.cId){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            formData.cVideo = this.fileList
            console.log(this.fileList)
            formData.cCover = this.imageList
            if(this.bFree){
              formData.cIsFree = '1'
            }else{
              formData.cIsFree = '0'
              formData.cPrice = 0
            }
            console.log("-------formData",formData)
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
      changeSportsList(id){
        this.model.sid = id
      },

    }
  }
</script>

<style lang="less" scoped>

</style>