<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="教程标题">
              <a-input placeholder="请输入教程标题" v-model="queryParam.cTitle"></a-input>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="运动类型">
              <a-select placeholder="请选择运动类别"  v-model="queryParam.sId">
                <a-select-option :value="sports.id"  v-for="sports in sportsTypeList">
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
      <a-button type="primary" icon="download" @click="handleExportXls('cms教程')">导出</a-button>
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
        rowKey="cId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="showTitle" slot-scope="text, record, index">
          <span class="text-nowrap" style="max-width: 300px;word-wrap:break-word;white-space: normal;">
            {{text}}
          </span>
        </template>

        <template slot="showInfo" slot-scope="text, record, index">
          <span class="text-nowrap" style="max-width: 1000px;word-wrap:break-word;white-space: normal;">
            {{text}}
          </span>
        </template>

        <template slot="avatarslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <img style="width: 100px;" :src="getAvatarView(record.cCover)">
          </div>
        </template>

        <span slot="video" slot-scope="text, record">
          <a @click="showVideo(record)">观看视频</a>
        </span>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.cId)">
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
        :visible.sync="videomodal.bShowVideo"
        :width="1200"
        :title="videomodal.title"
      >
        <template>
          <div v-html=""></div>
          <iframe :src="videomodal.cVideo" frameborder='0'
                  allow='autoplay;encrypted-media' allowfullscreen style='width:100%;height:500px;'>
          </iframe>
        </template>
      </j-modal>
    </div>

    <!-- 表单区域 -->
    <playballCourse-modal ref="modalForm" @ok="modalFormOk"></playballCourse-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import PlayballCourseModal from './modules/PlayballCourseModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getSportsTypeList } from '@/api/api'
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'

  export default {
    name: "PlayballCourseList",
    mixins:[JeecgListMixin],
    components: {
      PlayballCourseModal
    },
    data () {
      return {
        description: '教程管理页面',
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
            title: '教程标题',
            align:"center",
            dataIndex: 'cTitle',
            width:300,
            scopedSlots: {customRender: "showTitle"}
           },
		   {
            title: '教程详情',
            align:"center",
            dataIndex: 'cInfo',
            width:1000,
            scopedSlots: {customRender: "showInfo"}
           },
          {
            title: '封面图片',
            align:"center",
            dataIndex: '',
            scopedSlots: {customRender: "avatarslot"}
          },
		   {
            title: '教程视频',
            align:"center",
            dataIndex: '',
            scopedSlots: { customRender: 'video' }
           },
		   {
            title: '运动类型',
            align:"center",
            dataIndex: 'sId',
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
            title: '适龄范围',
            align:"center",
            dataIndex: 'cAgerange'
           },
		   {
            title: '是否付费',
            align:"center",
            dataIndex: 'cIsFree',
            customRender: (text, record, index) => {
              let re = "";
              if(text=='0') {
                re="免费"
              }else{
                re="付费"
              }
             return re;
           }

           },
		   {
            title: '价格',
            align:"center",
            dataIndex: 'cPrice'
           },
		   {
            title: '点赞数',
            align:"center",
            dataIndex: 'cFabulous'
           },
		   {
            title: '浏览数',
            align:"center",
            dataIndex: 'cBrowse'
           },

          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],

        sportsTypeList:{},
        videomodal: {
          title: '',
          bShowVideo:false,
          cvideo:'',
        },
        typeName:'',


		url: {
          list: "/playball/playballCourse/list",
          delete: "/playball/playballCourse/delete",
          deleteBatch: "/playball/playballCourse/deleteBatch",
          exportXlsUrl: "playball/playballCourse/exportXls",
          importExcelUrl: "playball/playballCourse/importExcel",
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

      showVideo(record){
        this.videomodal.bShowVideo = true
        this.videomodal.title = record.cTitle
        this.videomodal.cVideo = record.cVideo
      },
      getAvatarView: function (avatar) {
        return getFileAccessHttpUrl(avatar)
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>