<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="用户名称">
              <a-input placeholder="请输入用户名称" v-model="queryParam.userName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="用户昵称">
              <a-input placeholder="请输入用户昵称" v-model="queryParam.nickName"></a-input>
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
      <!--<a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
      <a-button type="primary" icon="download" @click="handleExportXls('用户动态发布')">导出</a-button>

       <!--
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
      -->
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
        rowKey="udyId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="showInfo" slot-scope="text, record, index">
          <span class="text-nowrap" style="max-width: 500px;word-wrap:break-word;white-space: normal;">
            {{text}}
          </span>
        </template>

        <span slot="show" slot-scope="text, record">
          <a @click="show(record)">观看视频</a>
        </span>

        <span slot="action" slot-scope="text, record">
          <!--
          <a @click="handleEdit(record)">编辑</a>
          -->

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.udyId)">
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
        :visible.sync="model.bShow"
        :width="1200"
        :title="model.title"
      >
        <template>
          <div v-html="">
            <iframe v-if="model.type==2"
              :src="model.resource" frameborder='0'
              allow='autoplay;encrypted-media' allowfullscreen style='width:100%;height:500px;'>
            </iframe>
            <div v-if="model.type==1" >
              <a-row type="flex" style="margin-bottom:10px" :gutter="16" >
                <a-col :span="6" v-for="(item, index) in model.imageList" :key="index">
                    <img :src="getImageUrl(item)" width="250" height="250" />
                </a-col>
              </a-row>

            </div>
          </div>
        </template>
      </j-modal>
    </div>

    <!-- 表单区域 -->
    <playballDynamic-modal ref="modalForm" @ok="modalFormOk"></playballDynamic-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import PlayballDynamicModal from './modules/PlayballDynamicModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getFileAccessHttpUrl } from '@/api/manage'

  export default {
    name: "PlayballDynamicList",
    mixins:[JeecgListMixin],
    components: {
      PlayballDynamicModal
    },
    data () {
      return {
        description: '用户动态发布管理页面',
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
            title: '用户名',
            align:"center",
            dataIndex: 'userName'
           },
		   {
            title: '用户昵称',
            align:"center",
            dataIndex: 'nickName'
           },
		   {
            title: '发布动态内容',
            align:"center",
            dataIndex: 'udyContent',
            scopedSlots: {customRender: "showInfo"}
           },
		   {
            title: '查看图片或视频',
            align:"center",
            dataIndex: 'udyResources',
            scopedSlots: { customRender: 'show' }

           },
		   {
            title: '资源类型',
            align:"center",
            dataIndex: 'udyResType',
             customRender: (text, record, index) => {
               let re = "";
               if(text==1){
                 re = "图片"
               }else{
                 re="视频"
               }
               return re;
             }
           },
		   {
            title: '发布动态信息位置',
            align:"center",
            dataIndex: 'udyPosition'
           },
		   {
            title: '是否可见',
            align:"center",
            dataIndex: 'udyVisible',
             customRender: (text, record, index) => {
               let re = "";
               if(text==1){
                 re = "全部可见"
               }else{
                 re="仅自己可见"
               }
               return re;
             }
           },
		   {
            title: '状态',
            align:"center",
            dataIndex: 'udyDraft',
             customRender: (text, record, index) => {
               let re = "";
               if(text==1){
                 re = "已完成"
               }else{
                 re="草稿"
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

          model: {
            title: '查看视频或图片',
            bShow:false,
            resource:'',
            type:0,
            imageList:{},
        },

		url: {
          list: "/playball/playballDynamic/list",
          delete: "/playball/playballDynamic/delete",
          deleteBatch: "/playball/playballDynamic/deleteBatch",
          exportXlsUrl: "playball/playballDynamic/exportXls",
          importExcelUrl: "playball/playballDynamic/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      show(record){
        this.model.bShow = true
        this.model.resource = record.udyResources
        this.model.type = record.udyResType
        if(this.model.type==1){
          this.model.imageList = this.model.resource.split(",");
        }
      },
      getImageUrl(imageUrl){
        return getFileAccessHttpUrl(imageUrl);
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>