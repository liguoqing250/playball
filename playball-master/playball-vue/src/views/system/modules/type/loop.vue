<template>
  <div>

    <template v-if="showCreateTemplate">
      <div>
        <a-button @click="createMatch" type="primary" icon="plus" >生成比赛</a-button>
        <a-button @click="confirmMatch" type="primary" icon="plus" style="margin-left: 8px" v-if="bAdd">确认</a-button>
      </div>

      <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="matchList">
        <a-list-item slot="renderItem"  slot-scope="itemList, index">
          <a-list-item-meta>
            <a slot="title">第{{round}}轮比赛</a>
          </a-list-item-meta>
          <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, i) in itemList" :key="i">
            <a-col :span="5">
                <a-card hoverable style="width: 240px">
                  <a-card-meta :title="item.teamName">
                  </a-card-meta>
                </a-card>
            </a-col>

            <a-col :span="5" v-if="item.opponentId">
                <a-card hoverable style="width: 240px">
                  <a-card-meta :title="item.opponentName" >
                  </a-card-meta>
                </a-card>
            </a-col>

          </a-row>
        </a-list-item>
      </a-list>
    </template>

    <template v-if="!showCreateTemplate">
      <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="gamesInfo.matchList">
        <a-list-item slot="renderItem"  slot-scope="itemList, index">
          <a-list-item-meta>
            <a slot="title">第{{round}}轮比赛</a>
          </a-list-item-meta>
            <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, i) in itemList" :key="i">
              <a-col :span="5">
                <a-card hoverable style="width: 240px">
                  <a-card-meta
                    :title="item.teamName"
                    :description="item.enterBall">
                    <a-avatar
                      slot="avatar"
                      size="large" icon="user"
                      src=""/>
                  </a-card-meta>
                </a-card>
              </a-col>

              <template v-if="item.opponentId">
                <a-col>
                  <div style="margin-left:15px;margin-top:35px;">
                    <span>vs</span>
                  </div>
                </a-col>
                <a-col :span="5">
                  <a-card hoverable style="width: 240px">
                    <a-card-meta
                      :title="item.opponentName"
                      :description="item.lostBall">
                      <a-avatar
                        slot="avatar"
                        size="large" icon="user"
                        src=""
                      />
                    </a-card-meta>
                  </a-card>
                </a-col>

                <a-col :span="10">
                  <a-card :bordered="false" >
                    <p v-if="item.matchTime">比赛时间：{{item.matchTime}}</p>
                    <p v-else>还没设置比赛时间，请到赛程管理中设置比赛时间</p>
                    <!--
                    <j-date v-model="item.matchTime"
                            placeholder="请设置比赛时间:"
                            :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss"
                            @ok="onDateOk(item)"/>
                     -->
                  </a-card>
                </a-col>
              </template>
              <template v-else>
                <a-col>
                  <div style="margin-left:15px;margin-top:35px;">
                    <span>该轮比赛轮空，默认3:0获胜</span>
                  </div>
                </a-col>
              </template>

          </a-row>
        </a-list-item>
      </a-list>
    </template>

  </div>
</template>

<script>
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'
  import moment from 'moment'

  export default {
    name: "loop",
    components: {
      JDate
    },
    props: {
      gamesId: {
        type: Number,
        default: 0
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

        bAdd:false,

        showCreateTemplate:true,

        matchList:[],
        round:1,
        finished:0,
        gamesInfo:{},

        url: {
          createLoopMacth: "/playball/playballSchedule/createLoopMacth",
          getLoopMatchList: "/playball/playballSchedule/getOutMatchList",
          addLoopMacth: "/playball/playballSchedule/addLoopMacth",
        }
      }
    },
    created () {
      this.loadMacth()
    },
    methods: {
      getSuccess(callback){
      },
      createMatch(){
        let params = {
          gamesId:this.gamesId
        }

        getAction(this.url.createLoopMacth, params).then((res)=>{
          if(res.success){
            this.matchList = res.result
            this.bAdd = true
            //this.finished = 1
            console.log(this.matchList)
          }
        })
      },
      loadMacth(){
        console.log("gamesid=",this.gamesId)
        let that = this
        //获取比赛
        let params = {gamesId:this.gamesId}
        getAction(that.url.getLoopMatchList, params).then((re)=> {
          if (re.success) {
            that.gamesInfo = re.result
            console.log("重新获取",re.result)
            if(that.gamesInfo.gameInfo.stage != 0 && that.gamesInfo.matchList.length > 0 ){
              this.showCreateTemplate = false
              /*let size = that.gamesInfo.gameInfo.stage
              let bFinish=true
              if(that.gamesInfo.gameInfo.finished==1){
                that.bChampion = true
                that.bNextStage = false
                return
              }
              for(var i in that.gamesInfo.scheduleList[size-1]){
                if(that.gamesInfo.scheduleList[size-1][i].gameSchedule.gameStatus == 2){
                  //表示有比赛没比完，不做任何事情
                  bFinish = false
                  break
                }
              }
              if(bFinish){
                that.bNextStage = true
              }*/
            }
          }
        })
      },
      setMatchTime(){
      },
      addMatch(){
        let that = this
        let httpurl = '';
        let method = '';
        httpurl+=this.url.addLoopMacth;
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
            that.showCreateTemplate = false
            that.loadMacth()
          }else{
          }
        })
      },
      confirmMatch() {
        let that = this
        this.$confirm({
          title: '确认赛程',
          content: '您是否确认以下赛程作为本赛季最终赛程？',
          okText: '确认',
          cancelText: '取消',
          onOk: function () {
            that.addMatch()
          }
        });
      },
      onDateOk(item){
        let that = this
        this.$confirm({
          title: '确认比赛时间',
          content: '您确认修改比赛时间为'+item.matchTime,
          okText: '确认',
          cancelText: '取消',
          onOk: function () {

          }
        });
      },

    }//end methods
  }
</script>

<style lang="less" scoped>

  .ant-card-body{
    padding:15px;
  }

</style>