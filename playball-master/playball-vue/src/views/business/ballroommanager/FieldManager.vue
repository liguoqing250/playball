<template>
  <a-card :bordered="false">

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
              label="场地类型">
              <select name="public-choice" v-model ="formData.sportsId" style="width: 200px;" autocomplete="off" @change ="changeSportsList($event)">
                <option value="" >请选择</option>
                <option :value="sports.id"  v-for="sports in this.gamesModel.sportsTypeList"  >
                  {{ sports.sportsName }}
                </option>
              </select>
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
      <a-button type="primary" icon="download" @click="handleExportXls('场地列表')">导出</a-button>
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
            <a-avatar shape="square" :src="getAvatarView(record.imageUrl)" icon="user"/>
          </div>
        </template>

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
    <FieldManager-modal ref="modalForm" @ok="modalFormOk"></FieldManager-modal>
  </a-card>
</template>

<script>
  import JDate from '@/components/jeecg/JDate'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'
  import FieldManagerModal from './modules/FieldManagerModal'

  export default {
    name: "FieldManager",
    mixins: [JeecgListMixin],
    components: {
      JDate,
      FieldManagerModal
    },
    data () {
      return {
        description: '赛程信息页面',
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
            title: '场地名称',
            align:"center",
            dataIndex: 'fieldName'
          },
          {
            title: '场地图片',
            align: "center",
            width: 120,
            dataIndex: '',
            scopedSlots: {customRender: "avatarslot"}
          },

          {
            title: '所属球馆',
            align:"center",
            dataIndex: 'businessName'
          },
          {
            title: '场地类型',
            align:"center",
            dataIndex: 'sportsName',
          },
          {
            title: '预定价格',
            align:"center",
            dataIndex: 'fieldPrice'
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
          list: "/businessinfo/field/list",
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
      getAvatarView: function (avatar) {
        return getFileAccessHttpUrl(avatar)
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