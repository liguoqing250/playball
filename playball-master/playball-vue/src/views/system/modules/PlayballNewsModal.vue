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
          label="封面图片">
          <j-image-upload class="avatar-uploader" text="上传" v-model="image" ></j-image-upload>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="资讯类型">
          <a-select placeholder="请输入资讯类型"  v-model="typeName" @change ="changeSportsList($event)">
            <a-select-option :value="sports.id"  v-for="sports in sportsTypeList" >
              {{ sports.sportsName }}
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

  export default {
    name: "PlayballNewsModal",
    components: {
      ARow,
      JDate,
      JImageUpload,
      JEditor
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

        sportsTypeList:{},
        typeName:'',
        image:'',

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/cms/news/add",
          edit: "/cms/news/edit",
          sportsTypeList: "/bm/common/sportslist",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit();
      },
      edit (record) {
        this.form.resetFields();
        this.newsModel = Object.assign({}, record);
        //--------------------------------------------------------

        //获取运动类型
        getAction(this.url.sportsTypeList).then((res)=>{
          if(res.success){
            this.sportsTypeList= res.result;
            this.typeName = this.sportsTypeList[0].sportsName
            //this.$forceUpdate()
            for (var i=0; i<this.sportsTypeList.length;i++){
              if(this.sportsTypeList[i].id == record.newsType){
                this.typeName =  this.sportsTypeList[i].sportsName
                break
              }
            }
          }
        })

        if(this.newsModel.id){
          setTimeout(() => {
            this.image = record.image;
          }, 5)
        }

        //--------------------------------------------------------
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.newsModel,'title','newsModel.content','image','newsModel.newsType'))
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
            console.log("打印表单",newsData)

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