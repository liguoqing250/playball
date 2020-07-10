<template>
  <a-modal
    :title="this.gamesInfo.gamesName"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row class="form-row" :gutter="16">
          <a-col :lg="4">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="">
              <a-button @click="showGamesTeam" icon="search">查看参数球队</a-button>
            </a-form-item>
          </a-col>

          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="比赛方式">
              <a-select placeholder="请选则比赛方式" v-decorator="['ctype',{}]">
                <a-select-option value="1">积分赛+淘汰赛</a-select-option>
                <a-select-option value="2">淘汰赛</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24" >
            <span style="float: left;overflow: hidden;" >
              <a-button  v-if="gamesInfo.stage==0"
                @click="createGame" icon="">
                生成比赛</a-button>
            </span>
          </a-col>
        </a-row>

        <a-tabs defaultActiveKey="1" >
        <a-tab-pane tab="赛程表" key="1">
          <div>
            <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(itemList, index) in matchModal.matchList" :key="index">
              <a-col :span="5" v-for="(item, i) in itemList" :key="i">
                <a-form-item>
                    <a-card hoverable style="width: 240px">
                      <a-card-meta :title="item.tname">
                      </a-card-meta>
                    </a-card>
                </a-form-item>
              </a-col>

            </a-row>
          </div>

          <div v-for="(itemList, index) in scheduleList" :key="index">
            <span>第{{index+1}}阶段对阵表</span>
            <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, i) in itemList" :key="i">
              <a-col :span="5">
                <a-form-item>
                  <a-card hoverable style="width: 240px">
                    <a-card-meta :title="item.team.tname">
                    </a-card-meta>

                    <a-input placeholder="请输入比分" v-model="item.gameSchedule.enterBall"/>

                  </a-card>
                </a-form-item>
              </a-col>
              <a-col :span="5">
                <a-form-item>
                  <a-card hoverable style="width: 240px" v-if="item.topponent != null">
                    <a-card-meta :title="item.topponent.tname">
                    </a-card-meta>

                    <a-input placeholder="请输入比分" v-model="item.gameSchedule.lostBall"/>

                  </a-card>
                </a-form-item>
              </a-col>

              <a-col :lg="8" v-if="item.topponent != null">
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="比赛时间">
                  <j-date v-model="item.gameSchedule.matchTime" :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss" />
                </a-form-item>
                <span v-if="item.gameSchedule.gameResult == 1">结果:{{item.team.tname}}胜</span>
                <span v-if="item.gameSchedule.gameResult == 3">结果:{{item.topponent.tname}}胜</span>
              </a-col>

              <a-col :md="6" :sm="24" v-if="item.topponent != null && item.gameSchedule.gameStatus==2">
                <span style="float: left;overflow: hidden;" >

                  <!--
                  <a-button type="primary" @click="setMacthTime(item)" v-if="Date.parse(item.gameSchedule.matchTime) > new Date() ">
                    设置比赛时间</a-button>
                  <a-button type="primary" @click="setMacthTime(item)" v-else-if="item.gameSchedule.matchTime==null">
                    设置比赛时间</a-button>
                    -->
                  <a-button type="primary" @click="setMacthTime(item)">
                    设置比赛时间</a-button>
                  <a-button type="primary" @click="setResult(item)" v-if="item.gameSchedule.gameStatus === 2 && Date.parse(item.gameSchedule.matchTime) < new Date()">
                    更新比赛结果</a-button>

                </span>
              </a-col>
            </a-row>

            <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-if="this.bChampion" v-for="(itemList, index) in matchModal.matchNextList" :key="index">
              <a-col :span="5" v-for="(item, i) in itemList" :key="i">
                <a-form-item>
                  <a-card hoverable style="width: 240px">
                    <a-card-meta :title="item.tname">
                    </a-card-meta>
                  </a-card>
                </a-form-item>
              </a-col>
            </a-row>

          </div>

          <a-row>
            <a-col :md="6" :sm="24" >
                <span style="float: left;overflow: hidden;"  v-if="nextStage">
                  <a-button @click="createNextGame" icon="">
                    生成下阶段比赛</a-button>
                </span>
            </a-col>
            <a-col :md="6" :sm="24" >
                <span style="float: left;overflow: hidden;"  v-if="nextStageOk">
                  <a-button @click="nextGameOK" icon="">
                    确定</a-button>
                </span>
            </a-col>
          </a-row>

          <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(itemList, index) in matchModal.matchNextList" :key="index">
            <a-col :span="5" v-for="(item, i) in itemList" :key="i">
              <a-form-item>
                <a-card hoverable style="width: 240px">
                  <a-card-meta :title="item.tname">
                  </a-card-meta>
                </a-card>
              </a-form-item>
            </a-col>
          </a-row>

        </a-tab-pane>
        </a-tabs>

        <!--报名面板显示-->
        <div>
          <j-modal
            :visible.sync="enrollModal.visible"
            :width="1200"
            :title="enrollModal.title"
          >
            <template>
              <div >
                <a-row type="flex" style="margin-bottom:10px" :gutter="16" >
                  <a-col :span="6"
                         @click="selectField(item,index)"
                         v-for="(item, index) in enrollModal.enrollTeamList" :key="index">
                    <a-form-item>
                      <a-card hoverable style="width: 240px">
                        <a-card-meta :title="item.tname">
                        </a-card-meta>
                      </a-card>
                    </a-form-item>

                  </a-col>
                </a-row>
              </div>

            </template>
          </j-modal>
        </div>

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
    name: "PlayballSchedule",
    components: {
      ARow,
      JDate,
      JEditor
    },
    data () {
      return {
        visible: false,
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

        gamesInfo:{},

        enrollModal:{
          visible:false,
          title:"",
          enrollTeamList:{}
        },

        matchModal:{
          visible:false,
          title:"",
          matchList:{},
          matchNextList:{}
        },

        scheduleList:{},
        nextStage:true,
        nextStageOk:false,
        bChampion:false,

        url: {
          getGamesInfoById: "/cms/playball/getGamesInfo",
          update:"/cms/schedule/update",
          scheduleList:"/cms/schedule/scheduleList",
          addMacthInfo:"/cms/schedule/addMatchInfo",
          enrollTeamList: "/cms/schedule/enrollteam",
          matchList: "/cms/schedule/matchinfo",
          createNextGame:"/cms/schedule/createNextGame",
          nextStageScheduleList:"/cms/schedule/nextStageScheduleList",
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

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        let formData = {}
        formData.gamesInfo = that.gamesInfo
        formData.matchList = that.matchModal.matchList

        let httpurl = that.url.addMacthInfo
        let method = 'post';

        console.log("formData",formData)
        //状态值为0时没有创建比赛，需创建并插入
        if(this.gamesInfo.stage ==0 ){
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


        this.visible = false
        this.close()
      },
      handleCancel () {
        this.visible = false
        this.close()
      },
      show(recode){
        console.log("赛事信息",this.gamesInfo)
        this.visible = true;
        this.gamesInfo = recode;
        this.enrollModal.enrollTeamList={}
        this.matchModal.matchList = {}
        this.matchModal.matchNextList = {}
        this.scheduleList = {}

        let params = {gamesId:this.gamesInfo.id}
        console.log("赛事信息",this.gamesInfo.id)
        //重新获取一次赛事信息
        getAction(this.url.getGamesInfoById, params).then((re)=> {
          if (re.success) {
            this.gamesInfo = re.result
            console.log("重新获取",this.gamesInfo)
            params.stage = this.gamesInfo.stage

            if(this.gamesInfo.stage !=0 ){
              //stage不等0，说明比赛已经确认，直接拉取不同stage的阶段的比赛显示
              getAction(this.url.scheduleList, params).then((res)=>{
                if(res.success){
                  this.scheduleList = res.result
                  console.log("======",this.scheduleList)
                  //把所有结果取来比较该阶段是否完赛
                  for (var i = 0; i < this.scheduleList.length; i++) {
                    //var tempResult=0;
                    for(var j=0; j<this.scheduleList[i].length;j++){
                      if(this.scheduleList[i][j].gameSchedule.gameStatus != 1){
                        this.nextStage=false;
                        break;
                      }
                    }
                  }

                }
              })
            }
          }
        })
      },
      setMacthTime(item){
        const that = this;
        let formData = Object.assign({},item.gameSchedule);
        let httpurl = that.url.update
        let method = 'put';

        console.log(formData)
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            that.$message.success(res.message);
            that.$emit('ok');
          }else{
            that.$message.warning(res.message);
          }
        }).finally(() => {
          //that.confirmLoading = false;
          //that.close();
          this.$forceUpdate()
        })

      },
      createNextGame(){
        let length = this.scheduleList.length

        //直接取最后一个阶段的数据
        let list = this.scheduleList[length-1]
        /*let paramList = new Array();
        let currentStage = 0
        for(var i=0; i<list.length; i++){
          let formData={}
          if(list[i].gameSchedule.gameStatus == 1){
            //=1胜利取team数据
            formData = Object.assign({},list[i].team)
          }else if(list[i].gameSchedule.gameStatus == 3){
            //=3失败取oppent数据
            formData = Object.assign({},list[i].topponent)
          }else{
            //淘汰赛没有平局
          }
          paramList.push(formData)
          currentStage = list[i].gameSchedule.stage
        }
        console.log("createNextGame",paramList)*/

        let params={}
        params.gamesId = this.gamesInfo.id
        params.currentStage = this.gamesInfo.stage

        console.log("params",params)

        getAction(this.url.nextStageScheduleList, params).then((res)=>{
          if(res.success){
            console.log("createNextGame 结果",res);
            this.matchModal.matchNextList = res.result
            if(this.this.matchModal.matchNextList.length==1){
                this.nextStage=false
                this.nextStageOk=false
                this.bChampion=true
            }else{
              this.nextStage=true
              this.nextStageOk=true
            }

          }
        })


      },

      setResult(item){
        const that = this;
        let formData = Object.assign({},item.gameSchedule);

        let enterBall = formData.enterBall
        let lostBall = formData.lostBall

        if(enterBall>lostBall){
          formData.gameResult = 1
        }else if(enterBall<lostBall){
          formData.gameResult = 3
        }else{
          formData.gameResult = 2
        }
        formData.gameStatus = 1

        let httpurl = that.url.update
        let method = 'put';

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
      },
      nextGameOK(){
        const that = this;
        let formData = {}
        formData.gamesInfo = that.gamesInfo
        formData.matchList = that.matchModal.matchNextList

        let httpurl = that.url.addMacthInfo
        let method = 'post';

        console.log("httpurl",httpurl)
        console.log("formData",formData)
        //状态值为0时没有创建比赛，需创建并插入

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

        that.matchModal.matchNextList={}
        this.nextStage=false;
        this.visible = false
        this.close()
      },
      createGame(){
        let params = {gamesId:this.gamesInfo.id}

        getAction(this.url.matchList, params).then((res)=>{
          if(res.success){
            this.matchModal.matchList = res.result
          }
        })
      },
      showGamesTeam(){
        let params = {gamesId:this.gamesInfo.id}
        getAction(this.url.enrollTeamList, params).then((res)=>{
          if(res.success){
            console.log("showGamesTeam 结果",res);
            this.enrollModal.enrollTeamList = res.result
            this.enrollModal.visible = true
          }
        })
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