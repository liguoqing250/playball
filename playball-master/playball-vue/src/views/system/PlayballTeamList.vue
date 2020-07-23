<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="球队名称">
              <a-input placeholder="请输入球队名称" v-model="queryParam.tName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="球队类别">
              <a-select placeholder="请选择球队类别"  v-model="queryParam.stId">
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
      <!--<a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
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
        rowKey="teamId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="avatarslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="getAvatarView(record.tImage)" icon="user"/>
          </div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.teamId)">
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
  import Area from '@/components/_util/Area'
  import PlayballTeamModal from './modules/PlayballTeamModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getSportsTypeList } from '@/api/api'
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'

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
            title: '队名',
            align:"center",
            dataIndex: 'tName'
           },
		   {
            title: '队徽',
            align:"center",
            dataIndex: '',
            scopedSlots: {customRender: "avatarslot"}
           },
		   {
            title: '所属单位名',
            align:"center",
            dataIndex: 'tUnitname'
           },
		   {
            title: '球队类型(关联运动类型表)',
            align:"center",
            dataIndex: 'stId',
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
            title: '球队介绍',
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
            dataIndex: 'userName'
           },
		   {
            title: '地址',
            align:"center",
            dataIndex: 'tDistrict',
            customRender: (text, record, index) => {
              let res =''
              if(text != null){
                res = this.areaData.getText(text.toString())
              }
              return res
            }
           },
		   {
            title: '建队时间',
            align:"center",
            dataIndex: 'tCreateteamtime'
           },
		   {
            title: '队长姓名',
            align:"center",
            dataIndex: 'captainName'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],

        sportsTypeList:{},
        areaData:'',
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
    created() {
      this.initAreaData();
    },
    methods: {
      initDictConfig() {
        getSportsTypeList('').then((res)=>{
          if(res.success){
            this.sportsTypeList = res.result.records;
          }
        })
      },

      initAreaData(){
        if(!this.areaData){
          this.areaData = new Area();
        }
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