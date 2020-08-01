<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <!-- 主表单区域 -->

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="标题">
          <a-input placeholder="请输入标题" v-decorator="['title', {rules: [{ required: true, message: '请输入赛事标题!' }]}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="简述">
          <a-input placeholder="请输入简述内容" v-decorator="['introduction', {}]" />
        </a-form-item>


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="封面类型">
          <j-search-select-tag placeholder="请选择封面类型" v-model="newsModel.label" :dictOptions="labelOptions">
          </j-search-select-tag>
        </a-form-item>

        <template v-if="newsModel.label==1">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="封面图片">
            <j-image-upload class="avatar-uploader" text="上传" v-model="image" ></j-image-upload>
          </a-form-item>
        </template>
        <template v-if="newsModel.label==2">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="封面视频">
            <j-upload v-model="image"></j-upload>
          </a-form-item>
        </template>


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="资讯类型">
          <a-select placeholder="请输入资讯类型"  v-model="newsModel.newsType" @change ="changeSportsList($event)">
            <a-select-option :value="item.ntId"  v-for="item in newsTypeList" >
              {{ item.ntName }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="资讯详情"
          style="min-height: 300px">
          <j-editor v-model="newsModel.content"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'
  import pick from 'lodash.pick'
  import moment from "moment"
  import ARow from 'ant-design-vue/es/grid/Row'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JEditor from '@/components/jeecg/JEditor'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JUpload from '@/components/jeecg/JUpload'

  export default {
    name: "PlayballNewsModal",
    components: {
      ARow,
      JDate,
      JImageUpload,
      JEditor,
      JSearchSelectTag,
      JUpload
    },
    data () {
      return {
        title:"操作",
        visible: false,

        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        newsModel: {
          content:'',
          title:'',
          image:'',
          newsType:1,
        },

        newsTypeList:{},
        typeName:'',
        image:'',

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },

        labelOptions:[{
          text:"图片",
          value:"1"
        },{
          text:"视频",
          value:"2"
        }],

        url: {
          add: "/playball/playballNews/add",
          edit: "/playball/playballNews/edit",
          getNewsTypeList:"/playball/playballCMSNewsType/queryList",
        },
      }
    },
    created () {
      this.getNewsTypeList();
    },
    methods: {
      add () {
        this.edit();
      },
      edit (record) {
        this.form.resetFields();
        this.newsModel = Object.assign({}, record);
        //--------------------------------------------------------

        if(this.newsModel.id){
          setTimeout(() => {
            this.image = record.image;
          }, 5)
        }

        //--------------------------------------------------------
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.newsModel,'title','introduction'))
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
          console.log("values=====",values)
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.newsModel.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
              method = 'put';
            }
            console.log("newsModel=====",this.newsModel)
            let newsData = Object.assign(this.newsModel, values);
            //时间格式化
            newsData.content = that.newsModel.content
            newsData.newsType = that.newsModel.newsType
            newsData.image = that.image

            httpAction(httpurl,newsData,method).then((res)=>{
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
        this.newsModel.newsType = id

      },
      getNewsTypeList(){
        getAction(this.url.getNewsTypeList,{}).then((res)=>{
          if(res.success){
            this.newsTypeList=res.result;
          }
        });
      },
    }
  }
</script>

<style scoped>
  .ant-btn {
    padding: 0 10px;
    margin-left: 3px;
  }
  .ant-form-item-control {
    line-height: 0px;
  }
  /** 主表单行间距 */
  .ant-form .ant-form-item {
    margin-bottom: 10px;
  }
  /** Tab页面行间距 */
  .ant-tabs-content .ant-form-item {
    margin-bottom: 0px;
  }

  .imgbox{
    position:relative;
    overflow: hidden;
  }
  img{
    width:100%;
  }
  .imgbox:after{
    position:absolute;
    left: 0;
    top:0;
    display: block;
    width:100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    content: attr(data-text);
    transition:all 1s ease;
    color: #FFF;
  }

  .imgboxsub:after{
    transform: translateY(-100%);
  }

  /*.imgbox:hover:after{
    transform: translateY(0);
  }*/
</style>