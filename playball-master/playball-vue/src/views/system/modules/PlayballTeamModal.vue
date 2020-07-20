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
          label="teamId">
          <a-input-number v-decorator="[ 'teamId', validatorRules.teamId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="队名">
          <a-input placeholder="请输入队名" v-decorator="['tName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="队徽（图片一张）">
          <a-input placeholder="请输入队徽（图片一张）" v-decorator="['tImage', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属单位名">
          <a-input placeholder="请输入所属单位名" v-decorator="['tUnitname', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="球队类型(关联运动类型表)">
          <a-input-number v-decorator="[ 'stId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前球队人数">
          <a-input-number v-decorator="[ 'tPlayersTotal', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前球队最大人数">
          <a-input-number v-decorator="[ 'tPlayersMax', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="球队介绍 招募说明">
          <a-input placeholder="请输入球队介绍 招募说明" v-decorator="['tIntroduce', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="胜 次数">
          <a-input-number v-decorator="[ 'tWinTotal', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="平 次数">
          <a-input-number v-decorator="[ 'tDivideTotal', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="负 次数">
          <a-input-number v-decorator="[ 'tLostTotal', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="球队创建人">
          <a-input-number v-decorator="[ 'uId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属省--对应地区表主键">
          <a-input-number v-decorator="[ 'tProvince', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属市--对应地区表主键">
          <a-input-number v-decorator="[ 'tCity', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属区--对应地区表主键">
          <a-input-number v-decorator="[ 'tDistrict', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="建队时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'tCreateteamtime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="队长 球员表ID  p_id">
          <a-input-number v-decorator="[ 'tCaptain', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="纪录创建时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'createtime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="纪录修改时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'updatetime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="逻辑删除">
          <a-input-number v-decorator="[ 'isDelete', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="版本号（用作乐观锁）">
          <a-input-number v-decorator="[ 'version', {}]" />
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
    name: "PlayballTeamModal",
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

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        teamId:{rules: [{ required: true, message: '请输入teamId!' }]},
        },
        url: {
          add: "/plalyball/playballTeam/add",
          edit: "/plalyball/playballTeam/edit",
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
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'teamId','tName','tImage','tUnitname','stId','tPlayersTotal','tPlayersMax','tIntroduce','tWinTotal','tDivideTotal','tLostTotal','uId','tProvince','tCity','tDistrict','tCaptain','isDelete','version'))
		  //时间格式化
          this.form.setFieldsValue({tCreateteamtime:this.model.tCreateteamtime?moment(this.model.tCreateteamtime):null})
          this.form.setFieldsValue({createtime:this.model.createtime?moment(this.model.createtime):null})
          this.form.setFieldsValue({updatetime:this.model.updatetime?moment(this.model.updatetime):null})
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
            formData.tCreateteamtime = formData.tCreateteamtime?formData.tCreateteamtime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.createtime = formData.createtime?formData.createtime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.updatetime = formData.updatetime?formData.updatetime.format('YYYY-MM-DD HH:mm:ss'):null;
            
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