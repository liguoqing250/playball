<template>
  <div>

    <a-select placeholder="请选择比赛球队" style="width:240px;" v-decorator="['why',{}]" >
      <a-select-option :value="item.teamId"  v-for="item in tempEnrollTeamList" >
        {{ item.tname }}
      </a-select-option>
    </a-select>

    <a-form :form="form">
      <!-- 子表单区域 -->
      <a-tabs defaultActiveKey="1" >
        <a-tab-pane tab="赛程信息" key="1">
          <div>
            <a-row type="flex" style="margin-bottom:10px" :gutter="16">
              <a-col :span="5">球队</a-col>
              <a-col :span="1"></a-col>
              <a-col :span="5">对阵球队</a-col>
              <a-col :span="5">比赛时间</a-col>
              <a-col :span="4">操作</a-col>
            </a-row>

            <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in matchList" :key="index">
              <a-col :span="5">
                <a-form-item>
                  <a-select placeholder="请选择比赛球队" style="width:200px;" v-model="item.teamId"
                            :disabled="item.bTeamSelect"
                            @change ="changeSportsList($event)">
                    <a-select-option :value="teamItem.teamId"  v-for="(teamItem,i) in tempEnrollTeamList" :key="i" :disabled="tempEnrollTeamList[i].bShow">
                      {{ teamItem.tname }}
                    </a-select-option>
                  </a-select>

                </a-form-item>
              </a-col>
              <a-col :span="1">
                <a-form-item>
                  <span>vs</span>
                </a-form-item>
              </a-col>
              <a-col :span="5">
                <!--v-decorator="['item.teamId',{}]" v-decorator="['matchList['+index+'].teamId',{}]"v-model="item.teamId"v-decorator="['matchList['+index+'].oppentntId',{}]"-->
                <a-form-item>
                  <a-select placeholder="请选择比赛球队" style="width:200px;" v-model="item.opponentId"
                            :disabled="item.bOpponentSelect"
                            @change ="changeSportsList($event)">
                    <a-select-option :value="teamItem.teamId"  v-for="(teamItem,i) in tempEnrollTeamList" :key="i" :disabled="tempEnrollTeamList[i].bShow">
                      {{ teamItem.tname }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="5">
                <a-form-item>
                  <j-date v-decorator="['matchList['+index+'].matchTime', {'initialValue':item.matchTime}]"
                          placeholder="请设置比赛时间:"
                          :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss"/>
                </a-form-item>
              </a-col>
              <a-col :span="4">
                <a-form-item>
                  <a-button @click="addRowCustom" icon="plus"></a-button>&nbsp;
                  <a-button @click="delRowCustom(index,item)" icon="minus"></a-button>
                </a-form-item>
              </a-col>
            </a-row>
          </div>
        </a-tab-pane>
      </a-tabs>
    </a-form>
  </div>
</template>

<script>
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'
  import moment from 'moment'
  export default {
    name: "custom",
    components: {
      JDate
    },
    props: {
      gamesId: {
        type: Number,
        default: 0
      },
      enrollTeamList: {
        type: Array,
        default: () => []
      },
    },
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        pagination: {
          onChange: page => {
            this.round = page
          },
          pageSize: 1,
        },

        bCreate:false,
        bAdd:false,

        tempEnrollTeamList:[],
        round:1,
        finished:0,
        gamesInfo:{},
        matchList: [{}],

        listShow:[],

        why:"",

        form: this.$form.createForm(this),
        url: {
          createLoopMacth: "/playball/playballSchedule/createLoopMacth",
          getLoopMatchList: "/playball/playballSchedule/getLoopMatchList",
          addLoopMacth: "/playball/playballSchedule/addLoopMacth",
        }
      }
    },
    created () {
      this.loadMacth()
    },
    methods: {
      getSuccess(callback){
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          console.log('values',values)
          console.log('matchList',this.matchList)
          if (!err) {
            console.log(values)
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
      loadMacth(){

       this.tempEnrollTeamList = Object.assign([], this.enrollTeamList);
       for(var i=0;i<this.tempEnrollTeamList.length;i++){
         //let data={bShow:true}
         //this.listShow.push(data)
         this.tempEnrollTeamList[i].bShow = false
       }
       console.log(this.listShow)
        console.log(this.tempEnrollTeamList )

      },
      addRowCustom () {
        this.matchList.push({});
        this.$forceUpdate();
      },
      delRowCustom (index,item) {
        console.log(item.teamId)
        for(var i in this.enrollTeamList){
          if(this.enrollTeamList[i].teamId == item.teamId){
            this.tempEnrollTeamList[i].bShow = false
          }
          if(this.enrollTeamList[i].teamId == item.opponentId){
            this.tempEnrollTeamList[i].bShow = false
          }
        }
        item.bTeamSelect =false
        item.bOpponentSelect =false

        if(this.matchList.length > 1){
          this.matchList.splice(index,1);
          this.$forceUpdate();
        }
      },
      changeSportsList(id){
        console.log("id",id)
        console.log("this.matchList matchList",this.matchList)
        this.tempEnrollTeamList.some((item, i) => {
          if (item.teamId == id ){
            this.tempEnrollTeamList[i].bShow = true
            return true
          }
        })

        for(var i in this.matchList){
          if(this.matchList[i].teamId != null){
            this.matchList[i].bTeamSelect =true
          }
          if(this.matchList[i].opponentId != null){
            this.matchList[i].bOpponentSelect =true
          }
        }

      },

    }//end methods
  }
</script>

<style lang="less" scoped>

  .ant-card-body{
    padding:15px;
  }

</style>