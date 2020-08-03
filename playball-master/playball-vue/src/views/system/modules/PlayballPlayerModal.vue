<template>
  <j-modal
    title="球员能力值设置"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-row >
          <a-col :span="8" v-for="(item, i) in abilityValueList" :key="i">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              :label="item.avName">
              <a-input-number style="width:80px" :min="1" :max="100" v-model="item.value"/>
            </a-form-item>
          </a-col>
        </a-row>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import ARow from 'ant-design-vue/es/grid/Row'

  export default {
    name: "PlayballPlayerModal",
    components: { ARow },
    props: {
      sportsId: {
        type: Number,
        default: null
      },

    },
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),

        abilityValueList:{},

        validatorRules:{
        tpId:{rules: [{ required: true, message: '请输入tpId!' }]},
        },
        url: {
          add: "/playball/playballPlayer/add",
          edit: "/playball/playballPlayer/edit",
          getAbilityBySportsId:"/playball/playballAbilityValue/getAbilityBySportsId",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        //this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;

        this.loadAbility(record)
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
            if(!this.model.tpId){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            let abilityData=[]
            for(var index in that.abilityValueList){
              abilityData.push({
                avName: that.abilityValueList[index].avName,
                value: that.abilityValueList[index].value,
              })
            }
            formData.abilityValue = JSON.stringify(abilityData)

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
      loadAbility(record){
        let that = this
        let list = JSON.parse(record.abilityValue)
        getAction(that.url.getAbilityBySportsId,{sportsId:record.sportsId}).then((res)=>{
          if(res.success){
            that.abilityValueList = res.result
            for(var index in list){
              for(var i in that.abilityValueList){
                if(list[index].avName == that.abilityValueList[i].avName){
                  that.abilityValueList[i].value = list[index].value
                }
              }
            }
          }else{
            that.$message.warning(res.message);
          }
        });
      },

    }
  }
</script>

<style lang="less" scoped>

</style>