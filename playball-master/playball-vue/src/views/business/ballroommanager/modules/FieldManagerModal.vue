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
        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="场地名">
              <a-input placeholder="请输入场地名" v-decorator="['fieldName', {rules: [{ required: true, message: '请输入场地名！' }]}]" />
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="场地类型">
              <select name="public-choice" v-model ="formData.sportsId" style="width: 200px;" autocomplete="off" @change ="changeSportsList($event)">
                <option value="" >请选择</option>
                <option :value="sports.id"  v-for="sports in this.orderMainModel.sportsTypeList"  >
                  {{ sports.sportsName }}
                </option>
              </select>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="场地价格">
              <a-input-number style="width: 200px" v-decorator="[ 'fieldPrice', {}]" />
            </a-form-item>
          </a-col>

        </a-row>

        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
              <a-form-item label="场馆图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-image-upload class="avatar-uploader" text="上传" v-model="fileList" ></j-image-upload>
              </a-form-item>
          </a-col>

        </a-row>
      </a-form>


    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction,getAction } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'
  import pick from 'lodash.pick'
  import moment from "moment"
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {initDictOptions, filterDictText,filterDictTextByCache} from '@/components/dict/JDictSelectUtil'
  import JImageUpload from '@/components/jeecg/JImageUpload'

  export default {
    name: "FieldManagerModal",
    components: {
      JDate,
      JDictSelectTag,
      JImageUpload
    },
    data () {
      return {
        title:"操作",
        visible: false,
        orderMainModel: { sportsTypeList:[{}]},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        formData: {},
        fileList:[],

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/businessinfo/field/add",
          edit: "/businessinfo/field/edit",
          sportsTypeList: "/bm/common/sportslist",
          businessList: "/bm/common/businesslist",
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
        this.orderMainModel = Object.assign({}, record);
        console.log("record==",record)
        console.log(this.orderMainModel.id)

        //获取运动类型
        getAction(this.url.sportsTypeList).then((res)=>{
          if(res.success){
            this.orderMainModel.sportsTypeList= res.result;
            this.$forceUpdate()
          }
        })

        if(this.orderMainModel.id){
          let params = {id:this.orderMainModel.id}
          console.log("是编辑进来的",this.orderMainModel.sportsId)
          this.formData.sportsId = this.orderMainModel.sportsId
          setTimeout(() => {
            this.fileList = record.imageUrl;
          }, 5)
        }
        //--------------------------------------------------------
        this.visible = true;
        this.$nextTick(() => {
          //this.form.setFieldsValue(pick(this.formData,'fieldName','fieldPrice','sportsId'))
          this.form.setFieldsValue(pick(record, 'fieldName','fieldPrice'))

        });
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        console.log("ok")
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            let formData = Object.assign(this.formData, values);
            console.log(this.formData)
            if(!this.orderMainModel.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
              formData.id = this.orderMainModel.id
            }

            if(that.fileList != ''){
              formData.imageUrl = that.fileList;
            }else{
              formData.imageUrl = null;
            }
            //formData.businessId = this.$store.getters.userInfo.departIds

            console.log("场地管理", formData)
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
      changeSportsList(){
        console.log("初始化下拉菜单")
      }
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
</style>