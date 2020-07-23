<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="联系电话">
              <a-input placeholder="请输入联系电话" v-model="queryParam.pfPhone"></a-input>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="联系人姓名">
              <a-input placeholder="请输入联系人姓名" v-model="queryParam.pfName"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('意见反馈')">导出</a-button>
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

        <span slot="actionShow" slot-scope="text, record">
          <a @click="showInfo(record)">查看详细</a>
        </span>

        <span slot="action" slot-scope="text, record">
           <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
             <a>删除</a>
           </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- table区域-end -->
    <div>
      <j-modal
        :visible.sync="infoModel.visible"
        :width="1200"
        :title="infoModel.title"
      >
        <template>
          <div v-html="infoModel.content"></div>
        </template>
      </j-modal>
    </div>
    <!-- 表单区域 -->
    <playballFeedback-modal ref="modalForm" @ok="modalFormOk"></playballFeedback-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import playballFeedbackModal from './modules/PlayballFeedbackModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "playballFeedbackList",
    mixins:[JeecgListMixin],
    components: {
      playballFeedbackModal
    },
    data () {
      return {
        description: '意见反馈管理页面',
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
            title: '内容描述',
            align:"center",
            dataIndex: 'pfDescribe'
           },
		   {
            title: '联系人姓名',
            align:"center",
            dataIndex: 'pfName'
           },
		   {
            title: '联系电话',
            align:"center",
            dataIndex: 'pfPhone'
           },
		   {
            title: '图片或者视频',
            align:"center",
            dataIndex: 'pfFeedres',
            scopedSlots: { customRender: 'actionShow' }
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],

        infoModel: {
          title: '',
          visible: false,
          content:'',
        },

		url: {
          list: "/playball/playballFeedback/list",
          delete: "/playball/playballFeedback/delete",
          deleteBatch: "/playball/playballFeedback/deleteBatch",
          exportXlsUrl: "playball/playballFeedback/exportXls",
          importExcelUrl: "playball/playballFeedback/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      showInfo(record){
        this.infoModel.visible = true
        this.infoModel.title = ''
        this.infoModel.content = record.pfFeedres
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>