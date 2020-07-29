<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品名称">
              <a-input placeholder="请输入商品名称" v-model="queryParam.ordGoodsName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="用户名称">
              <a-input placeholder="请输入用户名称" v-model="queryParam.userName"></a-input>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品订单号">
              <a-input placeholder="请输入商品订单号" v-model="queryParam.cprNumber"></a-input>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="订单状态">
              <a-select placeholder="请选择订单状态" style="width:190px;" v-model="queryParam.cprState">
                <a-select-option :value="item.id"  v-for="item in stateList" >
                  {{ item.value }}
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
    <!--
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('订单管理')">导出</a-button>
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
        rowKey="ordId"
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
    <playballFieldOrder-modal ref="modalForm" @ok="modalFormOk"></playballFieldOrder-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import PlayballFieldOrderModal from './modules/PlayballFieldOrderModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "PlayballFieldOrderList",
    mixins:[JeecgListMixin],
    components: {
      PlayballFieldOrderModal
    },
    data () {
      return {
        description: '订单管理管理页面',
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
            title: '商品订单号',
            align:"center",
            dataIndex: 'cprNumber'
          },
		   {
            title: '商品名称',
            align:"center",
            dataIndex: 'ordGoodsName'
           },
		   {
            title: '商品总价格',
            align:"center",
            dataIndex: 'cprPrice'
           },

		   {
            title: '订单状态',
            align:"center",
            dataIndex: 'cprState',
            customRender:function (text) {
              if(text==1){
                 return "已支付";
              }else if(text==2){
                 return "未支付";
              }
            }
           },
          {
            title: '下单用户',
            align:"center",
            dataIndex: 'userName'
          },

          /*{
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }*/
        ],
        stateList:[{
          id:"1",
          value:"已支付"
        },{
          id:"2",
          value:"未支付"
        }],
		url: {
          list: "/business/playballOrder/list",
          delete: "/business/playballOrder/delete",
          deleteBatch: "/business/playballOrder/deleteBatch",
          exportXlsUrl: "business/playballOrder/exportXls",
          importExcelUrl: "business/playballOrder/importExcel",
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