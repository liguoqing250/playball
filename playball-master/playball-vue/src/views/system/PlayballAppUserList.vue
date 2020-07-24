<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="用户名">
              <a-input placeholder="请输入用户名" v-model="queryParam.uName"></a-input>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="昵称">
              <a-input placeholder="请输入昵称" v-model="queryParam.uNickname"></a-input>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="手机号">
              <a-input placeholder="请输入手机号" v-model="queryParam.uPhonenumber"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('会员管理')">导出</a-button>
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
        rowKey="uId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="avatarslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="getAvatarView(record.uHeadimage)" icon="user"/>
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

    <!-- 表单区域 -->
    <playballAppUser-modal ref="modalForm" @ok="modalFormOk"></playballAppUser-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import PlayballAppUserModal from './modules/PlayballAppUserModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { httpAction,getAction,getFileAccessHttpUrl } from '@/api/manage'

  export default {
    name: "PlayballAppUserList",
    mixins:[JeecgListMixin],
    components: {
      PlayballAppUserModal
    },
    data () {
      return {
        description: '会员管理管理页面',
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
            title: '头像',
            align:"center",
            dataIndex: 'uHeadimage',
            scopedSlots: {customRender: "avatarslot"}
          },
		   {
            title: '用户名',
            align:"center",
            dataIndex: 'uName'
           },
		   {
            title: '昵称',
            align:"center",
            dataIndex: 'uNickname'
           },
		   {
            title: '手机号',
            align:"center",
            dataIndex: 'uPhonenumber'
           },
		   {
            title: '密码',
            align:"center",
            dataIndex: 'uPassworld'
           },
		   {
            title: '性别 1男/ 2女',
            align:"center",
            dataIndex: 'uSex',
            customRender: (text, record, index) => {
              let res = ''
              if(text=='1'){
                res='男'
              }else if(text=='2'){
                res='女'
              }
              return res
            }
           },
		   {
            title: '年龄',
            align:"center",
            dataIndex: 'uAge'
           },
		   {
            title: '身高',
            align:"center",
            dataIndex: 'uHeight',
             customRender: (text, record, index) => {
               return text+'cm'
             }
           },
		   {
            title: '体重',
            align:"center",
            dataIndex: 'uWeight',
             customRender: (text, record, index) => {
               return text+'kg'
             }
           },

		   {
            title: '注册渠道1 手机 2QQ   3微信',
            align:"center",
            dataIndex: 'uRegisterway',
             customRender: (text, record, index) => {
               let res = ''
               if(text=='1'){
                 res='手机'
               }else if(text=='2'){
                 res='QQ'
               }else if(text == '3'){
                 res = '微信'
               }
               return res
             }

           },
		   {
            title: 'QQ--openid',
            align:"center",
            dataIndex: 'wxOpenid'
           },
		   {
            title: '微信openid',
            align:"center",
            dataIndex: 'qqOpenid'
           },
		   {
            title: '支付宝账号',
            align:"center",
            dataIndex: 'uAlipayid'
           },
		   {
            title: '身份证号',
            align:"center",
            dataIndex: 'uIdcard'
           },
		   {
            title: '所属省--对应地区表主键',
            align:"center",
            dataIndex: 'uProvince'
           },
		   {
            title: '所属市--对应地区表主键',
            align:"center",
            dataIndex: 'uCity'
           },
		   {
            title: '所属区--对应地区表主键',
            align:"center",
            dataIndex: 'uDistrict'
           },
		   {
            title: '详细地址',
            align:"center",
            dataIndex: 'uAddress'
           },
		   {
            title: '最后登录时间',
            align:"center",
            dataIndex: 'uLastloginTime'
           },
		   {
            title: '个人简介',
            align:"center",
            dataIndex: 'uProfile'
           },
		   {
            title: '邀请人()',
            align:"center",
            dataIndex: 'uInvitedUser'
           },
		   {
            title: '创建时间',
            align:"center",
            dataIndex: 'createtime'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/playball/playballAppUser/list",
          delete: "/playball/playballAppUser/delete",
          deleteBatch: "/playball/playballAppUser/deleteBatch",
          exportXlsUrl: "playball/playballAppUser/exportXls",
          importExcelUrl: "playball/playballAppUser/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      getAvatarView: function (avatar) {
        return getFileAccessHttpUrl(avatar)
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>