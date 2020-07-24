<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="运动类型">
              <a-select placeholder="请选择运动类别"  v-model="queryParam.sportsId">
                <a-select-option :value="sports.id"  v-for="sports in sportsTypeList">
                  {{ sports.sportsName }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="能力值名称">
              <a-input placeholder="请输入能力值名称" v-model="queryParam.avName"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('能力值')">导出</a-button>
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
    <playballAbilityValue-modal ref="modalForm" @ok="modalFormOk"></playballAbilityValue-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import PlayballAbilityValueModal from './modules/PlayballAbilityValueModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getSportsTypeList } from '@/api/api'

  export default {
    name: "PlayballAbilityValueList",
    mixins:[JeecgListMixin],
    components: {
      PlayballAbilityValueModal
    },
    data () {
      return {
        description: '能力值管理页面',
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
            title: '运动类型',
            align:"center",
            dataIndex: 'sportsId',
            customRender: (text, record, index) => {
                let re = "";
                for (index in this.sportsTypeList){
                    if(this.sportsTypeList[index].id==text){
                    return this.sportsTypeList[index].sportsName
                }
              }
              return re;
             }
           },
		   {
            title: '能力值名称',
            align:"center",
            dataIndex: 'avName'
           },
          {
            title: '最大能力值',
            align:"center",
            dataIndex: 'maxValue'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],

        sportsTypeList:{},

		url: {
          list: "/playball/playballAbilityValue/list",
          delete: "/playball/playballAbilityValue/delete",
          deleteBatch: "/playball/playballAbilityValue/deleteBatch",
          exportXlsUrl: "playball/playballAbilityValue/exportXls",
          importExcelUrl: "playball/playballAbilityValue/importExcel",
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
          }
        })
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>