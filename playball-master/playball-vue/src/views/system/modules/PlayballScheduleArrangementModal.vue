<template>
  <a-modal
    :title="this.gamesInfo.gamesName"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">

    <div>
      <a-form-item>
        <span>共{{enrollTeamList.length?enrollTeamList.length:0}}只队伍参加比赛</span>

        <span style="overflow: hidden;" >
              <a @click="showTeam" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>


        <a-button @click="handleAdd" type="primary" icon="plus" v-if="bCreate">生成比赛</a-button>
      </a-form-item>


      <!-- 球队展示区域 -->
      <template v-if="toggleSearchStatus">
        <a-row type="flex" style="margin-bottom:10px" :gutter="16" >
          <a-col :span="6"
                 @click="selectField(item,index)"
                 v-for="(item, index) in enrollTeamList" :key="index">
            <a-form-item>
              <a-card hoverable style="width:240px">
                <a-card-meta :title="item.tname">
                </a-card-meta>
              </a-card>
            </a-form-item>
          </a-col>
        </a-row>
      </template>
    </div>

    <loop ref="loop" :games-id="gamesInfo.id" v-if="gameTypeShow.bLoop"/>
    <out ref="out" :games-id="gamesInfo.id" v-if="gameTypeShow.bOut"/>
    <group ref="group" :games-id="gamesInfo.id" v-if="gameTypeShow.bGroup"/>

  </a-modal>
</template>

<script>
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'
  import JEditor from '@/components/jeecg/JEditor'
  import pick from 'lodash.pick'
  import moment from "moment"
  import ARow from 'ant-design-vue/es/grid/Row'
  import '@/assets/less/TableExpand.less'
  import Out from './type/out'
  import Loop from './type/loop'
  import Group from './type/group'

  export default {
    name: "PlayballScheduleArrangementModal",
    components: {
      ARow,
      JDate,
      JEditor,
      Out,
      Loop,
      Group
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

        toggleSearchStatus:false,
        gamesInfo:{},
        enrollTeamList:{},
        gamesType:"",
        bCreate:false,
        gameTypeShow:{
          bGroup:false,
          bLoop:false,
          bOut:false,
        },

        url: {
          enrollTeamList: "/playball/playballEnroll/listByGameId",
          getGameInfoById: "/playball/playballGame/getGameById",
        },
      }
    },
    created () {
    },
    methods: {
      handleAdd(){

      },
      close () {
        this.$emit('close');
        this.visible = false;

        this.gameTypeShow.bGroup = false
        this.gameTypeShow.bLoop = false
        this.gameTypeShow.bOut = false

      },
      handleOk () {
        let that = this

        if(this.$refs.out){
          this.$refs.out.getSuccess(function (bOk) {
            if(bOk){
              that.gameTypeShow.bGroup = false
              that.gameTypeShow.bLoop = false
              that.gameTypeShow.bOut = false
              that.visible = false
              that.close()
            }else{
              that.$message.warning("请确认时间都设置完成！");
            }
          })
        }else{
          that.gameTypeShow.bGroup = false
          that.gameTypeShow.bLoop = false
          that.gameTypeShow.bOut = false
          that.visible = false
          that.close()
        }
      },
      handleCancel () {
        this.gameTypeShow.bGroup = false
        this.gameTypeShow.bLoop = false
        this.gameTypeShow.bOut = false

        this.visible = false
        this.close()
      },
      show(recode){
        this.toggleSearchStatus = false
        this.enrollTeamList = {}
        this.gamesInfo = recode
        this.visible = true
        //获取报名球队展示
        let params = {gamesId:this.gamesInfo.id}
        getAction(this.url.enrollTeamList, params).then((res)=>{
          if(res.success){
            this.enrollTeamList = res.result
          }
        })
        //判读，球队人数>8人，且大于报名截止日期方可生成后续比赛
        if(this.gamesInfo.gameType == 1){
          console.log("按小组赛+淘汰赛生成比赛")
          this.gameTypeShow.bGroup = true
          this.gameTypeShow.bLoop = false
          this.gameTypeShow.bOut = false

        }else if(this.gamesInfo.gameType == 2){
          console.log("按淘汰赛方式生成比赛")
          this.gameTypeShow.bGroup = false
          this.gameTypeShow.bLoop = false
          this.gameTypeShow.bOut = true

        }else if(this.gamesInfo.gameType == 3){
          console.log("按循环赛方式生成比赛！")
          this.gameTypeShow.bGroup = false
          this.gameTypeShow.bLoop = true
          this.gameTypeShow.bOut = false
        }else{
          console.log("创建赛事存在问题，无法生成比赛！")
        }

        //获取比赛阶段，是否生成比赛
        /*getAction(this.url.getGameInfoById, params).then((re)=> {
          if (re.success) {
            this.gamesInfo = re.result
            console.log("重新获取",this.gamesInfo)
            if(this.gamesInfo.stage == 0){
              this.bCreate = true
            }else{
              this.bCreate = false
            }
          }
        })*/


      },
      showTeam(){
        console.log("showteam")
        if(this.toggleSearchStatus){
          this.toggleSearchStatus = false
        }else{
          this.toggleSearchStatus = true
        }

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