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
              <a-table :pagination="pagination" :columns="columns" :data-source="data" :scroll="{ x: 500, y: 300 }">
                <template v-for="col in colData" :slot="col" slot-scope="text, record, index">
                  <div :key="col">
                    <a-input
                      v-if="record.editable"
                      style="margin: -5px 0"
                      :max-length="3"
                      :value="text"
                      @change="e => handleChange(e.target.value, record.key, col)"
                    />
                    <template v-else>
                      {{ text }}
                    </template>
                  </div>
                </template>

                <template slot="operation" slot-scope="text, record, index">
                  <div class="editable-row-operations">
                    <span v-if="record.editable">
                      <a @click="() => save(record.key)">保存</a>
                      <a @click="() => cancel(record.key)">取消</a>
                      <!--
                      <a-popconfirm title="Sure to cancel?" @confirm="() => cancel(record.key)">
                        <a>取消</a>
                      </a-popconfirm>
                      -->

                    </span>
                    <span v-else>
                      <a :disabled="editingKey !== ''" @click="() => edit(record.key)">编辑</a>
                    </span>
                  </div>
                </template>

              </a-table>
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

        colData:[],
        data:[],
        cacheData : [],
        editingKey: '',
        columns:[],

        url: {
          //add: "/playball/playballAbilityValue/add",
          //edit: "/playball/playballAbilityValue/edit",
          getSportsDataBySportsId: "/playball/playballSportsData/getSportsDataBySportsId",
          getGamePlayersList: "/playball/playballPlayer/getGamePlayersList",
          savePlayersData: "/playball/playballScheduleUserData/add",
          editPlayersData: "/playball/playballScheduleUserData/edit",
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
        this.close ()
      },
      handleCancel () {
        this.close()
      },
      show(id,teamName,record){
        this.colData=[];
        this.data=[];
        this.cacheData=[];
        this.sportsDataList={};
        this.playersDataList={};
        this.columns=[{ title: '球员姓名', width: 150, dataIndex: 'name', key: 'name', fixed: 'left' },];
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.teamName = teamName
        //获取数据列表
        this.loadUserData(id,record);
        //获取报名球员列表
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
              data.dataIndex = this.sportsDataList[i].dataName
              data.key = i.toString(),
              data.scopedSlots = { customRender: data.dataIndex },
              this.columns.push(data)
              this.colData.push(data.dataIndex)
            }
            this.columns.push({
              title: '操作',
              dataIndex: 'operation',
              scopedSlots: { customRender: 'operation' },
              fixed: 'right',
              width: 150,
            })

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
            console.log("----加载球员数据----",res.result)
            for(let i in that.playersDataList){
              let data = that.playersDataList[i]
              data.key = i.toString()
              data.name = that.playersDataList[i].nickName
              for(var j in this.sportsDataList) {
                data[this.sportsDataList[j].dataName] = 0
              }
              if( that.playersDataList[i].dataJson != null){
                Object.assign(data, ...JSON.parse(that.playersDataList[i].dataJson));
              }

              this.data.push(data)
            }

            this.cacheData = this.data.map(item => ({ ...item }));
          }else{
            that.$message.warning(res.message);
          }
        });
      },
      handleChange(value, key, column) {
        const reg = /^-?[0-9]*(\.[0-9]*)?$/;
        const newData = [...this.data];
        const target = newData.filter(item => key === item.key)[0];

        let temp = target[column]
        if (!reg.test(value)) {
          value = temp
        }

        if (target) {
          target[column] = value;
          this.data = newData;
        }
      },
      edit(key) {
        const newData = [...this.data];
        const target = newData.filter(item => key === item.key)[0];
        this.editingKey = key;
        if (target) {
          target.editable = true;
          this.data = newData;
        }
      },
      save(key) {
        const newData = [...this.data];
        const newCacheData = [...this.cacheData];
        const target = newData.filter(item => key === item.key)[0];
        const targetCache = newCacheData.filter(item => key === item.key)[0];
        if (target && targetCache) {
          delete target.editable;
          this.data = newData;
          Object.assign(targetCache, target);
          this.cacheData = newCacheData;
        }
        this.editingKey = '';

        let httpurl = '';
        let method = '';
        if(!target.scheduleUserDataId){
          httpurl+=this.url.savePlayersData;
          method = 'post';
        }else{
          httpurl+=this.url.editPlayersData;
          method = 'put';
        }
        let formData={}
        formData.id = target.scheduleUserDataId
        formData.scheduleId = this.model.id
        formData.sportsId = this.model.sportsId

        formData.playerId = target.tpId
        formData.userId = target.uid
        let dataJson=[]
        for(let index in this.colData){
          let temp={}
          let str = this.colData[index]
          temp[str] = target[str]
          dataJson.push(temp)
        }
        formData.dataJson = JSON.stringify(dataJson)
        console.log("---datajson---",dataJson)
        //时间格式化
        console.log(formData)
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            this.$message.success(res.message);
          }else{
            this.$message.warning(res.message);
          }
        })

      },
      cancel(key) {
        const newData = [...this.data];
        const target = newData.filter(item => key === item.key)[0];
        this.editingKey = '';
        if (target) {
          Object.assign(target, this.cacheData.filter(item => key === item.key)[0]);
          delete target.editable;
          this.data = newData;
        }
      },

    }
  }
</script>

<style lang="less" scoped>
  h3 {
    margin: 40px 0 0;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
  }

  .editable-row-operations a {
    margin-right: 8px;
  }
</style>