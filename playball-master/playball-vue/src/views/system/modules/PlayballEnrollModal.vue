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

      <a-row>
        <a-col :span="6" style="padding:10px" v-for="(item, index) in enrollPlayerList" :key="index">
          <a-card hoverable>
            <!--
            <div>
              <a-avatar slot="avatar" :src="item.headImage"/>
            </div>
            -->
            <a-card-meta :title="item.nickName?item.nickName:item.userName">
              <template slot="description">
                <p>球衣号码:{{item.tpClothesNumber?item.tpClothesNumber:'无'}}</p>
                <p>位置:{{item.positionName?item.positionName:'无'}}</p>
              </template>
            </a-card-meta>
          </a-card>
        </a-col>
      </a-row>

    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "PlayballEnrollModal",
    data () {
      return {
        title:"",
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

        enrollPlayerList:{},

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          getEnrollPlayerById:"/playball/playballEnroll/getEnrollPlayerById",
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
        this.title = this.model.teamName

        this.loadPlayers(this.model)

      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.enrollPlayerList = {}
      },
      handleOk () {
        this.$emit('ok');
        this.visible = false;
        this.enrollPlayerList = {}
      },
      handleCancel () {
        this.close()
      },

      loadPlayers(param){
        getAction(this.url.getEnrollPlayerById,param).then((res)=>{
          if(res.success){
            this.enrollPlayerList=res.result;
            console.log(this.enrollPlayerList)
          }
        });
      }

    }
  }
</script>

<style lang="less" scoped>

</style>