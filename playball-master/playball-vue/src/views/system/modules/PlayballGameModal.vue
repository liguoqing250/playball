<template>
  <j-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-row class="form-row" :gutter="32">
          <a-col :lg="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="赛事名称">
              <a-input style="width:240px;" placeholder="请输入赛事名称" v-decorator="['gamesName', {rules: [{ required: true, message: '请输入赛事名称!' }]}]" />
            </a-form-item>
          </a-col>

          <a-col :lg="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="报名时间">
              <a-date-picker style="width:240px;" showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'enrollTime',{}]"/>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row" :gutter="32">
          <a-col :lg="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="开始时间">
              <a-date-picker style="width:240px;" showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'startTime',{}]"/>
            </a-form-item>
          </a-col>

          <a-col :lg="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="结束时间">
              <a-date-picker style="width:240px;" showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'endTime',{}]"/>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row" :gutter="32">
          <a-col :lg="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="封面图片">
              <j-image-upload class="avatar-uploader" text="上传" v-model="gameImage" ></j-image-upload>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row" :gutter="32">
          <a-col :lg="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="赛事规则">
              <a-textarea style="width:800px;" placeholder="请输入赛事规则" v-decorator="['gameRule', {}]"/>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row" :gutter="32">
          <a-col :lg="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="赛事详细信息">
              <j-editor style="width:800px;" v-model="model.gamesInfo"/>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row" :gutter="32">
          <a-col :lg="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="运动类型">
              <a-select placeholder="请选择运动类别" style="width:240px;" v-model="model.sportsId"  @change ="changeSportsList($event)">
                <a-select-option :value="sports.id"  v-for="sports in typeModal.sportsTypeList" >
                  {{ sports.sportsName }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :lg="12">
          <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="赛事类型">
              <a-select placeholder="请选择赛事类型" style="width:240px;" v-model="model.gameType">
                <a-select-option :value="sports.id"  v-for="sports in typeModal.gameTypeList" >
                  {{ sports.value }}
                </a-select-option>
              </a-select>
            </a-form-item>
            </a-col>
        </a-row>

        <a-tabs defaultActiveKey="1" >
          <a-tab-pane tab="请选择要举办赛事的球馆" key="2" forceRender>
            <div >

              <a-row type="flex" style="margin-bottom:10px" :gutter="16" >
                <a-col :span="6"
                       @click="selectField(item,index)"
                       v-for="(item, index) in fieldInfoList" :key="index">

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
  </j-modal>
</template>

<script>
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import { getSportsTypeList } from '@/api/api'
  import JEditor from '@/components/jeecg/JEditor'
  import JDate from '@/components/jeecg/JDate'
  import JImageUpload from '@/components/jeecg/JImageUpload'

  export default {
    name: "PlayballGameModal",
    components: {
      JDate,
      JEditor,
      JImageUpload
    },
    data () {
      return {
        title:"操作",
        visible: false,
        model: {
          gamesInfo:'',
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        typeModal:{
          sportsTypeList:{},
          gameTypeList:[{
            id:"1",
            value:"小组赛+淘汰赛"
          },{
            id:"2",
            value:"淘汰赛"
          },{
            id:"3",
            value:"积分赛(联赛制)"
          }],
          typeName:''
        },

        fieldInfoList:{},
        fieldSelectInfo:{},
        gameImage:'',

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/playball/playballGame/add",
          edit: "/playball/playballGame/edit",
          fieldInfoList:"/businessinfo/field/listbsi"
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
        this.visible = true;

        getSportsTypeList('').then((res)=>{
          if(res.success){
            this.typeModal.sportsTypeList = res.result.records;
            //this.typeModal.typeName = this.typeModal.sportsTypeList[0].sportsName
          }
        })

        if(record){
          console.log("-----record",record)
          this.changeSportsList(record.sportsId)

          if(this.model.id){
            setTimeout(() => {
              this.gameImage = record.gameImage;
            }, 5)
          }
        }

        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'stage','sportsId','fieldId','gamesName','gamesInfo','gameRule','isDelete','version'))
		  //时间格式化
          this.form.setFieldsValue({enrollTime:this.model.enrollTime?moment(this.model.enrollTime):null})
          this.form.setFieldsValue({startTime:this.model.startTime?moment(this.model.startTime):null})
          this.form.setFieldsValue({endTime:this.model.endTime?moment(this.model.endTime):null})
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
            formData.enrollTime = formData.enrollTime?formData.enrollTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.startTime = formData.startTime?formData.startTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.endTime = formData.endTime?formData.endTime.format('YYYY-MM-DD HH:mm:ss'):null;
            if(this.fieldSelectInfo.id != null){
              formData.fieldId = this.fieldSelectInfo.id
            }
            if(this.gameImage != null){
              formData.gameImage = this.gameImage
            }

            console.log("---------",formData)
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
        this.fieldInfoList = {}
        this.$forceUpdate()
        //记得后面来调整分页，现在先走着
        console.log("获取运动类型sportid=",id)

        let params = {sportsId:id}
        getAction(this.url.fieldInfoList, params).then((res)=>{
          if(res.success){
            this.fieldInfoList= res.result.records;
            for(let index in this.fieldInfoList){
              this.fieldInfoList[index].bselect = true
            }
            this.$forceUpdate()
          }
        })
      },
      /*setFieldSelect(fieldInfo,fieldId){
        for(let i in this.fieldInfoList){
          if ( this.fieldInfoList[i].id == fieldId){
            this.fieldInfoList[i].bselect = false
            this.fieldSelectInfo = fieldInfo
          } else{
            this.fieldInfoList[i].bselect = true
          }
        }
        this.$forceUpdate()
      },*/
      selectField(fieldInfo,index){
        for(let i in this.fieldInfoList){
          if ( index == i){
            this.fieldInfoList[i].bselect = false
            this.fieldSelectInfo = fieldInfo
          } else{
            this.fieldInfoList[i].bselect = true
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

<style lang="less" scoped>
  .imgbox{
    position:relative;
    overflow: hidden;
  }
  img{
    width:100%;
    height:250px;
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
</style>