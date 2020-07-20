<template>
  <div>
    <template v-if="bVisible">
      <div v-if="bGroupCreate">
        <span>请输入分组组数:</span>
        <a-input-number placeholder=" 请输入分组数" style="width:120px;margin-left:5px" :min="1" v-model="groupNum" />
        <a-button @click="createGroup" style="margin-left: 8px" type="primary" icon="plus">生成小组赛</a-button>
        <a-button @click="confirmMatch" style="margin-left: 8px" type="primary" icon="plus" v-if="bOKGroup">确认并生成赛程</a-button>
      </div>

      <!--小组赛分组情况展现-->
      <div v-for="(itemList, index) in groupList" :key="index">
        <span>第{{index+1}}小组</span>
        <a-row type="flex" style="margin-bottom:10px" :gutter="16">
          <a-col :span="5" v-for="(item, i) in itemList" :key="i">
            <a-card hoverable style="width: 150px">
              <a-card-meta :title="item.tname">
              </a-card-meta>
            </a-card>
          </a-col>
        </a-row>
      </div>
    </template>

    <!--小组赛赛程-->
    <div>
      <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="gamesInfo.groupMatchList">
        <a-list-item slot="renderItem"  slot-scope="itemList, index">
          <a-list-item-meta>
            <a slot="title">第{{round}}小组比赛</a>
          </a-list-item-meta>
          <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, i) in itemList" :key="i">
            <template v-if="item.teamId !=null && item.opponentId !=null ">
              <a-col :span="5">
                <a-card hoverable style="width: 240px">
                  <a-card-meta
                    :title="item.teamName"
                    description="0">
                    <a-avatar
                      slot="avatar"
                      size="large" icon="user"
                      :src="item.teamImage"/>
                  </a-card-meta>
                </a-card>
              </a-col>
              <a-col>
                <div style="margin-left:15px;margin-top:35px;">
                  <span>vs</span>
                </div>
              </a-col>
              <a-col :span="5" >
                <a-card hoverable style="width: 240px">
                  <a-card-meta
                    :title="item.opponentName"
                    description="0">
                    <a-avatar
                      slot="avatar"
                      size="large" icon="user"
                      :src="item.opponentImage"
                    />
                  </a-card-meta>
                </a-card>
              </a-col>

              <a-col :span="5">
                <a-card :bordered="false" style="width: 240px">
                  <j-date v-model="item.matchTime"
                          placeholder="请设置比赛时间:"
                          :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss"
                          @ok="onDateOk(item)"/>
                </a-card>
              </a-col>
            </template>

          </a-row>
        </a-list-item>
      </a-list>
    </div>

  </div>

</template>

<script>
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'

  const directionType = {
    horizontal: 'horizontal',
    vertical: 'vertical'
  }

  export default {
    name: "group",
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
        bVisible:false,
        bGroupCreate: true,
        bOKGroup:false,
        groupNum:0,
        groupList:{},
        gamesInfo:{},
        round:1,

        pagination: {
          onChange: page => {
            this.round = page
          },
          pageSize: 1,
        },

        url: {
          createGroupMacth: "/playball/playballSchedule/createGroupMacth",
          getGroupMatchList: "/playball/playballSchedule/getGroupMatchList",
          addGroupMacth: "/playball/playballSchedule/addGroupMacth",
        }
      }
    },
    created () {
      this.loadMacth()
    },
    methods: {
      getSuccess(callback){
      },
      loadMacth(){
        this.bVisible=false
        this.bGroupCreate=true
        this.bOKGroup=false
        console.log("gamesid=",this.gamesId)
        let that = this
        //获取比赛
        let params = {gamesId:this.gamesId}
        getAction(that.url.getGroupMatchList, params).then((re)=> {
          if (re.success) {
            that.gamesInfo = re.result
            console.log("重新获取",re.result)
            if(that.gamesInfo.gameInfo.stage == 0){
              this.bCreate = true
              this.bVisible = true
            }else{
              that.bCreate = false
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
                that.bCreate = true
                that.bNextStage = true
              }*/
            }
          }
        })
      },
      createGroup(){
        let params = {
          gamesId:this.gamesId,
          groupNum:this.groupNum,
        }
        if(this.groupNum<3){
          return
        }
        let that=this;
        getAction(this.url.createGroupMacth, params).then((res)=>{
          if(res.success){
            that.groupList = res.result;
            that.bOKGroup=true;
          }
        })
      },
      confirmMatch(){
        let that = this
        let httpurl = '';
        let method = '';
        httpurl+=this.url.addGroupMacth;
        method = 'post';
        //let formData = Object.assign({}, this.matchList);
        let formData = {}
        //formData.finished = that.finished
        formData.gamesId = that.gamesId
        formData.groupList = Object.assign([], this.groupList)
        console.log("fromData", formData)
        //向数据库中存入比赛信息
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            console.log(res.result)
            that.bVisible = false
            that.gamesInfo = res.result
          }else{
          }
        })
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
    }
  }
</script>

<style scoped>

</style>