<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="teamId">
              <a-input placeholder="请输入teamId" v-model="queryParam.teamId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="队名">
              <a-input placeholder="请输入队名" v-model="queryParam.tName"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="队徽（图片一张）">
              <a-input placeholder="请输入队徽（图片一张）" v-model="queryParam.tImage"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="所属单位名">
              <a-input placeholder="请输入所属单位名" v-model="queryParam.tUnitname"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="球队类型(关联运动类型表)">
              <a-input placeholder="请输入球队类型(关联运动类型表)" v-model="queryParam.stId"></a-input>
            </a-form-item>
          </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('球队')">导出</a-button>
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

    <!-- 表单区域 -->
    <playballTeam-modal ref="modalForm" @ok="modalFormOk"></playballTeam-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import PlayballTeamModal from './modules/PlayballTeamModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "PlayballTeamList",
    mixins:[JeecgListMixin],
    components: {
      PlayballTeamModal
    },
    data () {
      return {
        description: '球队管理页面',
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
            title: 'teamId',
            align:"center",
            dataIndex: 'teamId'
           },
		   {
            title: '队名',
            align:"center",
            dataIndex: 'tName'
           },
		   {
            title: '队徽（图片一张）',
            align:"center",
            dataIndex: 'tImage'
           },
		   {
            title: '所属单位名',
            align:"center",
            dataIndex: 'tUnitname'
           },
		   {
            title: '球队类型(关联运动类型表)',
            align:"center",
            dataIndex: 'stId'
           },
		   {
            title: '当前球队人数',
            align:"center",
            dataIndex: 'tPlayersTotal'
           },
		   {
            title: '当前球队最大人数',
            align:"center",
            dataIndex: 'tPlayersMax'
           },
		   {
            title: '球队介绍 招募说明',
            align:"center",
            dataIndex: 'tIntroduce'
           },
		   {
            title: '胜 次数',
            align:"center",
            dataIndex: 'tWinTotal'
           },
		   {
            title: '平 次数',
            align:"center",
            dataIndex: 'tDivideTotal'
           },
		   {
            title: '负 次数',
            align:"center",
            dataIndex: 'tLostTotal'
           },
		   {
            title: '球队创建人',
            align:"center",
            dataIndex: 'uId'
           },
		   {
            title: '所属省--对应地区表主键',
            align:"center",
            dataIndex: 'tProvince'
           },
		   {
            title: '所属市--对应地区表主键',
            align:"center",
            dataIndex: 'tCity'
           },
		   {
            title: '所属区--对应地区表主键',
            align:"center",
            dataIndex: 'tDistrict'
           },
		   {
            title: '建队时间',
            align:"center",
            dataIndex: 'tCreateteamtime'
           },
		   {
            title: '队长 球员表ID  p_id',
            align:"center",
            dataIndex: 'tCaptain'
           },
		   {
            title: '纪录创建时间',
            align:"center",
            dataIndex: 'createtime'
           },
		   {
            title: '纪录修改时间',
            align:"center",
            dataIndex: 'updatetime'
           },
		   {
            title: '逻辑删除',
            align:"center",
            dataIndex: 'isDelete'
           },
		   {
            title: '版本号（用作乐观锁）',
            align:"center",
            dataIndex: 'version'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/plalyball/playballTeam/list",
          delete: "/plalyball/playballTeam/delete",
          deleteBatch: "/plalyball/playballTeam/deleteBatch",
          exportXlsUrl: "plalyball/playballTeam/exportXls",
          importExcelUrl: "plalyball/playballTeam/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
     
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>