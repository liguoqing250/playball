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
              label="赛事名称">
              <a-input placeholder="请输入赛事名称" v-decorator="['gamesName', {rules: [{ required: true, message: '请输入赛事名称!' }]}]" />
            </a-form-item>
          </a-col>

          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="报名时间">
              <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'enrollTime',{}]"/>
            </a-form-item>
          </a-col>

        </a-row>

        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="开始时间">
              <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'startTime',{}]"/>
            </a-form-item>
          </a-col>

          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="结束时间">
              <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'endTime',{}]"/>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row style="width: 100%;">
            <a-col :span="50">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="赛事详细信息"
                style="min-height: 300px">
                <j-editor v-model="orderMainModel.gamesInfo"/>
              </a-form-item>
            </a-col>
        </a-row>

        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="场地类型">
              <select name="public-choice" v-model ="orderMainModel.sportsId" style="width: 200px;" autocomplete="off" @change ="changeSportsList($event)">
                <option value="" >请选择</option>
                <option :value="sports.id"  v-for="sports in sportsTypeList"  >
                  {{ sports.sportsName }}
                </option>
              </select>
            </a-form-item>
          </a-col>
        </a-row>

        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1" >
          <a-tab-pane tab="请选择要举办赛事的球馆" key="2" forceRender>
            <div >
              <a-row type="flex" style="margin-bottom:10px" :gutter="16" >
                <a-col :span="6"
                       @click="selectField(item,index)"
                       v-for="(item, index) in businessInfoList" :key="index">

                  <div class="imgbox imgboxsub" v-if=item.bselect>
                  <img slot="cover"
                       alt="example"
                       :src="getImageUrl(item.imageUrl)"
                  />
                  <div style="margin-bottom: 3px" slot="title">{{ item.fieldName }}</div>
                  <div class="meta-content" slot="description">{{ item.businessName }}</div>
                </div>
                  <div class="imgbox "  v-else=item.bselect>
                    <img slot="cover"
                         alt="example"
                         :src="getImageUrl(item.imageUrl)"
                    />
                    <div style="margin-bottom: 3px" slot="title">{{ item.fieldName }}</div>
                    <div class="meta-content" slot="description">{{ item.businessName }}</div>
                  </div>

                </a-col>
              </a-row>

            </div>
          </a-tab-pane>
        </a-tabs>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'
  import JEditor from '@/components/jeecg/JEditor'
  import pick from 'lodash.pick'
  import moment from "moment"
  import ARow from 'ant-design-vue/es/grid/Row'

  export default {
    name: "PlayballGamesModal",
    components: {
      ARow,
      JDate,
      JEditor
    },
    data () {
      return {
        title:"操作",
        visible: false,
        orderMainModel: {
          gamesInfo:'',
          sportsId:'',
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        sportsTypeList:{},
        businessInfoList:{},
        fieldSelectInfo:{},

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/cms/playball/add",
          edit: "/cms/playball/edit",
          sportsTypeList: "/bm/common/sportslist",
          businessInfoList:"/businessinfo/field/listbsi"
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
        this.businessInfoList = {}
        this.form.resetFields();
        this.orderMainModel = Object.assign({}, record);
        //--------------------------------------------------------

        //获取运动类型
        getAction(this.url.sportsTypeList).then((res)=>{
          if(res.success){
            this.sportsTypeList= res.result;
            this.$forceUpdate()
          }
        })

        //初始化明细表数据
        console.log(this.orderMainModel.id)
        /*if(this.orderMainModel.id){
          let params = {id:this.orderMainModel.id}
          //初始化订单机票列表
          getAction(this.url.orderCustomerList,params).then((res)=>{
            if(res.success){
              this.orderMainModel.jeecgOrderCustomerList = res.result;
              this.$forceUpdate()
            }
          })
          //初始化订单客户列表
          getAction(this.url.orderTicketList,params).then((res)=>{
            if(res.success){
              this.orderMainModel.jeecgOrderTicketList = res.result;
              this.$forceUpdate()
            }
          })
        }*/
        //--------------------------------------------------------
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.orderMainModel,'gamesName','enrollTime','startTime','endTime'))
          this.form.setFieldsValue({enrollTime:this.orderMainModel.enrollTime?moment(this.orderMainModel.enrollTime):null}) //时间格式化
          this.form.setFieldsValue({startTime:this.orderMainModel.startTime?moment(this.orderMainModel.startTime):null}) //时间格式化
          this.form.setFieldsValue({endTime:this.orderMainModel.endTime?moment(this.orderMainModel.endTime):null}) //时间格式化
        });
        console.log(this.orderMainModel)
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
            if(!this.orderMainModel.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            console.log("orderMainModel=====",this.orderMainModel)
            let gamesData = Object.assign(this.orderMainModel, values);
            //时间格式化
            gamesData.enrollTime = gamesData.enrollTime?gamesData.enrollTime.format('YYYY-MM-DD HH:mm:ss'):null;
            gamesData.startTime = gamesData.startTime?gamesData.startTime.format('YYYY-MM-DD HH:mm:ss'):null;
            gamesData.endTime = gamesData.endTime?gamesData.endTime.format('YYYY-MM-DD HH:mm:ss'):null;
            gamesData.fieldId = this.fieldSelectInfo.id
            console.log("打印表单",gamesData)

            httpAction(httpurl,gamesData,method).then((res)=>{
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

      changeSportsList(e){
        this.businessInfoList = {}
        this.$forceUpdate()
        //记得后面来调整分页，现在先走着
        console.log("获取运动类型sportid=",this.orderMainModel.sportsId)
        let params = {sportsId:this.orderMainModel.sportsId}
        getAction(this.url.businessInfoList, params).then((res)=>{
          if(res.success){
            this.businessInfoList= res.result.records;
            for(let index in this.businessInfoList){
              this.businessInfoList[index].bselect = true
            }
            this.$forceUpdate()
          }
        })
      },
      selectField(fieldInfo,index){
        for(let i in this.businessInfoList){
          if ( index == i){
            this.businessInfoList[i].bselect = false
            this.fieldSelectInfo = fieldInfo
          } else{
            this.businessInfoList[i].bselect = true
          }
        }
        this.$forceUpdate()

      },
      getImageUrl(imageUrl){
        return getFileAccessHttpUrl(imageUrl);
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