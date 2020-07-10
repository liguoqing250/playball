<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24">
            <a-form-item label="比赛名称">
              <a-input placeholder="请输入比赛名称" v-model="queryParam.gamesName"></a-input>
            </a-form-item>
          </a-col>

          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="场地类型">
              <select name="public-choice" v-model ="formData.sportsId" style="width: 200px;" autocomplete="off" @change ="changeSportsList($event)">
                <option value="" >请选择</option>
                <option :value="sports.id"  v-for="sports in this.gamesModel.sportsTypeList"  >
                  {{ sports.sportsName }}
                </option>
              </select>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <div>
    <j-modal
      :visible.sync="modal.visible"
      :width="1200"
      :title="modal.title"
    >
      <template>
        <div v-html="modal.gamesContent">{{modal.gamesContent}}</div>
      </template>
    </j-modal>
    </div>

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24">
            <a-form-item label="场地名称">
              <a-input placeholder="请输入场地名称" v-model="queryParam.fieldName"></a-input>
            </a-form-item>
          </a-col>

          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="开始时间">
              <j-date v-model="formData.value" :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['openTime', {} ]"/>
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

        <span slot="actionGamesInfo" slot-scope="text, record">
          <a @click="showGamesInfo(record)">查看比赛信息</a>
        </span>

        <span slot="actionSchedule" slot-scope="text, record">
          <a @click="handleEditSchedule(record)">编辑查看赛程</a>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <playballGames-modal ref="modalForm" @ok="modalFormOk"></playballGames-modal>
    <playball-schedule ref="schedule"></playball-schedule>

  </a-card>
</template>

<script>
  import JDate from '@/components/jeecg/JDate'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { httpAction,getAction } from '@/api/manage'
  import PlayballGamesModal from '@views/system/modules/PlayballGamesModal'
  import PlayballGamesContentModal from '@views/system/modules/PlayballSchedule'
  import PlayballSchedule from './modules/PlayballSchedule'


  export default {
    name: "PlayballGames",
    mixins: [JeecgListMixin],
    components: {
      PlayballSchedule,
      PlayballGamesModal,
      JDate
    },
    data () {
      return {
        description: '赛事管理页面',
        importExcelUrl:`${window._CONFIG['domianURL']}/test/jeecgOrderMain/importExcel`,
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
            title: '赛事类型',
            align:"center",
            dataIndex: 'sportsName'
          },
          {
            title: '比赛场地',
            align:"center",
            dataIndex: 'fieldName'
          },
          {
            title: '开始日期',
            align:"center",
            dataIndex: 'startTime'
          },
          {
            title: '结束日期',
            align:"center",
            dataIndex: 'endTime'
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        formData: {},
        gamesModel: { sportsTypeList:[{}]},
        labelCol: {
        },
        wrapperCol: {
        },
        modal: {
          title: '',
          visible: false,
          gamesContent:'',
        },

		url: {
          list: "/cms/playball/list",
          //delete: "/test/jeecgOrderMain/delete",
          //deleteBatch: "/test/jeecgOrderMain/deleteBatch",
         // exportXlsUrl: "/test/jeecgOrderMain/exportXls",
          sportsTypeList: "/bm/common/sportslist",
        }
      }
    },
    methods: {
      initDictConfig() {
        //获取运动类型
        getAction(this.url.sportsTypeList).then((res)=>{
          if(res.success){
            this.gamesModel.sportsTypeList= res.result;
            this.$forceUpdate()
          }
        })
      },

      changeSportsList(){
      },

      showGamesInfo(record){
        this.modal.visible = true
        this.modal.title = record.gamesName
        this.modal.gamesContent = record.gamesInfo
      },
      handleEditSchedule(record){
        this.$refs.schedule.show(record);
      }

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