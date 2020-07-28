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

        <a-tabs defaultActiveKey="1" >
          <a-tab-pane :tab="teamName" key="1">
            <div>
              <a-form-item>
                <a-table :pagination="pagination" :columns="columns" :data-source="data" :scroll="{ x: 1500, y: 300 } ">

                  <span slot="actionTeamData" slot-scope="text, record">
                    <a @click="onClick(text, record)">{{text}}</a>
                 </span>

                  <a slot="action" slot-scope="text,record" @click="onClick">
                    <!--
                   <a-input-number v-decorator="[ 'dataValue', {}]" @change="onChange(text, record)"/>
                   -->
                  {{text}}
                  {{record}}
                </a>
              </a-table>
              </a-form-item>
            </div>
          </a-tab-pane>
        </a-tabs>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import { getSportsTypeList } from '@/api/api'

  export default {
    name: "PlayballScheduleUserDataModal",
    data () {
      return {
        title:"球员数据编辑",
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
        },
        sportsDataList:{},
        playersDataList:{},
        teamName:'',

        pagination:{
          pageSize: 100,
          hideOnSinglePage:true,
        },

        data:[],
        columns :[{ title: '球员姓名', width: 150, dataIndex: 'name', key: 'name', fixed: 'left' },],

        url: {
          //add: "/playball/playballAbilityValue/add",
          //edit: "/playball/playballAbilityValue/edit",
          getSportsDataBySportsId: "/playball/playballSportsData/getSportsDataBySportsId",
          getGamePlayersList: "/playball/playballPlayer/getGamePlayersList",
        },
      }
    },
    created () {

    },
    methods: {
      add () {
        //this.edit({});
      },
      edit (text, record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        console.log("----handleOk----",this.data)
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
            /*httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })*/

          }
        })
      },
      handleCancel () {
        this.close()
      },
      show(id,teamName,record){
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.teamName = teamName
        //获取球员数据列表
        this.loadUserData(id,record);
        //获取报名球队列表
        //this.loadPlayers(id,record);
      },
      loadUserData(teamId,record){
        let that = this
        getAction(that.url.getSportsDataBySportsId,{sportsId:record.sportsId}).then((res)=>{
          if(res.success){
            this.sportsDataList = res.result
            for(var i in this.sportsDataList){
              let data={}
              data.title = this.sportsDataList[i].dataName
              data.width=100
              data.dataIndex = "'dataIndex"+this.sportsDataList[i].id+"'"
              data.key = i,
              data.scopedSlots = { customRender: 'action' },
              this.columns.push(data)
            }
            console.log("------columns-----",this.columns)
            this.loadPlayers(teamId, record)

          }else{
            that.$message.warning(res.message);
          }
        });
      },
      loadPlayers(teamId, record){
        let that = this
        getAction(that.url.getGamePlayersList,{teamId:teamId,gameId:record.gamesId,scheduleId:record.id}).then((res)=>{
          if(res.success){
            that.playersDataList = res.result
            console.log("--------gui------",res);
            for(let i in that.playersDataList){
              let data={}
              data.key = i
              data.name = that.playersDataList[i].nickName
              for(var j in this.sportsDataList) {
                let dataName = "'dataIndex"+this.sportsDataList[j].id+"'"
                data[dataName] = this.sportsDataList[j].id
              }
              this.data.push(data)
            }
            console.log("------loadPlayers----",this.data)
          }else{
            that.$message.warning(res.message);
          }
        });
      },
      onChange(text, record){
        console.log(text)
        console.log(record)
      },
      onClick(item,x,xx){
        console.log(item)
        console.log(x)
        console.log(xx)
      }

    }
  }
</script>

<style lang="less" scoped>

</style>