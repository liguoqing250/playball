<template>
  <div>

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
              <a-col :span="4">比赛分组</a-col>
              <a-col :span="4">操作</a-col>
            </a-row>

            <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="item in gamesInfo.matchList">
              <a-col :span="5">
                <p>{{item.teamName}}</p>
              </a-col>

              <a-col :span="1">
                  <span>vs</span>
              </a-col>

              <a-col :span="5">
                <p>{{item.opponentName}}</p>
              </a-col>

              <a-col :span="5">
                <p v-if="item.matchTime">{{item.matchTime}}</p>
                <p v-else>请到赛程管理中设置比赛时间</p>
              </a-col>

              <a-col :span="4">
                {{item.groupId}}
              </a-col>
            </a-row>

            <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in matchList" :key="index">
              <a-col :span="5">
                <a-form-item>
                  <a-select placeholder="请选择比赛球队" style="width:200px;" v-decorator="['matchList['+index+'].teamId',{rules: [{ required: true, message: '请选择球队!' }]}]" >
                    <a-select-option :value="teamItem.teamId"  v-for="(teamItem,i) in enrollTeamList" :key="i">
                      {{ teamItem.tName }}
                    </a-select-option>
                  </a-select>

                </a-form-item>
              </a-col>
              <a-col :span="1">
                  <span>vs</span>
              </a-col>
              <a-col :span="5">
               <a-form-item>
                  <a-select placeholder="请选择比赛球队" style="width:200px;" v-decorator="['matchList['+index+'].opponentId',{rules: [{ required: true, message: '请选择球队!' }]}]">
                    <a-select-option :value="teamItem.teamId"  v-for="(teamItem,i) in enrollTeamList" :key="i">
                      {{ teamItem.tName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="5">
                <a-form-item>
                  <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="['matchList['+index+'].matchTime',{rules: [{validator: validateMatchTime,},]}]"/>
                </a-form-item>
              </a-col>
              <a-col :span="4">
                <a-form-item>
                  <a-input placeholder="请输入分组(选填)" v-decorator="['matchList['+index+'].groupId',{}]"></a-input>
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

        gamesInfo:{},
        matchList: [{}],
        model: {matchList: [{}]},

        form: this.$form.createForm(this),
        url: {
          getCustomMatchList: "/playball/playballSchedule/getCustomMatchList",
          addCustomMacth: "/playball/playballSchedule/addCustomMacth",
        }
      }
    },
    created () {
      this.loadMacth()
    },
    methods: {
      getSuccess(callback){
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            let httpurl = '';
            let method = '';
            httpurl += this.url.addCustomMacth;
            method = 'post';

            let tempModel = Object.assign(this.model, values);
            let formData = tempModel.matchList.map(item => ({ ...item,
              gamesId:this.gamesId,
              matchTime:item.matchTime?item.matchTime.format('YYYY-MM-DD HH:mm:ss'):null }));

            console.log("formData",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                this.$message.success(res.message);
              }else{
                this.$message.warning(res.message);

              }
            }).finally(() => {
              callback()
            })

          }
        })

      },
      loadMacth(){
        let that = this
        //获取比赛
        let params = {gameId:this.gamesId}
        getAction(that.url.getCustomMatchList, params).then((re)=> {
          if (re.success) {
            that.gamesInfo = re.result
            console.log("--------重新获取-------",re.result)
            if(that.gamesInfo.matchList.length == 0){
            }
          }
        })

      },
      addRowCustom () {
        this.matchList.push({});
        this.$forceUpdate();
      },
      delRowCustom (index,item) {
        console.log("---index----",index)
        if(this.matchList.length > 1){
          this.matchList.splice(index,1);
          this.$forceUpdate();
        }
      },
      validateMatchTime(rule, value, callback){
        let endTime = new Date(moment(this.gamesInfo.gameInfo.endTime))
        let straTime = new Date(moment(this.gamesInfo.gameInfo.startTime))
        if(!value){
          callback("请设置比赛时间")
        }else{
          let setTime = new Date(value)
          if(setTime.getTime() > endTime.getTime() || setTime.getTime() < straTime.getTime()){
            callback("您输入的比赛时间不在赛事举办时间内，请重新输入！");
          }else{
            callback()
          }
        }
      }

    }//end methods
  }
</script>

<style lang="less" scoped>

  .ant-card-body{
    padding:15px;
  }

</style>