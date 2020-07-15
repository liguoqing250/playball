<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24">
            <a-form-item label="标题">
              <a-input placeholder="请输入标题" v-model="queryParam.orderCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="资讯类型">
              <a-select placeholder="请输入资讯类型"  v-model="queryParam.ctype">
                <a-select-option :value="sports.id"  v-for="sports in sportsTypeList"  >
                  {{ sports.sportsName }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24" >
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
      <a-button type="primary" icon="download" @click="handleExportXls('一对多示例')">导出</a-button>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="avatarslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="getAvatarView(record.image)" icon="user"/>
          </div>
        </template>

        <span slot="newsInfo" slot-scope="text, record">
          <a @click="showNewsInfo(record)">查看资讯详情</a>
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
        :visible.sync="newsInfomodal.visible"
        :width="1200"
        :title="newsInfomodal.title"
      >
        <template>
          <div v-html="newsInfomodal.content">{{newsInfomodal.content}}</div>
        </template>
      </j-modal>
    </div>

    <!-- 表单区域 -->
    <PlayballNews-modal ref="modalForm" @ok="modalFormOk"></PlayballNews-modal>
  </a-card>
</template>

<script>
  import PlayballNewsModal from './modules/PlayballNewsModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'

  export default {
    name: "PlayballNewsList",
    mixins: [JeecgListMixin],
    components: {
      PlayballNewsModal
    },
    data () {
      return {
        description: '订单管理页面',
        importExcelUrl:`${window._CONFIG['domianURL']}/cms/news/importExcel`,
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
            title: '资讯标题',
            align:"center",
            dataIndex: 'title'
          },
          {
            title: '封面图片',
            align:"center",
            dataIndex: '',
            scopedSlots: {customRender: "avatarslot"}
          },
          {
            title: '资讯详情',
            align:"center",
            dataIndex: '',
            scopedSlots: { customRender: 'newsInfo' }
          },
          {
            title: '评论量',
            align:"center",
            dataIndex: 'commentTotal'
          },
          {
            title: '浏览量',
            align:"center",
            dataIndex: 'pageView'
          },
          {
            title: '资讯类型',
            align:"center",
            dataIndex: 'newsType',
            customRender: (text, record, index) => {
              let re = "";
              for (index in this.sportsTypeList)  // x 为属性名
              {
                if(this.sportsTypeList[index].id==text){
                  return this.sportsTypeList[index].sportsName
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

        sportsTypeList:{},
        newsInfomodal: {
          title: '',
          visible: false,
          content:'',
        },

		url: {
          list: "/cms/news/list",
          delete: "/cms/news/delete",
          deleteBatch: "/cms/news/deleteBatch",
          exportXlsUrl: "/cms/news/exportXls",
          sportsTypeList: "/bm/common/sportslist",
        }
      }
    },
    methods: {
      initDictConfig() {
        //获取运动类型
        getAction(this.url.sportsTypeList).then((res)=>{
          if(res.success){
            this.sportsTypeList= res.result;
            this.$forceUpdate()
          }
        })
      },
      getAvatarView: function (avatar) {
        return getFileAccessHttpUrl(avatar)
      },
      showNewsInfo(record){
        this.newsInfomodal.visible = true
        this.newsInfomodal.title = record.title
        this.newsInfomodal.content = record.content
      },
    }
  }
</script>
<style scoped>
  /** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }
  .ant-card-body .table-operator{
    margin-bottom: 18px;
  }
  .ant-table-tbody .ant-table-row td{
    padding-top:15px;
    padding-bottom:15px;
  }
  .anty-row-operator button{margin: 0 5px}
  .ant-btn-danger{background-color: #ffffff}

  .ant-modal-cust-warp{height: 100%}
  .ant-modal-cust-warp .ant-modal-body{height:calc(100% - 110px) !important;overflow-y: auto}
  .ant-modal-cust-warp .ant-modal-content{height:90% !important;overflow-y: hidden}
</style>