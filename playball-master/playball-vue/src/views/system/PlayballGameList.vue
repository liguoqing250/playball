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
            <a-form-item label="运动类型">
              <!--<a-input placeholder="请输入运动类型" v-model="queryParam.sportsId"></a-input>-->
              <a-select placeholder="请选择运动类别"  v-model="queryParam.sportsId">
                <a-select-option :value="sports.id"  v-for="sports in sportsTypeList"  @change ="changeSportsList($event)">
                  {{ sports.sportsName }}
                </a-select-option>
              </a-select>
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
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('赛事信息')">导出</a-button>
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

        <span slot="actionGamesInfo" slot-scope="text, record">
          <a @click="showGameInfo(record)">查看赛事信息</a>
        </span>

        <span slot="actionSchedule" slot-scope="text, record">
          <a @click="handleEditSchedule(record)">编辑查看赛程</a>
        </span>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->
    <div>
      <j-modal
        :visible.sync="infoModal.visible"
        :width="1200"
        :title="infoModal.title"
      >
        <template>
          <div v-html="infoModal.gamesContent">{{infoModal.gamesContent}}</div>
        </template>
      </j-modal>
    </div>

    <!-- 表单区域 -->
    <playballGame-modal ref="modalForm" @ok="modalFormOk"></playballGame-modal>
    <playball-schedule ref="schedule"></playball-schedule>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import PlayballGameModal from './modules/PlayballGameModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getSportsTypeList } from '@/api/api'
  import PlayballSchedule from './modules/PlayballSchedule'

  export default {
    name: "PlayballGameList",
    mixins:[JeecgListMixin],
    components: {
      PlayballGameModal,
      PlayballSchedule
    },
    data () {
      return {
        description: '赛事信息管理页面',
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
            title: '赛事信息',
            align:"center",
            dataIndex: 'actionGamesInfo',
            scopedSlots: { customRender: 'actionGamesInfo' }
           },
       {
            title: '赛程信息',
            align:"center",
            dataIndex: 'actionSchedule',
            scopedSlots: { customRender: 'actionSchedule' }
          },
          {
            title: '赛事规则',
            align:"center",
            dataIndex: 'gameRule'
          },
		   {
            title: '报名时间',
            align:"center",
            dataIndex: 'enrollTime'
           },
		   {
            title: '赛事开始时间',
            align:"center",
            dataIndex: 'startTime'
           },
		   {
            title: '赛事结束时间',
            align:"center",
            dataIndex: 'endTime'
           },
          {
            title: '运动类型',
            align:"center",
            dataIndex: 'sportsName'
          },
          {
            title: '比赛场地',
            align:"center",
            dataIndex: 'fieldName'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],

        sportsTypeList:{},
        typeName:'',
        infoModal: {
          title: '',
          visible: false,
          gamesContent:'',
        },

		url: {
          list: "/playball/playballGame/list",
          delete: "/playball/playballGame/delete",
          deleteBatch: "/playball/playballGame/deleteBatch",
          exportXlsUrl: "playball/playballGame/exportXls",
          importExcelUrl: "playball/playballGame/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      initDictConfig() {
        getSportsTypeList('').then((res)=>{
          if(res.success){
            this.sportsTypeList = res.result.records;
            this.typeName = this.sportsTypeList[0].sportsName
          }
        })
      },
      changeSportsList(id){
        console.log("-----id=",id)
        queryParam.sportsId = id;
      },
      showGameInfo(record){
        this.infoModal.visible = true
        this.infoModal.title = record.gamesName
        this.infoModal.gamesContent = record.gamesInfo
      },
      handleEditSchedule(record){
        this.$refs.schedule.show(record);
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>