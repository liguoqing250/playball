<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
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
        rowKey="posId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="avatarslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <img style="width: 100px;" :src="getAvatarView(record.posRotation)">
          </div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.posId)">
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
    <playballAdvertisement-modal ref="modalForm" @ok="modalFormOk"></playballAdvertisement-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import PlayballAdvertisementModal from './modules/PlayballAdvertisementModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'

  export default {
    name: "PlayballAdvertisementList",
    mixins:[JeecgListMixin],
    components: {
      PlayballAdvertisementModal
    },
    data () {
      return {
        description: '广告管理管理页面',
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
            title: '广告描述',
            align:"center",
            dataIndex: 'posDescribe'
           },
		   {
            title: '广告图片',
            align:"center",
            dataIndex: 'posRotation',
            scopedSlots: {customRender: "avatarslot"}
           },
		   {
            //title: '广告类型:1、web页面 2、App本地页面',
            title:'页面跳转',
            align:"center",
            dataIndex: 'posType',
             customRender: (text, record, index) => {
               let re = "";
               if(text == 1){
                 return record.posJumpUrl;
               }else if(text == 2){
                 for (index in this.appRouterList){
                   if(this.appRouterList[index].appUrl === record.posJumpUrl){
                     return this.appRouterList[index].appName
                   }
                 }
               }

               return re;
             }
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        appRouterList:{},
		url: {
          list: "/playball/playballAdvertisement/list",
          delete: "/playball/playballAdvertisement/delete",
          deleteBatch: "/playball/playballAdvertisement/deleteBatch",
          exportXlsUrl: "playball/playballAdvertisement/exportXls",
          importExcelUrl: "playball/playballAdvertisement/importExcel",
          getAppRouteList:"/playball/playballAdvertisement/getAdvertisementAppRoute",
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
        this.getAppRouteList()
      },
      getAvatarView: function (avatar) {
        return getFileAccessHttpUrl(avatar)
      },
      getAppRouteList() {
        getAction(this.url.getAppRouteList, {}).then((res) => {
          if (res.success) {
            this.appRouterList = res.result;
          }
        });
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>