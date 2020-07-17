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

  export default {
    name: "PlayballScheduleArrangementModal",
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

        toggleSearchStatus:false,
        gamesInfo:{},
        enrollTeamList:{},
        gamesType:"",
        bCreate:false,

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
      },
      handleOk () {
        this.visible = false
        this.close()
      },
      handleCancel () {
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

        //获取比赛阶段，是否生成比赛
        getAction(this.url.getGameInfoById, params).then((re)=> {
          if (re.success) {
            this.gamesInfo = re.result
            console.log("重新获取",this.gamesInfo)
            if(this.gamesInfo.stage == 0){
              this.bCreate = true
            }else{
              this.bCreate = false
            }

          }
        })


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