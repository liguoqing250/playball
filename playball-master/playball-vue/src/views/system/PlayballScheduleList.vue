<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="赛事名称">
              <a-input placeholder="请输入赛事名称" v-model="queryParam.gamesName"></a-input>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <!--
    <div class="table-operator">
      <a-button type="primary" icon="download" @click="handleExportXls('赛程表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>
    -->

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

         <span slot="actionTeamData" slot-scope="text, record">
            <a @click="handleEditTeamUserData(text, record)">{{record.teamName}}</a>
         </span>

        <span slot="actionOpponentData" slot-scope="text, record">
            <a @click="handleEditOpponentUserData(text, record)" v-if="text!=null">
              {{record.opponentName}}
            </a>
         </span>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">设置比赛时间和比赛结果</a>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <playballSchedule-modal ref="modalForm" @ok="modalFormOk"></playballSchedule-modal>
    <PlayballScheduleUserDataModal ref="PlayballScheduleUserDataModal"></PlayballScheduleUserDataModal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import PlayballScheduleModal from './modules/PlayballScheduleModal'
  import PlayballScheduleUserDataModal from './modules/PlayballScheduleUserDataModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "PlayballScheduleList",
    mixins:[JeecgListMixin],
    components: {
      PlayballScheduleModal,
      PlayballScheduleUserDataModal
    },
    data () {
      return {
        description: '赛程表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		   {
            title: '赛事名称',
            align:"center",
            dataIndex: 'gamesName'
           },
		   {
            title: '球队名称',
            align:"center",
            dataIndex: 'teamId',
            scopedSlots: { customRender: 'actionTeamData' }
           },
       {
            title: '对阵球队',
            align:"center",
            dataIndex: 'opponentId',
            scopedSlots: { customRender: 'actionOpponentData' }
            /*customRender:function (t,r,index) {
              if(t.opponentId != null){
                return t.opponentName
              }else{
                return "轮空"
              }
            }*/
          },
       {
            title: '比分',
            align:"center",
            dataIndex: '',
            customRender:function (t,r,index) {
              if(t.opponentId != null){
                return t.enterBall+":"+t.lostBall;
              }else{
                return "3:0"
              }

            }
          },
          {
            title: '状态',
            align:"center",
            dataIndex: 'gameStatus',
            customRender:function (text) {
              if(text==1){
                return "已赛";
              }else{
                return "未赛";
              }
            }
          },
		   {
            title: '比赛日期',
            align:"center",
            dataIndex: 'matchTime'
           },
		   {
            title: '属于分组',
            align:"center",
            dataIndex: 'groupId'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/playball/playballSchedule/list",
          delete: "/playball/playballSchedule/delete",
          deleteBatch: "/playball/playballSchedule/deleteBatch",
          exportXlsUrl: "playball/playballSchedule/exportXls",
          importExcelUrl: "playball/playballSchedule/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      handleEditTeamUserData(text,record){
        this.$refs.PlayballScheduleUserDataModal.show(text,record.teamName,record);
      },
      handleEditOpponentUserData(text,record){
        this.$refs.PlayballScheduleUserDataModal.show(text,record.opponentName,record);
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>