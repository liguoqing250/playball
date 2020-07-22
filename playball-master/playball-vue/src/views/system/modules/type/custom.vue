<template>
  <div>

    <a-select placeholder="请选择比赛球队" style="width:240px;"  >
      <a-select-option :value="item.teamId"  v-for="item in tempEnrollTeamList" >
        {{ item.tname }}
      </a-select-option>
    </a-select>

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
      },
      loadMacth(){

       this.tempEnrollTeamList = Object.assign({}, this.enrollTeamList);
        console.log(this.tempEnrollTeamList )
      },


    }//end methods
  }
</script>

<style lang="less" scoped>

  .ant-card-body{
    padding:15px;
  }

</style>