<template>
    <div>
      <a-tabs>
        <a-tab-pane tab="赛程表" key="1">
          <!--已有模块-->
          <div v-for="(itemList, index) in gamesInfo.matchList" :key="index">
            <span>第{{index+1}}阶段对阵表</span>
            <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, i) in itemList" :key="i">
              <a-col :span="5">
                <a-form-item>
                  <a-card hoverable style="width: 240px">
                    <a-card-meta :title="item.teamName">
                    </a-card-meta>

                    <a-input placeholder="请输入比分" v-model="item.enterBall"/>

                  </a-card>
                </a-form-item>
              </a-col>

              <a-col :span="5">
                <a-form-item>
                  <a-card hoverable style="width: 240px" v-if="item.opponentId != null">
                    <a-card-meta :title="item.opponentName">
                    </a-card-meta>

                    <a-input placeholder="请输入比分" v-model="item.lostBall"/>

                  </a-card>
                </a-form-item>
              </a-col>

              <a-col :lg="8" >
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="比赛时间:" v-if="item.opponentId != null">
                  <span>{{item.matchTime}}</span>
                </a-form-item>
                <span v-if="item.gameResult == 1">结果:{{item.teamName}}获胜</span>
                <span v-if="item.gameResult == 3">结果:{{item.opponentName}}获胜</span>
                <span v-if="item.gameStatus == 2">比赛还未开始</span>
              </a-col>
            </a-row>
          </div>
          <div>
          </div>

          <a-button @click="createMatch" type="primary" icon="plus" v-if="bCreate">生成比赛</a-button>
          <!--创建模块-->
          <div>
            <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in matchList" :key="index">
              <a-col>
                <a-form-item>
                  <a-card hoverable style="width: 240px">
                    <a-card-meta :title="item.teamName">
                    </a-card-meta>
                  </a-card>
                </a-form-item>
              </a-col>

              <a-col>
                <a-form-item>
                  <a-card hoverable style="width: 240px" v-if="item.opponentId != null">
                    <a-card-meta :title="item.opponentName">
                    </a-card-meta>
                  </a-card>
                </a-form-item>
              </a-col>

              <a-col :md="12">
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="请设置比赛时间" v-if="item.opponentId != null">
                  <j-date v-model="item.matchTime" :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss" />
                </a-form-item>
              </a-col>
            </a-row>
          </div>

        </a-tab-pane>
      </a-tabs>

    </div>
</template>

<script>
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'

  export default {
    name: "out",
    props: {
      gamesId: {
        type: Number,
        default: 0
      },

    },
    components: {
      JDate
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

        bCreate:false,
        bNextStage:false,
        bChampion:false,
        matchList:{},
        gamesInfo:{},
        finished:0,

        url: {
          getGameInfoById: "/playball/playballGame/getGameById",
          getOutMatchList: "/playball/playballSchedule/getOutMatchList",
          createOutMacth: "/playball/playballSchedule/createOutMacth",
          addOutMacth: "/playball/playballSchedule/addOutMacth",
        }
      }
    },
    created () {
      this.loadMacth()
    },
    beforeDestroy() {
      this.bCreate=false
      this.bNextStage=false
      this.bChampion = false
      this.matchList={}
      this.gamesInfo={}
    },
    methods: {
      getSuccess(callback){
        if(this.bCreate){
          if (this.matchList.length > 0){
            for (var i in this.matchList) {
              console.log("比赛时间",this.matchList[i].matchTime)
              if(this.matchList[i].matchTime == null && this.matchList[i].opponentId != null){
                callback(false)
                return
              }
            }
            let that = this
            let httpurl = '';
            let method = '';
            httpurl+=this.url.addOutMacth;
            method = 'post';
            //let formData = Object.assign({}, this.matchList);
            let formData = {}
            formData.finished = that.finished
            formData.gamesId = that.gamesId
            formData.matchList = Object.assign([], this.matchList)
            console.log("fromData", formData)
            //向数据库中存入比赛信息
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                callback(true)
              }else{
                callback(false)
              }
            })
          }else{
            callback(true)
          }
        }else{
          callback(true)
        }

      },

      loadMacth(){
        console.log("gamesid=",this.gamesId)
        let that = this
        //获取比赛
        let params = {gamesId:this.gamesId}
        getAction(that.url.getOutMatchList, params).then((re)=> {
          if (re.success) {
            that.gamesInfo = re.result
            console.log("--------重新获取-------",re.result)
            if(that.gamesInfo.gameInfo.stage == 0){
              this.bCreate = true
            }else{
              that.bCreate = false
              let size = that.gamesInfo.gameInfo.stage
              let bFinish=true
              if(that.gamesInfo.gameInfo.finished==1){
                that.bChampion = true
                that.bNextStage = false
                return
              }
              //获取最后一个阶段结果
              /*if(that.gamesInfo.scheduleList[size-1].length == 1){
                console.log("冠军啊冠军")
                that.bChampion = true
                return
              }*/
              for(var i in that.gamesInfo.matchList[size-1]){
                if(that.gamesInfo.matchList[size-1][i].gameStatus == 2){
                  //表示有比赛没比完，不做任何事情
                  bFinish = false
                  break
                }
              }
              if(bFinish){
                that.bCreate = true
                that.bNextStage = true
              }
            }
          }
        })

      },
      createMatch(){
        let params = {
          gamesId:this.gamesId,
          nextStage:this.bNextStage
        }
        console.log("-------创建比赛-------params",params)
        getAction(this.url.createOutMacth, params).then((res)=>{
          console.log("-------创建比赛-------",res)
          if(res.success){
            this.matchList = res.result.matchList
            this.finished = res.result.finished
            //this.bCreate = false
            console.log(this.matchList)
          }
        })
      },

    }
  }
</script>

<style scoped>

</style>